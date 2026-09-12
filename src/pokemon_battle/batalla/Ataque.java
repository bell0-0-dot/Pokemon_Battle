package pokemon_battle.batalla;

import pokemon_battle.EnumTipo;

public class Ataque {

    private String nombre;
    private EnumTipo tipo;
    private int poder;

    public Ataque(String nombre, EnumTipo tipo, int poder) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.poder = poder;
    }

    public String getNombre() {
        return nombre;
    }

    public EnumTipo getTipo() {
        return tipo;
    }

    public int getPoder() {
        return poder;
    }

    @Override
    public String toString() {
        return nombre + " (" + tipo + ", poder " + poder + ")";
    }
}
