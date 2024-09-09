package com.tcs.enums;

public enum EnumMensajes {
	NO_EXISTE("El elemento {0} no existe en la base de datos"),
	ELEMENTO_INACTIVO("El elemento {0} se encuentra inactivo"),
	API_INCORRECTA("El api que invoco es incorrecto. Esta API sirve para {0}"),
	CAMPO_REQUERIDO("El campo {0} es requerido para {1}"),
	ELEMENTO_REPETIDO("Ya existe una lista de precio con el rango de fechas indicados"),
	REGISTRO_GUARDADO("Se guardo correctamente el registro"),
	REGISTRO_MODIFICADO("Se modifico correctamente el registro"),
	REGISTRO_ELIMINADO("Se elimino correctamente el registro"),
	OK("OK")
	;

	/**
	 * Constructor
	 * @param descripcion
	 */
	private EnumMensajes(final String descripcion) {
		this.descripcion = descripcion;
	}

	private String descripcion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	

	
}
