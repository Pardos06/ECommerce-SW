package com.ecommerce.app.compra.domain.repository;

import com.ecommerce.app.compra.domain.models.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<Integer, Compra> {
    
}
