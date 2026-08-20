package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LectorDeArchivos {
    /*Esta clase esta pensada para poder leer los archivos de texto especificados en el proyecto.
    * Independientemente de si se trata de la carga de libros, prestamos y existencias. Esta clase la estoy pensando
    * para manejar cada una de ellas.*/

    //CARGA DE LOS LIBROS
    public void cargarLibros(String ruta, GestorBiblioteca gestor){
        //Contador de archivos cargados y errores
        int cargados = 0;
        int errores = 0;

        //Creamos el lector e intentamos leer el archivo
        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))){
            //Almacenamos la linea leida para almacenarla y el numero de lineas para saber cuantas hemos procesado
            String linea;
            int numeroLinea = 0;

            //Siempre que leamos algo (que exista la linea)
            while ((linea = lector.readLine()) != null){
                //Aumentamos el numero de lineas
                numeroLinea++;

                //Si la linea esta vacia, no hacemos nada
                if(linea.trim().isEmpty()){
                    continue;
                }

                //Extraemos los datos
                String[] datos = linea.split("\\|", -1);

                //Si no tenemos 8 datos, es una linea invalida
                if(datos.length != 8){
                    System.out.println("Línea " + numeroLinea + " inválida en libros.txt");
                    errores++;
                    continue;
                }

                try {
                    //Extraemos los datos del arreglo, en este caso todos los del libro para poder registrarlo o actualizarlo
                    String isbn = datos[0].trim();
                    String titulo = datos[1].trim();
                    String autor = datos[2].trim();
                    String editorial = datos[3].trim();
                    int anio = Integer.parseInt(datos[4].trim());
                    String categoria = datos[5].trim();
                    int disponibles = Integer.parseInt(datos[6].trim());
                    int prestados = Integer.parseInt(datos[7].trim());

                    //Registramos el libro
                    gestor.registrarOActualizarLibro(isbn, titulo, autor, editorial,
                            anio, categoria, disponibles, prestados);
                    cargados++;

                } catch (NumberFormatException e){
                    //Cuando encontremos datos erroneos en las lineas procesadas
                    System.out.println("Datos numéricos inválidos en la línea " + numeroLinea + " de libros.txt");
                    errores++;
                }
            }
        } catch (IOException e){
            //Si la ruta no existe, no hay nada que leer...
            System.out.println("No fue posible leer el archivo: " + ruta);
            return;
        }
        System.out.println("Carga de Libros finalizada. Registros procesados: " + cargados
                + " | Errores: " + errores);
    }

    //CARGA DE LOS PRESTAMOS
    public void cargarPrestamos(String ruta, GestorBiblioteca gestor){
        //Contador de archivos cargados y errores
        int cargados = 0;
        int errores = 0;

        //Creamos el lector e intentamos leer el archivo
        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))){
            //Almacenamos la linea leida para almacenarla y el numero de lineas para saber cuantas hemos procesado
            String linea;
            int numeroLinea = 0;

            //Siempre que leamos algo (que exista la linea)
            while ((linea = lector.readLine()) != null){
                //Aumentamos el numero de lineas
                numeroLinea++;

                //Si la linea esta vacia, no hacemos nada
                if(linea.trim().isEmpty()){
                    continue;
                }

                //Extraemos los datos
                String[] datos = linea.split("\\|", -1);

                //Si no tenemos 3 datos, es una linea invalida
                if(datos.length != 3){
                    System.out.println("Línea " + numeroLinea + " inválida en libros.txt");
                    errores++;
                    continue;
                }

                try {
                    //Extraemos el isbn, la fecha y la cantidad del arreglo de datos que creamos anteriormente
                    String isbn = datos[0].trim();
                    String fecha = datos[1].trim();
                    int cantidad = Integer.parseInt(datos[2].trim());

                    //Intentamos registrar el prestamo... de ser posible la carga es exitosa y aumenta el contador
                    if (gestor.registrarPrestamo(isbn, fecha, cantidad)){
                        cargados++;
                    } else {
                        //Si no se encuentra el ISBN, no se puede registrar el prestamo
                        System.out.println("ISBN no encontrado en la línea " + numeroLinea
                                + " de prestamos.txt: " + isbn);
                        errores++;
                    }
                } catch (NumberFormatException e){
                    //Cuando encontremos datos erroneos en las lineas procesadas
                    System.out.println("Cantidad inválida en la línea " + numeroLinea + " de prestamos.txt");
                    errores++;
                }
            }
        } catch (IOException e){
            //Si la ruta no existe, no hay nada que leer...
            System.out.println("No fue posible leer el archivo: " + ruta);
            return;
        }
        System.out.println("Carga de préstamos finalizada. Registros procesados: " + cargados
                + " | Errores: " + errores);
    }

    //CARGA DE LAS EXISTENCIAS
    public void cargarExistencias(String ruta, GestorBiblioteca gestor){
        //Contador de archivos cargados y errores
        int cargados = 0;
        int errores = 0;

        //Creamos el lector e intentamos leer el archivo
        try (BufferedReader lector = new BufferedReader(new FileReader(ruta))){
            //Almacenamos la linea leida para almacenarla y el numero de lineas para saber cuantas hemos procesado
            String linea;
            int numeroLinea = 0;

            //Siempre que leamos algo (que exista la linea)
            while ((linea = lector.readLine()) != null){
                //Aumentamos el numero de lineas
                numeroLinea++;

                //Si la linea esta vacia, no hacemos nada
                if(linea.trim().isEmpty()){
                    continue;
                }

                //Extraemos los datos
                String[] datos = linea.split("\\|", -1);

                //Si no tenemos 3 datos, es una linea invalida
                if(datos.length != 3){
                    System.out.println("Línea " + numeroLinea + " inválida en libros.txt");
                    errores++;
                    continue;
                }

                try {
                    //Extraemos el isbn, la fecha y la cantidad del arreglo de datos que creamos anteriormente
                    String isbn = datos[0].trim();
                    String fecha = datos[1].trim();
                    int cantidad = Integer.parseInt(datos[2].trim());

                    //Intentamos registrar el prestamo... de ser posible la carga es exitosa y aumenta el contador
                    if (gestor.registrarPrestamo(isbn, fecha, cantidad)){
                        cargados++;
                    } else {
                        //Si no se encuentra el ISBN, no se puede registrar el prestamo
                        System.out.println("ISBN no encontrado en la línea " + numeroLinea
                                + " de prestamos.txt: " + isbn);
                        errores++;
                    }
                } catch (NumberFormatException e){
                    //Cuando encontremos datos erroneos en las lineas procesadas
                    System.out.println("Cantidad inválida en la línea " + numeroLinea + " de prestamos.txt");
                    errores++;
                }
            }
        } catch (IOException e){
            //Si la ruta no existe, no hay nada que leer...
            System.out.println("No fue posible leer el archivo: " + ruta);
            return;
        }
        System.out.println("Carga de existencias finalizada. Registros procesados: " + cargados
                + " | Errores: " + errores);
    }

}
