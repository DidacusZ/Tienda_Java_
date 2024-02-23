
package com.tienda5.controladores;

import org.springframework.web.bind.annotation.*;

import com.tienda5.Fichero.FicheroLog;
import com.tienda5.dto.UsuarioDTO;
//import com.tienda5.servicios.UsuarioServicioInterfaz;
import com.tienda5.servicios.UsuarioServicioInterfaz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * Controlador para el registro del usuario
 */
@Controller
@RequestMapping("/registro")
public class RegistroControlador {

	@Autowired
	private UsuarioServicioInterfaz usuarioServicioInterfaz;	
	
	/**
	 * Inyección de dependencias
	 * Proporciona instancias de las interfaces cuando se crea esta instancia
	 * Recibir e inicializar las dependencias necesarias para que esta clase (controlador) funcione correctamente
	 * @param usuarioServicioInterfaz
	 */
	@Autowired
	public RegistroControlador(UsuarioServicioInterfaz usuarioServicioInterfaz) {
		super();
		this.usuarioServicioInterfaz = usuarioServicioInterfaz;
	}

	/*
	 * Model
	 * Se utiliza para pasar datos entre el controlador y la vista.
	 * Actúa como un contenedor para los datos que se deben pasar a la vista.
	 */
	
	/** 
	 * Envia a la vista de registro con la ruta: /registro
	 * @param modelo Envia usuarioDTO vacío a vista
	 * @return Vista registro
	 */
	@GetMapping
	public String mostrarRegistro(Model modelo) {
		FicheroLog.escribir("El usuario a entrado en la vista registro");
		modelo.addAttribute("usuarioDTO", new UsuarioDTO());		
		return "registroA";
	}	
	
	/**
	 * Registra un nuevo usuario a partir de un modelo
	 * @param usuarioDTO Espera a recibir los datos del nuevo usuario
	 * @param modelo Envia datos a la vista especificada
	 * @return
	 */
	@PostMapping
	public String RegistrarUsuario(@ModelAttribute UsuarioDTO usuarioDTO, Model modelo) {
		
		UsuarioDTO usuarioNuevo = usuarioServicioInterfaz.guardarUsuario(usuarioDTO);
		

		//comprobar
		/*
		 * si existe el mail en BD
		 * 
		 */		
		
		return "inicioSesion";
	}
}
