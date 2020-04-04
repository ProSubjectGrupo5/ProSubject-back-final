package com.prosubject.prosubject.backend.apirest.model;



import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity(name = "alumnos")
public class Alumno implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "useraccount_id",nullable = false, unique=true)
	private UserAccount useraccount;
	
	@NotBlank
	@Column(nullable = false)
	private String nombre;
	
	@NotBlank
	@Column(nullable = false)
	private String apellido1;
	
	@NotBlank
	@Column(nullable = false)
	private String apellido2;
	
	@NotBlank
	@Column(nullable = false)
	private String universidad;
	
	@NotBlank
	@Column(nullable = false)
	private String facultad;
	
	@NotBlank
	@Column(nullable = false)
	private String grado;
	
	@NotBlank
	@Pattern(regexp = "^(\\d{8})([A-Z])$",message = "Debe tener 8 numeros y 1 letra")
	@Column(unique = true , nullable = false)
	private String dni;
	
	@NotBlank
	@NotNull
	@Email
	@Column(unique = true ,  nullable = false)
	private String email;
	
	@Pattern(regexp="^\\d{9}|^$",message = "Debe introducir un numero de telefono correcto")
	private String telefono;
	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public UserAccount getUserAccount() {
		return useraccount;
	}



	public void setUserAccount(UserAccount userAccount) {
		this.useraccount = userAccount;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}





	public String getApellido1() {
		return apellido1;
	}



	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}



	public String getApellido2() {
		return apellido2;
	}



	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}



	public String getDni() {
		return dni;
	}



	public void setDni(String dni) {
		this.dni = dni;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getTelefono() {
		return telefono;
	}



	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	
	public String getUniversidad() {
		return universidad;
	}



	public void setUniversidad(String universidad) {
		this.universidad = universidad;
	}



	public String getFacultad() {
		return facultad;
	}



	public void setFacultad(String facultad) {
		this.facultad = facultad;
	}



	public String getGrado() {
		return grado;
	}



	public void setGrado(String grado) {
		this.grado = grado;
	}







	
	

}
