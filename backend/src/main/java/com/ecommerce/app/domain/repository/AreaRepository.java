package com.ecommerce.app.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.app.domain.models.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer>{
	Optional<Area> findByNombre(String nombre);
}
