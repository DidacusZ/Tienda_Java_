package com.tienda5.servicios;

import com.tienda5.dto.UsuarioDTO;

public interface UsuarioServicioInterfaz {

	/**
	 * Recibe un DTO y guarda el usuario en BD (DAO)
	 * @param usuarioDTO
	 * @return
	 */
	public UsuarioDTO guardarUsuario(UsuarioDTO usuarioDTO);
}
