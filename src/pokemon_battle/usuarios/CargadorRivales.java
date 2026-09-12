package pokemon_battle.usuarios;

import pokemon_battle.datos.Pokedex;

public class CargadorRivales {

    public static ListaUsuarios crearRivales() {
        Pokedex pokedex = new Pokedex();
        ListaUsuarios lista = new ListaUsuarios();

        lista.insertar(crearRival(pokedex, "Brock", "Geodude", "Onix", "Golem"));
        lista.insertar(crearRival(pokedex, "Misty", "Staryu", "Starmie", "Psyduck"));
        lista.insertar(crearRival(pokedex, "Erika", "Bulbasaur", "Vileplume", "Tangela"));
        lista.insertar(crearRival(pokedex, "Sabrina", "Abra", "Kadabra", "Alakazam"));
        lista.insertar(crearRival(pokedex, "Koga", "Ekans", "Arbok", "Weezing"));
        lista.insertar(crearRival(pokedex, "Blaine", "Golem", "Arbok", "Starmie"));
        lista.insertar(crearRival(pokedex, "Giovanni", "Onix", "Weezing", "Alakazam"));
        lista.insertar(crearRival(pokedex, "Lance", "Kadabra", "Vileplume", "Golem"));
        lista.insertar(crearRival(pokedex, "Bruno", "Geodude", "Ekans", "Tangela"));
        lista.insertar(crearRival(pokedex, "Agatha", "Weezing", "Alakazam", "Starmie"));

        return lista;
    }

    private static Usuario crearRival(Pokedex pokedex, String nombre, String primero, String segundo, String tercero) {
        Usuario rival = new Usuario(nombre, "cpu");
        rival.getEntrenador().agregarPokemon(pokedex.crear(primero));
        rival.getEntrenador().agregarPokemon(pokedex.crear(segundo));
        rival.getEntrenador().agregarPokemon(pokedex.crear(tercero));
        return rival;
    }
}
