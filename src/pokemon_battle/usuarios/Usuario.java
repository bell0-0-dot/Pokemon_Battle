package pokemon_battle.usuarios;

import pokemon_battle.Entrenador;

public class Usuario {

    private String nombre;
    private String clave;
    private Entrenador entrenador;
    private int victorias;
    private int derrotas;

    public Usuario(String nombre, String clave) {
        this.nombre = nombre;
        this.clave = clave;
        this.entrenador = new Entrenador(nombre);
        this.victorias = 0;
        this.derrotas = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public int getVictorias() {
        return victorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public boolean claveCorrecta(String intento) {
        return intento != null && clave.equals(intento);
    }

    public boolean cambiarClave(String claveActual, String claveNueva) {
        if (!claveCorrecta(claveActual)) {
            return false;
        }
        this.clave = claveNueva;
        return true;
    }

    public void registrarVictoria() {
        victorias++;
    }

    public void registrarDerrota() {
        derrotas++;
    }

    public boolean listoParaBatalla() {
        return entrenador.pokemonDisponibles() > 0;
    }

    @Override
    public String toString() {
        return nombre + " [" + victorias + "V / " + derrotas + "D]";
    }
}
