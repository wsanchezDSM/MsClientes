package com.tcs.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.entity.TblUsuario;
import com.tcs.services.TblUsuarioService;



@RestController
@RequestMapping("/api/clientes/")
public class TblUsuarioController {

	@Autowired
	private TblUsuarioService tblUsuarioService;
	
	@PostMapping
	public HashMap<String, Object> creaCliente(@RequestBody TblUsuario body) throws RuntimeException, Exception{
		return tblUsuarioService.creaCliente(body);
	}
	
	@GetMapping
	public HashMap<String, Object> obtieneCliente(@RequestParam Long id){
		return tblUsuarioService.obtieneCliente(id);
	}
	
	@PutMapping
	public HashMap<String, Object> actualizaCliente(@RequestBody TblUsuario body) throws RuntimeException, Exception{
		return tblUsuarioService.actualizaCliente(body);
	}
	
	@DeleteMapping
	public HashMap<String, Object> eliminaClientes(@RequestParam Long id) throws RuntimeException, Exception{
		return tblUsuarioService.eliminaClientes(id);
	}
}
