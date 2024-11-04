package mediatecaapp;

public class CDDeAudio extends Material {
    private String artista;
    private String genero;
    private String duracion;
    private int numeroCanciones;

    public CDDeAudio(String codigo, String titulo, String artista, String genero, String duracion, int numeroCanciones, int unidadesDisponibles) {
        super(codigo, titulo, unidadesDisponibles);
        this.artista = artista;
        this.genero = genero;
        this.duracion = duracion;
        this.numeroCanciones = numeroCanciones;
    }

    public String getArtista() {
        return artista;
    }

    public String getGenero() {
        return genero;
    }

    public String getDuracion() {
        return duracion;
    }

    public int getNumeroCanciones() {
        return numeroCanciones;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public void setNumeroCanciones(int numeroCanciones) {
        this.numeroCanciones = numeroCanciones;
    }
}
