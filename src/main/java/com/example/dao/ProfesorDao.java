package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Profesor;

public interface ProfesorDao  extends JpaRepository <Profesor, Integer>{

}
