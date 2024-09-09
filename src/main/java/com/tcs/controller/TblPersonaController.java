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

import com.tcs.entity.TblPersona;
import com.tcs.services.TblPersonaService;


@RestController
@RequestMapping("/api/personas/")
public class TblPersonaController {

	@Autowired
	private TblPersonaService tblPersonaService;
	
	@PostMapping
	public HashMap<String, Object> creaPersona(@RequestBody TblPersona body) throws RuntimeException, Exception{
		return tblPersonaService.creaPersona(body);
	}
	
	@GetMapping
	public HashMap<String, Object> obtienePersona(@RequestParam Long id){
		return tblPersonaService.obtienePersona(id);
	}
	
	@PutMapping
	public HashMap<String, Object> actualizaPersona(@RequestBody TblPersona body) throws RuntimeException, Exception{
		return tblPersonaService.actualizaPersona(body);
	}
	
	@DeleteMapping
	public HashMap<String, Object> eliminaPersona(@RequestParam Long id) throws RuntimeException, Exception{
		return tblPersonaService.eliminaPersona(id);
	}
}
