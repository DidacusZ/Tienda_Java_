
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
		try {
			FicheroLog.escribir("[INFO] [InicioControlador-inicioControlador()] - El usuario a entrado en la vista inicioSesion");
			return "inicioSesion";
		}catch(Exception e){
			FicheroLog.escribir("[ERROR] [InicioControlador-inicioControlador()] - Al entrar en la vista inicioSesion");
			System.err.println("\n[ERROR] [InicioControlador-inicioControlador()] - Al entrar en la vista inicioSesion: "+e);
			return "inicioSesion";
		}		
		
	}
	
	
}
