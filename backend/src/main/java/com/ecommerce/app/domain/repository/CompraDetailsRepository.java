package com.ecommerce.app.domain.repository;

import com.ecommerce.app.domain.models.CompraDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraDetailsRepository extends JpaRepository<CompraDetails, Integer> {
    
}
