package com.tienda5.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tienda5.Fichero.FicheroLog;
import com.tienda5.servicios.UsuarioServicioInterfaz;

@Controller
public class ConfirmarCuenta {

	@Autowired
    private UsuarioServicioInterfaz UsuarioServicioInterfaz;
	/**
	 * ruta de confirmación de la cuenta /confirmarCuenta
	 * @param modelo
	 * @param token
	 * @return
	 */
	@GetMapping("/confirmarCuenta")
	public String confirmarCuenta(Model modelo, @RequestParam("token") String token/*, HttpServletResponse enviarRespuesta*/) {
		try {
			FicheroLog.escribir("[INFO] [ConfirmarCuenta-confirmarCuenta()]");
			if(UsuarioServicioInterfaz.confirmarCuenta(token)) {
				modelo.addAttribute("cuentaConfirmada",true);
			}
            //enviarRespuesta.sendRedirect("https://mail.google.com/mail");
            //return new RedirectView("https://mail.google.com/mail");
            return "inicioSesion";
        } catch (Exception e) {
        	FicheroLog.escribir("[ERROR] [ConfirmarCuenta-confirmarCuenta()] En la vista confirmarCuenta (/confirmarCuenta)");
            return "inicioSesion";
        }	
	}
}
