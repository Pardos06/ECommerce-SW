package com.ecommerce.app.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.app.application.dto.request.AreaRequest;
import com.ecommerce.app.application.dto.response.AreaResponse;
import com.ecommerce.app.application.mapper.AreaMapper;
import com.ecommerce.app.domain.models.Area;
import com.ecommerce.app.domain.repository.AreaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AreaService {
	
	private final AreaRepository areaRepository;

	public AreaService(AreaRepository areaRepository) {
		this.areaRepository = areaRepository;
	}
	
	public List<AreaResponse> listarAreas() {
		return areaRepository.findAll()
				.stream()
				.map(AreaMapper::toResponse)
				.toList();
	}
	
	public AreaResponse obtenerPorId(int id) {
		Area area =  areaRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("No hay un área con el ID " + id));
		return AreaMapper.toResponse(area);
	}
	
	@Transactional
	public AreaResponse registrarArea(AreaRequest request) {
		Optional<Area> areaExistente = areaRepository.findByNombre(request.nombre());
		
		if (areaExistente.isPresent()) {
			throw new IllegalArgumentException("Ya existe un área con el nombre " + request.nombre());
		}
		
		Area area = AreaMapper.toEntity(request);
		area.setId(null);
		Area areaGuardada = areaRepository.save(area);
		
		return AreaMapper.toResponse(areaGuardada);
	}
	
	@Transactional
	public AreaResponse editarArea(AreaRequest request) {
		Area area = areaRepository.findById(request.id())
				.orElseThrow(() -> new EntityNotFoundException("Área no encontrada con ID " + request.id()));
		
		area.setNombre(request.nombre());
		area.setDescripcion(request.descripcion());
		
		areaRepository.save(area);
		
		return AreaMapper.toResponse(area);
	}
	
	@Transactional
	public void eliminarArea(int id) {
		Area area = areaRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Área no encontrada con ID " + id));
		
		if (area.getEmpleados() != null || !area.getEmpleados().isEmpty()) {
			throw new IllegalStateException("No se puede eliminar un cargo asociado a un empleado.");
		}
		
		areaRepository.deleteById(id);
	}
	
}
