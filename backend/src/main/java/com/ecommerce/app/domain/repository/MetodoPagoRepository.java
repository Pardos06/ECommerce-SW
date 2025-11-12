package com.ecommerce.app.domain.repository;

import com.ecommerce.app.domain.models.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {
    Optional<MetodoPago> findByNombre(String nombre);
    List<MetodoPago> findByNombreContainingIgnoreCase(String nombre);
}
