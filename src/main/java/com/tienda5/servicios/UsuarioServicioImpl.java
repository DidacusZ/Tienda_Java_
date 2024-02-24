package com.tienda5.servicios;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tienda5.Fichero.FicheroLog;
import com.tienda5.dao.UsuarioDAO;
import com.tienda5.dto.UsuarioDTO;
import com.tienda5.repositorios.UsuarioRepositorio;

import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioServicioImpl implements UsuarioServicioInterfaz {

	//Interfaces necesarias para el servicio

	@Autowired//Facilita la gestión de los componentes de la aplicación. Inyeccion de dependencias automáticamente en un bean
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private BCryptPasswordEncoder encriptarClave;

	@Autowired
	private UsuarioDTOaDAOInterfaz usuarioDTOaDAO;
	
	@Autowired
	private ImagenServicioInterfaz imagenServicioInterfaz;
	
	@Autowired
	private EmailServicioInterfaz emailServicioInterfaz;
	
	@Autowired
	private UsuarioDAOaDTOInterfaz usuarioDAOaDTO;

	/**
	 * Inyección de dependencias
	 * Proporciona instancias de las interfaces cuando se crea esta instancia
	 * Recibir e inicializar las dependencias necesarias para que esta clase (servicio) funcione correctamente
	 * @param usuarioRepositorio
	 * @param encriptarClave
	 * @param usuarioDTOaDAO
	 * @param imagenServicioInterfaz
	 * @param emailServicioInterfaz
	 * @param usuarioDAOaDTO
	 */
	public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio, BCryptPasswordEncoder encriptarClave,
			UsuarioDTOaDAOInterfaz usuarioDTOaDAO, ImagenServicioInterfaz imagenServicioInterfaz,
			EmailServicioInterfaz emailServicioInterfaz, UsuarioDAOaDTOInterfaz usuarioDAOaDTO) {
		super();
		this.usuarioRepositorio = usuarioRepositorio;
		this.encriptarClave = encriptarClave;
		this.usuarioDTOaDAO = usuarioDTOaDAO;
		this.imagenServicioInterfaz = imagenServicioInterfaz;
		this.emailServicioInterfaz = emailServicioInterfaz;
		this.usuarioDAOaDTO = usuarioDAOaDTO;
	}
	
	
	 
	@Override
	public UsuarioDTO guardarUsuario(UsuarioDTO usuarioDTO) {		
		
		try {

			//Comprobamos si existe el email en BD
			UsuarioDAO usuEmail = usuarioRepositorio.findFirstByEmail(usuarioDTO.getEmail());

			if (usuEmail != null ) {
				System.err.printf("\n[ERROR] [UsuarioServicioImpl-guardarUsuario()] - El email: %s ya existe en la BD\n",usuarioDTO.getEmail());
				FicheroLog.escribir("[ERROR] [UsuarioServicioImpl-guardarUsuario()] - El email: " + usuarioDTO.getEmail() + " ya existe en la BD"+usuarioDTO);
				//excepcion de tiempo de ejecución
	            throw new IllegalArgumentException("\nEl email: " + usuarioDTO.getEmail() + " ya existe en la BD");	            
	        }
			
			FicheroLog.escribir("[INFO] [UsuarioServicioImpl-guardarUsuario()] - El usuario " +usuarioDTO.getEmail()+ " esta intentando registrarse");	
			
			//encriptar contraseña
			usuarioDTO.setClave(encriptarClave.encode(usuarioDTO.getClave()));
			
			UsuarioDAO usuarioDao = usuarioDTOaDAO.usuarioDTOaDAO(usuarioDTO);
			usuarioDao.setRol("ROLE_USER");
			
			//foto
			if(usuarioDao.getImagen() == null)
				usuarioDao.setImagen(imagenServicioInterfaz.cargarFotoPredeterminada());			
						
			//confirmación de cuenta por token
			if (usuarioDTO.isCuentaConfirmada()) {//comprueba si esta confirmada
				usuarioDao.setCuentaConfirmada(true);
				usuarioRepositorio.save(usuarioDao);
			} else {
				usuarioDao.setCuentaConfirmada(false);
				// Generar token de confirmación
				String token = encriptarClave.encode(RandomStringUtils.random(30));
				usuarioDao.setToken(token);

				// Guardar el usuario en la base de datos
				usuarioRepositorio.save(usuarioDao);

				// Enviar el correo de confirmación
				String nombreUsuario = usuarioDao.getNombre();
				emailServicioInterfaz.emailConfirmarCuenta(usuarioDTO.getEmail(), nombreUsuario, token);
			}
			
			usuarioRepositorio.save(usuarioDao);
			
			FicheroLog.escribir("[INFO] [UsuarioServicioImpl-guardarUsuario()] - El usuario " + usuarioDTO.getEmail() + " se ha registrado");			
			return usuarioDTO;
			
		}catch(Exception e){
			System.err.println("[ERROR] [UsuarioServicioImpl-guardarUsuario()] - Al guardar el usuario en BD: "+e);
			FicheroLog.escribir("[ERROR] [UsuarioServicioImpl-guardarUsuario()] - Al guardar el usuario en BD");
		}
		return null;
	}

	@Override
	public boolean confirmarCuenta(String token) {
		try {
			//bucamos al usuario por el token
			UsuarioDAO usuarioDao = usuarioRepositorio.findByToken(token);

			usuarioDao.setCuentaConfirmada(true);//confirmamos la cuenta
			usuarioDao.setToken(null);//borramos el token
			usuarioRepositorio.save(usuarioDao);

			System.out.println("[INFO] [UsuarioServicioImpl-confirmarCuenta()] - Cuenta confirmada correctamente");
			FicheroLog.escribir("[INFO] [UsuarioServicioImpl-confirmarCuenta()] - Cuenta confirmada correctamente");
			return true;
		}catch(Exception e){
			System.err.println("[ERROR] [UsuarioServicioImpl-confirmarCuenta()] - Al confirmar la cuenta: "+e);
			FicheroLog.escribir("[ERROR] [UsuarioServicioImpl-confirmarCuenta()] - Al confirmar la cuenta");
		}
		return false;
	}


	@Override
	public boolean cambiarClavePorToken(UsuarioDTO usuarioDTO) {
		try {
			UsuarioDAO usuarioDao = usuarioRepositorio.findByToken(usuarioDTO.getToken());

			if (usuarioDao != null) {
				String nuevaClave = encriptarClave.encode(usuarioDTO.getClave());
				usuarioDao.setClave(nuevaClave);
				usuarioDao.setToken(null);//borramos el token
				usuarioRepositorio.save(usuarioDao);

				System.out.println("[INFO] [UsuarioServicioImpl-cambiarClavePorToken()] - Contraseña cambiada correctamente");
				FicheroLog.escribir("[INFO] [UsuarioServicioImpl-cambiarClavePorToken()] - Contraseña cambiada correctamente");
				return true;
			}

		} catch (Exception e) {
			System.err.println("[ERROR] [UsuarioServicioImpl-cambiarClavePorToken()] - Al cambiar la contraseña: "+e);
			FicheroLog.escribir("[ERROR] [UsuarioServicioImpl-cambiarClavePorToken()] - Al cambiar la contraseña");
		}
		return false;
	}


	@Override
	public UsuarioDTO encontrarUsuarioPorToken(String token) {

		try {
			UsuarioDAO usuarioDao = usuarioRepositorio.findByToken(token);

				UsuarioDTO usuario = usuarioDAOaDTO.usuarioDAOaDTO(usuarioDao);
				System.out.println("[INFO] [UsuarioServicioImpl-encontrarUsuarioPorToken()] - Usuario encontrado por token correctamente");
				FicheroLog.escribir("[INFO] [UsuarioServicioImpl-encontrarUsuarioPorToken()] - Usuario encontrado por token correctamente");
				return usuario;
				
		} catch (Exception e) {
			System.err.println("[ERROR] [UsuarioServicioImpl-encontrarUsuarioPorToken()] - Al encontrar a usuario por su token: "+e);
			FicheroLog.escribir("[ERROR] [UsuarioServicioImpl-encontrarUsuarioPorToken()] - Al encontrar a usuario por su token");
			return null;
		}
	}



	@Override
	public boolean iniciarCambioClave(String email) {
		try {
			UsuarioDAO usuarioDao = usuarioRepositorio.findFirstByEmail(email);

			if (usuarioDao != null) {
				//Creamos el token y la fecha de expiracion del mismo
				String token = encriptarClave.encode(RandomStringUtils.random(30));
				Calendar fechaExpiracion = Calendar.getInstance();
				fechaExpiracion.add(Calendar.MINUTE, 10);

				usuarioDao.setToken(token);
				usuarioDao.setFechaExpiracionToken(fechaExpiracion);

				usuarioRepositorio.save(usuarioDao);

				String nombreUsuario = usuarioDao.getNombre();
				emailServicioInterfaz.emailCambiarClave(email, nombreUsuario, token);
				
				System.out.println("[INFO] [UsuarioServicioImpl-iniciarCambioClave()] - Inicio el cambio de clave correcto (correo enviado)");
				FicheroLog.escribir("[INFO] [UsuarioServicioImpl-iniciarCambioClave()] - Inicio el cambio de clave correcto (correo enviado)");
				return true;

			} else {
				System.err.println("[ERROR] [UsuarioServicioImpl-iniciarCambioClave()] - El usuario con el email:"+email+" no existe");
				FicheroLog.escribir("[ERROR] [UsuarioServicioImpl-iniciarCambioClave()] - El usuario con el email:"+email+" no existe");
				return false;
			}
		} catch (Exception e) {
			System.err.println("[ERROR] [UsuarioServicioImpl-iniciarCambioClave()] - Al iniciar el cambio de clave: "+e);
			FicheroLog.escribir("[ERROR] [UsuarioServicioImpl-iniciarCambioClave()] - Al iniciar el cambio de clave");
			return false;
		}
	}



	@Override
	public List<UsuarioDTO> todosUsuarios() {
		return usuarioDAOaDTO.listaUsuarioDAOaDTO(usuarioRepositorio.findAll());
	}



	@Override
	public UsuarioDTO buscarPorId(long id) {
		try {
			UsuarioDAO usuarioDao = usuarioRepositorio.findById(id).orElse(null);
			if (usuarioDao != null) {
				return usuarioDAOaDTO.usuarioDAOaDTO(usuarioDao);
			}
		} catch (Exception e) {
			System.err.println("[ERROR] [UsuarioServicioImpl-buscarPorId()] - Al buscar por id"+e);
			FicheroLog.escribir("[ERROR] [UsuarioServicioImpl-buscarPorId()] - Al buscar por id");
		}
		return null;
	}



	@Override
	public void eliminarUsuario(long id) {
		try {
			UsuarioDAO usuarioDao = usuarioRepositorio.findById(id).orElse(null);
			if (usuarioDao != null) {
				usuarioRepositorio.delete(usuarioDao);
			}
		} catch (Exception e) {
			System.err.println("[ERROR] [UsuarioServicioImpl-eliminarUsuario()] - Al eliminar usuario por id"+e);
			FicheroLog.escribir("[ERROR] [UsuarioServicioImpl-eliminarUsuario()] - Al eliminar usauario por id");
		
		}
	}



	@Override
	public void editarUsuario(UsuarioDTO usuarioDTO) {
		try {
			UsuarioDAO usuarioDao = usuarioRepositorio.findById(usuarioDTO.getId()).orElse(null);

			usuarioDao.setNombre(usuarioDTO.getNombre());
			usuarioDao.setMovil(usuarioDTO.getMovil());
			usuarioDao.setRol(usuarioDTO.getRol());
			//foto
			//usuarioDao.setImagen(imagenServicioInterfaz.base64aArrayBYTES(usuarioDTO.getImagen()));

			usuarioRepositorio.save(usuarioDao);
		} catch (PersistenceException pe) {
			System.out.println("[Error UsuarioServicioImpl - actualizarUsuario()] Al modificar el usuario " + pe.getMessage());
			
		}
		
	}





}
