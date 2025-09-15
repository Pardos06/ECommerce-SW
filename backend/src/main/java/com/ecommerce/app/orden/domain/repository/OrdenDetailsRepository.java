package com.ecommerce.app.orden.domain.repository;

import com.ecommerce.app.orden.domain.models.OrdenDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenDetailsRepository extends JpaRepository<Integer, OrdenDetails> {
    
}
