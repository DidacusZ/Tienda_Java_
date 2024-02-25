package com.tienda5.dao;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * Entidad representada en la BD como la tabla productos (modelo virtual) DAO
 */
@Entity
@Table(name = "productos", schema = "gestion_logica_negocio")
public class ProductoDAO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_producto", nullable = false)
	private long id;

	@Column(name = "nom_producto", nullable = false)
	private String nombre;

	@Column(name = "desc_producto", nullable = false)
	private String descripcion;

	@Column(name = "precio_producto", nullable = false)
	private String precio;

	@Column(name = "cant_producto", nullable = false)
	private int cantidad;

	@Column(name = "img_producto", nullable = true)//imagen real bytes
	private byte[] imagen;
	
	//relaciones
	
	//productos-carritos
	@ManyToMany
    @JoinTable(
        name = "productos_carritos",
        schema = "gestion_logica_negocio",
        joinColumns = @JoinColumn(name = "id_producto"),//relación de esta entidad
        inverseJoinColumns = @JoinColumn(name = "id_carrito")//relación de la otra entidad
    )
    private List<CarritoDAO> carrito;
	
	
}
