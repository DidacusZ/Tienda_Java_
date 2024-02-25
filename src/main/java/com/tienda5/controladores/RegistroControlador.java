
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
		try {
			FicheroLog.escribir("[INFO] [RegistroControlador-mostrarRegistro()] - El usuario a entrado en la vista registro");
			modelo.addAttribute("usuarioDTO", new UsuarioDTO());		
			return "registro";
		}catch(Exception e){
			modelo.addAttribute("error",true);
			FicheroLog.escribir("[ERROR] [RegistroControlador-mostrarRegistro()] - Al entrar en la vista registro");
			System.err.println("\n[ERROR] [[RegistroControlador-mostrarRegistro()] - Al entrar en la vista registro: "+e);
			return "registro";
		}		
	}	
	
	/**
	 * Registra un nuevo usuario a partir de un modelo
	 * @param usuarioDTO Espera a recibir los datos del nuevo usuario
	 * @param modelo Envia datos a la vista especificada
	 * @return
	 */
	@PostMapping
	public String RegistrarUsuario(@ModelAttribute UsuarioDTO usuarioDTO, Model modelo) {
		try {
			FicheroLog.escribir("[INFO] [RegistroControlador-RegistrarUsuario()]");
			UsuarioDTO usuarioDto = usuarioServicioInterfaz.guardarUsuario(usuarioDTO);
			
			if (usuarioDto == null) {
				modelo.addAttribute("emailError", true);
				System.err.println("Ya existe un usuario con ese email");
				FicheroLog.escribir("[ERROR] [RegistroControlador-RegistrarUsuario()] - Ya existe email");
				return "registro";
			}
			modelo.addAttribute("exitoRegistro", true);
			return "registro";//se redirige a inicioSesion desde vista
					        
		}catch(Exception e){
			modelo.addAttribute("error",true);
			FicheroLog.escribir("[ERROR] [RegistroControlador-RegistrarUsuario()] - Al mandar datos para registrar al usuario");
			System.err.println("[ERROR] [[RegistroControlador-RegistrarUsuario()] - Al mandar datos para registrar al usuario: "+e);
			return "registro";
		}		
	}
}
