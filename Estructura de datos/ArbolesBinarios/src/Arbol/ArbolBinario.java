package Arbol;

public class ArbolBinario {
	NodoArbol raiz;
	
	public ArbolBinario(){
		raiz=null;
	}

	public void agregarNodo(int d, String nom){
		NodoArbol nuevo=new NodoArbol(d, nom);
		if(raiz==null){
			raiz=nuevo;
		}else{
			NodoArbol padre,aux=raiz;
			while(true){
				padre=aux;
				if(d<aux.dato){
					aux=aux.hijoIzquierdo;
				}if(aux==null){
					padre.hijoIzquierdo=nuevo;
					return;
				}else{
					aux=aux.hijoDerecho;
					if(aux==null){
						padre.hijoDerecho=nuevo;
						return;
					}
					
				}
			}
		}
	}
}
