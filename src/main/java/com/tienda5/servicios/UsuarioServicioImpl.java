package com.tienda5.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
	 * Constructor de las interfaces, es necesario para implementarlas sin fallos
	 * @param usuarioRepositorio
	 * @param usuarioDTOaDAO
	 */
	public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio, UsuarioDTOaDAOInterfaz usuarioDTOaDAO) {
		super();
		this.usuarioRepositorio = usuarioRepositorio;
		this.usuarioDTOaDAO = usuarioDTOaDAO;
	}

	@Override
	public UsuarioDTO guardarUsuario(UsuarioDTO usuarioDTO) {

		//encriptar contraseña
		UsuarioDAO usuario = usuarioDTOaDAO.usuarioDTOaDAO(usuarioDTO);
		usuario.setRol("USUARIO");
		usuarioRepositorio.save(usuario);
		
		return usuarioDTO;
	}

	
	//spring security
	/*
    private String encriptarConBCrypt(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }*/
}
