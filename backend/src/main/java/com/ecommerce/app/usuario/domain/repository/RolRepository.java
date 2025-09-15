package com.ecommerce.app.usuario.domain.repository;

import com.ecommerce.app.usuario.domain.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Integer, Rol> {
    
}
