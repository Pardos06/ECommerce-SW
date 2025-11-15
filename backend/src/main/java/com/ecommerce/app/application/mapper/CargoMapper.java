package com.ecommerce.app.application.mapper;

import com.ecommerce.app.application.dto.request.CargoRequest;
import com.ecommerce.app.application.dto.response.CargoResponse;
import com.ecommerce.app.domain.models.Cargo;

public class CargoMapper {
	public static Cargo toEntity(CargoRequest request) {
		if (request == null) {
			return null;
		}
		
		Cargo cargo = new Cargo();
		cargo.setId(request.id());
		cargo.setNombre(request.nombre());
		cargo.setDescripcion(request.descrippcion());
		
		return cargo;
	}
	
	public static CargoResponse toResponse(Cargo cargo) {
		if (cargo == null) {
			return null;
		}
		
		return new CargoResponse(
				cargo.getId(),
				cargo.getNombre(),
				cargo.getDescripcion()
		);		
	}
}
