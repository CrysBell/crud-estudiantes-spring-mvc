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
import com.example.entities.Estudiante;
import com.example.entities.Telefono;
import com.example.services.CorreoService;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;
import com.example.services.TelefonoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private static final Logger LOG = Logger.getLogger("EstudianteController");

    private final EstudianteService estudianteService;
    private final FacultadService facultadService;
    private final CorreoService correoService;
	private final TelefonoService telefonoService;


    @GetMapping("/listar")
    public String listarEstudiantes(Model model) {

        model.addAttribute("estudiantes", 
            estudianteService.getAllEstudiantes());

        // Mostrar la vista del archvo HTML
        return "listadoEstudiantes";
    }

    // Metodo que muestra el formulario de creación de estudiantes
    @GetMapping("/alta")
    public String mostrarFormularioAlta(Model model,
        @ModelAttribute Estudiante estudiante) {

        // Se necesitan los facultads desde la capa de servicios
        model.addAttribute("facultades", 
        facultadService.getAllFacultades());

        // Se necesita enviar un objeto Estudiante vacio, para que se vinculen sus
        // propiedades
        // con cada control (element, input, select, etc) del atributo
        //model.addAttribute("estudiantes", new Estudiante());

        return "formularioAltaModificacion";
    }

    // Metodo para recibir por post los datos procedentes del formulario de creación
    // de estudiantes
    @PostMapping("/persistir")
    public String procesarFormularioAltaModificacion(
            @Valid
            @ModelAttribute Estudiante estudiante,
            BindingResult result,
            @RequestParam String numerosTelefono,
            @RequestParam String direccionesCorreo,
            Model model,
            @RequestParam(name = "file", required = false) MultipartFile file)
    {

        // Comprobar si hay errores en la informacion procedente del formulario
        if (result.hasErrors()) {

            model.addAttribute("facultades",
                    facultadService.getAllFacultades());

            return "formularioAltaModificacion";
        }

// Preguntar si me han envado foto para el empleado, y si es asi, guardar el
		// nombre de la foto en la propiedad atributo o variable miembro de la clase,
		// foto
		//Guardar el contenido de la foto como un archivo en el sistema de archivos (File System) del servidor
		if (file != null && !file.isEmpty()){

			Path rutaRelativa = Paths.get("src/main/resources/static/imagenes");

			String rutaAbsoluta = rutaRelativa.toFile().getAbsolutePath();

			Path rutaCompleta = Paths.get(rutaAbsoluta + "/" + file.getOriginalFilename());

			try {
				byte[] bytesFotoRecibida = file.getBytes();
				Files.write(rutaCompleta, bytesFotoRecibida);
				estudiante.setFoto(file.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}



        LOG.info("Objeto estudiante recibido");
        LOG.info(estudiante.toString());
        LOG.info("Numeros de telefono recibidos: " + numerosTelefono);
        LOG.info("Direcciones de correo recibidas: " + direccionesCorreo);

        // Hy que procesar los datos de los telefonos y correos. que vienen en un String
        // separados por comas, y convertirlos en listas de objetos Telefono y Correo,
        // para luego agregarlo al objeto Estudiante antes de persistirlo en la BD
        // Set<Telefono> telefonos = new HashSet<Telefono>();

        if (!numerosTelefono.isEmpty() && !numerosTelefono.isBlank()) {

            String[] arrayNumerosTelefono = numerosTelefono.split(";");
            List<String> listadoNumeros = Arrays.asList(arrayNumerosTelefono);

            listadoNumeros.forEach(numero -> {
                estudiante.getTelefonos().add(Telefono.builder().numero(numero).estudiante(estudiante).build());
            });

            // estudiante.setTelefonos(telefonos);
        }

        if (!direccionesCorreo.isEmpty() && !direccionesCorreo.isBlank()) {
            String[] arrayDirCorreos = direccionesCorreo.split(";");
            List<String> listadoCorreos = Arrays.asList(arrayDirCorreos);

            listadoCorreos.forEach(dirCorreo -> {
                estudiante.getEmails().add(Correo.builder().email(dirCorreo).estudiante(estudiante).build());
            });
        }

		
        //Antes de persistir el empleado hay que eliminar los telefonos y los correos que tenga
		if (telefonoService.existsByEstudiante(estudiante))
		    telefonoService.deleteByEstudiante(estudiante);

		if (correoService.existsByEstudiante(estudiante))
			correoService.deleteByEstudiante(estudiante);






        // Se recibe un objeto Estudiante con los datos del formulario
        // Se envía a la capa de servicios para que lo guarde en la BD
        estudianteService.saveEstudiante(estudiante);
        
        return "redirect:/estudiantes/listar";
    }
  

//Método que muestra los detalles de un empleado cuyo id se recibe como parámetro
	@GetMapping("/details/{id}")
	public String mostrarDetalles (Model model, 
		@PathVariable(name = "id", required = true) int estudiante_id){

	//recuperar el empleado cuyo id se recibe como parametro
	model.addAttribute("estudiante", estudianteService.getEstudianteById(estudiante_id));

		return "details";
	}


@GetMapping("/update/{id}")
	public String updateEstudiante(Model model, @PathVariable(name = "id", required = true) int idEstudiante){
	
	Estudiante estudiante = estudianteService.getEstudianteById(idEstudiante);

	//recuperar el empleado cuyo id se recibe como parametro
	model.addAttribute("estudiante", estudiante);


    model.addAttribute("facultades", facultadService.getAllFacultades());


	//Procesando los telefonos y los correos porque no se deben hacer los calculos en la vista
	
	Set <Telefono> telefonos = estudiante.getTelefonos(); 

	if(telefonos.size() > 0 ){
		
		String numerosTelefono = telefonos.stream()
		.map(telefono -> telefono.getNumero())
		.collect(Collectors.joining(";"));

		model.addAttribute("numerosTelefono", numerosTelefono);
	}

	Set<Correo> correos = estudiante.getEmails();

	if (correos.size() > 0 ) {
		String direccionesCorreos = correos.stream()
		.map(correo -> correo.getEmail())
		.collect(Collectors.joining(";"));	

		model.addAttribute("direccionesCorreos", direccionesCorreos);
	}
	


		return "formularioAltaModificacion";
	}



}
