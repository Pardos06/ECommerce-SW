package com.ecommerce.app.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.app.application.dto.request.CargoRequest;
import com.ecommerce.app.application.dto.response.CargoResponse;
import com.ecommerce.app.application.mapper.CargoMapper;
import com.ecommerce.app.domain.models.Cargo;
import com.ecommerce.app.domain.repository.CargoRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CargoService {

	private final CargoRepository cargoRepository;

	public CargoService(CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
	}
	
	public List<CargoResponse> listarCargos() {
		return cargoRepository.findAll()
				.stream()
				.map(CargoMapper::toResponse)
				.toList();
	}
	
	public CargoResponse obtenerPorId(int id) {
		Cargo cargo =  cargoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("No hay un cargo con el ID " + id));
		return CargoMapper.toResponse(cargo);
	}
	
	@Transactional
	public CargoResponse registrarCargo(CargoRequest request) {
		Optional<Cargo> cargoExistente = cargoRepository.findByNombre(request.nombre());
		
		if (cargoExistente.isPresent()) {
			throw new IllegalArgumentException("Ya existe un cargo con el nombre " + request.nombre());
		}
		
		Cargo cargo = CargoMapper.toEntity(request);
		cargo.setId(null);
		Cargo cargoGuardado = cargoRepository.save(cargo);
		
		return CargoMapper.toResponse(cargoGuardado);
	}
	
	@Transactional
	public CargoResponse editarCargo(CargoRequest request) {
		Cargo cargo = cargoRepository.findById(request.id())
				.orElseThrow(() -> new EntityNotFoundException("Cargo no encontrado con ID " + request.id()));
		
		cargo.setNombre(request.nombre());
		cargo.setDescripcion(request.descrippcion());
		
		cargoRepository.save(cargo);
		
		return CargoMapper.toResponse(cargo);
	}
	
	@Transactional
	public void eliminarCargo(int id) {
		Cargo cargo = cargoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Cargo no encontrado con ID " + id));
		
		if (cargo.getEmpleados() != null || !cargo.getEmpleados().isEmpty()) {
			throw new IllegalStateException("No se puede eliminar un cargo asociado a un empleado.");
		}
		
		cargoRepository.deleteById(id);
	}
}
