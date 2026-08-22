package org.example;

public class MinHeap {

    private NodoHeap raiz;
    private int cantidadNodos;

    public MinHeap() {
        raiz = null;
        cantidadNodos = 0;
    }

    public NodoHeap getRaiz() {
        return raiz;
    }

    public void insertar(Libro libro) {
        if (libro == null) {
            return;
        }

        cantidadNodos++;
        NodoHeap nuevo = new NodoHeap(libro);

        if (raiz == null) {
            raiz = nuevo;
            return;
        }

        int indiceNuevo = cantidadNodos;
        NodoHeap padre = obtenerNodoPorIndice(indiceNuevo / 2);

        if (indiceNuevo % 2 == 0) {
            padre.izquierdo = nuevo;
        } else {
            padre.derecho = nuevo;
        }

        nuevo.padre = padre;
        burbujaArriba(nuevo);
    }

    public void reevaluar(String isbn) {
        NodoHeap nodo = buscarNodo(raiz, isbn);

        if (nodo == null) {
            return;
        }

        if (nodo.padre != null
                && nodo.getLibro().getCantidadDisponible() < nodo.padre.getLibro().getCantidadDisponible()) {
            burbujaArriba(nodo);
        } else {
            burbujaAbajo(nodo);
        }
    }

    private void burbujaArriba(NodoHeap nodo) {
        while (nodo.padre != null
                && nodo.getLibro().getCantidadDisponible() < nodo.padre.getLibro().getCantidadDisponible()) {
            nodo.intercambiarLibro(nodo.padre);
            nodo = nodo.padre;
        }
    }

    private void burbujaAbajo(NodoHeap nodo) {
        while (nodo != null) {
            NodoHeap menor = nodo;

            if (nodo.izquierdo != null
                    && nodo.izquierdo.getLibro().getCantidadDisponible()
                    < menor.getLibro().getCantidadDisponible()) {
                menor = nodo.izquierdo;
            }

            if (nodo.derecho != null
                    && nodo.derecho.getLibro().getCantidadDisponible()
                    < menor.getLibro().getCantidadDisponible()) {
                menor = nodo.derecho;
            }

            if (menor == nodo) {
                break;
            }

            nodo.intercambiarLibro(menor);
            nodo = menor;
        }
    }

    private NodoHeap buscarNodo(NodoHeap actual, String isbn) {
        if (actual == null) {
            return null;
        }

        if (actual.getLibro().getIsbn().equals(isbn)) {
            return actual;
        }

        NodoHeap encontrado = buscarNodo(actual.izquierdo, isbn);
        if (encontrado != null) {
            return encontrado;
        }

        return buscarNodo(actual.derecho, isbn);
    }

    private NodoHeap obtenerNodoPorIndice(int indice) {
        if (indice == 1) {
            return raiz;
        }

        int divisor = 1;
        while (divisor * 2 <= indice) {
            divisor *= 2;
        }

        divisor /= 2;
        NodoHeap actual = raiz;

        while (divisor > 0 && actual != null) {
            if ((indice / divisor) % 2 == 0) {
                actual = actual.izquierdo;
            } else {
                actual = actual.derecho;
            }
            divisor /= 2;
        }

        return actual;
    }

    public void mostrar() {
        System.out.println("=========== MONTÍCULO MÍNIMO ===========");
        mostrarPreOrden(raiz, 0);
    }

    private void mostrarPreOrden(NodoHeap nodo, int nivel) {
        if (nodo != null) {
            System.out.println("Nodo (Nivel " + nivel + ") -> ISBN: "
                    + nodo.getLibro().getIsbn()
                    + " | Libro: " + nodo.getLibro().getTitulo()
                    + " | Disponibles: " + nodo.getLibro().getCantidadDisponible());

            mostrarPreOrden(nodo.izquierdo, nivel + 1);
            mostrarPreOrden(nodo.derecho, nivel + 1);
        }
    }
}
