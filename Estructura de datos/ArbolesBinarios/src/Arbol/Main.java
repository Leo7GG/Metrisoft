package Arbol;

import java.util.Scanner;

public class Main {

    public static Scanner teclado;

    public static void main (String[] args) {
        teclado = new Scanner(System.in);
        int opcion = 0, elemento = 0;
        String nombre;
        ArbolBinario arbolito = new ArbolBinario();

        do {
            System.out.println("=======================================");
            System.out.println("= 1. Agregar Nodo  =");
            System.out.println("= 2. in Orden      =");
            System.out.println("= 3. pre Orden    =");
            System.out.println("= 4. post Orden    =");
            System.out.println("=======================================");
            try {
                opcion = teclado.nextInt();
                switch (opcion) {
                    case 1:
                        System.out.println("Ingresar el elemento a insertar");
                        elemento = teclado.nextInt();
                        System.out.println("Ingresar nombre del elemento");
                        nombre = teclado.next();
                        arbolito.agregarNodo(elemento,nombre);
                        break;
                    case 2:
                        if (!arbolito.estaVacio()){
                            arbolito.inOrden(arbolito.raiz);
                        }else{
                            System.out.println("El arbol estab vacio");
                        }
                        break;
                    case 3:
                        if (!arbolito.estaVacio()){
                            arbolito.preOrden(arbolito.raiz);
                        }else{
                            System.out.println("El arbol estab vacio");
                        }
                        break;
                    case 4:
                        if (!arbolito.estaVacio()){
                            arbolito.postOrden(arbolito.raiz);
                        }else{
                            System.out.println("El arbol estab vacio");
                        }
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error");
                opcion = 7;
            }
        } while (opcion != 7);
    }
}
