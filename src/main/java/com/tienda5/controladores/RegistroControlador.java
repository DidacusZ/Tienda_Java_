
package com.tienda5.controladores;

import org.springframework.web.bind.annotation.*;

import com.tienda5.dto.UsuarioDTO;
//import com.tienda5.servicios.UsuarioServicioInterfaz;
import com.tienda5.servicios.UsuarioServicioInterfaz;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * controlador para el registro del usuario
 *registro
 */
@Controller
@RequestMapping("/registro")
public class RegistroControlador {

	private UsuarioServicioInterfaz usuarioServicioInterfaz;

	public RegistroControlador(UsuarioServicioInterfaz usuarioServicioInterfaz) {
		super();
		this.usuarioServicioInterfaz = usuarioServicioInterfaz;
	}
	
	@ModelAttribute("usuario")
	public UsuarioDTO nuevoUsuarioDTO() {
		return new UsuarioDTO();
	}	
	
	/*
	 * Model
	 * Se utiliza para pasar datos entre el controlador y la vista.
	 * Actúa como un contenedor para los datos que se deben pasar a la vista.
	 */
	
	/** 
	 * Ruta /registro
	 * @param modelo Envia usuarioDTO vacío a vista
	 * @return Vista registro
	 */
	@GetMapping
	public String mostrarRegistro(Model modelo) {		
		modelo.addAttribute("usuarioDTO", new UsuarioDTO());		
		return "registroA";
	}	
	
	/**
	 * 
	 * @param usuarioDTO Espera a recibir los datos del nuevo usuario
	 * @param modelo Envia datos a la vista especificada
	 * @return
	 */
	@PostMapping
	public String RegistrarUsuario(@ModelAttribute UsuarioDTO usuarioDTO, Model modelo) {
		
		UsuarioDTO usuarioNuevo = usuarioServicioInterfaz.guardarUsuario(usuarioDTO);		
		
		return "inicioSesion";
	}
	
	
/*
	@PostMapping
	public String registrarUsuario(@ModelAttribute("usuario") UsuarioDTO usuarioRegistrado) {
		usuarioServicioInterfaz.guardarUsuario(usuarioRegistrado);
		return "redirect:/registro?exito";
	}*/
	
}
