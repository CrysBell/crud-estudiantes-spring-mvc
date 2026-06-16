package com.example.services;

import java.util.List;

import com.example.entities.Estudiante;

public interface EstudianteService {
//Definir los metodos a implementa en EstudianteServicesImpl


//Metodo para obtener todos los estudiantes
List<Estudiante> getAllEstudiantes();


//Metodo para obtener a un estudiante por su ID
Estudiante getEstudianteById(int id);

//Metodo para persistir/guardar un estudiante
Estudiante saveEstudiante(Estudiante estudiante);

//Metodo para eliminar un estudiante
void deleteEstudiante(int id);

//Metodo que elimina un estudiante recibiendo el objeto estudiante
void deleteEstudiante(Estudiante estudiante);

//Metodo para actualizar un estudiante
Estudiante updateEstudiante(Estudiante estudiante);

}
