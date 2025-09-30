package com.ecommerce.app.application.dto.response;

public class ProductoImagenResponse {
    private String nombreArchivo;
    private String url;

    public ProductoImagenResponse(String nombreArchivo, String url) {
        this.nombreArchivo = nombreArchivo;
        this.url = url;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
