package com.tcs.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

@Entity
@Table(name ="tbl_usuarios", schema = "public")
@Builder
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class TblUsuario{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
	
	@Column(name="contrasenia")
	private String contrasenia;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_persona", referencedColumnName = "id")
    private TblPersona tblPersona;
    
    @Column(name="estado")
	private Boolean estado;
	
	@Column(name="usuario_creacion")
	private String usuarioCreacion;
	
	@Column(name="fecha_creacion")
	private Timestamp fechaCreacion;
	
	@Column(name="usuario_modificacion")
	private String usuarioModificacion;
	
	@Column(name="fecha_modificacion")
	private Timestamp fechaModificacion;
	
	
}
