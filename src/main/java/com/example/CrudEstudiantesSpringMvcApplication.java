package com.example;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entities.Correo;
import com.example.entities.Estudiante;
import com.example.entities.Facultad;
import com.example.entities.Telefono;
import com.example.model.Genero;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class CrudEstudiantesSpringMvcApplication implements CommandLineRunner{

	private final EstudianteService estudianteService;
	private final FacultadService facultadService;


	public static void main(String[] args) {
		SpringApplication.run(CrudEstudiantesSpringMvcApplication.class, args);

	}


	@Override
	public void run(String... args) throws Exception {
// Crear Facultades
		
	Facultad facultad1 = Facultad.builder()
				.nombre("Ciencias de la Salud")
				.build();

	Facultad facultad2 = Facultad.builder()
				.nombre("Artes y humanidades")
				.build();

	//Persistir facultades en la base de datos
	facultadService.saveFacultad(facultad1);
	facultadService.saveFacultad(facultad2);


	

	Estudiante estudiante1 = Estudiante.builder()
			.nombre("Vaiana")
			.primerApellido("De Motunui")
			.segundoApellido("Princess")
			.genero(Genero.MUJER)
			.facultad(facultad1)
			.fechaMatriculacion(LocalDate.of(2022, 02, 22))
			.telefonos(
				Set.of(
					Telefono.builder().numero("789456").build()
				)
			)
			.emails(
				Set.of(
					Correo.builder().email("mawi@island.com").build()
				)
			)
			.build();
	
	estudiante1.getTelefonos().forEach(telefono -> telefono.setEstudiante(estudiante1));
	estudiante1.getEmails().forEach(correo -> correo.setEstudiante(estudiante1));
	
	estudianteService.saveEstudiante(estudiante1);
}

}
