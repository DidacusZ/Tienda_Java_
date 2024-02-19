package com.tienda5.servicios;

import java.util.List;

import com.tienda5.dao.UsuarioDAO;
import com.tienda5.dto.UsuarioDTO;

public interface UsuarioDAOaDTOInterfaz {

	/**
	 * Covierte usuarios de DAO a DTO
	 * @param usuarioDAO
	 * @return
	 */
	public UsuarioDTO usuarioDTOaDAO(UsuarioDAO  usuarioDAO);
	
	
	/**
	 * Conviete una lista de DAOs en una de DTOs
	 * @param listaUsuarioDAO
	 * @return
	 */
	public List<UsuarioDTO> listaUsuarioDAOaDTO(List<UsuarioDAO> listaUsuarioDAO);
	
}
