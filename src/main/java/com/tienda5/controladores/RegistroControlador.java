
package com.tienda5.controladores;

import org.springframework.web.bind.annotation.*;

import com.tienda5.dto.UsuarioDTO;
//import com.tienda5.servicios.UsuarioServicioInterfaz;

import org.springframework.stereotype.Controller;

/**
 * controlador para el registro del usuario
 */
@Controller
@RequestMapping("/registro")
public class RegistroControlador {

	//private UsuarioServicioInterfaz usuarioServicioInterfaz;
/*
	public RegistroControlador(UsuarioServicioInterfaz usuarioServicioInterfaz) {
		super();
		this.usuarioServicioInterfaz = usuarioServicioInterfaz;
	}*/

	@ModelAttribute("usuario")
	public UsuarioDTO nuevoUsuarioDTO() {
		return new UsuarioDTO();
	}

	@GetMapping
	public String mostrarRegistro() {
		return "registroA";
	}
/*
	@PostMapping
	public String registrarUsuario(@ModelAttribute("usuario") UsuarioDTO usuarioRegistrado) {
		usuarioServicioInterfaz.guardarUsuario(usuarioRegistrado);
		return "redirect:/registro?exito";
	}*/
	
}
