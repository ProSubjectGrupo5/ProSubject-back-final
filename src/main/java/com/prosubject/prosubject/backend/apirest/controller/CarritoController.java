package com.prosubject.prosubject.backend.apirest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prosubject.prosubject.backend.apirest.model.Alumno;
import com.prosubject.prosubject.backend.apirest.model.Carrito;
import com.prosubject.prosubject.backend.apirest.model.Horario;
import com.prosubject.prosubject.backend.apirest.service.AlumnoService;
import com.prosubject.prosubject.backend.apirest.service.CarritoService;
import com.prosubject.prosubject.backend.apirest.service.HorarioService;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = {"http://localhost:4200", "https://prosubject-final.herokuapp.com"})
public class CarritoController {

	@Autowired
	private CarritoService carritoService;
	
	@Autowired
	private HorarioService horarioService;
	
	@Autowired
	private AlumnoService alumnoService;
	
	@GetMapping("")
	public List<Carrito> findAll(){
		return this.carritoService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findOne(@PathVariable Long id) {
		Carrito carrito = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			carrito = this.carritoService.findOne(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(carrito == null) {
			response.put("mensaje",	 "El carrito con ID: ".concat(id.toString()).concat(" no existe"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<Carrito>(carrito, HttpStatus.OK);
	}
	
//	@GetMapping("/contadorHorarios")
//	public ResponseEntity<?> countHorario(@RequestParam Long id) {
//		Alumno alum = null;
//		Map<String, Object> response = new HashMap<String, Object>();
//		
//		try {
//			alum = this.carritoService.contadorHorarios(id);
//		}catch(DataAccessException e) {
//			response.put("mensaje", "Error al realizar la consulta en la base de datos");
//			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
//			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		
//		return new ResponseEntity<Alumno>(alum, HttpStatus.OK);
//	}
	
	
	@GetMapping("/precioMensual/{id}")
	public ResponseEntity<?> precioMensualHorarios(@PathVariable Long id){
		Carrito carrito = null;
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			carrito = this.carritoService.precioMensualHorarios(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(carrito == null) {
			response.put("mensaje",	 "El carrito con ID: ".concat(id.toString()).concat(" no existe"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		return new ResponseEntity<Carrito>(carrito, HttpStatus.OK);
	}
	
	@PostMapping("/addHorario")
	 public ResponseEntity<?> addHorarioCarrito(@RequestParam(value = "carritoId") Long 
	    carritoId, @RequestParam Long horarioId, @RequestParam Long alumId) throws Exception {
	       Carrito carro = this.carritoService.findOne(carritoId);
	       Horario horario = this.horarioService.findOne(horarioId);
	       Alumno alum = this.alumnoService.findOne(alumId);
	       List<Horario> listaHorariosAlumno = this.horarioService.horariosDeAlumno(alumId);
	       Map<String, Object> response = new HashMap<String, Object>();
	       Carrito carrito = new Carrito();
	       if(carro == null) {
				response.put("mensaje",	 "El carrito con ID: ".concat(carritoId.toString()).concat(" no existe"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
			}
	       
	       if(listaHorariosAlumno.contains(horario)) {
	    	   response.put("mensaje",	 "El alumno ya tiene comprado este horario.");
	    	   return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
	       }
	       
	       if(carro.getHorario().contains(horario)) {
				response.put("mensaje",	 "El carrito ya tiene almacenado este horario.");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
			}
	       
	       if(horario == null) {
				response.put("mensaje",	 "El horario con ID: ".concat(horarioId.toString()).concat(" no existe"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
			}
	       
	       try {
				carrito = this.carritoService.addHorario(carro, horario);
				Integer contador = alum.getContadorDescuento();
				alum.setContadorDescuento(contador + 1);
				this.alumnoService.save(alum);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	       
	       return new ResponseEntity<Carrito>(carrito, HttpStatus.OK);
	 }
	
	
	@PostMapping("/borrarHorario")
	 public ResponseEntity<?> deleteHorarioCarrito(@RequestParam(value = "carritoId") Long 
	    carritoId, @RequestParam Long horarioId, @RequestParam Long alumnoId) {
		   Carrito carro = this.carritoService.findOne(carritoId);
		   Alumno alum = this.alumnoService.findOne(alumnoId);
	       Horario horario = this.horarioService.findOne(horarioId);
	       Map<String, Object> response = new HashMap<String, Object>();
	       Carrito carrito = new Carrito();
	       if(carro == null) {
				response.put("mensaje",	 "El carrito con ID: ".concat(carritoId.toString()).concat(" no existe"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
			}
	       
	       if(horario == null) {
				response.put("mensaje",	 "El horario con ID: ".concat(horarioId.toString()).concat(" no existe"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND); 
			}
	       
	       try {
				carrito = this.carritoService.removeHorario(carro, horario);
				Integer contador = alum.getContadorDescuento();
				alum.setContadorDescuento(contador - 1);
				this.alumnoService.save(alum);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	       
	       return new ResponseEntity<Carrito>(carrito, HttpStatus.OK);
	 }
	
	
	
}
