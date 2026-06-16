package com.example.entities;

import com.example.model.Genero;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="estudiantes")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Estudiante {
       @Id
       @GeneratedValue(strategy=GenerationType.IDENTITY)
       private int id;


       private String nombre;
       private String primerApellido;
       private String segundoApellido;

       @Enumerated(EnumType.STRING)
       private Genero genero;


}
