package pokemon_battle.usuarios;

import pokemon_battle.EnumTipo;
import pokemon_battle.Pokemon;

public class CargadorRivales {

    public static ListaUsuarios crearRivales() {
        ListaUsuarios lista = new ListaUsuarios();

        lista.insertar(crearRival("Brock",
                new Pokemon(null, "Onix", 16, EnumTipo.Roca, 110, 30, 45),
                new Pokemon(null, "Geodude", 14, EnumTipo.Roca, 90, 28, 40),
                new Pokemon(null, "Golem", 18, EnumTipo.Roca, 130, 38, 50)));

        lista.insertar(crearRival("Misty",
                new Pokemon(null, "Staryu", 15, EnumTipo.Agua, 95, 30, 28),
                new Pokemon(null, "Starmie", 18, EnumTipo.Agua, 115, 40, 32),
                new Pokemon(null, "Psyduck", 13, EnumTipo.Agua, 85, 25, 26)));

        lista.insertar(crearRival("Erika",
                new Pokemon(null, "Bulbasaur", 14, EnumTipo.Planta, 90, 26, 30),
                new Pokemon(null, "Vileplume", 19, EnumTipo.Planta, 120, 38, 34),
                new Pokemon(null, "Tangela", 16, EnumTipo.Planta, 105, 32, 36)));

        lista.insertar(crearRival("Sabrina",
                new Pokemon(null, "Abra", 13, EnumTipo.Psiquico, 75, 22, 20),
                new Pokemon(null, "Kadabra", 17, EnumTipo.Psiquico, 100, 42, 24),
                new Pokemon(null, "Alakazam", 20, EnumTipo.Psiquico, 115, 50, 26)));

        lista.insertar(crearRival("Koga",
                new Pokemon(null, "Ekans", 14, EnumTipo.Veneno, 88, 27, 25),
                new Pokemon(null, "Arbok", 18, EnumTipo.Veneno, 112, 38, 32),
                new Pokemon(null, "Weezing", 19, EnumTipo.Veneno, 118, 36, 40)));

        lista.insertar(crearRival("Blaine",
                new Pokemon(null, "Charmander", 13, EnumTipo.Fuego, 82, 28, 24),
                new Pokemon(null, "Ninetales", 19, EnumTipo.Fuego, 116, 40, 34),
                new Pokemon(null, "Arcanine", 21, EnumTipo.Fuego, 135, 46, 38)));

        lista.insertar(crearRival("Giovanni",
                new Pokemon(null, "Rhyhorn", 17, EnumTipo.Tierra, 120, 38, 44),
                new Pokemon(null, "Nidoking", 20, EnumTipo.Veneno, 130, 44, 40),
                new Pokemon(null, "Persian", 18, EnumTipo.Normal, 105, 36, 30)));

        lista.insertar(crearRival("Lance",
                new Pokemon(null, "Dratini", 15, EnumTipo.Dragon, 95, 30, 28),
                new Pokemon(null, "Dragonair", 19, EnumTipo.Dragon, 120, 40, 34),
                new Pokemon(null, "Gyarados", 22, EnumTipo.Agua, 145, 50, 42)));

        lista.insertar(crearRival("Bruno",
                new Pokemon(null, "Machop", 15, EnumTipo.Lucha, 100, 35, 30),
                new Pokemon(null, "Machoke", 18, EnumTipo.Lucha, 120, 42, 34),
                new Pokemon(null, "Hitmonlee", 20, EnumTipo.Lucha, 110, 48, 30)));

        lista.insertar(crearRival("Agatha",
                new Pokemon(null, "Gastly", 14, EnumTipo.Fantasma, 80, 30, 22),
                new Pokemon(null, "Haunter", 17, EnumTipo.Fantasma, 100, 38, 26),
                new Pokemon(null, "Gengar", 21, EnumTipo.Fantasma, 125, 48, 32)));

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
