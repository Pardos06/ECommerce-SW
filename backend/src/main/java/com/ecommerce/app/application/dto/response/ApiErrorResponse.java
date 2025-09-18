package com.ecommerce.app.application.dto.response;

import java.time.LocalDateTime;

public class ApiErrorResponse {
    private int status;
    private String mensaje;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ApiErrorResponse(int status, String mensaje) {
        this.status = status;
        this.mensaje = mensaje;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
