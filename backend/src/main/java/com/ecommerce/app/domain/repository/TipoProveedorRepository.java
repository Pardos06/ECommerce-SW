package com.ecommerce.app.domain.repository;

import com.ecommerce.app.domain.models.TipoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoProveedorRepository extends JpaRepository<TipoProveedor, Integer> {
    Optional<TipoProveedor> findByNombre(String nombre);
    List<TipoProveedor> findByNombreContainingIgnoreCase(String nombre);
}
