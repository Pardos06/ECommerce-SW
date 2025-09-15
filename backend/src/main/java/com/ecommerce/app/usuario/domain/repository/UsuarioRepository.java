package com.ecommerce.app.usuario.domain.repository;

import com.ecommerce.app.usuario.domain.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Integer, Usuario> {
    
}
