package org.example;

public class TablaHash {

    private NodoHash[] tabla;
    private int cantidadElementos;

    public TablaHash() {
        tabla = new NodoHash[13];
        cantidadElementos = 0;
    }

    // La función hash trabaja directamente con el ISBN como String.
    private int hash(String isbn) {
        long valor = 0;

        for (int i = 0; i < isbn.length(); i++) {
            valor = (valor * 31 + isbn.charAt(i)) % tabla.length;
        }

        return (int) valor;
    }

    public boolean insertar(Libro libro) {
        if (libro == null) {
            return false;
        }

        if (buscar(libro.getIsbn()) != null) {
            return false;
        }

        // Se redimensiona antes de insertar para no saturar demasiado la tabla.
        if ((cantidadElementos + 1) * 100 / tabla.length >= 75) {
            redimensionar();
        }

        insertarSinValidar(libro);
        cantidadElementos++;
        return true;
    }

    private void insertarSinValidar(Libro libro) {
        int indice = hash(libro.getIsbn());
        NodoHash nuevo = new NodoHash(libro);

        // Encadenamiento propio para manejar colisiones.
        if (tabla[indice] == null) {
            tabla[indice] = nuevo;
        } else {
            NodoHash actual = tabla[indice];

            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }

            actual.siguiente = nuevo;
        }
    }

    public Libro buscar(String isbn) {
        int indice = hash(isbn);
        NodoHash actual = tabla[indice];

        while (actual != null) {
            if (actual.getLibro().getIsbn().equals(isbn)) {
                return actual.getLibro();
            }
            actual = actual.siguiente;
        }

        return null;
    }

    private void redimensionar() {
        NodoHash[] anterior = tabla;
        tabla = new NodoHash[(anterior.length * 2) + 1];

        for (int i = 0; i < anterior.length; i++) {
            NodoHash actual = anterior[i];

            while (actual != null) {
                insertarSinValidar(actual.getLibro());
                actual = actual.siguiente;
            }
        }
    }

    public int obtenerPosicion(String isbn) {
        Libro libro = buscar(isbn);

        if (libro == null) {
            return -1;
        }

        return hash(isbn);
    }

    public void mostrarTablaHash() {
        System.out.println("=========== TABLA HASH ===========");

        for (int i = 0; i < tabla.length; i++) {
            System.out.print("[" + i + "] ");

            if (tabla[i] == null) {
                System.out.println("VACÍA");
            } else {
                NodoHash actual = tabla[i];
                int colision = 0;

                while (actual != null) {
                    if (colision > 0) {
                        System.out.print(" -> ");
                    }

                    System.out.print("ISBN: " + actual.getLibro().getIsbn()
                            + " | " + actual.getLibro().getTitulo());

                    actual = actual.siguiente;
                    colision++;
                }

                if (colision > 1) {
                    System.out.print("  [" + (colision - 1) + " colisión(es)]");
                }

                System.out.println();
            }
        }
    }
}
