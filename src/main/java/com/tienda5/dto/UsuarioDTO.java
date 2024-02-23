package com.tienda5.dto;

/**
 * DTO usado para pasar la informacion del usuario entre capas (de la vista al
 * dao y del dao a la BD)
 */
public class UsuarioDTO {

	private long id;
	private String nombre;
	private String movil;
	private String email;
	private String clave;
	private String rol;
	private byte[] imagen;

	public UsuarioDTO() {
		super();
	}

	public UsuarioDTO(long id, String nombre, String movil, String email, String clave, String rol, byte[] imagen) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.movil = movil;
		this.email = email;
		this.clave = clave;
		this.rol = rol;
		this.imagen = imagen;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	
}
