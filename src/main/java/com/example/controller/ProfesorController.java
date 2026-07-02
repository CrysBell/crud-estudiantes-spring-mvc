package com.example.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.entities.Correo;

import com.example.entities.Profesor;
import com.example.entities.Telefono;
import com.example.services.CorreoService;
import com.example.services.FacultadService;
import com.example.services.ProfesorService;
import com.example.services.TelefonoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/profesores")
@RequiredArgsConstructor
public class ProfesorController {

    private static final Logger LOG = Logger.getLogger("ProfesorController");

    private final ProfesorService profesorService;
    private final FacultadService facultadService;
    private final CorreoService correoService;
	private final TelefonoService telefonoService;

    @GetMapping("/listar")
    public String listarProfesores(Model model) {

        model.addAttribute("profesores", 
            profesorService.getAllProfesores());

        // Mostrar la vista del archvo HTML
        return "listadoProfesores";
    }

    @GetMapping("/alta")
    public String mostrarFormularioAlta(Model model,
        @ModelAttribute Profesor profesor) {

        // Se necesitan los facultads desde la capa de servicios
        model.addAttribute("facultades", 
        facultadService.getAllFacultades());


        return "formularioAltaModificacionProf";
    }

        @PostMapping("/persistir")
    public String procesarFormularioAltaModificacion(
            @Valid
            @ModelAttribute Profesor profesor,
            BindingResult result,
            @RequestParam String numerosTelefono,
            @RequestParam String direccionesCorreo,
            Model model,
            @RequestParam(name = "file", required = false) MultipartFile file)
    {
            if (result.hasErrors()) {

                        model.addAttribute("facultades",
                                facultadService.getAllFacultades());

                        return "formularioAltaModificacion";
                    }
            if (file != null && !file.isEmpty()){

			Path rutaRelativa = Paths.get("src/main/resources/static/imagenes");

			String rutaAbsoluta = rutaRelativa.toFile().getAbsolutePath();

			Path rutaCompleta = Paths.get(rutaAbsoluta + "/" + file.getOriginalFilename());

			try {
				byte[] bytesFotoRecibida = file.getBytes();
				Files.write(rutaCompleta, bytesFotoRecibida);
				profesor.setFoto(file.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

        LOG.info("Objeto profesor recibido");
        LOG.info(profesor.toString());
        LOG.info("Numeros de telefono recibidos: " + numerosTelefono);
        LOG.info("Direcciones de correo recibidas: " + direccionesCorreo);

        if (!numerosTelefono.isEmpty() && !numerosTelefono.isBlank()) {

            String[] arrayNumerosTelefono = numerosTelefono.split(";");
            List<String> listadoNumeros = Arrays.asList(arrayNumerosTelefono);

            listadoNumeros.forEach(numero -> {
                profesor.getTelefonos().add(Telefono.builder().numero(numero).profesor(profesor).build());
            });


        }

        if (!direccionesCorreo.isEmpty() && !direccionesCorreo.isBlank()) {
            String[] arrayDirCorreos = direccionesCorreo.split(";");
            List<String> listadoCorreos = Arrays.asList(arrayDirCorreos);

            listadoCorreos.forEach(dirCorreo -> {
                profesor.getEmails().add(Correo.builder().email(dirCorreo).profesor(profesor).build());
            });
        }


        //Antes de persistir el empleado hay que eliminar los telefonos y los correos que tenga
		if (profesor.getId() != 0) {
            if (telefonoService.existsByProfesor(profesor))
		    telefonoService.deleteByProfesor(profesor);

		    if (correoService.existsByProfesor(profesor))
			correoService.deleteByProfesor(profesor);

        }
        profesorService.saveProfesor(profesor);
        
        return "redirect:/profesores/listar";

    }

//Método que muestra los detalles de un empleado cuyo id se recibe como parámetro
	@GetMapping("/detailsProf/{id}")
	public String mostrarDetalles (Model model, 
		@PathVariable(name = "id", required = true) int profesor_id){

	//recuperar el empleado cuyo id se recibe como parametro
	model.addAttribute("profesor", profesorService.getProfesorById(profesor_id));

		return "detailsProf";
	}


    @GetMapping("/update/{id}")
    public String updateProfesor(Model model, @PathVariable(name = "id", required = true) int idProfesor){
	
	Profesor profesor = profesorService.getProfesorById(idProfesor);

	//recuperar el empleado cuyo id se recibe como parametro
	model.addAttribute("profesor", profesor);


    model.addAttribute("facultades", facultadService.getAllFacultades());


	//Procesando los telefonos y los correos porque no se deben hacer los calculos en la vista
	
	Set <Telefono> telefonos = profesor.getTelefonos(); 

	if(telefonos.size() > 0 ){
		
		String numerosTelefono = telefonos.stream()
		.map(telefono -> telefono.getNumero())
		.collect(Collectors.joining(";"));

		model.addAttribute("numerosTelefono", numerosTelefono);
	}

	Set<Correo> correos = profesor.getEmails();

	if (correos.size() > 0 ) {
		String direccionesCorreos = correos.stream()
		.map(correo -> correo.getEmail())
		.collect(Collectors.joining(";"));	

		model.addAttribute("direccionesCorreos", direccionesCorreos);
	}
	


		return "formularioAltaModificacionProf";
	}




@GetMapping("/delete/{idProfesor}")
	public String deleteProfesor(Model model, @PathVariable int idProfesor) {

		// Comprobar si el empleado tiene foto para eliminarla

		Profesor profesorEliminar = profesorService.getProfesorById(idProfesor);

		if (profesorEliminar.getFoto() != null) {

			// Ruta relativa del fichero que se va a eliminar
			Path rutaRelativa = Paths.get("src/main/resources/static/imagenes/"
					+ profesorEliminar.getFoto());

			if (Files.exists(rutaRelativa)) {

				try {
					Files.delete(rutaRelativa);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		// Eliminar el empleado

		profesorService.deleteProfesor(profesorEliminar);;

		return "redirect:/profesores/listar";
	}




}
