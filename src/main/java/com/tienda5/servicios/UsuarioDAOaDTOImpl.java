package com.tienda5.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tienda5.Fichero.FicheroLog;
import com.tienda5.dao.UsuarioDAO;
import com.tienda5.dto.UsuarioDTO;

/**
 * Servicio que implementa los metodos de la interface {@link UsuarioDAOaDTOInterfaz}
 * Logica para pasar de dao a dto
 * dpm 25/01/24
 */
@Service
public class UsuarioDAOaDTOImpl implements UsuarioDAOaDTOInterfaz {

	@Override
	public UsuarioDTO usuarioDTOaDAO(UsuarioDAO usuarioDAO) {

		try {
			UsuarioDTO usuarioDto = new UsuarioDTO();
			usuarioDto.setNombre(usuarioDAO.getNombre());
			usuarioDto.setMovil(usuarioDto.getMovil());
			usuarioDto.setEmail(usuarioDAO.getEmail());
			usuarioDto.setClave(usuarioDAO.getClave());
			//imagen

			return usuarioDto;
		} catch (Exception ex) {
			System.err.println("\n[ERROR] [UsuarioDAOaDTOImpl-usuarioDTOaDAO()] - Al convertir UsuarioDAO a UsuarioDTO: " + ex);
			FicheroLog.escribir("[ERROR] [UsuarioDAOaDTOImpl-usuarioDTOaDAO()] - Al convertir UsuarioDAO a UsuarioDTO");
			return null;
		}
	}

	@Override
	public List<UsuarioDTO> listaUsuarioDAOaDTO(List<UsuarioDAO> listaUsuarioDAO) {

		List<UsuarioDTO> listaUsuDto = new ArrayList<UsuarioDTO>();
		
		try {			
			for(UsuarioDAO usuDao : listaUsuarioDAO)
				listaUsuDto.add(usuarioDTOaDAO(usuDao));			
			return listaUsuDto;
			
		}catch(Exception ex) {
			System.err.println("\n[ERROR] [UsuarioDAOaDTOImpl-listaUsuarioDAOaDTO()] - Al convertir la lista de usuarios de DAO a DTO: " + ex);
			FicheroLog.escribir("[ERROR] [UsuarioDAOaDTOImpl-listaUsuarioDAOaDTO()] - Al convertir la lista de usuarios de DAO a DTO");
			return null;
		}		
	}

}
