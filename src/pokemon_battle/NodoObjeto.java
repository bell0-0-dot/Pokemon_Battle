/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon_battle;

/**
 *
 * @author vasqu
 */
public class NodoObjeto {
    private Objeto objeto;
    private NodoObjeto siguiente;

    public NodoObjeto(Objeto objeto) {
        this.objeto = objeto;
        this.siguiente = null;
    }

    public Objeto getObjeto() {
        return objeto;
    }

    public void setObjeto(Objeto objeto) {
        this.objeto = objeto;
    }

    public NodoObjeto getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoObjeto siguiente) {
        this.siguiente = siguiente;
    }
    
    
    
}
