package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.entities.Estudiante;
import com.example.entities.Profesor;
import com.example.entities.Telefono;

public interface TelefonoDao extends JpaRepository<Telefono, Integer>{
    boolean existsByEstudiante(Estudiante estudiante);
    void deleteByEstudiante(Estudiante estudiante);
    List<Telefono> findByEstudiante(Estudiante estudiante);

    boolean existsByProfesor(Profesor profesor);
    void deleteByProfesor(Profesor profesor);
    List<Telefono> findByProfesor(Profesor profesor);

}
