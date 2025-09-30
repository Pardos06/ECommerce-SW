package com.ecommerce.app.domain.repository;

import com.ecommerce.app.domain.models.Empleado;
import com.ecommerce.app.domain.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Optional<Empleado> findByUsuario(Usuario usuario);
}
