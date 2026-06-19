package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.EstudianteDao;
import com.example.entities.Estudiante;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteDao estudianteDao;

	@Override
	public List<Estudiante> getAllEstudiantes() {
		// TODO Auto-generated method stub
		return estudianteDao.findAll();
	}

	@Override
	public Estudiante getEstudianteById(int id) {
		// TODO Auto-generated method stub
		return estudianteDao.findById(id).orElseThrow(() -> 
		          new RuntimeException("Estudiante no encontrado con id: " + id));
	}

	@Override
	public Estudiante saveEstudiante(Estudiante estudiante) {
		// TODO Auto-generated method stub
		return estudianteDao.save(estudiante);
	}

	@Override
	public void deleteEstudiante(int id) {
		// TODO Auto-generated method stub
		estudianteDao.deleteById(id);
	}

	@Override
	public void deleteEstudiante(Estudiante estudiante) {
		// TODO Auto-generated method stub
		estudianteDao.delete(estudiante);
	}

	@Override
	public Estudiante updateEstudiante(Estudiante estudiante) {
		// TODO Auto-generated method stub
		return estudianteDao.save(estudiante);
	}
}
