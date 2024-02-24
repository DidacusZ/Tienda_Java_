package com.tienda5.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.tienda5.dto.UsuarioDTO;
import com.tienda5.servicios.UsuarioServicioInterfaz;



@Controller
public class SolicitarCambiarClaveControlador {

    @Autowired
    private UsuarioServicioInterfaz UsuarioServicioInterfaz;
    
    @GetMapping("/iniciarCambioClave")
    public String mostrarVistainiciarRecuperacion(Model model) {
        /*
    	try {
           
        } catch (Exception e) {
            model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
            return "solicitarRecuperacionPassword";
        }
        */
        
        model.addAttribute("usuarioDTO", new UsuarioDTO());
        return "solicitarCambioClave";
    }
    
    
    @PostMapping("/iniciarCambioClave")
    public String procesarInicioRecuperacion(@ModelAttribute UsuarioDTO usuarioDTO, Model model) {
        try {
        	
        	
            boolean envioConExito = UsuarioServicioInterfaz.iniciarCambioClave(usuarioDTO.getEmail());

            
            
            
            if (envioConExito) {
                model.addAttribute("mensajeExitoMail", "Proceso de recuperación OK");
                return "inicioSesion";
            } else {
                model.addAttribute("mensajeErrorMail", "Error en el proceso de recuperación.");
            }
            return "solicitarCambioClave";
        } catch (Exception e) {
            model.addAttribute("error", "Error al procesar la solicitud. Por favor, inténtelo de nuevo.");
            return "solicitarCambioClave";
        }
    }
}
