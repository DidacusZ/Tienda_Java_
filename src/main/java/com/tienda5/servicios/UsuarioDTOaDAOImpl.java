package com.tienda5.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tienda5.dao.UsuarioDAO;
import com.tienda5.dto.UsuarioDTO;

/**
 * Servicio que implementa los metodos de la interface {@link UsuarioDTOaDAOInterfaz}
 * Logica para pasar de dto a dao
 * dpm 25/01/24
 */
@Service
public class UsuarioDTOaDAOImpl implements UsuarioDTOaDAOInterfaz {

	@Override
	public UsuarioDAO usuarioDTOaDAO(UsuarioDTO usuarioDTO) {

		try {
			UsuarioDAO usuarioDao = new UsuarioDAO();
			usuarioDao.setNombre(usuarioDTO.getNombre());
			usuarioDao.setMovil(usuarioDTO.getMovil());
			usuarioDao.setEmail(usuarioDTO.getEmail());
			usuarioDao.setClave(usuarioDTO.getClave());
			//imagen
			usuarioDao.setImagen(usuarioDTO.getImagen());
			
			return usuarioDao;
		} catch (Exception ex) {
			System.out.println("\n[ERROR] [UsuarioDTOaDAOImpl - usuarioDTOaDAO] - Al convertir UsuarioDTO a UsuarioDAO [return null]: " + ex);
			return null;
		}

	}

	@Override
	public List<UsuarioDAO> listaUsuarioDTOaDAO(List<UsuarioDTO> listaUsuarioDTO) {
		
		List<UsuarioDAO> listaUsuDao = new ArrayList<UsuarioDAO>();
		
		try {
			for(UsuarioDTO usuDto:listaUsuarioDTO)
				listaUsuDao.add(usuarioDTOaDAO(usuDto));			
			return listaUsuDao;
			
		}catch(Exception ex) {
			System.out.println("\n[ERROR] [UsuarioDTOaDAOImpl - usuarioDTOaDAO] - Al convertir la lista de usuarios de DTO a DAO [return null]: " + ex);
			return null;
		}

	}

}
