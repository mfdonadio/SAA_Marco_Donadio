package org.example;

public class ArbolBMas {

    //Atributos del ArbolBMas
    private NodoBMas raiz;
    private final int orden = 2;
    private final int maximoClaves = (2 * orden) - 1;

    //Constructor (mas como incializador diria yo)
    public ArbolBMas(){
        //La raiz inicia como hoja.
        raiz = new NodoBMas(true, maximoClaves);
    }

    public boolean insertar(Libro libro){
        //Si el libro no existe o ya se encuentra en el arbol
        if (libro == null || buscarLibro(libro.getIsbn()) != null){
            return false;
        }

        //De lo contrario,  seteamos la raiz
        NodoBMas r = raiz;

        //Si la raiz se encuentra llena
        if(r.cantidadClaves == maximoClaves){
            //Creamos un nuevo nodo, pero esta vez como no hoja. Esto porque sube de nivel.
            NodoBMas nuevo = new NodoBMas(false, maximoClaves);
            //La raiz anterior pasa a ser el primer hijo de la nueva raiz
            nuevo.hijos[0] = r;
            //Dividimos el hijo lleno y hacemos una promocion de clave a la nueva raiz
            dividirHijo(nuevo, 0);
            //Se actualiza la referencia de la raiz del arbol
            raiz = nuevo;
        }

        //Y si se da el caso en que podemos insertar sin necesidad de hacer divisiones
        insertarNoLleno(raiz, libro);
        return true;
    }

    private void insertarNoLleno(NodoBMas nodo, Libro libro){
        //obtenemos la clave del libro
        String clave = libro.getIsbn();

        //Si el nodo es hoja
        if(nodo.esHoja){
            //Iniciando la isercion de atras para adelante
            int i = nodo.cantidadClaves - 1;

            // Recorremos las claves del nodo de derecha a izquierda
            // hasta encontrar la posición correcta para insertar la nueva clave
            while(i >= 0 && clave.compareTo(nodo.claves[i]) < 0){
                //Desplazamos la clave y el libro una posicion hacia la derecha
                nodo.claves[i + 1] = nodo.claves[i];
                nodo.libros[i + 1] = nodo.libros[i];
                //Continuamos la comparacion con lal clave anterior
                i--;
            }

            // Insertamos la nueva clave y su libro en la posición encontrada
            nodo.claves[i + 1] = clave;
            nodo.libros[i + 1] = libro;
            //Aumentamos la cantidad de claves almacenadas en el nodo.
            nodo.cantidadClaves++;
            return;
        }

        //Si no es hoja, buscamos el hijo correcto para insertar el libro
        int i = 0;
        //Siempre que el índice sea menor que la cantidad de claves y la clave mayor que la de la clave a la derecha
        while (i < nodo.cantidadClaves && clave.compareTo(nodo.claves[i]) >= 0){
            i++;
        }

        // Si el hijo donde se debe insertar está lleno, se divide antes de continuar.
        if(nodo.hijos[i].cantidadClaves == maximoClaves){
            // Divide el hijo en la posición i y promueve una clave al nodo actual.
            dividirHijo(nodo, i);
            // Después de dividir, decidimos si la clave debe insertarse en el hijo izquierdo o en el nuevo hijo derecho.
            if(clave.compareTo(nodo.claves[i]) >= 0){
                i++;
            }
        }

        //Llamada recursiva
        insertarNoLleno(nodo.hijos[i], libro);
    }

