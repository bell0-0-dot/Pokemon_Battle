package pokemon_battle.batalla;

import pokemon_battle.EnumTipo;

public class CatalogoAtaques {

    public static Ataque paraTipo(EnumTipo tipo) {
        if (tipo == null) {
            return new Ataque("Placaje", null, 40);
        }

        switch (tipo) {
            case Fuego:
                return new Ataque("Lanzallamas", tipo, 60);
            case Agua:
                return new Ataque("Pistola Agua", tipo, 55);
            case Electrico:
                return new Ataque("Impactrueno", tipo, 55);
            case Planta:
                return new Ataque("Latigo Cepa", tipo, 50);
            case Hielo:
                return new Ataque("Rayo Hielo", tipo, 60);
            case Tierra:
                return new Ataque("Terremoto", tipo, 65);
            case Acero:
                return new Ataque("Garra Metal", tipo, 50);
            case Dragon:
                return new Ataque("Furia Dragon", tipo, 60);
            case Lucha:
                return new Ataque("Golpe Karate", tipo, 55);
            case Volador:
                return new Ataque("Ataque Ala", tipo, 50);
            case Veneno:
                return new Ataque("Bomba Lodo", tipo, 55);
            case Bicho:
                return new Ataque("Picadura", tipo, 45);
            case Roca:
                return new Ataque("Lanzarrocas", tipo, 55);
            case Fantasma:
                return new Ataque("Lenguetazo", tipo, 45);
            case Psiquico:
                return new Ataque("Confusion", tipo, 55);
            case Siniestro:
                return new Ataque("Mordisco", tipo, 55);
            case Hada:
                return new Ataque("Beso Drenaje", tipo, 50);
            default:
                return new Ataque("Placaje", tipo, 40);
        }
    }
}
