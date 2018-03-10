package Arbol;

public class NodoArbol {
	int dato;
	NodoArbol hijoIzquierdo, hijoDerecho;
	String nombre;
	
	public NodoArbol(int d, String nom){
		this.dato=d;
		this.nombre=nom;
		this.hijoDerecho=null;
		this.hijoIzquierdo=null;
	}
	public String toString(){
		return nombre+" su base es: "+dato;
	}
}
