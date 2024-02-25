package com.tienda5.controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tienda5.Fichero.FicheroLog;
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
			FicheroLog.escribir("[INFO] [AdministracionControlador-administracion()]");
	        
			model.addAttribute("usuariosDTO", usuarioServicioInterfaz.todosUsuarios());			
		
			if (request.isUserInRole("ROLE_ADMIN")) {
				return "administracion";
			}
			return "permisoDenegado";
		} catch (Exception e) {
			model.addAttribute("error",true);
			FicheroLog.escribir("[ERROR] [AdministracionControlador-administracion()]");
			return "index";
		}		
	}
		
	
	/**
	 * Controlamos si el usuario no es administrador para la vista /administracion/eliminarUsuario
	 * @param request
	 * @return
	 */
	@GetMapping("/eliminarUsuario/**")
	public String eliminarUsuario(HttpServletRequest request) {
		if (request.isUserInRole("ROLE_USER")) {
			FicheroLog.escribir("[ERROR] [AdministracionControlador-eliminarUsuario()] Permiso Denegado");
			return "permisoDenegado";
		}
		return "redirect:/administracion";		
	}
	
	/**
	 * Solicitud para eliminar el usuario /eliminarUsuario/{id}
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@PostMapping("/eliminarUsuario/{id}")
	public String eliminarUsuario(@PathVariable Long id, Model model, HttpServletRequest request) {
		try {
			FicheroLog.escribir("[INFO] [AdministracionControlador-eliminarUsuario()]");
			if (request.isUserInRole("ROLE_USER")) {
				return "permisoDenegado";
			}
			else {
				UsuarioDTO usuario = usuarioServicioInterfaz.buscarPorId(id);
				List<UsuarioDTO> usuarios = usuarioServicioInterfaz.todosUsuarios();
				
				String emailUsuarioActual = request.getUserPrincipal().getName();
				System.err.println("usuario actual: "+emailUsuarioActual);				

				if (emailUsuarioActual.equals(usuario.getEmail())) {
					model.addAttribute("noBorrarse",true);
					//model.addAttribute("usuarios", usuarios);
					return "administracion";
				}
				usuarioServicioInterfaz.eliminarUsuario(id);
				model.addAttribute("usuarios", usuarioServicioInterfaz.todosUsuarios());
				return "redirect:/administracion";	
			}		

		} catch (Exception e) {
			model.addAttribute("error",true);
			FicheroLog.escribir("[ERROR] [AdministracionControlador-eliminarUsuario()]");
			return "index";
		}		
	}	
	
	/**
	 * Controlamos si el usuario no es administrador para la vista /administracion/editarUsuario
	 * @param request
	 * @return
	 */
	@GetMapping("/editarUsuario/**")
	public String editarUsuario(HttpServletRequest request) {
		if (request.isUserInRole("ROLE_USER")) {
			FicheroLog.escribir("[ERROR] [AdministracionControlador-editarUsuario()] Permiso Denegado");
			return "permisoDenegado";
		}
		return "redirect:/administracion";		
	}
	
	
	/**
	 * solicitud para editar un usuario por id
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@PostMapping("/editarUsuario/{id}")
	public String editarUsuario(@PathVariable Long id, Model model, HttpServletRequest request) {
		try {
			FicheroLog.escribir("[INFO] [AdministracionControlador-editarUsuario()]");

				UsuarioDTO usuarioDTO = usuarioServicioInterfaz.buscarPorId(id);

				model.addAttribute("usuarioDTO", usuarioDTO);
				return "editarUsuario";
			
		} catch (Exception e) {
			model.addAttribute("error",true);
			FicheroLog.escribir("[ERROR] [AdministracionControlador-editarUsuario()]");
			return "index";
		}
	}
	
	/**
	 * Controlamos si el usuario no es administrador para la vista /administracion/procesarEditarUsuario
	 * @param request
	 * @return
	 */
	@GetMapping("/procesarEditarUsuario")
	public String procesarEditarUsuario(HttpServletRequest request) {
		if (request.isUserInRole("ROLE_USER")) {
			FicheroLog.escribir("[ERROR] [AdministracionControlador-procesarEditarUsuario()] Permiso Denegado");
			return "permisoDenegado";
		}
		return "redirect:/administracion";
		
	}
	
	/**
	 * recibe los datos del formulario
	 * @param id
	 * @param nombre
	 * @param movil
	 * @param rol
	 * @param model
	 * @return
	 */
	@PostMapping("/procesarEditarUsuario")
	public String procesarEditarUsuario(@RequestParam("id") Long id, @RequestParam("nombre") String nombre, @RequestParam("movil") String movil,
			@RequestParam("rol") String rol,/* @RequestParam("imagen") MultipartFile imagen,*/ Model model) {
		try {
			FicheroLog.escribir("[INFO] [AdministracionControlador-procesarEditarUsuario()]");
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setId(id);
			usuarioDTO.setNombre(nombre);
			usuarioDTO.setMovil(movil);
			if (rol.equals("Administrador")) {
				usuarioDTO.setRol("ROLE_ADMIN");
			}else {
				usuarioDTO.setRol(rol);
			}
			/*
			if (!imagen.isEmpty()) {
				String fotoUsuario = imagenServicioInterfaz.ArrayBYTESaBase64(imagen.getBytes());
				usuarioDTO.setImagen(fotoUsuario);
			} else {
		        UsuarioDTO usuarioActualDTO = usuarioServicioInterfaz.buscarPorId(id);
		        String fotoActual = usuarioActualDTO.getImagen();
				usuarioDTO.setImagen(fotoActual);
			}
			*/
			usuarioServicioInterfaz.editarUsuario(usuarioDTO);
			model.addAttribute("exitoEdicion",true);
			//model.addAttribute("usuarios", usuarioServicioInterfaz.todosUsuarios());
			return "redirect:/administracion";
		} catch (Exception e) {
			model.addAttribute("error",true);
			FicheroLog.escribir("[ERROR] [AdministracionControlador-procesarEditarUsuario()]");
			return "index";
		}
	}
	
	
	/**
	 * vista para crear un usuario desde administracion ha /administracion/crearUsuario
	 * @param modelo
	 * @param request
	 * @return
	 */
	@GetMapping("/crearUsuario")
	public String crearUsuario(Model modelo, HttpServletRequest request) {
		try {
			FicheroLog.escribir("[INFO] [AdministracionControlador-crearUsuario()]");
			
			if (request.isUserInRole("ROLE_USER")) {
				FicheroLog.escribir("[ERROR] [AdministracionControlador-crearUsuario()] Permiso Denegado");
				return "permisoDenegado";
			}
			
			modelo.addAttribute("usuarioDTO", new UsuarioDTO());
			return "crearUsuario";
			
		} catch (Exception e) {
			modelo.addAttribute("error",true);
			FicheroLog.escribir("[ERROR] [AdministracionControlador-crearUsuario()]");
			return "administracion";
		}
	}
	
	
	/**
	 * Controlamos si el usuario no es administrador para la vista /administracion/procesarCrearUsuario
	 * @param request
	 * @return
	 */
	@GetMapping("/procesarCrearUsuario")
	public String procesarCrearUsuario(HttpServletRequest request) {
		if (request.isUserInRole("ROLE_USER")) {
			FicheroLog.escribir("[ERROR] [AdministracionControlador-procesarCrearUsuario()] Permiso Denegado");
			return "permisoDenegado";
		}
		return "redirect:/administracion";
	}
	
	/**
	 * Guardamos la info del nuevo usuario
	 * @param usuarioDTO
	 * @param modelo
	 * @param request
	 * @return
	 */
	@PostMapping("/procesarCrearUsuario")
	public String procesarCrearUsuario(@ModelAttribute UsuarioDTO usuarioDTO, Model modelo, HttpServletRequest request) {
		
		try {
			FicheroLog.escribir("[INFO] [AdministracionControlador-procesarCrearUsuario()]");
			
			UsuarioDTO usuarioDto = usuarioServicioInterfaz.guardarUsuario(usuarioDTO);
						
			if (usuarioDto == null) {
				modelo.addAttribute("emailError", true);
				FicheroLog.escribir("[ERROR] [AdministracionControlador-procesarCrearUsuario()] - Ya existe email");
				return "crearUsuario";
			}
			modelo.addAttribute("exitoRegistro", true);
			return "crearUsuario";
			
		}catch(Exception e){
			modelo.addAttribute("error",true);
			FicheroLog.escribir("[ERROR] [AdministracionControlador-procesarCrearUsuario()]");
			return "administracion";
		}
	}
	
	
	/**
	 * Control de todas las rutas que no existen 
	 */
	@GetMapping("/**")
    public String PaginaNoEncontrada() {
		FicheroLog.escribir("[INFO] [AdministracionControlador-PaginaNoEncontrada()]");
        return "paginaNoEncontrada";
    }
    
}