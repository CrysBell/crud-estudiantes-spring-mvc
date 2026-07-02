package com.example.services;

import java.util.List;

import com.example.entities.Profesor;

public interface ProfesorService {

    List<Profesor> getAllProfesores();
    
    //Metodo para obtener a un Profesor por su ID
    Profesor getProfesorById(int id);

    //Metodo para persistir/guardar un Profesor
    Profesor saveProfesor(Profesor profesor);

    //Metodo para eliminar un Profesor
    void deleteProfesor(int id);

    //Metodo que elimina un profesor recibiendo el objeto Profesor
    void deleteProfesor(Profesor profesor);

    //Metodo para actualizar un profesor
    Profesor updateProfesor(Profesor profesor);

}
