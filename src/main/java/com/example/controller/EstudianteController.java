package com.example.controller;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entities.Estudiante;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;

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

    
    //@GetMapping("/listar")
    //public String getMethodName(@RequestParam String param) {
    //    return new String();
    //}
    
    @GetMapping("/listar")
    public String listarEstudiantes(Model model){

        model.addAttribute("estudiantes", estudianteService.getAllEstudiantes());

        //Mostrar la vista del archvo HTML
        return "listadoEstudiantes";
    }

     //Metodo que muestra el formulario de creación de estudiantes
    @GetMapping("/alta")
    public String mostrarFormularioAlta(Model model){
    //Se necesitan los facultads desde la capa de servicios
        model.addAttribute("facultades", facultadService.getAllFacultades());

    //Se necesita enviar un objeto Estudiante vacio, para que se vinculen sus propiedades
    //con cada control (element, input, select, etc) del atributo
        model.addAttribute("estudiantes", new Estudiante());

        return "formularioAltaModificacion";
    }

    // Metodo para recibir por post los datos procedentes del formulario de creación
    // de estudiantes
    @PostMapping("/persistir")
    public String procesarFormularioAltaModificacion(@ModelAttribute Estudiante estudiante,
        @RequestParam String numerosTelefono,
        @RequestParam String direccionesCorreo) {

        LOG.info("Objeto estudiante recibido");
        LOG.info(estudiante.toString());
        LOG.info("Numeros de telefono recibidos" + numerosTelefono);
        LOG.info("Direcciones de correo recibidas" + direccionesCorreo);
        

        return "redirect:/estudiantes/listar";
    }


}
