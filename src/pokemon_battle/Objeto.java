/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon_battle;

/**
 *
 * @author vasqu
 */
public class Objeto {
    private String nombre;
    private String descripcion;
    private int cantidad;
    private TipoEfecto tipoEfecto;
    private int valorEfecto;
    
     public enum TipoEfecto {
        CURAR,   
        REVIVIR  
    }

    public Objeto(String nombre, String descripcion, int cantidad, TipoEfecto tipoEfecto, int valorEfecto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.tipoEfecto = tipoEfecto;
        this.valorEfecto = valorEfecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public TipoEfecto getTipoEfecto() {
        return tipoEfecto;
    }

    public void setTipoEfecto(TipoEfecto tipoEfecto) {
        this.tipoEfecto = tipoEfecto;
    }

    public int getValorEfecto() {
        return valorEfecto;
    }

    public void setValorEfecto(int valorEfecto) {
        this.valorEfecto = valorEfecto;
    }

    public boolean hayDisponible() {
        return cantidad > 0;
    }
     public void usar() {
        if (cantidad > 0) {
            cantidad--;
        }
    }
     @Override
    public String toString() {
        return nombre + " x" + cantidad + " - " + descripcion;
    }
}
