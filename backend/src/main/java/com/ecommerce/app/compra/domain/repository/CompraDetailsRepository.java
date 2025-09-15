package com.ecommerce.app.compra.domain.repository;

import com.ecommerce.app.compra.domain.models.CompraDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraDetailsRepository extends JpaRepository<Integer, CompraDetails> {
    
}
