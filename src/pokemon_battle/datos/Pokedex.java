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
        // Agua
        plantillas.insertar(new Pokemon("/Imagenes/staryuo.png", "Staryu", 15, EnumTipo.Agua, 95, 30, 28));
        plantillas.insertar(new Pokemon("/Imagenes/Starmie.png", "Starmie", 18, EnumTipo.Agua, 115, 40, 32));
        plantillas.insertar(new Pokemon("/Imagenes/Psyduck.png", "Psyduck", 13, EnumTipo.Agua, 85, 25, 26));

        plantillas.insertar(new Pokemon("/Imagenes/Bulbasaur.jpg", "Bulbasaur", 14, EnumTipo.Planta, 90, 26, 30));
        plantillas.insertar(new Pokemon("/Imagenes/Vileplume.png", "Vileplume", 19, EnumTipo.Planta, 120, 38, 34));
        plantillas.insertar(new Pokemon("/Imagenes/Tangela.png", "Tangela", 16, EnumTipo.Planta, 105, 32, 36));

        plantillas.insertar(new Pokemon("/Imagenes/Abra.png", "Abra", 13, EnumTipo.Dragon, 75, 22, 20));
        plantillas.insertar(new Pokemon("/Imagenes/Kadabra.png", "Kadabra", 17, EnumTipo.Dragon, 100, 42, 24));
        plantillas.insertar(new Pokemon("/Imagenes/Alakazam.png", "Alakazam", 20, EnumTipo.Dragon, 115, 50, 26));

        plantillas.insertar(new Pokemon("/Imagenes/Ekans.png", "Ekans", 14, EnumTipo.Tierra, 88, 27, 25));
        plantillas.insertar(new Pokemon("/Imagenes/Arbok.png", "Arbok", 18, EnumTipo.Tierra, 112, 38, 32));
        plantillas.insertar(new Pokemon("/Imagenes/Weezing.png", "Weezing", 19, EnumTipo.Tierra, 118, 36, 40));
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