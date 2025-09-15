package com.ecommerce.app.orden.domain.repository;

import com.ecommerce.app.orden.domain.models.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenRepository extends JpaRepository<Integer, Orden> {
    
}
