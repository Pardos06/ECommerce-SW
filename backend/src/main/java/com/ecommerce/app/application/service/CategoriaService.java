package com.ecommerce.app.application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.app.application.dto.request.CategoriaRequest;
import com.ecommerce.app.application.dto.response.CategoriaResponse;
import com.ecommerce.app.application.mapper.CategoriaMapper;
import com.ecommerce.app.domain.models.Categoria;
import com.ecommerce.app.domain.repository.CategoriaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaResponse> listarCategorias() {
        return categoriaRepository.findAll()
            .stream()
            .map(CategoriaMapper::toResponse)
            .collect(Collectors.toList());
    }

    public CategoriaResponse obtenerPorId(int id) {
        return categoriaRepository.findById(id)
            .map(CategoriaMapper::toResponse)
            .orElse(null);
    }

    public CategoriaResponse registrarCategoria(CategoriaRequest request) {
        Optional<Categoria> categoriaExistente = categoriaRepository.findByNombre(request.getNombre());

        if (categoriaExistente.isPresent()) {
            throw new IllegalArgumentException("La categoría con el nombre especificado ya existe: " + request.getNombre());
        }

        Categoria categoria = CategoriaMapper.toEntity(request);
        Categoria categoriaGuardada = categoriaRepository.save(categoria);

        return CategoriaMapper.toResponse(categoriaGuardada);
    }

    public CategoriaResponse editarCategoria(CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(request.getId())
            .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + request.getId()));

        categoria.setNombre(request.getNombre());

        categoriaRepository.save(categoria);

        return CategoriaMapper.toResponse(categoria);
    }

    public void eliminarCategoria(int id) {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));

        if (categoria.getProductos() != null || !categoria.getProductos().isEmpty()) {
            throw new IllegalStateException("No se puede eliminar una categoría asociada a un producto.");
        }

        categoriaRepository.delete(categoria);
    }
}
