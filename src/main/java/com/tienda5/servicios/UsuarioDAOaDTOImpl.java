package com.tienda5.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private ImagenServicioInterfaz ImagenServicioInterfaz;
		
	public UsuarioDAOaDTOImpl(com.tienda5.servicios.ImagenServicioInterfaz imagenServicioInterfaz) {
		super();
		ImagenServicioInterfaz = imagenServicioInterfaz;
	}

	@Override
	public UsuarioDTO usuarioDAOaDTO(UsuarioDAO usuarioDAO) {

		try {
			UsuarioDTO usuarioDto = new UsuarioDTO();
			usuarioDto.setId(usuarioDAO.getId());
			usuarioDto.setNombre(usuarioDAO.getNombre());
			usuarioDto.setMovil(usuarioDAO.getMovil());
			usuarioDto.setEmail(usuarioDAO.getEmail());
			usuarioDto.setClave(usuarioDAO.getClave());
			usuarioDto.setRol(usuarioDAO.getRol());
			//imagen
			if(usuarioDAO.getImagen() != null) {
				System.out.println("[INFO] [UsuarioDAOaDTOImpl-usuarioDTOaDAO()] - convertir imagen a dto");
				FicheroLog.escribir("[INFO] [UsuarioDAOaDTOImpl-usuarioDTOaDAO()] - convertir imagen a dto");
				usuarioDto.setImagen(
						ImagenServicioInterfaz.ArrayBYTESaBase64(usuarioDAO.getImagen())
						);
			}
			
			//token y confirmacion
			usuarioDto.setToken(usuarioDAO.getToken());
			usuarioDto.setFechaExpiracionToken(usuarioDAO.getFechaExpiracionToken());
			usuarioDto.setCuentaConfirmada(usuarioDAO.getCuentaConfirmada());

			System.out.println("[INFO] [UsuarioDAOaDTOImpl-usuarioDTOaDAO()] - Conversión correcta de UsuarioDAO a UsuarioDTO");
			FicheroLog.escribir("[INFO] [UsuarioDAOaDTOImpl-usuarioDTOaDAO()] - Conversión correcta de UsuarioDAO a UsuarioDTO");
			
			return usuarioDto;
		} catch (Exception ex) {
			System.err.println("[ERROR] [UsuarioDAOaDTOImpl-usuarioDTOaDAO()] - Al convertir UsuarioDAO a UsuarioDTO: " + ex);
			FicheroLog.escribir("[ERROR] [UsuarioDAOaDTOImpl-usuarioDTOaDAO()] - Al convertir UsuarioDAO a UsuarioDTO");
			return null;
		}
	}

	@Override
	public List<UsuarioDTO> listaUsuarioDAOaDTO(List<UsuarioDAO> listaUsuarioDAO) {

		List<UsuarioDTO> listaUsuDto = new ArrayList<UsuarioDTO>();
		
		try {			
			for(UsuarioDAO usuDao : listaUsuarioDAO)
				listaUsuDto.add(usuarioDAOaDTO(usuDao));
			
			System.out.println("[INFO] [UsuarioDAOaDTOImpl-listaUsuarioDAOaDTO()] - Conversión correcta de lista de usuarios de DAO a DTO");
			FicheroLog.escribir("[INFO] [UsuarioDAOaDTOImpl-listaUsuarioDAOaDTO()] - Conversión correcta de lista de usuarios de DAO a DTO");
			
			return listaUsuDto;
			
		}catch(Exception ex) {
			System.err.println("[ERROR] [UsuarioDAOaDTOImpl-listaUsuarioDAOaDTO()] - Al convertir la lista de usuarios de DAO a DTO: " + ex);
			FicheroLog.escribir("[ERROR] [UsuarioDAOaDTOImpl-listaUsuarioDAOaDTO()] - Al convertir la lista de usuarios de DAO a DTO");
			return null;
		}		
	}

}
