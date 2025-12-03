package com.ecommerce.app.application.dto.request;
import java.util.List;

public record OrdenCompletaRequest(
    OrdenRequest orden,
    List<OrdenDetailsRequest> detalles
) {}
