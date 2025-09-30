package com.ecommerce.app.domain.repository;

import com.ecommerce.app.domain.models.Cliente;
import com.ecommerce.app.domain.models.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Integer> {
    List<Orden> findByCliente(Cliente cliente);
}
