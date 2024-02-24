package com.tienda5.servicios;

import java.util.List;

import com.tienda5.dao.UsuarioDAO;
import com.tienda5.dto.UsuarioDTO;

public interface UsuarioDAOaDTOInterfaz {

	/**
	 * Covierte usuario de DAO a DTO
	 * @param usuarioDAO "DAO"
	 * @return UsuarioDTO "DTO"
	 */
	public UsuarioDTO usuarioDAOaDTO(UsuarioDAO  usuarioDAO);
	
	
	/**
	 * Conviete una lista de objetos usuarios DAOs en una de DTOs
	 * @param listaUsuarioDAO
	 * @return
	 */
	public List<UsuarioDTO> listaUsuarioDAOaDTO(List<UsuarioDAO> listaUsuarioDAO);
	
}
