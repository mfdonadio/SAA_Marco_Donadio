package org.example;

public class NodoHeap {

    //Atributos del NodoHeap
    private Libro libro;
    NodoHeap izquierdo;
    NodoHeap derecho;
    NodoHeap padre;

    //Constructor
    public NodoHeap(Libro libro) {
        this.libro = libro;
        this.izquierdo = null;
        this.derecho = null;
        this.padre = null;
    }

    //Getters y Setters
    public Libro getLibro() {
        return libro;
    }
    public NodoHeap getIzquierdo() {
        return izquierdo;
    }
    public NodoHeap getDerecho() {
        return derecho;
    }
    public NodoHeap getPadre() {
        return padre;
    }

    //En el heap es necesario que se intercambien los nodos. Ya sea cuando se hace BurbujaArriba o BurbujaAbajo.
    public void intercambiarLibro(NodoHeap otro){
        //Si no existe otro nodo, no hacemos nada
        if(otro == null) return;

        //Si sí existe otro nodo, procedemos a intercambiar
        Libro temporal = this.libro;
        this.libro = otro.libro;
        otro.libro = temporal;
    }
}
