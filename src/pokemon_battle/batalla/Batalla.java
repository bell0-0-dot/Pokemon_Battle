package pokemon_battle.batalla;

import java.util.Random;
import pokemon_battle.Entrenador;
import pokemon_battle.Pokemon;

public class Batalla {

    public enum Estado {
        EN_CURSO,
        VICTORIA,
        DERROTA
    }

    private Entrenador jugador;
    private Entrenador rival;
    private Historial historial;
    private EstadisticasBatalla estadisticas;
    private Estado estado;
    private Random random;
    private boolean ultimaAccionAplicada;

    public Batalla(Entrenador jugador, Entrenador rival) {
        this.jugador = jugador;
        this.rival = rival;
        this.historial = new Historial();
        this.estadisticas = new EstadisticasBatalla();
        this.estado = Estado.EN_CURSO;
        this.random = new Random();
        this.ultimaAccionAplicada = false;
        historial.registrar("Comienza la batalla contra " + rival.getNombre() + ".");
    }

    public String atacar() {
        ultimaAccionAplicada = false;
        if (estado != Estado.EN_CURSO) {
            return "La batalla ya termino.\n";
        }

        Pokemon mio = jugador.getPokemonActivo();
        Pokemon suyo = rival.getPokemonActivo();
        if (mio == null || suyo == null) {
            return "No hay Pokemon en combate.";
        }

        ultimaAccionAplicada = true;
        estadisticas.contarTurno();
        StringBuilder texto = new StringBuilder();
        texto.append(ejecutarAtaque(mio, suyo, true));

        if (suyo.getHp() <= 0) {
            texto.append(caer(rival, suyo, false));
        }

        if (estado == Estado.EN_CURSO) {
            texto.append(turnoDelRival());
        }

        cerrarTurno();
        return texto.toString();
    }

    public String usarObjeto(String nombreObjeto, String nombrePokemon) {
        ultimaAccionAplicada = false;
        if (estado != Estado.EN_CURSO) {
            return "La batalla ya termino.";
        }

        Pokemon objetivo;
        if (nombrePokemon == null || nombrePokemon.trim().isEmpty()) {
            objetivo = jugador.getPokemonActivo();
        } else {
            objetivo = jugador.buscarPokemon(nombrePokemon);
        }

        if (objetivo == null) {
            return "Ese Pokemon no esta en tu equipo.\n";
        }
        if (!jugador.usarObjeto(nombreObjeto, objetivo)) {
            return "No se pudo usar " + nombreObjeto + " en " + objetivo.getNombre() + ".\n";
        }

        ultimaAccionAplicada = true;
        estadisticas.contarTurno();
        estadisticas.contarObjeto();

        StringBuilder texto = new StringBuilder();
        registrar(texto, jugador.getNombre() + " utilizo " + nombreObjeto + " en " + objetivo.getNombre() + ".");
        registrar(texto, objetivo.getNombre() + ": " + vida(objetivo));

        texto.append(turnoDelRival());
        cerrarTurno();
        return texto.toString();
    }

    public String cambiarPokemon(String nombre) {
        ultimaAccionAplicada = false;
        if (estado != Estado.EN_CURSO) {
            return "La batalla ya termino.";
        }

        Pokemon anterior = jugador.getPokemonActivo();
        if (anterior != null && anterior.getNombre().equalsIgnoreCase(nombre)) {
            return anterior.getNombre() + " ya esta en combate.\n";
        }
        if (!jugador.cambiarPokemon(nombre)) {
            return "No puedes enviar a ese Pokemon.\n";
        }

        ultimaAccionAplicada = true;
        estadisticas.contarTurno();
        estadisticas.contarCambio();

        StringBuilder texto = new StringBuilder();
        registrar(texto, jugador.getNombre() + " envia a " + jugador.getPokemonActivo().getNombre() + ".");

        texto.append(turnoDelRival());
        cerrarTurno();
        return texto.toString();
    }

    public void reiniciar() {
        jugador.reiniciarEquipo();
        jugador.reiniciarInventario();
        rival.reiniciarEquipo();
        historial.limpiar();
        estadisticas.reiniciar();
        estado = Estado.EN_CURSO;
        historial.registrar("Comienza la batalla contra " + rival.getNombre() + ".");
    }

