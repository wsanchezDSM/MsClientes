package com.tcs.services;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tcs.entity.TblUsuario;
import com.tcs.enums.EnumMensajes;
import com.tcs.repository.TblPersonaRepository;
import com.tcs.repository.TblUsuarioRepository;
import com.tcs.util.Utileria;

@Service
public class TblUsuarioService {

	@Autowired
	private TblUsuarioRepository tblUsuarioRepository;
	
	@Autowired
	private TblPersonaRepository tblPersonaRepository;
	
	@Autowired
	private Utileria util;
	
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public HashMap<String, Object> creaCliente(TblUsuario body)throws RuntimeException, Exception{
		HashMap<String, Object> salida=new HashMap<>();
		try {
			if(Objects.nonNull(body.getId())) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.API_INCORRECTA.getDescripcion().replace("{0}", "guardar"), null);
				return salida;
			}
			
			if(Objects.isNull(body.getContrasenia())) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.CAMPO_REQUERIDO.getDescripcion().replace("{0}", "contraseña").replace("{1}", "guardar"), null);
				return salida;
			}
			
			if(Objects.isNull(body.getTblPersona())) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.CAMPO_REQUERIDO.getDescripcion().replace("{0}", "persona").replace("{1}", "guardar"), null);
				return salida;
			}
			
			tblPersonaRepository.findByIdAndEstadoTrue(body.getTblPersona().getId()).ifPresent(y->{
				body.setTblPersona(y);
			});
			
			body.setFechaCreacion(util.fechaActual());
			body.setFechaModificacion(null);
			body.setUsuarioModificacion(null);
			body.setEstado(Boolean.TRUE);
			tblUsuarioRepository.save(body);
			salida=util.salidaDatos(Boolean.TRUE, EnumMensajes.REGISTRO_GUARDADO.getDescripcion(), null);
		} catch (Exception e) {
			salida=util.salidaDatos(Boolean.FALSE, e.getMessage(), null);
			// TODO: handle exception
		}
		return salida;
	}

	public HashMap<String, Object> obtieneCliente(Long id){
		HashMap<String, Object> salida=new HashMap<>();
		try {
			if(Objects.isNull(id)) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.CAMPO_REQUERIDO.getDescripcion().replace("{0}", "id").replace("{1}", "obtener info"), null);
				return salida;
			}
			Optional<TblUsuario> clienteOpt =tblUsuarioRepository.findByIdAndEstadoTrue(id);
			if (clienteOpt.isPresent()) 
		       salida = util.salidaDatos(Boolean.TRUE, 
		    		   EnumMensajes.OK.getDescripcion(), 
		    		   clienteOpt.get());
		    else 
		       salida = util.salidaDatos(
		                Boolean.FALSE, 
		                EnumMensajes.ELEMENTO_INACTIVO.getDescripcion().replace("{0}", id.toString()), 
		                null
		            );
		} catch (Exception e) {
			salida=util.salidaDatos(Boolean.FALSE, e.getMessage(), null);
			// TODO: handle exception
		}
		return salida;

	}
	
	public TblUsuario seteaDataUsuario(TblUsuario regActual, TblUsuario body) {
		regActual.setContrasenia(body.getContrasenia());
		regActual.setFechaModificacion(util.fechaActual());
		regActual.setUsuarioModificacion(body.getUsuarioModificacion());
		if(Objects.nonNull(body.getTblPersona())) {
			regActual.setTblPersona(tblPersonaRepository.findByIdAndEstadoTrue(body.getTblPersona().getId()).get());
		}
		return regActual;
	}
	
	@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	public HashMap<String, Object> actualizaCliente(TblUsuario body)throws RuntimeException, Exception{
		HashMap<String, Object> salida=new HashMap<>();
		try {
			if(Objects.isNull(body.getId())) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.CAMPO_REQUERIDO.getDescripcion().replace("{0}", "id").replace("{1}", "obtener info"), null);
				return salida;
			}
			Optional<TblUsuario> clienteOpt =tblUsuarioRepository.findById(body.getId());
			if (clienteOpt.isPresent()) { 
				TblUsuario userActual=clienteOpt.get();
				if(userActual.getEstado()) {
					tblUsuarioRepository.save(seteaDataUsuario(userActual,body));
					salida=util.salidaDatos(Boolean.TRUE, EnumMensajes.REGISTRO_MODIFICADO.getDescripcion(), null);
				}else if(!userActual.getEstado() && body.getEstado()) {
					tblUsuarioRepository.save(seteaDataUsuario(userActual,body));
					salida=util.salidaDatos(Boolean.TRUE, EnumMensajes.REGISTRO_MODIFICADO.getDescripcion(), null);
				}else
					salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.ELEMENTO_INACTIVO.getDescripcion().replace("{0}", body.getId().toString()), null);

			}else 
		       salida = util.salidaDatos(
		                Boolean.FALSE, 
		                EnumMensajes.NO_EXISTE.getDescripcion().replace("{0}", body.getId().toString()), 
		                null
		            );
			
		} catch (Exception e) {
			salida=util.salidaDatos(Boolean.FALSE, e.getMessage(), null);
			// TODO: handle exception
		}
		return salida;
	}
	
	@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	public HashMap<String, Object> eliminaClientes(Long id)throws RuntimeException, Exception{
		HashMap<String, Object> salida=new HashMap<>();
		try {
			if(Objects.isNull(id)) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.CAMPO_REQUERIDO.getDescripcion().replace("{0}", "id").replace("{1}", "eliminar el cliente"), null);
				return salida;
			}
			
			Optional<TblUsuario> clienteOpt =tblUsuarioRepository.findByIdAndEstadoTrue(id);
			if (clienteOpt.isPresent()) {
				tblUsuarioRepository.delete(clienteOpt.get());
				salida=util.salidaDatos(Boolean.TRUE, EnumMensajes.REGISTRO_ELIMINADO.getDescripcion(), null);
			}else{
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.ELEMENTO_INACTIVO.getDescripcion().replace("{0}",id.toString()), null);
			}
		} catch (Exception e) {
			salida= util.salidaDatos(Boolean.FALSE, e.getMessage(), null);
			// TODO: handle exception
		}
		return salida;

	}
	
}
