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
		
		FicheroLog.escribir("El usuario " +usuarioDTO.getEmail()+ " esta intentando registrarse");
		//comprovar excepciones		
		
		//encriptar contraseña
		usuarioDTO.setClave(encriptarClave.encode(usuarioDTO.getClave()));
		
		UsuarioDAO usuario = usuarioDTOaDAO.usuarioDTOaDAO(usuarioDTO);
		usuario.setRol("USUARIO");
		usuarioRepositorio.save(usuario);
		
		FicheroLog.escribir("El usuario " + usuarioDTO.getEmail() + " se ha registrado");
		
		return usuarioDTO;
	}
	
}
