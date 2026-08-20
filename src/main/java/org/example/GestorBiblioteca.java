package org.example;

public class GestorBiblioteca {

    //Atributos de la clase GestorBiblioteca
    private ArbolBMas arbolBMas;
    private TablaHash tablaHash;
    private MaxHeap maxHeap;
    private MinHeap minHeap;

    //Consructor
    public GestorBiblioteca(){
        arbolBMas = new ArbolBMas();
        tablaHash = new TablaHash();
        maxHeap = new MaxHeap();
        minHeap = new MinHeap();
    }

    //Resgistro o actualizacion de libros ----> debe aplicarse a todas las estructuras
    public void RegistrarOActualizarLibro(String isbn, String titulo, String autor,
                                          String editorial, int anio, String categoria,
                                          int cantidadDisponible, int cantidadPrestada){
        //Verificamos la existencia del libro
        Libro existente = tablaHash.buscar(isbn);

        //Si no existe, lo creamos.
        if(existente == null){
            Libro nuevo = new Libro(isbn, titulo, autor, editorial, anio, categoria,
                    cantidadDisponible, cantidadPrestada);
            tablaHash.insertar(nuevo);
            arbolBMas.insertar(nuevo);
            maxHeap.insertar(nuevo);
            minHeap.insertar(nuevo);
        } else {
            //Si existe, actualizamos
            existente.actualizarDatos(titulo, autor, editorial, anio, categoria,
                    cantidadDisponible, cantidadPrestada);

            /**En este caso, propongo que como el objeto ya existe en las estructuras y son sus aributos los que se
             * actualizan, no es necesario volver a insertarlo. Lo unico que si puede cambiar luego de la actualizacion
             * es que su prioridad tanto en el maxHeap como en el minHeap se vean modificadas, por lo tanto...*/
            //Reevaluamos la prioridad del libro usando su identificador unico: el isbn
            maxHeap.reevaluar(isbn);
            minHeap.reevaluar(isbn);

        }
    }

    public boolean registrarPrestamo(String isbn, String fecha, int cantidad){
        //Buscamos el libro. Lo hacemos enla tabla hash porque es la estructura mas eficiente de las 4 para hacerlo.
        Libro libro = tablaHash.buscar(isbn);

        //Si el libro no existe
        if(libro == null){
            return false;
        }

        //Si sí existe...
        //1. Sumamos la cantidad de libros prestados.
        libro.sumarPrestados(cantidad);
        //2. Reevaluamos su prioridad en el MaxHeap
        maxHeap.reevaluar(isbn);
        return true;
    }

    //Metodos auxiliares: busqueda de libros y muestra de estructuras en consola jeje :D
    public Libro buscarLibroHash(String isbn){
        return tablaHash.buscar(isbn);
    }
    public Libro buscarLibroArbol(String isbn) {
        return arbolBMas.buscarLibro(isbn);
    }

    public void mostrarArbolBMas() {
        arbolBMas.imprimir();
    }

    public void mostrarMaxHeap() {
        maxHeap.mostrar();
    }

    public void mostrarMinHeap() {
        minHeap.mostrar();
    }

    public void mostrarTablaHash() {
        tablaHash.mostrarTablaHash();
    }

    //Y para que pueda mostrar todas las estructuras...
    public void mostrarTodo() {
        mostrarArbolBMas();
        System.out.println();
        mostrarMaxHeap();
        System.out.println();
        mostrarMinHeap();
        System.out.println();
        mostrarTablaHash();
    }
}
