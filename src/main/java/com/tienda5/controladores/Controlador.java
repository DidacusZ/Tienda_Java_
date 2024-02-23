package com.tienda5.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.tienda5.Fichero.FicheroLog;

@Controller
@RequestMapping
public class Controlador {
	
	@GetMapping
    public String mostrarPerfilUsuario() {
		FicheroLog.escribir("[INFO] [Controlador-mostrarPerfilUsuario()] El usuario a entrado en la vista index (/)");
        return "index";
    }
}
