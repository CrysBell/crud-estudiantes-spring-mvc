package com.example.services;

import java.util.List;

import com.example.entities.Correo;
import com.example.entities.Estudiante;
import com.example.entities.Profesor;


public interface CorreoService {
    Correo saveCorreo(Correo correo);
    List <Correo> getAllCorreos();
    boolean existsByEstudiante(Estudiante estudiante);
    void deleteByEstudiante(Estudiante estudiante);
    List <Correo> findByEstudiante(Estudiante estudiante);

    boolean existsByProfesor(Profesor profesor);
	void deleteByProfesor(Profesor profesor);
	List<Correo> findByProfesor(Profesor profesor);
}
