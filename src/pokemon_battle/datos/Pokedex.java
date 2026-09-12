package pokemon_battle.datos;

import pokemon_battle.ListaEnlazada;
import pokemon_battle.Pokemon;

public class Pokedex {

    private ListaEnlazada plantillas;

    public Pokedex() {
        plantillas = new ListaEnlazada();
        cargar();
    }

    private void cargar() {
        plantillas.insertar(new Pokemon("Bulbasaur", 14, null, 90, 26, 30));
        plantillas.insertar(new Pokemon("Ivysaur", 17, null, 105, 32, 34));
        plantillas.insertar(new Pokemon("Venusaur", 21, null, 130, 42, 40));
        plantillas.insertar(new Pokemon("Charmander", 14, null, 85, 30, 24));
        plantillas.insertar(new Pokemon("Charmeleon", 17, null, 105, 36, 30));
        plantillas.insertar(new Pokemon("Charizard", 21, null, 128, 46, 36));
        plantillas.insertar(new Pokemon("Squirtle", 14, null, 92, 26, 34));
        plantillas.insertar(new Pokemon("Wartortle", 17, null, 108, 32, 40));
        plantillas.insertar(new Pokemon("Blastoise", 21, null, 132, 40, 48));
        plantillas.insertar(new Pokemon("Pikachu", 15, null, 95, 34, 24));
        plantillas.insertar(new Pokemon("Raichu", 19, null, 115, 44, 30));
        plantillas.insertar(new Pokemon("Jigglypuff", 13, null, 110, 20, 18));
        plantillas.insertar(new Pokemon("Meowth", 14, null, 85, 28, 22));
        plantillas.insertar(new Pokemon("Psyduck", 14, null, 90, 26, 26));
        plantillas.insertar(new Pokemon("Machop", 15, null, 100, 35, 30));
        plantillas.insertar(new Pokemon("Geodude", 15, null, 95, 30, 42));
        plantillas.insertar(new Pokemon("Gastly", 14, null, 80, 32, 20));
        plantillas.insertar(new Pokemon("Onix", 16, null, 110, 30, 46));
        plantillas.insertar(new Pokemon("Eevee", 15, null, 95, 30, 28));
        plantillas.insertar(new Pokemon("Snorlax", 22, null, 160, 40, 38));
    }

    public int contar() {
        return plantillas.contar();
    }

    public boolean existe(String nombre) {
        return plantillas.buscar(nombre) != null;
    }

    public Pokemon verPlantilla(int indice) {
        return plantillas.obtenerPorIndice(indice);
    }

    public String listar() {
        return plantillas.recorrer();
    }

    public Pokemon crear(String nombre) {
        return copiar(plantillas.buscar(nombre));
    }

    public Pokemon crear(String nombre, int nivel) {
        Pokemon copia = crear(nombre);
        if (copia != null && nivel > 0) {
            copia.setNivel(nivel);
        }
        return copia;
    }

    public Pokemon crearPorIndice(int indice) {
        return copiar(plantillas.obtenerPorIndice(indice));
    }

    private Pokemon copiar(Pokemon plantilla) {
        if (plantilla == null) {
            return null;
        }
        return new Pokemon(plantilla.getNombre(), plantilla.getNivel(), plantilla.getTipo(),
                plantilla.getHp(), plantilla.getAtaque(), plantilla.getDefensa());
    }
}
