package com.example.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entities.Correo;
import com.example.entities.Estudiante;
import com.example.entities.Telefono;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private static final Logger LOG = Logger.getLogger("EstudianteController");

    private final EstudianteService estudianteService;
    private final FacultadService facultadService;

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
            Model model) {

        // Comprobar si hay errores en la informacion procedente del formulario
        if (result.hasErrors()) {

            model.addAttribute("facultades",
                    facultadService.getAllFacultades());

            return "formularioAltaModificacion";
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

        // Se recibe un objeto Estudiante con los datos del formulario
        // Se envía a la capa de servicios para que lo guarde en la BD
        estudianteService.saveEstudiante(estudiante);
        
        return "redirect:/estudiantes/listar";
    }

}
