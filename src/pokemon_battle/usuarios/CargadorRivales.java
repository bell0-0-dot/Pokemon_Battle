package pokemon_battle.usuarios;

import pokemon_battle.Pokemon;
import pokemon_battle.datos.Pokedex;
public class CargadorRivales {
    private static final Pokedex pokedex = new Pokedex();
    public static ListaUsuarios crearRivales() {
        ListaUsuarios lista = new ListaUsuarios();

        lista.insertar(crearRival("Brock",
                pokedex.crear("Onix", 16),
                pokedex.crear("Geodude", 14),
                pokedex.crear("Golem", 18)));

        lista.insertar(crearRival("Misty",
                pokedex.crear("Staryu", 15),
                pokedex.crear("Starmie", 18),
                pokedex.crear("Psyduck", 13)));

        lista.insertar(crearRival("Erika",
                pokedex.crear("Bulbasaur", 14),
                pokedex.crear("Vileplume", 19),
                pokedex.crear("Tangela", 16)));

        lista.insertar(crearRival("Sabrina",
                pokedex.crear("Abra", 13),
                pokedex.crear("Kadabra", 17),
                pokedex.crear("Alakazam", 20)));

        lista.insertar(crearRival("Koga",
                pokedex.crear("Ekans", 14),
                pokedex.crear("Arbok", 18),
                pokedex.crear("Weezing", 19)));


        return lista;
    }

    private static Usuario crearRival(String nombre, Pokemon primero, Pokemon segundo, Pokemon tercero) {
        Usuario rival = new Usuario(nombre, "cpu");
        rival.getEntrenador().agregarPokemon(primero);
        rival.getEntrenador().agregarPokemon(segundo);
        rival.getEntrenador().agregarPokemon(tercero);
        return rival;
    }
}
