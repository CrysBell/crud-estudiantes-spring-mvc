package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.entities.Estudiante;
import com.example.services.EstudianteService;
import com.example.services.FacultadService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {
    
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

}
