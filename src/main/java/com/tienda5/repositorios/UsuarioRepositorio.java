package com.tienda5.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda5.dao.UsuarioDAO;

/**
 * Repositorio de la entidad Usuario
 * Hereda de JpaRepository para poder hacer el CRUD
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioDAO, Long> {

	/**
	 * Encuentra a un usuario en BD por email
	 * @param email
	 * @return
	 */
	public UsuarioDAO findFirstByEmail(String email);	

}
