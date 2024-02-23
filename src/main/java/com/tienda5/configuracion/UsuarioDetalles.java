package com.tienda5.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tienda5.Fichero.FicheroLog;
import com.tienda5.dao.UsuarioDAO;
import com.tienda5.repositorios.UsuarioRepositorio;

/**
 * Delega en spring la autenticación y la autorización de la aplicación
 */
@Service("usuarioDetalles")
public class UsuarioDetalles implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio usuRepo;

	/**
	 * Se sobrescribe para que Spring haga la autenticación del usuario
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

	    System.out.printf("\nEl usuario %s esta intentando iniciar sesión\n", email);//formato
	    FicheroLog.escribir("El usuario " +email+ " esta intentando iniciar sesión");

		//Busca un usuario por el email
		UsuarioDAO usuario = usuRepo.findFirstByEmail(email);
		
	    //Comprueba si el usuario existe
	    if (usuario == null) {
	    	FicheroLog.escribir("El usuario " +email+ " no existe");
	        throw new UsernameNotFoundException("Usuario no encontrado: " + email);        
	    }
		//Construir la instancia con datos del usuario
		UserBuilder contructor = null;
	    	System.out.printf("\nEl suario %s ha iniciado sesión\n", usuario.getEmail());
	    	FicheroLog.escribir("El usuario " +email+ " ha iniciado sesión");

	    	contructor = User.withUsername(email);
	    	contructor.disabled(false);
			contructor.password(usuario.getClave());
			contructor.authorities(usuario.getRol());

		return contructor.build();
	}
}
