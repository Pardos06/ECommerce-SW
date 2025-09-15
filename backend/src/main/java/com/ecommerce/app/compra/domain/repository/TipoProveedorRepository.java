package com.ecommerce.app.compra.domain.repository;

import com.ecommerce.app.compra.domain.models.TipoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoProveedorRepository extends JpaRepository<Integer, TipoProveedor> {
    
}
