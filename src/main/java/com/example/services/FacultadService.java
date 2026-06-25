package com.example.services;

import java.util.List;

import com.example.entities.Facultad;

public interface FacultadService {
    
    Facultad saveFacultad(Facultad facultad);
    List<Facultad> getAllFacultades();
    
}
