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
                }
            } catch (Exception e) {
                System.out.println("Error");
                opcion = 7;
            }
        } while (opcion != 7);
    }
}
