package com.ecommerce.app.catalogo.domain.repository;

import com.ecommerce.app.catalogo.domain.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Integer, Categoria> {
    
}
