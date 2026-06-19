package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.services.EstudianteService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {
    
    private final EstudianteService estudianteService;

    
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
}
