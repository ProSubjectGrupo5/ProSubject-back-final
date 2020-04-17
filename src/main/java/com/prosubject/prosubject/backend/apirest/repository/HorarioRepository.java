package com.prosubject.prosubject.backend.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.prosubject.prosubject.backend.apirest.model.Horario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long>  {
	
	@Query("select distinct(h) from horario h where h.capacidad > (select count(r.alumno.id) from rangos r where r.horario.id = h.id)  AND h.fechaInicio > CURRENT_DATE AND h.espacio.id=?1")
	List<Horario> horariosDisponiblesDeUnEspacio(Long id);
	
	@Query("select h from horario h where h.espacio.id=?1")
	List<Horario> horariosDeUnEspacio(Long id);
	
	
	@Query("select r.horario from rangos r where r.alumno.id=?1")
	List<Horario> horariosDeAlumno(Long alumnoId);
	
	@Query("select h from horario h where h.espacio.profesor.id = ?1")
	List<Horario> horariosDeProfesor(Long profesorId);
	
	@Query("select h from horario h where h.espacio.profesor.id = ?1 AND h.espacio.draftMode = 0")
	List<Horario> horariosNoEditablesDeUnProfesor(Long profesorId);
	

	
	

	
	

}
