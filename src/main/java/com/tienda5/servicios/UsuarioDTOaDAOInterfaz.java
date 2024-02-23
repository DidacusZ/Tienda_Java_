package com.tienda5.servicios;

import java.util.List;

import com.tienda5.dao.UsuarioDAO;
import com.tienda5.dto.UsuarioDTO;

public interface UsuarioDTOaDAOInterfaz {

	/**
	 * Convierte usuario de DTO a DAO
	 * @param usuarioDTO "DTO"
	 * @return usuarioDAO "DAO"
	 */
	public UsuarioDAO usuarioDTOaDAO(UsuarioDTO usuarioDTO);
	
	
	/**
	 * Convierte una lista de objetos usuarios DTOs en una de DAOs
	 * @param listaUsuarioDTO
	 * @return
	 */
	public List<UsuarioDAO> listaUsuarioDTOaDAO(List<UsuarioDTO> listaUsuarioDTO);
}
