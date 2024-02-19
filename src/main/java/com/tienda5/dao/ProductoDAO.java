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

@Entity
@Table(name = "productos", schema = "logica")
public class ProductoDAO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_producto", nullable = false)
	private long id;

	@Column(name = "nom_producto", nullable = true)
	private String nombre;

	@Column(name = "desc_producto", nullable = true)
	private String descripcion;

	@Column(name = "precio_producto", nullable = false)
	private String precio;

	@Column(name = "cant_producto", nullable = false)
	private int cantidad;

	@Column(name = "img_producto", nullable = true)
	private String imagen;
	
	//relaciones
	
	//productos-carritos
	@ManyToMany
    @JoinTable(
        name = "productos_carritos",
        joinColumns = @JoinColumn(name = "id_producto"),//relación de esta entidad
        inverseJoinColumns = @JoinColumn(name = "id_carrito")//relación de la otra entidad
    )
    private List<CarritoDAO> carrito;
	
	
}
