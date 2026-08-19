package org.example;

public class NodoBMas {

    //Atributos del NodoBMas
    boolean esHoja;
    String[] claves;
    Libro[] libros;
    NodoBMas[] hijos;
    int cantidadClaves;
    NodoBMas siguiente;

    //Constructor
    public NodoBMas(boolean esHoja, int maximo) {
        this.esHoja = esHoja;
        this.claves = new String[maximo];
        this.libros = new Libro[maximo];
        this.hijos = new NodoBMas[maximo + 1];
        this.cantidadClaves = 0;
        this.siguiente = null;
    }

}
