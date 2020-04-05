package com.prosubject.prosubject.backend.apirest.model;

import java.io.Serializable;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;


import org.hibernate.validator.constraints.Range;

@Entity(name = "espacios")
public class Espacio implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	
	
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "asignatura_id")
	private Asignatura asignatura;
	
	
	@Valid
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "foro_id")
	private Foro foro;
	
	
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "profesor_id")
	private Profesor profesor;
	
	
	@Column(nullable = false)
	@Min(0)
	private Double precio;
	
	@Range(min = 0, max = 1)
	private int draftMode;
	
	
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	public Foro getForo() {
		return foro;
	}

	public void setForo(Foro foro) {
		this.foro = foro;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double prec) {
		precio = prec;
	}

	public int getDraftMode() {
		return draftMode;
	}

	public void setDraftMode(int draftMode) {
		this.draftMode = draftMode;
	}
	
	


	
	

	

	
	
	
	
	
	
	
}
