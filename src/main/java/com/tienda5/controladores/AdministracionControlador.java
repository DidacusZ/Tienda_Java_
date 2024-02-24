package com.tienda5.controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tienda5.dto.UsuarioDTO;
import com.tienda5.servicios.ImagenServicioInterfaz;
import com.tienda5.servicios.UsuarioServicioInterfaz;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/administracion")
public class AdministracionControlador {
	
	@Autowired
	private UsuarioServicioInterfaz usuarioServicioInterfaz;

	@Autowired
	private ImagenServicioInterfaz imagenServicioInterfaz;
	
	/**
	 * muestra vista administracion con todos los usuarios
	 * @param model
	 * @param request
	 * @param authentication
	 * @return
	 */
	@GetMapping
    public String administracion(Model model, HttpServletRequest request, Authentication authentication) {
		try {
			//
			List<UsuarioDTO> usuariosDTO = new ArrayList<>();			

			model.addAttribute("usuariosDTO", usuarioServicioInterfaz.todosUsuarios());			
		
			if (request.isUserInRole("ROLE_ADMIN")) {
				return "administracion";
			}
			//
			model.addAttribute("noAdmin", "No eres admin");
			model.addAttribute("nombreUsuario", authentication.getName());
			return "permisoDenegado";
		} catch (Exception e) {
			model.addAttribute("Error", "Ocurrió un error al obtener la lista de usuarios");
			return "index";
		}		
	}
	
	
	@GetMapping("/crearUsuario")
	public String crearUsuario() {
		return "crearUsuario";		
	}
		
	
	/**
	 * Solicitud para eliminar el usuario /eliminarUsuario/{id}
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@PostMapping("/eliminarUsuario/{id}")
	@GetMapping("/eliminarUsuario/{id}")
	public String eliminarUsuario(@PathVariable Long id, Model model, HttpServletRequest request) {
		try {	
			if (request.isUserInRole("ROLE_USER")) {
				return "permisoDenegado";
			}
			else {
				UsuarioDTO usuario = usuarioServicioInterfaz.buscarPorId(id);
				List<UsuarioDTO> usuarios = usuarioServicioInterfaz.todosUsuarios();
				
				String emailUsuarioActual = request.getUserPrincipal().getName();
				System.err.println("usuario actuall: "+emailUsuarioActual);
				

				if (emailUsuarioActual.equals(usuario.getEmail())) {
					model.addAttribute("noTePuedesEliminar", "No puedes eliminarte a ti mismo como administrador");
					model.addAttribute("usuarios", usuarios);
					return "redirect:/administracion";
				}
				usuarioServicioInterfaz.eliminarUsuario(id);
				model.addAttribute("eliminacionCorrecta", "El usuario se ha eliminado correctamente");
				model.addAttribute("usuarios", usuarioServicioInterfaz.todosUsuarios());
				return "redirect:/administracion";	
			}		

		} catch (Exception e) {
			model.addAttribute("Error", "Ocurrió un error al eliminar el usuario");
			return "index";
		}		
	}	
	
	/**
	 * solicitud para editar un usuario
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@PostMapping("/editarUsuario/{id}")
	public String editarUsuario(@PathVariable Long id, Model model, HttpServletRequest request) {
		try {
			if (request.isUserInRole("ROLE_USER")) {
				model.addAttribute("noAdmin", "No tiene permiso para realizar esta accion");
				return "permisoDenegado";
			} else {
				UsuarioDTO usuarioDTO = usuarioServicioInterfaz.buscarPorId(id);
				if (usuarioDTO == null) {
					return "administracionUsuarios";
				}
				model.addAttribute("usuarioDTO", usuarioDTO);
				return "editarUsuario";
			}
		} catch (Exception e) {
			model.addAttribute("Error", "Ocurrió un error al obtener el usuario para editar");
			return "index";
		}
	}
	
	@PostMapping("/procesarEditarUsuario/")
	public String procesarEditarUsuario(@RequestParam("id") Long id, @RequestParam("nombre") String nombre, @RequestParam("movil") String movil,
			@RequestParam("rol") String rol, @RequestParam("imagen") MultipartFile imagen, Model model) {
		try {
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setId(id);
			usuarioDTO.setNombre(nombre);
			usuarioDTO.setMovil(movil);
			
			if (rol.equals("Administrador")) {
				usuarioDTO.setRol("ROLE_ADMIN");
			}else {
				usuarioDTO.setRol(rol);
			}
			
			if (!imagen.isEmpty()) {
				String fotoUsuario = imagenServicioInterfaz.ArrayBYTESaBase64(imagen.getBytes());
				usuarioDTO.setImagen(fotoUsuario);
			} else {
		        UsuarioDTO usuarioActualDTO = usuarioServicioInterfaz.buscarPorId(id);
		        String fotoActual = usuarioActualDTO.getImagen();
				usuarioDTO.setImagen(fotoActual);
			}
			
			usuarioServicioInterfaz.editarUsuario(usuarioDTO);
			model.addAttribute("edicionCorrecta", "El Usuario se ha editado correctamente");
			model.addAttribute("usuarios", usuarioServicioInterfaz.todosUsuarios());
			return "administracionUsuarios";
		} catch (Exception e) {
			model.addAttribute("Error", "Ocurrió un error al editar el usuario");
			return "dashboard";
		}
	}
    
}