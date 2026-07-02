package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.TelefonoDao;
import com.example.entities.Estudiante;
import com.example.entities.Profesor;
import com.example.entities.Telefono;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TelefonoServiceImpl implements TelefonoService{
	private final TelefonoDao telefonoDao;

	@Override
	public List<Telefono> getAllTelefonos() {
		// TODO Auto-generated method stub
		return telefonoDao.findAll();
	}

	@Override
	public Telefono saveTelefono(Telefono telefono) {
	
		return telefonoDao.save(telefono);
	}

	@Override
	public boolean existsByEstudiante(Estudiante estudiante) {
	
		return telefonoDao.existsByEstudiante(estudiante);
	}

	@Override
	@Transactional
	public void deleteByEstudiante(Estudiante estudiante) {

		telefonoDao.deleteByEstudiante(estudiante);
	}

	@Override
	public List<Telefono> findByEstudiante(Estudiante estudiante) {
	
		return telefonoDao.findByEstudiante(estudiante);
	}



	
	@Override
	public boolean existsByProfesor(Profesor profesor) {

		return telefonoDao.existsByProfesor(profesor);
	}

	@Override
	@Transactional
	public void deleteByProfesor(Profesor profesor) {

		telefonoDao.deleteByProfesor(profesor);
	}

	@Override
	public List<Telefono> findByProfesor(Profesor profesor) {

		return telefonoDao.findByProfesor(profesor);
	}

}
