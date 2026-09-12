/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon_battle;

/**
 *
 * @author vasqu
 */
public class ListaObjetos {
    private NodoObjeto cabeza;
    private int size;

    public ListaObjetos() {
        this.cabeza = null;
        this.size = 0;
    }
    
    public void insertar(Objeto objeto) {
        NodoObjeto nuevo = new NodoObjeto(objeto);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoObjeto actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        size++;
    }
     public Objeto buscar(String nombre) {
        NodoObjeto actual = cabeza;
        while (actual != null) {
            if (actual.getObjeto().getNombre().equalsIgnoreCase(nombre)) {
                return actual.getObjeto();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }
     
      public Objeto[] recorrer() {
        Objeto[] inventario = new Objeto[size];
        NodoObjeto actual = cabeza;
        int i = 0;
        while (actual != null) {
            inventario[i] = actual.getObjeto();
            actual = actual.getSiguiente();
            i++;
        }
        return inventario;
    }
      public int contar() {
        return size;
    }
      
      public boolean usarObjeto(String nombreObjeto, Pokemon objetivo) {
        Objeto objeto = buscar(nombreObjeto);
        if (objeto == null || !objeto.hayDisponible()) {
            return false;
        }

        switch (objeto.getTipoEfecto()) {
            case CURAR:
                if (objetivo.getHp() <= 0) {
                    return false;
                }
                int nuevoHp = objetivo.getHp() + objeto.getValorEfecto();
                objetivo.setHp(Math.min(nuevoHp, objetivo.getHpMaximo()));
                break;
            case REVIVIR:
                if (objetivo.getHp() > 0) return false; 
                objetivo.setHp(objetivo.getHpMaximo() / 2); 
                break;
        }

        objeto.usar(); 
        return true;
    }
    
}
