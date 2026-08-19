package org.example;

public class NodoHash {

    //Atributos del NodoHash
    private Libro libro;
    NodoHash siguiente;

    //Constructor
    public NodoHash(Libro libro) {
        this.libro = libro;
        this.siguiente = null;
    }

    //Getters y Setters
    public Libro getLibro(){
        return libro;
    }

    public NodoHash getSiguiente() {
        return siguiente;
    }

}
