package com.tienda5.dao;

import java.util.List;

import jakarta.persistence.*;

/**
 * Entidad representada en la BD como la tabla usuarios (modelo virtual) DAO
 */
@Entity
@Table(name = "usuarios", schema = "gestion_usu")
public class UsuarioDAO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario", nullable = false)
	private long id;

	@Column(name = "nom_usuario", nullable = true, length = 50)
	private String nombre;

	@Column(name = "mvl_usuario", nullable = true, unique = true)
	private String movil;

	@Column(name = "email_usuario", nullable = false, unique = true)
	private String email;

	@Column(name = "clv_usuario", nullable = false)
	private String clave;

	@Column(name = "rol_usuario", nullable = true)
	private String rol;
	
	@Column(name = "img_producto", nullable = true)
	private String imagen;
	
	
	//relaciones
	
	//usuario_carrito
	@OneToOne(mappedBy = "usuario")
	private CarritoDAO carrito;
    
	//usuario-compras
	@OneToMany(mappedBy = "usuario")
	private List<CompraDAO> compra;

        
	//objetos
	public UsuarioDAO() {
		super();
	}

	public UsuarioDAO(long id, String nombre, String movil, String email, String clave, String rol, String imagen) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.movil = movil;
		this.email = email;
		this.clave = clave;
		this.rol = rol;
		this.imagen = imagen;
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
	
}
