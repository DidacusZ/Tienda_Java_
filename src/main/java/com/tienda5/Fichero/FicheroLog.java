package com.tienda5.Fichero;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase encargada del Fichero Log
 */
public class FicheroLog {

	/**
	 * Se escribe en fichero con fecha y hora actuales
	 * @param contenido Lo que se va a escribir en el fichero
	 */
	public static void escribir(String contenido) {
		
		//String rutaArchivo = "C:\\Users\\Altair\\Desktop\\Proy\\ficheroLog.txt";
		String rutaArchivo = "C:\\Users\\clase\\Desktop\\Versiones java\\ficheroLog.txt";
		
		try {
            FileWriter escritor = new FileWriter(rutaArchivo, true); // true para que agregue al final del archivo
            
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String fechaHoraActual = formatoFecha.format(new Date());
            
            String contenidoConFecha = fechaHoraActual + ": " + contenido + "\n";
            
            escritor.write(contenidoConFecha);
            escritor.close();
            //System.out.println("Se ha escrito una línea en el archivo exitosamente.");
        } catch (IOException e) {
            //System.out.println("Ha ocurrido un error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
        }//comentar
    }
}
