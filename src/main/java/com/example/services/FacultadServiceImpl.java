package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.FacultadDao;
import com.example.entities.Facultad;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FacultadServiceImpl implements FacultadService{
    	
	private final FacultadDao facultadDao;

	@Override
	public Facultad saveFacultad(Facultad facultad) {
		// TODO Auto-generated method stub
		return facultadDao.save(facultad);
	}

	@Override
	public List<Facultad> getAllFacultades() {
		// TODO Auto-generated method stub
		return facultadDao.findAll();
	}



}
