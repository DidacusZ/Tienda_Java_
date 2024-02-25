package com.tienda5.dao;

import java.util.Calendar;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidad representada en la BD como la tabla compras (modelo virtual) DAO
 */
@Entity
@Table(name = "compras", schema = "gestion_logica_negocio")
public class CompraDAO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_compra", nullable = false)
	private long id;

	@Column(name = "cant_compra", nullable = false)
	private int cantidad;

	@Column(name = "fch_compra", nullable = true)//date time
	private Calendar fecha;
	
	//relaciones
	
	//compras-usuario
	@ManyToOne
    @JoinColumn(name = "id_usuario")
    private UsuarioDAO usuario;
	
	//compras-productos
	@ManyToMany
    @JoinTable(
        name = "compras_productos",
        schema = "gestion_logica_negocio",
        joinColumns = @JoinColumn(name = "id_compra"),//relación de esta entidad
        inverseJoinColumns = @JoinColumn(name = "id_producto")//relación de la otra entidad
    )
    private List<ProductoDAO> producto;
}
