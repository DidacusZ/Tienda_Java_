package com.tienda5.servicios;

import org.springframework.stereotype.Service;

/**
 * Metodos para guardar imagenes
 */
public interface ImagenServicioInterfaz {

	/**
	 * Transforma un array de bytes a una cadena base64 estandar
	 * @param datosImg array de bytes
	 * @return cadena base64
	 */
	public String ArrayBYTESaBase64(byte[] datosImgagen);
	
	/**
	 * Transforma una cadena base64 a un array de bytes.
	 * @param imgBase64 cadena base64
	 * @return array de bytes
	 */
	public byte[] base64aArrayBYTES(String imgagenBase64);
	

	/**
	 * Carga foto predeterminada
	 * @return array bytes
	 */
	public byte[] cargarFotoPredeterminada();
	
	
	
	/*
	 * Base64 es un esquema de codificación que permite representar datos binarios de manera segura en formato de texto ASCII
	 * 
	 * En este contexto, se utiliza para convertir datos binarios, como imágenes, a una representación de cadena de caracteres
	 * que puede ser transmitida o almacenada de manera segura
	 */

}
