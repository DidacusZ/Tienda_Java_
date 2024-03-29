package com.tienda5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tienda5.Fichero.FicheroLog;


/**
 * Clase que contiene el metodo por la que se lanza la app
 */
@SpringBootApplication
public class Tienda5Application {

	/**
	 * Metodo por el se lanza la app
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SpringApplication.run(Tienda5Application.class, args);
		}catch(Exception e) {
			System.err.println("[ERROR] [Tienda5Application-main()]: "+e);
			FicheroLog.escribir("[ERROR] [Tienda5Application-main()]");
		}
		
		
	}

}
