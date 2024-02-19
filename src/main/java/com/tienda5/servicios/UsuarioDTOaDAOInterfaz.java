package com.tienda5.servicios;

import java.util.List;

import com.tienda5.dao.UsuarioDAO;
import com.tienda5.dto.UsuarioDTO;

public interface UsuarioDTOaDAOInterfaz {

	/**
	 * Convierte objeto usuario de DTO a DAO
	 * @param usuarioDTO "DTO"
	 * @return usuarioDAO "DAO"
	 */
	public UsuarioDAO usuarioDTOaDAO(UsuarioDTO usuarioDTO);
	
	
	/**
	 * Convierte una lista de objetos usuarios(dto) dao
	 * @param listaUsuarioDTO
	 * @return
	 */
	public List<UsuarioDAO> listaUsuarioDTOaDAO(List<UsuarioDTO> listaUsuarioDTO);
}
