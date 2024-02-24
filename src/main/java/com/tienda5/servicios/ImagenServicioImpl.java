package com.tienda5.servicios;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/*
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
*/
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.tienda5.Fichero.FicheroLog;

/**
 * Logica necesaria para el tratamieno de imagenes
 */
@Service
public class ImagenServicioImpl implements ImagenServicioInterfaz {

	@Override
	public String ArrayBYTESaBase64(byte[] datosImgagen) {
		try {
			if (datosImgagen != null && datosImgagen.length > 0) {
				System.out.println("[INFO] [ImagenServicioImpl-ArrayBYTESaBase64()] - Conversión correcta de array de bytes a base64");
				FicheroLog.escribir("[INFO] [ImagenServicioImpl-ArrayBYTESaBase64()] - Conversión correcta de array de bytes a base64");
				return Base64.getEncoder().encodeToString(datosImgagen);				
			}
		}catch (Exception e) {
			System.err.println("[ERROR] [ImagenServicioImpl-ArrayBYTESaBase64()] - Al pasar de array de bytes a base64: "+e);
			FicheroLog.escribir("[ERROR] [ImagenServicioImpl-ArrayBYTESaBase64()] - Al pasar de array de bytes a base64");
		}		
		return null;
	}

	@Override
	public byte[] base64aArrayBYTES(String imgagenBase64) {
		try {
			if (imgagenBase64 != null && !imgagenBase64.isEmpty()) {
				System.out.println("[INFO] [ImagenServicioImpl-base64aArrayBYTES()] - Conversión correcta de base64 a array de bytes");
				FicheroLog.escribir("[INFO] [ImagenServicioImpl-base64aArrayBYTES()] - Conversión correcta de base64 a array de bytes");
				return Base64.getDecoder().decode(imgagenBase64);				
			}
		}catch (Exception e) {
			System.err.println("[ERROR] [ImagenServicioImpl-base64aArrayBYTES()] - Al pasar de base64 a array de bytes: "+e);
			FicheroLog.escribir("[ERROR] [ImagenServicioImpl-base64aArrayBYTES()] - Al pasar de base64 a array de bytes");
		}		
		return null;
	}

	@Override
	public byte[] cargarFotoPredeterminada() {
		try {
			String ruta = "src/main/resources/static/imagenes/fotoPerfil.jpg";
		    Path ubucacionArchivo = Paths.get(ruta);
		    System.out.println("[INFO] [ImagenServicioImpl-cargarFotoPredeterminada()] - Cargar correcta de la imagen por defecto");
			FicheroLog.escribir("[INFO] [ImagenServicioImpl-cargarFotoPredeterminada()] - Cargar correcta de la imagen por defecto");
		    return Files.readAllBytes(ubucacionArchivo);
		} catch(IOException e) {
			System.err.println("[ERROR] [ImagenServicioImpl-cargarFotoPredeterminada()] - Al cargar la imagen por defecto: "+e);
			FicheroLog.escribir("[ERROR] [ImagenServicioImpl-cargarFotoPredeterminada()] - Al cargar la imagen por defecto");
			return null;
		}
	}
	
	
}
