package com.tienda5.servicios;

import java.util.List;

import com.tienda5.dto.UsuarioDTO;

public interface UsuarioServicioInterfaz {

	/**
	 * Recibe un DTO y guarda el usuario en BD (DAO)
	 * @param usuarioDTO
	 * @return UsuarioDTO
	 */
	public UsuarioDTO guardarUsuario(UsuarioDTO usuarioDTO);
	
	
	/**
	 * Confirma la cuenta mediante el token
	 * @param token
	 * @return boolean
	 */
	public boolean confirmarCuenta(String token);
	
	
	/**
	 * Encuentra al usuario por su email y crea un token
	 * @param email
	 * @return
	 */
	public boolean iniciarCambioClave(String email);
	
	/**
	 * Cambia la clave mediante el token
	 * @param usuarioDTO
	 * @return boolean
	 */
	public boolean cambiarClavePorToken(UsuarioDTO usuarioDTO);
	
	
	/**
	 * Encuentra un usuario por su token y devuelve un dto
	 * @param token
	 * @return UsuarioDTO
	 */
	public UsuarioDTO encontrarUsuarioPorToken(String token);
	
		
	////CRUD////
	/**
	 * Lista de todos los usuarios de la app
	 * @return
	 */
	public List<UsuarioDTO> todosUsuarios();
	

	/**
	 * Busca un usuario por su id
	 * @param id
	 * @return
	 */
	public UsuarioDTO buscarPorId(long id);
	

	/**
	 * Elimina a un usuario por su id
	 * @param id
	 */
	public void eliminarUsuario(long id);
	

	/**
	 * Edita a un usuario
	 * @param usuarioDTO usuario que se edita
	 */
	public void editarUsuario(UsuarioDTO usuarioDTO);
	
	
	
	
	
	
	
	
	
}
