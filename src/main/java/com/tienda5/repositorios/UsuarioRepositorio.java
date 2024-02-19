package com.tienda5.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tienda5.dao.UsuarioDAO;

@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioDAO, Long> {

	public UsuarioDAO findFirstByEmail(String email);
}
