package com.tienda5.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tienda5.Fichero.FicheroLog;

import jakarta.mail.internet.MimeMessage;

/**
 * 
 */
@Service
public class EmailServicioImpl implements EmailServicioInterfaz {

	@Autowired
	private JavaMailSender javaMailSender;	
		
	public EmailServicioImpl(JavaMailSender javaMailSender) {
		super();
		this.javaMailSender = javaMailSender;
	}

	@Override
	public void emailCambiarClave(String emailDestino, String nombreUsuario, String token) {
		try {
            MimeMessage mensaje = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(emailDestino);
            helper.setSubject("Cambiar contraseña de la Tienda de Gorras");

            String urlDominio = "http://localhost:8080";
            String urlDeRecuperacion = String.format("%s/clave/cambioClave?token=%s", urlDominio, token);

            String cuerpoMensaje = String.format(
            		cabeceraEmail+emailCambiarClave,
                nombreUsuario, urlDeRecuperacion);

            helper.setText(cuerpoMensaje, true);

            javaMailSender.send(mensaje);
            FicheroLog.escribir("[INFO] [EmailServicioImpl-emailCambiarClave()] - Email enviado correctamente"); 
        } catch (Exception e) {
			System.err.println("[ERROR] [EmailServicioImpl-emailCambiarClave()] - Al enviar email: "+e);
			FicheroLog.escribir("[ERROR] [EmailServicioImpl-emailCambiarClave()] - Al enviar email"); 
        }
		
	}

	@Override
	public void emailConfirmarCuenta(String emailDestino, String nombreUsuario, String token) {
		
		try {
            MimeMessage mensaje = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(emailDestino);
            helper.setSubject("Confirmación de cuenta de la Tienda de Gorras");

            String urlDominio = "http://localhost:8080";
            String urlDeConfirmacion = String.format("%s/confirmarCuenta?token=%s", urlDominio, token);

            String cuerpoMensaje = String.format(
            		cabeceraEmail+emailConfirmacion,
                nombreUsuario, urlDeConfirmacion);

            helper.setText(cuerpoMensaje, true);

            javaMailSender.send(mensaje);
            FicheroLog.escribir("[INFO] [EmailServicioImpl-emailCambiarClave()] - Email enviado correctamente");
        } catch (Exception e) {
			System.err.println("[ERROR] [EmailServicioImpl-emailConfirmarCuenta()] - Al enviar email: "+e);
			FicheroLog.escribir("[ERROR] [EmailServicioImpl-emailConfirmarCuenta()] - Al enviar email");   
        }
		
	}
	
	String cabeceraEmail=
			"<!DOCTYPE html>\r\n"
			+ "<html lang='en'>\r\n"
			+ "<head>\r\n"
			+ "    <style>\r\n"
			+ "        body {\r\n"
			+ "            font-family: Arial, sans-serif;\r\n"
			+ "            background-color: #f4f4f4;\r\n"
			+ "            margin: 0;padding: 0;\r\n"
			+ "        }\r\n"
			+ "        .container {\r\n"
			+ "            max-width: 600px;margin: 50px auto;\r\n"
			+ "            background-color: #fff;border-radius: 10px;\r\n"
			+ "            padding: 20px;box-shadow: none;\r\n"
			+ "        }\r\n"
			+ "        h2 {color: #007bff;}\r\n"
			+ "        p {color: #333;line-height: 1.6;}\r\n"
			+ "        .btn {\r\n"
			+ "            display: inline-block;padding: 10px 20px;\r\n"
			+ "            background-color: #007bff;\r\n"
			+ "            color: #000;text-decoration: none;\r\n"
			+ "            border-radius: 5px;transition: background-color 0.3s ease;\r\n"
			+ "        }\r\n"
			+ "        .btn:hover {background-color: #0056b3;}\r\n"
			+ "    </style>\r\n"
			+ "</head>\r\n"
			+ "<body>";	
	
	String emailCambiarClave=
			"    <div class='container'>\r\n"
			+ "        <h2>Cambiar Contraseña</h2>\r\n"
			+ "        <p>Hola <strong>%s</strong>,</p>\r\n"
			+ "        <p>Recibes este mensaje porque solicitaste cambiar tu contraseña. Haz clic en el siguiente botón para cambiarla:</p>\r\n"
			+ "        <p style='text-align: center;'>\r\n"
			+ "            <a href='%s' class='btn'>Cambiar Contraseña</a>\r\n"
			+ "        </p>\r\n"
			+ "        <p>Si no solicitaste cambiar tu contraseña, puedes ignorar este mensaje.</p>\r\n"
			+ "        <p>Gracias,</p>\r\n"
			+ "        <p>Equipo de la <strong>Tienda de Gorras</strong></p>\r\n"
			+ "    </div>\r\n"
			+ "</body>\r\n"
			+ "</html>";	
	
	String emailConfirmacion ="<div class='container'>\r\n"
			+ "        <h2>Confirmación de Cuenta</h2>\r\n"
			+ "        <p>Hola <strong>%s</strong>,</p>\r\n"
			+ "        <p>Gracias por registrarte en nuestro sitio. Por favor, haz clic en el siguiente enlace para confirmar tu cuenta:</p>\r\n"
			+ "        <p style='text-align: center;'>\r\n"
			+ "            <a href='%s' class='btn'>Confirmar Cuenta</a>\r\n"
			+ "        </p>\r\n"
			+ "        <p>Si no has solicitado esta confirmación, puedes ignorar este mensaje.</p>\r\n"
			+ "        <p>Atentamente,</p>\r\n"
			+ "        <p>Equipo de la <strong>Tienda de Gorras</strong></p>\r\n"
			+ "    </div>\r\n"
			+ "</body>\r\n"
			+ "</html>";

}
