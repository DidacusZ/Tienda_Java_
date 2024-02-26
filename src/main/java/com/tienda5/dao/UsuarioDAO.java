package com.tienda5.dao;

import java.util.Calendar;
import java.util.List;

import jakarta.persistence.*;

/**
 * Entidad representada en la BD como la tabla usuarios (modelo virtual) DAO
 */
@Entity
@Table(name = "usuarios", schema = "gestion_usuarios")
public class UsuarioDAO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario", nullable = false)
	private long id;

	@Column(name = "nom_usuario", nullable = true, length = 50)
	private String nombre;

	@Column(name = "mvl_usuario", nullable = true)
	private String movil;

	@Column(name = "email_usuario", nullable = false, unique = true)
	private String email;

	@Column(name = "clv_usuario", nullable = false)
	private String clave;

	@Column(name = "rol_usuario", nullable = true)
	private String rol;
	
	@Column(name = "img_usuario", nullable = true)//imagen real bytes
	private byte[] imagen;	
	
	
	@Column(name = "token_recuperacion", nullable = true)
	private String token;
	
	@Column(name = "fch_expiracion_token", nullable = true)
	private Calendar fechaexpiracionToken;
	
	@Column(name = "cuenta_confirmada", nullable = true)//poner en false
	private boolean cuentaConfirmada;
	
	//relaciones
	
	//usuario_carrito
	@OneToOne(mappedBy = "usuario")
	private CarritoDAO carrito;
    
	//usuario-compras
	@OneToMany(mappedBy = "usuario")
	private List<CompraDAO> compra;

	//constructor
	public UsuarioDAO() {
		super();
	}

	//getters y settters
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Calendar getFechaExpiracionToken() {
		return fechaexpiracionToken;
	}

	public void setFechaExpiracionToken(Calendar fechaexpiracionToken) {
		this.fechaexpiracionToken = fechaexpiracionToken;
	}

	public boolean getCuentaConfirmada() {
		return cuentaConfirmada;
	}

	public void setCuentaConfirmada(boolean cuentaConfirmada) {
		this.cuentaConfirmada = cuentaConfirmada;
	}



}
