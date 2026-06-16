package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Estudiante;



public interface EstudianteDao extends JpaRepository <Estudiante, Integer>{
List<Estudiante> findByNombre(String nombre);
List<Estudiante> findByPrimerApellido(String primerApellido);
List<Estudiante> findBySegundoApellido(String segundoApellido);

}
