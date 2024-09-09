package com.tcs.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class Utileria {

	public Timestamp fechaActual() {
		return Timestamp.from(Instant.now());
	}
	
	public HashMap<String, Object> convertirStringAMap(String elementos) {
		try {
			ObjectMapper om = new ObjectMapper();
			return om.readValue(elementos, new TypeReference<HashMap<String, Object>>() {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public HashMap<String, Object> salidaDatos(Boolean success, String mensaje, Object datos){
		HashMap<String, Object> salida= new HashMap<>();
		salida.put("success", success);
		salida.put("mensaje", mensaje);
		if(Objects.nonNull(datos))
			salida.put("datos", datos);
		return salida;
	}
}
