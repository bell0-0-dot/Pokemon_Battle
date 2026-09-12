package pokemon_battle;

public class Entrenador {

    private String nombre;
    private ListaEnlazada equipo;

    public Entrenador(String nombre) {
        this.nombre = nombre;
        this.equipo = new ListaEnlazada();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean agregarPokemon(Pokemon pokemon) {
        if (pokemon == null || equipo.buscar(pokemon.getNombre()) != null) {
            return false;
        }
        equipo.insertar(pokemon);
        return true;
    }

    public Pokemon buscarPokemon(String nombre) {
        return equipo.buscar(nombre);
    }

    public boolean eliminarPokemon(String nombre) {
        return equipo.eliminar(nombre);
    }

    public boolean modificarPokemon(String nombre, int nivel, EnumTipo tipo, int hp, int ataque, int defensa) {
        return equipo.modificar(nombre, nivel, tipo, hp, ataque, defensa);
    }

    public boolean moverAlFrente(String nombre) {
        return equipo.moverAlFrente(nombre);
    }

    public Pokemon getPokemonActivo() {
        return equipo.obtenerActivo();
    }

    public boolean cambiarPokemon(String nombre) {
        return equipo.cambiarActivo(nombre);
    }

    public boolean enviarSiguienteDisponible() {
        return equipo.avanzarASiguienteDisponible();
    }

    public Pokemon obtenerPorIndice(int indice) {
        return equipo.obtenerPorIndice(indice);
    }

    public int totalPokemon() {
        return equipo.contar();
    }

    public int pokemonDisponibles() {
        return equipo.contarDisponibles();
    }

    public boolean estaDerrotado() {
        return !equipo.hayDisponibles();
    }

    public boolean tieneEquipo() {
        return !equipo.estaVacia();
    }

    public String verEquipo() {
        return equipo.recorrer();
    }

    @Override
    public String toString() {
        return nombre + " (" + equipo.contarDisponibles() + "/" + equipo.contar() + ")";
    }
}
