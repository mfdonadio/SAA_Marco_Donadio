package org.example;

public class Libro {

    //Atributos del objeto org.example.Libro
    private String isbn;
    private String titulo;
    private String autor;
    private String editorial;
    private int anio;
    private String categoria;
    private int cantidadDisponible;
    private int cantidadPrestada;

    //Constructor
    public Libro(String isbn, String titulo, String autor, String editorial, int anio,
                 String categoria, int cantidadDisponible, int cantidadPrestada) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anio = anio;
        this.categoria = categoria;
        this.cantidadDisponible = cantidadDisponible;
        this.cantidadPrestada = cantidadPrestada;
    }

    //Getters y Setters
    public String getIsbn() {
        return isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public int getAnio() {
        return anio;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public int getCantidadPrestada() {
        return cantidadPrestada;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public void setCantidadPrestada(int cantidadPrestada) {
        this.cantidadPrestada = cantidadPrestada;
    }

    public void sumarDisponibles(int cantidad){
        this.cantidadDisponible += cantidad;
    }

    public void sumarPrestados(int cantidad){
        this.cantidadPrestada += cantidad;
    }

    /*Dados los requerimientos de proyecto, va a llegar un punto en el que tengamos que actualizar la informacion.
        Por lo tanto, decidi crear un nuevo constructor que me permita "actualizar los datos"
     */
    public void actualizarDatos(String titulo, String autor, String editorial, int anio,
                                String categoria, int cantidadDisponible, int cantidadPrestada){
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anio = anio;
        this.categoria = categoria;
        this.cantidadDisponible = cantidadDisponible;
        this.cantidadPrestada = cantidadPrestada;
    }

    //Por ultimo, toString para poder mostrar la informacion de cada libro de manera limpia y ordenada.
    @Override
    public String toString() {
        return "org.example.Libro{" +
                "isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", editorial='" + editorial + '\'' +
                ", anio=" + anio +
                ", categoria='" + categoria + '\'' +
                ", cantidadDisponible=" + cantidadDisponible +
                ", cantidadPrestada=" + cantidadPrestada +
                '}';
    }
}
