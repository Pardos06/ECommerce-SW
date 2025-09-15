package com.ecommerce.app.compra.domain.repository;

import com.ecommerce.app.compra.domain.models.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Integer, Proveedor> {
    
}
