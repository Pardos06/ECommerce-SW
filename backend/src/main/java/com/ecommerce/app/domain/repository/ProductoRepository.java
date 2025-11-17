package com.ecommerce.app.domain.repository;

import com.ecommerce.app.domain.models.Producto;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	@Query("""
	        SELECT p 
	        FROM Producto p 
	        WHERE p.nombre LIKE LOWER(CONCAT('%', :nombre, '%')) AND p.disponibilidad = 'Disponible'
	    """)
	List<Producto> buscarProductosDisponibles(@Param("nombre") String nombre);
}
