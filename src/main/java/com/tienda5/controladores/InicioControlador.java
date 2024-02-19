
package com.tienda5.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * controlador de inicio de la app
 */
@Controller
public class InicioControlador {

	@GetMapping("/")
	public String inicioControlador() {
		return "index";
	}
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
}
