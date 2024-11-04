package mediatecaapp;

public class Revista extends Material {
    private String editorial;
    private String periodicidad;
    private String fechaPublicacion;

    public Revista(String codigo, String titulo, String editorial, String periodicidad, String fechaPublicacion, int unidadesDisponibles) {
        super(codigo, titulo, unidadesDisponibles);
        this.editorial = editorial;
        this.periodicidad = periodicidad;
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getEditorial() {
        return editorial;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
}
