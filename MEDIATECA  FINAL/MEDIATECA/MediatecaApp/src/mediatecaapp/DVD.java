package mediatecaapp;

public class DVD extends Material {
    private String director;
    private String duracion;
    private String genero;

    public DVD(String codigo, String titulo, String director, String duracion, String genero, int unidadesDisponibles) {
        super(codigo, titulo, unidadesDisponibles);
        this.director = director;
        this.duracion = duracion;
        this.genero = genero;
    }

    public String getDirector() {
        return director;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getGenero() {
        return genero;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
