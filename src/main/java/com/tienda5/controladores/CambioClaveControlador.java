package com.tienda5.controladores;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tienda5.Fichero.FicheroLog;
import com.tienda5.dto.UsuarioDTO;
import com.tienda5.repositorios.UsuarioRepositorio;
import com.tienda5.servicios.UsuarioServicioInterfaz;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/clave")
public class CambioClaveControlador {

	@Autowired
    private UsuarioServicioInterfaz usuarioServicioInterfaz;
	
    @GetMapping("/cambioClave")
    public String mostrarCambioClave(@RequestParam(name = "token") String token, Model model) {
    	try {
    		FicheroLog.escribir("[INFO] [CambioClaveControlador-mostrarCambioClave()]");
            UsuarioDTO usuario = usuarioServicioInterfaz.buscarUsuarioPorToken(token);
            if (usuario != null) {
                model.addAttribute("usuarioDTO", usuario);
                System.err.println(usuario.getEmail());
            }
            return "cambioClave";
        } catch (Exception e) {
        	model.addAttribute("error", true);
        	FicheroLog.escribir("[ERROR] [CambioClaveControlador-mostrarCambioClave()]");
        }
    	return "solicitarCambioClave";
    }
    
    @PostMapping("/cambioClave")
    public String procesarCambioClave(@ModelAttribute UsuarioDTO usuarioDTO, Model model) {
    	try {
    		FicheroLog.escribir("[INFO] [CambioClaveControlador-procesarCambioClave()]");
    		System.err.println("token: "+usuarioDTO.getToken());
            UsuarioDTO usuarioExistente = usuarioServicioInterfaz.buscarUsuarioPorToken(usuarioDTO.getToken());
            
            if (usuarioExistente == null) {
            	model.addAttribute("error", true);
                return "solicitarCambioClave";
            }
            if (usuarioExistente.getFechaExpiracionToken().before(Calendar.getInstance())) {
            	model.addAttribute("errorFch", true);
                return "solicitarCambioClave";
            }

            boolean modificadaPassword = usuarioServicioInterfaz.cambiarClavePorToken(usuarioDTO);

            if (modificadaPassword) {
                model.addAttribute("cambioOK",true);
                return "cambioClave";//redireccion a iniciosesion en vista
            }
        } catch (Exception e) {
        	FicheroLog.escribir("[ERROR] [CambioClaveControlador-procesarCambioClave()]");
        }
    	model.addAttribute("error", true);
    	return "solicitarCambioClave";
    }
    
    @GetMapping("/iniciarCambioClave")
    public String mostrarVistainiciarRecuperacion(Model model) {
    	try 
    	{
    		FicheroLog.escribir("[INFO] [CambioClaveControlador-mostrarVistainiciarRecuperacion()]");
          model.addAttribute("usuarioDTO", new UsuarioDTO());
        } catch (Exception e) {
        	FicheroLog.escribir("[ERROR] [CambioClaveControlador-mostrarVistainiciarRecuperacion()]");
            model.addAttribute("error", true);
        }
    	return "solicitarCambioClave";
    }
    
    
    @PostMapping("/iniciarCambioClave")
    public String procesarInicioRecuperacion(@ModelAttribute UsuarioDTO usuarioDTO, Model model, HttpServletResponse enviarRespuesta) {
        try {
        	FicheroLog.escribir("[INFO] [CambioClaveControlador-procesarInicioRecuperacion()]");
        	
            if(usuarioServicioInterfaz.iniciarCambioClave(usuarioDTO.getEmail())){
                model.addAttribute("email", true);
                enviarRespuesta.sendRedirect("https://mail.google.com/mail");
                return null;
            }else {
            	//no esta el email en BD
            	model.addAttribute("noEmailBD", true);
            	return "solicitarCambioClave";
            }

        } catch (Exception e) {
        	FicheroLog.escribir("[ERROR] [CambioClaveControlador-procesarInicioRecuperacion()]");
        }
        model.addAttribute("error", true);
        return "solicitarCambioClave";
    }
    
	/**
	 * Control de todas las rutas que no existen 
	 * no dejaba confirmar cuenta
	 
	@GetMapping("/**")
    public String PaginaNoEncontrada() {
		FicheroLog.escribir("[INFO] [CambioClaveControlador-PaginaNoEncontrada()]");
        return "paginaNoEncontrada";
    }
    */
}
