package pokemon_battle.usuarios;

import pokemon_battle.Pokemon;

public class CargadorRivales {

    public static ListaUsuarios crearRivales() {
        ListaUsuarios lista = new ListaUsuarios();

        lista.insertar(crearRival("Brock",
                new Pokemon("Onix", 16, null, 110, 30, 45),
                new Pokemon("Geodude", 14, null, 90, 28, 40),
                new Pokemon("Golem", 18, null, 130, 38, 50)));

        lista.insertar(crearRival("Misty",
                new Pokemon("Staryu", 15, null, 95, 30, 28),
                new Pokemon("Starmie", 18, null, 115, 40, 32),
                new Pokemon("Psyduck", 13, null, 85, 25, 26)));

        lista.insertar(crearRival("Erika",
                new Pokemon("Bulbasaur", 14, null, 90, 26, 30),
                new Pokemon("Vileplume", 19, null, 120, 38, 34),
                new Pokemon("Tangela", 16, null, 105, 32, 36)));

        lista.insertar(crearRival("Sabrina",
                new Pokemon("Abra", 13, null, 75, 22, 20),
                new Pokemon("Kadabra", 17, null, 100, 42, 24),
                new Pokemon("Alakazam", 20, null, 115, 50, 26)));

        lista.insertar(crearRival("Koga",
                new Pokemon("Ekans", 14, null, 88, 27, 25),
                new Pokemon("Arbok", 18, null, 112, 38, 32),
                new Pokemon("Weezing", 19, null, 118, 36, 40)));

        lista.insertar(crearRival("Blaine",
                new Pokemon("Charmander", 13, null, 82, 28, 24),
                new Pokemon("Ninetales", 19, null, 116, 40, 34),
                new Pokemon("Arcanine", 21, null, 135, 46, 38)));

        lista.insertar(crearRival("Giovanni",
                new Pokemon("Rhyhorn", 17, null, 120, 38, 44),
                new Pokemon("Nidoking", 20, null, 130, 44, 40),
                new Pokemon("Persian", 18, null, 105, 36, 30)));

        lista.insertar(crearRival("Lance",
                new Pokemon("Dratini", 15, null, 95, 30, 28),
                new Pokemon("Dragonair", 19, null, 120, 40, 34),
                new Pokemon("Gyarados", 22, null, 145, 50, 42)));

        lista.insertar(crearRival("Bruno",
                new Pokemon("Machop", 15, null, 100, 35, 30),
                new Pokemon("Machoke", 18, null, 120, 42, 34),
                new Pokemon("Hitmonlee", 20, null, 110, 48, 30)));

        lista.insertar(crearRival("Agatha",
                new Pokemon("Gastly", 14, null, 80, 30, 22),
                new Pokemon("Haunter", 17, null, 100, 38, 26),
                new Pokemon("Gengar", 21, null, 125, 48, 32)));

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
