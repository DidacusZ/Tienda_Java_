
package com.tienda5.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.tienda5.Fichero.FicheroLog;

/**
 * controlador de inicio de sesion de la app
 */
@Controller
@RequestMapping("/inicioSesion")
public class InicioControlador {
	
	/**
	 * Envia a la vista de inicio de sesión con la con ruta: /inicioSesion
	 * @return
	 */
	@GetMapping
	public String inicioControlador() {
		FicheroLog.escribir("El usuario a entrado en la vista inicioSesion");
		return "inicioSesion";
	}
	
}
