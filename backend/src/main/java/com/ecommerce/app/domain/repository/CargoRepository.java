package com.ecommerce.app.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.app.domain.models.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Integer>{
	Optional<Cargo> findByNombre(String nombre);
}
