package com.tienda5.controladores;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tienda5.Fichero.FicheroLog;
import com.tienda5.dto.UsuarioDTO;
import com.tienda5.servicios.UsuarioServicioInterfaz;

@Controller
@RequestMapping
public class Controlador {
	
	@Autowired
    private UsuarioServicioInterfaz UsuarioServicioInterfaz;
	
	
	/**
	 * ruta principal
	 * @return
	 */
	@GetMapping
    public String mostrarPerfilUsuario() {
		FicheroLog.escribir("[INFO] [Controlador-mostrarPerfilUsuario()] El usuario a entrado en la vista index (/)");
        return "index";
    }
	

	/**
	 * ruta de confirmación de la cuenta /confirmarCuenta
	 * @param modelo
	 * @param token
	 * @return
	 */
	@GetMapping("/confirmarCuenta")
	public String confirmarCuenta(Model modelo, @RequestParam("token") String token) {
		try {
            UsuarioServicioInterfaz.confirmarCuenta(token);//es booleano para luego feedback
            return "inicioSesion";
        } catch (Exception e) {
        	FicheroLog.escribir("[ERROR] [Controlador-confirmarCuenta()] En la vista confirmarCuenta (/confirmarCuenta)");
            return "inicioSesion";
        }	
	}

	/**
	 * ruta de la vista para cambiar la contraseña /cambiarClave
	 * @param token
	 * @param modelo del usuario que quiere cambiar la contraseña
	 * @return
	 */
	@GetMapping("/cambiarClave")
	public String mostrarRecuperarClave(@RequestParam(name = "token") String token, Model modelo) {
		try {
            UsuarioDTO usuarioDto = UsuarioServicioInterfaz.encontrarUsuarioPorToken(token);
            if (usuarioDto != null) {
                modelo.addAttribute("usuarioDTO", usuarioDto);
            } else {
                modelo.addAttribute("mensajeErrorTokenValidez", "El enlace de recuperación no válido o usuario no encontrado");
                return "solicitarCambiarClave";
            }
            return "cambiarClave";
        } catch (Exception e) {
        	FicheroLog.escribir("[ERROR] [Controlador-mostrarRecuperarClave()] En la vista recuperarClave (/recuperarClave)");
            return "solicitarCambiarClave";
        }	
	}
	
	@PostMapping("/cambiarClave")
	public String recuperarClave(@ModelAttribute UsuarioDTO usuarioDTO, Model modelo) {
		try {
            UsuarioDTO usuarioDto = UsuarioServicioInterfaz.encontrarUsuarioPorToken(usuarioDTO.getToken());

            if (usuarioDto == null) {
                modelo.addAttribute("mensajeErrorTokenValidez", "El enlace de recuperación no válido");
                return "solicitarCambiarClave";
            }
            if (usuarioDto.getFechaExpiracionToken().before(Calendar.getInstance())) {
                modelo.addAttribute("mensajeErrorTokenExpirado", "El enlace de recuperación ha expirado");
                return "solicitarCambiarClave";
            }

            boolean modificadaPassword = UsuarioServicioInterfaz.cambiarClavePorToken(usuarioDTO);

            if (modificadaPassword) {
                modelo.addAttribute("contraseñaModificadaExito", "Contraseña modificada OK");
                return "inicioSesion";
            } else {
                modelo.addAttribute("contraseñaModificadaError", "Error al cambiar de contraseña");
                return "solicitarCambiarClave";
            }
        } catch (Exception e) {
            modelo.addAttribute("error", "Error al procesar la solicitud recuperar");
            return "solicitarCambiarClave";//ha habido un error vuelva a solicitar el cambio de contraseña
        }	
	}
}
