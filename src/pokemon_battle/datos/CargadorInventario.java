package pokemon_battle.datos;

import pokemon_battle.ListaObjetos;
import pokemon_battle.Objeto;

public class CargadorInventario {

    public static ListaObjetos crear() {
        ListaObjetos inventario = new ListaObjetos();
        inventario.insertar(new Objeto("Pocion", "Recupera 20 HP", 3, Objeto.TipoEfecto.CURAR, 20));
        inventario.insertar(new Objeto("Superpocion", "Recupera 50 HP", 2, Objeto.TipoEfecto.CURAR, 50));
        inventario.insertar(new Objeto("Revivir", "Recupera un Pokemon derrotado", 1, Objeto.TipoEfecto.REVIVIR, 0));
        return inventario;
    }
}