    //Divide un hijo lleno del nodo padre
    private void dividirHijo(NodoBMas padre, int indice) {
        //Obtenemos el hijo que sera dividido
        NodoBMas hijo = padre.hijos[indice];
        //Creamos un nuevo nodo del mismo tipo que el hijo
        NodoBMas nuevo = new NodoBMas(hijo.esHoja, maximoClaves);
        //Calculamos el punto de division
        int medio = orden - 1;

        //Desplazamos los hijos del padre para abrir espacio
        for (int j = padre.cantidadClaves; j >= indice + 1; j--){
            padre.hijos[j+1] = padre.hijos[j];
        }

        //Desplazamos las claves del padre para insertar la clave separadora
        for (int j = padre.cantidadClaves - 1; j >= indice; j--){
            padre.claves[j+1] = padre.claves[j];
        }

        //Si el hijo es hoja, movemos parte de sus claves al nuevo nodo hoja
        if (hijo.esHoja){
            //Calculamos cuantas claves se moveran al nuevo nodo
            int cantidadMover = hijo.cantidadClaves - medio;
            for (int j = 0; j < cantidadMover; j++){
                //Movemos la clave y su libro asociado
                nuevo.claves[j] = hijo.claves[medio + j];
                nuevo.libros[j] = hijo.libros[medio + j];

                //Limpiamos las posiciones movidas del nodo original
                hijo.claves[medio + j] = null;
                hijo.libros[medio + j] = null;
            }
            //Actualizamos la cantidad de claves en ambos nodos
            nuevo.cantidadClaves = cantidadMover;
            hijo.cantidadClaves = medio;

            //Enlazamos el nuevo nodo dentro de la lista de hojas
            nuevo.siguiente = hijo.siguiente;
            hijo.siguiente = nuevo;
        } else {
            //En nodos internos, la clave del medio sube al padre
            String claveSeparadora = hijo.claves[medio];
            //Calculamos cuantas claves quedaran en el nuevo nodo derecho
            int cantidadDerecha = hijo.cantidadClaves - medio - 1;

            //Movemos las claves a la derecha del medio hacia el nuevo nodo
            for(int j = 0; j < cantidadDerecha; j++){
                nuevo.claves[j] = hijo.claves[medio + j + 1];
                hijo.claves[medio + j + 1] = null;
            }

            //Movemos los hijos correspondientes al nuevo nodo interno
            for(int j = 0; j <= cantidadDerecha; j++){
                nuevo.hijos[j] = hijo.hijos[medio + 1 + j];
                hijo.hijos[medio + 1 + j] = null;
            }

            hijo.claves[medio] = null;
            nuevo.cantidadClaves = cantidadDerecha;
            hijo.cantidadClaves = medio;

            padre.claves[indice] = claveSeparadora;
            padre.hijos[indice + 1] = nuevo;
            padre.cantidadClaves++;
        }

    }

    //Metodos Auxiliares y de Funcionalidad
    public Libro buscarLibro(String isbn) {
        return buscarEnNodo(raiz, isbn);
    }

    public boolean buscar(String isbn) {
        return buscarLibro(isbn) != null;
    }

    private Libro buscarEnNodo(NodoBMas nodo, String isbn) {
        if (nodo == null) {
            return null;
        }

        if (nodo.esHoja) {
            for (int i = 0; i < nodo.cantidadClaves; i++) {
                if (nodo.claves[i].equals(isbn)) {
                    return nodo.libros[i];
                }
            }
            return null;
        }

        int i = 0;
        while (i < nodo.cantidadClaves && isbn.compareTo(nodo.claves[i]) >= 0) {
            i++;
        }

        return buscarEnNodo(nodo.hijos[i], isbn);
    }

    public void imprimir() {
        System.out.println("=========== ÁRBOL B+ ===========");
        imprimirNodo(raiz, 0, 1);
    }

    private int imprimirNodo(NodoBMas nodo, int nivel, int numeroNodo) {
        if (nodo == null) {
            return numeroNodo;
        }

        System.out.print("Nivel " + nivel + " | Nodo " + numeroNodo + ": [");

        for (int i = 0; i < nodo.cantidadClaves; i++) {
            System.out.print(nodo.claves[i]);
            if (i < nodo.cantidadClaves - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]" + (nodo.esHoja ? " HOJA" : " INTERNO"));

        int siguienteNumero = numeroNodo + 1;

        if (!nodo.esHoja) {
            for (int i = 0; i <= nodo.cantidadClaves; i++) {
                siguienteNumero = imprimirNodo(nodo.hijos[i], nivel + 1, siguienteNumero);
            }
        }

        return siguienteNumero;
    }

}
