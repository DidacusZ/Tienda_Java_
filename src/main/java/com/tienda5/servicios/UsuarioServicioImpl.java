package com.tienda5.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tienda5.Fichero.FicheroLog;
import com.tienda5.dao.UsuarioDAO;
import com.tienda5.dto.UsuarioDTO;
import com.tienda5.repositorios.UsuarioRepositorio;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioServicioImpl implements UsuarioServicioInterfaz {

	//Interfaces necesarias para el servicio

	@Autowired//Facilita la gestión de los componentes de la aplicación. Inyeccion de dependencias automáticamente en un bean
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private BCryptPasswordEncoder encriptarClave;

	@Autowired
	private UsuarioDTOaDAOInterfaz usuarioDTOaDAO;	
	
	/**
	 * Inyección de dependencias
	 * Proporciona instancias de las interfaces cuando se crea esta instancia
	 * Recibir e inicializar las dependencias necesarias para que esta clase (servicio) funcione correctamente
	 * @param usuarioRepositorio
	 * @param encriptarClave
	 * @param usuarioDTOaDAO
	 */
	@Autowired
	public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio, BCryptPasswordEncoder encriptarClave,
			UsuarioDTOaDAOInterfaz usuarioDTOaDAO) {
		super();
		this.usuarioRepositorio = usuarioRepositorio;
		this.encriptarClave = encriptarClave;
		this.usuarioDTOaDAO = usuarioDTOaDAO;
	}
	

	@Override
	public UsuarioDTO guardarUsuario(UsuarioDTO usuarioDTO) {		
		
		try {			
			//Comprobamos si existe el email en BD
			UsuarioDAO usuEmail = usuarioRepositorio.findFirstByEmail(usuarioDTO.getEmail());

			if (usuEmail != null ) {
				System.err.printf("\n[ERROR] [UsuarioServicioImpl-guardarUsuario()] - El email: %s ya existe en la BD",usuarioDTO.getEmail());
				FicheroLog.escribir("[ERROR] [UsuarioServicioImpl-guardarUsuario()] - El email: " + usuarioDTO.getEmail() + " ya existe en la BD"+usuarioDTO);
				//excepcion de tiempo de ejecución
	            throw new IllegalArgumentException("\nEl email: " + usuarioDTO.getEmail() + " ya existe en la BD");	            
	        }
			/*
			if (usuEmail != null ) {
				System.err.printf("\n[ERROR] [UsuarioServicioImpl-guardarUsuario()] - El email: %s ya existe en la BD",usuarioDTO.getEmail());
				FicheroLog.escribir("[ERROR] [UsuarioServicioImpl-guardarUsuario()] - El email: " + usuarioDTO.getEmail() + " ya existe en la BD"+usuarioDTO);
				return usuarioDTO;
			}*/
			
			FicheroLog.escribir("[INFO] [UsuarioServicioImpl-guardarUsuario()] - El usuario " +usuarioDTO.getEmail()+ " esta intentando registrarse");	
			
			//encriptar contraseña
			usuarioDTO.setClave(encriptarClave.encode(usuarioDTO.getClave()));
			
			UsuarioDAO usuario = usuarioDTOaDAO.usuarioDTOaDAO(usuarioDTO);
			usuario.setRol("USUARIO");		
			//foto
			
			//confirmación de cuenta por token
			
			usuarioRepositorio.save(usuario);
			
			FicheroLog.escribir("[INFO] [UsuarioServicioImpl-guardarUsuario()] - El usuario " + usuarioDTO.getEmail() + " se ha registrado");			
			return usuarioDTO;
			
		}catch(Exception e){
			System.err.println("\n[ERROR] [UsuarioServicioImpl-guardarUsuario()] - Al guardar el usuario en BD: "+e);
			FicheroLog.escribir("[ERROR] [UsuarioServicioImpl-guardarUsuario()] - Al guardar el usuario en BD");
		}
		return null;
	}
	
	
	
	
	
	
}
