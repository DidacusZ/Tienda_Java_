package com.tienda5.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tienda5.Fichero.FicheroLog;
import com.tienda5.servicios.UsuarioServicioInterfaz;

@Controller
@RequestMapping("/principal")
public class Controlador {
	
	@Autowired
    private UsuarioServicioInterfaz usuarioServicioInterfaz;
    
	/**
	 * ruta principal
	 * @return
	 */
	@GetMapping
    public String mostrarPrincipal(Model model, Authentication authentication) {
		FicheroLog.escribir("[INFO] [Controlador-mostrarPerfilUsuario()]");
        try {
            if (usuarioServicioInterfaz.CuentaConfirmada(authentication.getName())) {
                return "index";
            } else {
                model.addAttribute("cuentaNoConfirmada", true);
                System.err.println("cuenta no confirmada");
                return "inicioSesion";
            }
        } catch (Exception e) {
            model.addAttribute("error", true);
            FicheroLog.escribir("[ERROR] [Controlador-mostrarPerfilUsuario()]");
            System.err.println("[ERROR] [Controlador-mostrarPerfilUsuario()]");
            return "inicioSesion";
        }
    }
	
	/**
	 * Control de todas las rutas que no existen 
	 */
	@GetMapping("/**")
    public String PaginaNoEncontrada() {
		FicheroLog.escribir("[INFO] [Controlador-PaginaNoEncontrada()]");
        return "paginaNoEncontrada";
    }

}
