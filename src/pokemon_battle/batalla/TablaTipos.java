package pokemon_battle.batalla;

import pokemon_battle.EnumTipo;

public class TablaTipos {

    private static final double[][] TABLA = construir();

    public static double multiplicador(EnumTipo ataque, EnumTipo defensa) {
        if (ataque == null || defensa == null) {
            return 1.0;
        }
        return TABLA[ataque.ordinal()][defensa.ordinal()];
    }

    public static String describir(double multiplicador) {
        if (multiplicador == 0.0) {
            return "No afecta al objetivo.";
        }
        if (multiplicador > 1.0) {
            return "Es muy eficaz.";
        }
        if (multiplicador < 1.0) {
            return "No es muy eficaz.";
        }
        return "";
    }

    private static double[][] construir() {
        int total = EnumTipo.values().length;
        double[][] tabla = new double[total][total];

        for (int i = 0; i < total; i++) {
            for (int j = 0; j < total; j++) {
                tabla[i][j] = 1.0;
            }
        }

        fuerte(tabla, EnumTipo.Fuego, EnumTipo.Planta, EnumTipo.Hielo, EnumTipo.Bicho, EnumTipo.Acero);
        debil(tabla, EnumTipo.Fuego, EnumTipo.Fuego, EnumTipo.Agua, EnumTipo.Roca, EnumTipo.Dragon);

        fuerte(tabla, EnumTipo.Agua, EnumTipo.Fuego, EnumTipo.Tierra, EnumTipo.Roca);
        debil(tabla, EnumTipo.Agua, EnumTipo.Agua, EnumTipo.Planta, EnumTipo.Dragon);

        fuerte(tabla, EnumTipo.Electrico, EnumTipo.Agua, EnumTipo.Volador);
        debil(tabla, EnumTipo.Electrico, EnumTipo.Electrico, EnumTipo.Planta, EnumTipo.Dragon);
        nulo(tabla, EnumTipo.Electrico, EnumTipo.Tierra);

        fuerte(tabla, EnumTipo.Planta, EnumTipo.Agua, EnumTipo.Tierra, EnumTipo.Roca);
        debil(tabla, EnumTipo.Planta, EnumTipo.Fuego, EnumTipo.Planta, EnumTipo.Veneno,
                EnumTipo.Volador, EnumTipo.Bicho, EnumTipo.Dragon, EnumTipo.Acero);

        fuerte(tabla, EnumTipo.Hielo, EnumTipo.Planta, EnumTipo.Tierra, EnumTipo.Volador, EnumTipo.Dragon);
        debil(tabla, EnumTipo.Hielo, EnumTipo.Fuego, EnumTipo.Agua, EnumTipo.Hielo, EnumTipo.Acero);

        fuerte(tabla, EnumTipo.Tierra, EnumTipo.Fuego, EnumTipo.Electrico, EnumTipo.Veneno,
                EnumTipo.Roca, EnumTipo.Acero);
        debil(tabla, EnumTipo.Tierra, EnumTipo.Planta, EnumTipo.Bicho);
        nulo(tabla, EnumTipo.Tierra, EnumTipo.Volador);

        fuerte(tabla, EnumTipo.Acero, EnumTipo.Hielo, EnumTipo.Roca, EnumTipo.Hada);
        debil(tabla, EnumTipo.Acero, EnumTipo.Fuego, EnumTipo.Agua, EnumTipo.Electrico, EnumTipo.Acero);

        fuerte(tabla, EnumTipo.Dragon, EnumTipo.Dragon);
        debil(tabla, EnumTipo.Dragon, EnumTipo.Acero);
        nulo(tabla, EnumTipo.Dragon, EnumTipo.Hada);

        debil(tabla, EnumTipo.Normal, EnumTipo.Roca, EnumTipo.Acero);
        nulo(tabla, EnumTipo.Normal, EnumTipo.Fantasma);

        fuerte(tabla, EnumTipo.Lucha, EnumTipo.Normal, EnumTipo.Hielo, EnumTipo.Roca,
                EnumTipo.Siniestro, EnumTipo.Acero);
        debil(tabla, EnumTipo.Lucha, EnumTipo.Veneno, EnumTipo.Volador, EnumTipo.Psiquico,
                EnumTipo.Bicho, EnumTipo.Hada);
        nulo(tabla, EnumTipo.Lucha, EnumTipo.Fantasma);

        fuerte(tabla, EnumTipo.Volador, EnumTipo.Planta, EnumTipo.Lucha, EnumTipo.Bicho);
        debil(tabla, EnumTipo.Volador, EnumTipo.Electrico, EnumTipo.Roca, EnumTipo.Acero);

        fuerte(tabla, EnumTipo.Veneno, EnumTipo.Planta, EnumTipo.Hada);
        debil(tabla, EnumTipo.Veneno, EnumTipo.Veneno, EnumTipo.Tierra, EnumTipo.Roca, EnumTipo.Fantasma);
        nulo(tabla, EnumTipo.Veneno, EnumTipo.Acero);

        fuerte(tabla, EnumTipo.Bicho, EnumTipo.Planta, EnumTipo.Psiquico, EnumTipo.Siniestro);
        debil(tabla, EnumTipo.Bicho, EnumTipo.Fuego, EnumTipo.Lucha, EnumTipo.Veneno,
                EnumTipo.Volador, EnumTipo.Fantasma, EnumTipo.Acero, EnumTipo.Hada);

        fuerte(tabla, EnumTipo.Roca, EnumTipo.Fuego, EnumTipo.Hielo, EnumTipo.Volador, EnumTipo.Bicho);
        debil(tabla, EnumTipo.Roca, EnumTipo.Lucha, EnumTipo.Tierra, EnumTipo.Acero);

        fuerte(tabla, EnumTipo.Fantasma, EnumTipo.Psiquico, EnumTipo.Fantasma);
        debil(tabla, EnumTipo.Fantasma, EnumTipo.Siniestro);
        nulo(tabla, EnumTipo.Fantasma, EnumTipo.Normal);

        fuerte(tabla, EnumTipo.Psiquico, EnumTipo.Lucha, EnumTipo.Veneno);
        debil(tabla, EnumTipo.Psiquico, EnumTipo.Psiquico, EnumTipo.Acero);
        nulo(tabla, EnumTipo.Psiquico, EnumTipo.Siniestro);

        fuerte(tabla, EnumTipo.Siniestro, EnumTipo.Psiquico, EnumTipo.Fantasma);
        debil(tabla, EnumTipo.Siniestro, EnumTipo.Lucha, EnumTipo.Siniestro, EnumTipo.Hada);

        fuerte(tabla, EnumTipo.Hada, EnumTipo.Lucha, EnumTipo.Dragon, EnumTipo.Siniestro);
        debil(tabla, EnumTipo.Hada, EnumTipo.Fuego, EnumTipo.Veneno, EnumTipo.Acero);

        return tabla;
    }

    private static void fuerte(double[][] tabla, EnumTipo ataque, EnumTipo... defensas) {
        asignar(tabla, ataque, 2.0, defensas);
    }

    private static void debil(double[][] tabla, EnumTipo ataque, EnumTipo... defensas) {
        asignar(tabla, ataque, 0.5, defensas);
    }

    private static void nulo(double[][] tabla, EnumTipo ataque, EnumTipo... defensas) {
        asignar(tabla, ataque, 0.0, defensas);
    }

    private static void asignar(double[][] tabla, EnumTipo ataque, double valor, EnumTipo[] defensas) {
        for (EnumTipo defensa : defensas) {
            tabla[ataque.ordinal()][defensa.ordinal()] = valor;
        }
    }
}
