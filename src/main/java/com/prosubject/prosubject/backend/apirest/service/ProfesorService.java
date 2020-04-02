package com.prosubject.prosubject.backend.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prosubject.prosubject.backend.apirest.model.Administrador;
import com.prosubject.prosubject.backend.apirest.model.Profesor;
import com.prosubject.prosubject.backend.apirest.model.ValidacionExpediente;
import com.prosubject.prosubject.backend.apirest.repository.ProfesorRepository;

@Service
public class ProfesorService {

	
	@Autowired
	private ProfesorRepository profesorRepository;
	
	public List<Profesor> findAll() {
		return this.profesorRepository.findAll();
	}
	
	public Profesor findOne(final Long profesorId) {
		return this.profesorRepository.findById(profesorId).orElse(null);
	}
	
	public Profesor findByUserAccount(final Long userAccountId) {
		return this.profesorRepository.findProfesorByUserAccountId(userAccountId);
	}
	
	public Profesor findByUsername(final String username) {
		return this.profesorRepository.findProfesorByUsername(username);
	}

	
	public Profesor save(final Profesor p) { 
		return this.profesorRepository.save(p);	
	}
	
	public Profesor edit(Long id, Profesor profesor) {

		Profesor profe = findOne(id);
		
		if(profe.getExpendiente().getId()!=profesor.getExpendiente().getId()) {
			profe.setExpedienteValidado(ValidacionExpediente.PENDIENTE);
			profe.setExpendiente(profesor.getExpendiente());
		}

		profe.setApellido1(profesor.getApellido1());
		profe.setApellido2(profesor.getApellido2());
		profe.setDni(profesor.getDni());
		profe.setEmail(profesor.getEmail());
		profe.setNombre(profesor.getNombre());
		profe.setTelefono(profesor.getTelefono());
		profe.setTarifaPremium(profesor.getTarifaPremium());
		profe.getUserAccount().setUsername((profesor.getUserAccount().getUsername()));
		profe.getUserAccount().setPassword((profesor.getUserAccount().getPassword()));
		
		Profesor profeEditado = save(profe);

		return profeEditado;
	}
	
	
	
	public List<String> emailsProfesor() {
		return this.profesorRepository.emailsProfesor();
	}
	
	public List<String> DNIsProfesor() {
		return this.profesorRepository.DNIsProfesor();
	}
	
	public List<Profesor> profesoresExpedientePendiete() {
		return this.profesorRepository.profesoresExpedientePendiete();
	}
	
	public List<Profesor> profesoresTarifaPremium(){
		return this.profesorRepository.profesoresTarifaPremium();
	}

}
