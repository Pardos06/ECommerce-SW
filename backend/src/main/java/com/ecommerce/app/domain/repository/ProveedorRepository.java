package com.ecommerce.app.domain.repository;

import com.ecommerce.app.domain.models.Proveedor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
	@Query(value = """
		    SELECT p.*
		    FROM proveedor p
		    INNER JOIN tipo_proveedor tp ON p.tipo_proveedor_id = tp.id
		    WHERE LOWER(tp.nombre) LIKE LOWER(CONCAT('%', :tipoNombre, '%'))
		""", nativeQuery = true)
	List<Proveedor> buscarPorTipoProveedor(@Param("tipoNombre") String tipoNombre);
}
