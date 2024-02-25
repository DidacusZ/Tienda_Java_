package com.tienda5.dto;

import java.util.Calendar;

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
	private String imagen;//Base64
	
	private String token;
	private Calendar fechaExpiracionToken;
	private boolean cuentaConfirmada;
	
	//constructor
	public UsuarioDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	//getters y setters
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

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Calendar getFechaExpiracionToken() {
		return fechaExpiracionToken;
	}

	public void setFechaExpiracionToken(Calendar fechaExpiracionToken) {
		this.fechaExpiracionToken = fechaExpiracionToken;
	}

	public boolean isCuentaConfirmada() {
		return cuentaConfirmada;
	}

	public void setCuentaConfirmada(boolean cuentaConfirmada) {
		this.cuentaConfirmada = cuentaConfirmada;
	}

	
}
