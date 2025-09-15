package com.ecommerce.app.usuario.domain.repository;

import com.ecommerce.app.usuario.domain.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Integer, Cliente> {
    
}
