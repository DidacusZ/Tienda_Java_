package com.tienda5.servicios;

/**
 * metodos para gestion de email
 */
public interface EmailServicioInterfaz {

	/**
	 * Envía email para cambiar la contraseña
	 * @param emailDestino
	 * @param nombreUsuario
	 * @param token
	 */
	public void emailCambiarClave(String emailDestino, String nombreUsuario, String token);
	
	/**
	 * Envía email de confirmacion
	 * @param emailDestino
	 * @param nombreUsuario
	 * @param token
	 */
	public void emailConfirmarCuenta(String emailDestino, String nombreUsuario, String token);
}
