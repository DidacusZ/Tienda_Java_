package com.tienda5.dao;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Entidad representada en la BD como la tabla carritos (modelo virtual) DAO
 */
@Entity
@Table(name = "carritos", schema = "gestion_logica_negocio")
public class CarritoDAO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_carrito", nullable = false)
	private long id;

	@Column(name = "cant_carrito", nullable = false)
	private int cantidad;

	//carrito-usuario	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario")				/*, referencedColumnName = "id", nullable = false,
            									oreignKey = @ForeignKey(name = "fk_carrito_usuario", foreignKeyDefinition = "FOREIGN KEY (id_usuario) REFERENCES usuario(id) ON DELETE CASCADE")*/
    private UsuarioDAO usuario;
	
}
