package com.ecommerce.app.catalogo.domain.repository;

import com.ecommerce.app.catalogo.domain.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Integer, Producto> {
    
}
