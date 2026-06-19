package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.CorreoDao;
import com.example.entities.Correo;
import com.example.entities.Estudiante;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class CorreoServiceImpl implements CorreoService{
	private final CorreoDao correoDao;
	
	@Override
	public Correo saveCorreo(Correo correo) {
		// TODO Auto-generated method stub
		return correoDao.save(correo);
	}

	@Override
	public List<Correo> getAllCorreos() {
		// TODO Auto-generated method stub
		return correoDao.findAll();
	}

	@Override
	public boolean existsByEstudiante(Estudiante estudiante) {
		// TODO Auto-generated method stub
		return correoDao.existsByEstudiante(estudiante);
	}

	@Override
	public void deleteByEstudiante(Estudiante estudiante) {
		// TODO Auto-generated method stub
		correoDao.deleteByEstudiante(estudiante);
	}

	@Override
	public List<Correo> findByEstudiante(Estudiante estudiante) {
		// TODO Auto-generated method stub
		return correoDao.findByEstudiante(estudiante);
	}

}
