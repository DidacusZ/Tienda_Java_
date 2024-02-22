
package com.tienda5.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * controlador de inicio de la app
 */
@Controller
@RequestMapping("/inicioSesion")
public class InicioControlador {

	@GetMapping
	public String inicioControlador() {
		return "inicioSesion";
	}
	
}
