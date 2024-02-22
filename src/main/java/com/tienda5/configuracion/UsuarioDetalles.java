package com.tienda5.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tienda5.dao.UsuarioDAO;
import com.tienda5.repositorios.UsuarioRepositorio;

@Service("usuarioDetalles")
public class UsuarioDetalles implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio usuRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	    System.out.printf("\nIntento de inicio de sesión para el usuario: %s\n", username);

		//El nombre de usuario en la aplicación es el email
		UsuarioDAO usuario = usuRepo.findFirstByEmail(username);
		
	    // Verificar si el usuario existe en la base de datos
	    if (usuario == null) {
	        throw new UsernameNotFoundException("Usuario no encontrado: " + username);
	    }
	    
		//Construir la instancia de UserDetails con los datos del usuario
		UserBuilder contructor = null;

	    	System.out.printf("\nUsuario encontrado en la base de datos: %s\n", usuario.getEmail());

	    	contructor = User.withUsername(username);
	    	contructor.disabled(false);
			contructor.password(usuario.getClave());
			contructor.authorities(usuario.getRol());

		return contructor.build();
	}
}
