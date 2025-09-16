package com.ecommerce.app.domain.repository;

import com.ecommerce.app.domain.models.TipoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoProveedorRepository extends JpaRepository<TipoProveedor, Integer> {
    
}
