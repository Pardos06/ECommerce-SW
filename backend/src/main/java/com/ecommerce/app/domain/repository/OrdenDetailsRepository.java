package com.ecommerce.app.domain.repository;

import com.ecommerce.app.domain.models.OrdenDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdenDetailsRepository extends JpaRepository<OrdenDetails, Integer> {
    
}
