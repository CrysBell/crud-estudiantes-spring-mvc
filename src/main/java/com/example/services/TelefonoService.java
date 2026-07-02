package com.example.services;

import java.util.List;

import com.example.entities.Estudiante;
import com.example.entities.Profesor;
import com.example.entities.Telefono;

public interface TelefonoService {
 
	List<Telefono> getAllTelefonos();
	Telefono saveTelefono(Telefono telefono);
	
	boolean existsByEstudiante(Estudiante estudiante);
	void deleteByEstudiante(Estudiante estudiante);
	List<Telefono> findByEstudiante(Estudiante estudiante);

	boolean existsByProfesor(Profesor profesor);
	void deleteByProfesor(Profesor profesor);
	List<Telefono> findByProfesor(Profesor profesor);

}