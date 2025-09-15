package com.ecommerce.app.usuario.domain.repository;

import com.ecommerce.app.usuario.domain.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Integer, Empleado> {
    
}
