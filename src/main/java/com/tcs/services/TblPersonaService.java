package com.tcs.services;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.entity.TblPersona;
import com.tcs.enums.EnumMensajes;
import com.tcs.repository.TblPersonaRepository;
import com.tcs.util.Utileria;

@Service
public class TblPersonaService {

	
	@Autowired
	private TblPersonaRepository tblPersonaRepository;
	
	@Autowired
	private Utileria util;
	
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
	public HashMap<String, Object> creaPersona(TblPersona body)throws RuntimeException, Exception{
		HashMap<String, Object> salida=new HashMap<>();
		try {
			if(Objects.nonNull(body.getId())) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.API_INCORRECTA.getDescripcion().replace("{0}", "guardar"), null);
				return salida;
			}
			body.setFechaCreacion(util.fechaActual());
			body.setFechaModificacion(null);
			body.setUsuarioModificacion(null);
			body.setEstado(Boolean.TRUE);
			tblPersonaRepository.save(body);
			salida=util.salidaDatos(Boolean.TRUE, EnumMensajes.REGISTRO_GUARDADO.getDescripcion(), null);
		} catch (Exception e) {
			salida=util.salidaDatos(Boolean.FALSE, e.getMessage(), null);
			// TODO: handle exception
		}
		return salida;
	}

	public HashMap<String, Object> obtienePersona(Long id){
		HashMap<String, Object> salida=new HashMap<>();
		try {
			if(Objects.isNull(id)) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.CAMPO_REQUERIDO.getDescripcion().replace("{0}", "id").replace("{1}", "obtener info"), null);
				return salida;
			}
			Optional<TblPersona> clienteOpt =tblPersonaRepository.findByIdAndEstadoTrue(id);
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
	
	public TblPersona seteaDataUsuario(TblPersona regActual, TblPersona body) {
		regActual.setDireccion(body.getDireccion());
		regActual.setEdad(body.getEdad());
		regActual.setGenero(body.getGenero());
		regActual.setIdentificacion(body.getIdentificacion());
		regActual.setNombre(body.getNombre());
		regActual.setTelefono(body.getTelefono());
		regActual.setFechaModificacion(util.fechaActual());
		regActual.setUsuarioModificacion(body.getUsuarioModificacion());
	
		return regActual;
	}
	
	@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	public HashMap<String, Object> actualizaPersona(TblPersona body)throws RuntimeException, Exception{
		HashMap<String, Object> salida=new HashMap<>();
		try {
			if(Objects.isNull(body.getId())) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.CAMPO_REQUERIDO.getDescripcion().replace("{0}", "id").replace("{1}", "obtener info"), null);
				return salida;
			}
			Optional<TblPersona> clienteOpt =tblPersonaRepository.findById(body.getId());
			if (clienteOpt.isPresent()) { 
				TblPersona userActual=clienteOpt.get();
				if(userActual.getEstado()) {
					tblPersonaRepository.save(seteaDataUsuario(userActual,body));
					salida=util.salidaDatos(Boolean.TRUE, EnumMensajes.REGISTRO_MODIFICADO.getDescripcion(), null);
				}else if(!userActual.getEstado() && body.getEstado()) {
					tblPersonaRepository.save(seteaDataUsuario(userActual,body));
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
	public HashMap<String, Object> eliminaPersona(Long id)throws RuntimeException, Exception{
		HashMap<String, Object> salida=new HashMap<>();
		try {
			if(Objects.isNull(id)) {
				salida=util.salidaDatos(Boolean.FALSE, EnumMensajes.CAMPO_REQUERIDO.getDescripcion().replace("{0}", "id").replace("{1}", "eliminar el cliente"), null);
				return salida;
			}
			
			Optional<TblPersona> clienteOpt =tblPersonaRepository.findByIdAndEstadoTrue(id);
			if (clienteOpt.isPresent()) {
				tblPersonaRepository.delete(clienteOpt.get());
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
