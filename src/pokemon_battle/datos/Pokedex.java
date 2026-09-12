package pokemon_battle.datos;

import pokemon_battle.EnumTipo;
import pokemon_battle.ListaEnlazada;
import pokemon_battle.Pokemon;

public class Pokedex {

    private ListaEnlazada plantillas;

    public Pokedex() {
        plantillas = new ListaEnlazada();
        cargar();
    }

    private void cargar() {
        plantillas.insertar(new Pokemon(null, "Bulbasaur", 14, EnumTipo.Planta, 90, 26, 30));
        plantillas.insertar(new Pokemon(null, "Ivysaur", 17, EnumTipo.Planta, 105, 32, 34));
        plantillas.insertar(new Pokemon(null, "Venusaur", 21, EnumTipo.Planta, 130, 42, 40));
        plantillas.insertar(new Pokemon(null, "Charmander", 14, EnumTipo.Fuego, 85, 30, 24));
        plantillas.insertar(new Pokemon(null, "Charmeleon", 17, EnumTipo.Fuego, 105, 36, 30));
        plantillas.insertar(new Pokemon(null, "Charizard", 21, EnumTipo.Fuego, 128, 46, 36));
        plantillas.insertar(new Pokemon(null, "Squirtle", 14, EnumTipo.Agua, 92, 26, 34));
        plantillas.insertar(new Pokemon(null, "Wartortle", 17, EnumTipo.Agua, 108, 32, 40));
        plantillas.insertar(new Pokemon(null, "Blastoise", 21, EnumTipo.Agua, 132, 40, 48));
        plantillas.insertar(new Pokemon(null, "Pikachu", 15, EnumTipo.Electrico, 95, 34, 24));
        plantillas.insertar(new Pokemon(null, "Raichu", 19, EnumTipo.Electrico, 115, 44, 30));
        plantillas.insertar(new Pokemon(null, "Jigglypuff", 13, EnumTipo.Hada, 110, 20, 18));
        plantillas.insertar(new Pokemon(null, "Meowth", 14, EnumTipo.Normal, 85, 28, 22));
        plantillas.insertar(new Pokemon(null, "Psyduck", 14, EnumTipo.Agua, 90, 26, 26));
        plantillas.insertar(new Pokemon(null, "Machop", 15, EnumTipo.Lucha, 100, 35, 30));
        plantillas.insertar(new Pokemon(null, "Geodude", 15, EnumTipo.Roca, 95, 30, 42));
        plantillas.insertar(new Pokemon(null, "Gastly", 14, EnumTipo.Fantasma, 80, 32, 20));
        plantillas.insertar(new Pokemon(null, "Onix", 16, EnumTipo.Roca, 110, 30, 46));
        plantillas.insertar(new Pokemon(null, "Eevee", 15, EnumTipo.Normal, 95, 30, 28));
        plantillas.insertar(new Pokemon(null, "Snorlax", 22, EnumTipo.Normal, 160, 40, 38));
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
        return new Pokemon(plantilla.getRutaImagen(), plantilla.getNombre(), plantilla.getNivel(),
                plantilla.getTipo(), plantilla.getHpMaximo(), plantilla.getAtaque(), plantilla.getDefensa());
    }
}
