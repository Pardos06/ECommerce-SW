package com.ecommerce.app.pago.domain.repository;

import com.ecommerce.app.pago.domain.models.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetodoPagoRepository extends JpaRepository<Integer, MetodoPago> {
    
}
