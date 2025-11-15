package com.ecommerce.app.application.mapper;


import com.ecommerce.app.application.dto.request.AreaRequest;
import com.ecommerce.app.application.dto.response.AreaResponse;
import com.ecommerce.app.domain.models.Area;

public class AreaMapper {
	public static Area toEntity(AreaRequest request) {
		if (request == null) {
			return null;
		}
		
		Area area = new Area();
		area.setId(request.id());
		area.setNombre(request.nombre());
		area.setDescripcion(request.descripcion());
		
		return area;		
	}
	
	public static AreaResponse toResponse(Area area) {
		if (area == null) {
			return null;
		}
		
		return new AreaResponse(
				area.getId(),
				area.getNombre(),
				area.getDescripcion()
		);
	}
}