    public Entrenador getJugador() {
        return jugador;
    }

    public Entrenador getRival() {
        return rival;
    }

    public Pokemon getPokemonJugador() {
        return jugador.getPokemonActivo();
    }

    public Pokemon getPokemonRival() {
        return rival.getPokemonActivo();
    }

    public Historial getHistorial() {
        return historial;
    }

    public EstadisticasBatalla getEstadisticas() {
        return estadisticas;
    }

    public Estado getEstado() {
        return estado;
    }

    public boolean ultimaAccionAplicada() {
        return ultimaAccionAplicada;
    }

    public boolean terminada() {
        return estado != Estado.EN_CURSO;
    }

    private String turnoDelRival() {
        Pokemon suyo = rival.getPokemonActivo();
        Pokemon mio = jugador.getPokemonActivo();
        if (suyo == null || mio == null) {
            return "";
        }

        StringBuilder texto = new StringBuilder();
        texto.append(ejecutarAtaque(suyo, mio, false));

        if (mio.getHp() <= 0) {
            texto.append(caer(jugador, mio, true));
        }

        return texto.toString();
    }

    private String ejecutarAtaque(Pokemon atacante, Pokemon defensor, boolean esDelJugador) {
        Ataque ataque = CatalogoAtaques.paraTipo(atacante.getTipo());
        double multiplicador = TablaTipos.multiplicador(ataque.getTipo(), defensor.getTipo());
        int danio = calcularDanio(atacante, defensor, ataque, multiplicador);

        int restante = defensor.getHp() - danio;
        if (restante < 0) {
            restante = 0;
        }
        defensor.setHp(restante);

        if (esDelJugador) {
            estadisticas.contarAtaqueJugador(danio);
        } else {
            estadisticas.contarAtaqueRival(danio);
        }

        StringBuilder texto = new StringBuilder();
        registrar(texto, atacante.getNombre() + " utilizo " + ataque.getNombre() + ".");
        registrar(texto, defensor.getNombre() + " recibio " + danio + " puntos de danio.");

        String efecto = TablaTipos.describir(multiplicador);
        if (!efecto.isEmpty()) {
            registrar(texto, efecto);
        }

        registrar(texto, defensor.getNombre() + ": " + vida(defensor));
        return texto.toString();
    }

    private int calcularDanio(Pokemon atacante, Pokemon defensor, Ataque ataque, double multiplicador) {
        int defensa = defensor.getDefensa();
        if (defensa < 1) {
            defensa = 1;
        }

        double base = ((2.0 * atacante.getNivel() / 5.0 + 2.0) * ataque.getPoder() * atacante.getAtaque() / defensa) / 25.0 + 2.0;
        double variacion = 0.85 + random.nextDouble() * 0.15;
        int danio = (int) (base * multiplicador * variacion);

        if (multiplicador > 0 && danio < 1) {
            danio = 1;
        }
        return danio;
    }

    private String caer(Entrenador duenio, Pokemon caido, boolean esDelJugador) {
        StringBuilder texto = new StringBuilder();
        registrar(texto, caido.getNombre() + " fue derrotado.");

        if (esDelJugador) {
            estadisticas.contarPropioDebilitado();
        } else {
            estadisticas.contarRivalDebilitado();
        }

        if (duenio.enviarSiguienteDisponible()) {
            registrar(texto, duenio.getNombre() + " envia a " + duenio.getPokemonActivo().getNombre() + ".");
        } else if (esDelJugador) {
            estado = Estado.DERROTA;
            estadisticas.marcarDerrota();
            registrar(texto, duenio.getNombre() + " se quedo sin Pokemon. Has perdido la batalla.");
        } else {
            estado = Estado.VICTORIA;
            estadisticas.marcarVictoria();
            registrar(texto, duenio.getNombre() + " se quedo sin Pokemon. Has ganado la batalla.");
        }

        return texto.toString();
    }

    private void cerrarTurno() {
        if (estado == Estado.EN_CURSO) {
            historial.siguienteTurno();
        }
    }

    private void registrar(StringBuilder texto, String mensaje) {
        historial.registrar(mensaje);
        texto.append(mensaje).append("\n");
    }

    private String vida(Pokemon pokemon) {
        return pokemon.getHp() + "/" + pokemon.getHpMaximo() + " HP";
    }
}
