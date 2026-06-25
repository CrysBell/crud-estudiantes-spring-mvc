package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.TelefonoDao;
import com.example.entities.Estudiante;
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
		// TODO Auto-generated method stub
		return telefonoDao.save(telefono);
	}

	@Override
	public boolean existsByEstudiante(Estudiante estudiante) {
		// TODO Auto-generated method stub
		return telefonoDao.existsByEstudiante(estudiante);
	}

	@Override
	@Transactional
	public void deleteByEstudiante(Estudiante estudiante) {
		// TODO Auto-generated method stub
		telefonoDao.deleteByEstudiante(estudiante);
	}

	@Override
	public List<Telefono> findByEstudiante(Estudiante estudiante) {
		// TODO Auto-generated method stub
		return telefonoDao.findByEstudiante(estudiante);
	}

}
