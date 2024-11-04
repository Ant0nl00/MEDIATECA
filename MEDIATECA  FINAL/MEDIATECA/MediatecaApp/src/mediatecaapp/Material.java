package mediatecaapp;

public class Material {
    private String codigoIdentificacion;
    private String titulo;
    private int unidadesDisponibles;

    // Constructor
    public Material(String codigoIdentificacion, String titulo, int unidadesDisponibles) {
        this.codigoIdentificacion = codigoIdentificacion;
        this.titulo = titulo;
        this.unidadesDisponibles = unidadesDisponibles;
    }

    // Getters y Setters
    public String getCodigoIdentificacion() {
        return codigoIdentificacion;
    }

    public void setCodigoIdentificacion(String codigoIdentificacion) {
        this.codigoIdentificacion = codigoIdentificacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getUnidadesDisponibles() {
        return unidadesDisponibles;
    }

    public void setUnidadesDisponibles(int unidadesDisponibles) {
        this.unidadesDisponibles = unidadesDisponibles;
    }
}
