/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon_battle;

import pokemon_battle.Entrenador;
import pokemon_battle.ListaObjetos;
import pokemon_battle.Pokemon;
import pokemon_battle.batalla.Historial;
import pokemon_battle.batalla.EstadisticasBatalla;

public class LogicaJuego {
    private Entrenador jugador;
    private Entrenador rival;
    private ListaObjetos inventario;
    private Historial historial;
    private EstadisticasBatalla estadisticas;
    private boolean batallaTerminada;

    public LogicaJuego(Entrenador jugador, Entrenador rival, ListaObjetos inventario, Historial historial, EstadisticasBatalla estadisticas) {
        this.jugador = jugador;
        this.rival = rival;
        this.inventario = inventario;
        this.historial = historial;
        this.estadisticas = estadisticas;
        this.batallaTerminada = false;
    }

    private int calcularDaño(Pokemon atacante, Pokemon defensor) {
        if (atacante == null || defensor == null) return 1;
        int damage = atacante.getAtaque() - (defensor.getDefensa() / 2);
        return Math.max(damage, 1);
    }

    public String ataca() {
        if (batallaTerminada) {
            return "La Batalla ya ha terminado.";
        }

        Pokemon jugadorActivo = jugador.getPokemonActivo();
        Pokemon rivalActivo = rival.getPokemonActivo();

        if (jugadorActivo == null || rivalActivo == null) {
            return "Ya no quedan Pokémon activos en combate.";
        }

        StringBuilder resultado = new StringBuilder();

        int damageJugador = calcularDaño(jugadorActivo, rivalActivo);
        rivalActivo.setHp(Math.max(0, rivalActivo.getHp() - damageJugador));
        estadisticas.contarAtaqueJugador(damageJugador);

        historial.registrar(jugadorActivo.getNombre() + " atacó a " + rivalActivo.getNombre() + ".");
        historial.registrar(rivalActivo.getNombre() + " perdió " + damageJugador + " HP.");

        resultado.append(jugadorActivo.getNombre()).append(" atacó a ").append(rivalActivo.getNombre())
                .append(". ").append(rivalActivo.getNombre()).append(": ")
                .append(rivalActivo.getHp()).append("/").append(rivalActivo.getHpMaximo()).append(" HP\n");

        if (rivalActivo.getHp() <= 0) {
            historial.registrar(rivalActivo.getNombre() + " fue derrotado.");
            estadisticas.contarRivalDebilitado();
            resultado.append(rivalActivo.getNombre()).append(" fue derrotado.\n");

            if (rival.estaDerrotado()) {
                finalizarBatalla(true);
                resultado.append("¡Has ganado la batalla!");
                return resultado.toString();
            } else {
                rival.enviarSiguienteDisponible();
                Pokemon nuevoRival = rival.getPokemonActivo();
                if (nuevoRival != null) {
                    historial.registrar(rival.getNombre() + " envió a " + nuevoRival.getNombre() + ".");
                    resultado.append(rival.getNombre()).append(" envió a ").append(nuevoRival.getNombre()).append(".\n");
                }
            }
        } else {
            int damageRival = calcularDaño(rivalActivo, jugadorActivo);
            jugadorActivo.setHp(Math.max(0, jugadorActivo.getHp() - damageRival));
            estadisticas.contarAtaqueRival(damageRival);

            historial.registrar(rivalActivo.getNombre() + " atacó a " + jugadorActivo.getNombre() + ".");
            historial.registrar(jugadorActivo.getNombre() + " perdió " + damageRival + " HP.");

            resultado.append(rivalActivo.getNombre()).append(" atacó a ").append(jugadorActivo.getNombre())
                    .append(". ").append(jugadorActivo.getNombre()).append(": ")
                    .append(jugadorActivo.getHp()).append("/").append(jugadorActivo.getHpMaximo()).append(" HP\n");

            if (jugadorActivo.getHp() <= 0) {
                historial.registrar(jugadorActivo.getNombre() + " fue derrotado.");
                estadisticas.contarPropioDebilitado();
                resultado.append(jugadorActivo.getNombre()).append(" fue derrotado.\n");

                if (jugador.estaDerrotado()) {
                    finalizarBatalla(false);
                    resultado.append("Has perdido la batalla.");
                } else {
                    jugador.enviarSiguienteDisponible();
                    Pokemon nuevoJugador = jugador.getPokemonActivo();
                    if (nuevoJugador != null) {
                        historial.registrar(jugador.getNombre() + " envió automáticamente a " + nuevoJugador.getNombre() + ".");
                        resultado.append(jugador.getNombre()).append(" envió a ").append(nuevoJugador.getNombre()).append(".\n");
                    }
                }
            }
        }

        historial.siguienteTurno();
        estadisticas.contarTurno();
        return resultado.toString();
    }

    public boolean cambiarPokemon(String nombre) {
        if (batallaTerminada) return false;
        boolean cambiado = jugador.cambiarPokemon(nombre);
        if (cambiado) {
            estadisticas.contarCambio();
            historial.registrar(jugador.getNombre() + " cambió a " + nombre + ".");
        }
        return cambiado;
    }

    public boolean usarObjeto(String nombreObjeto, String nombrePokemonObjetivo) {
        if (batallaTerminada) return false;
        Pokemon objetivo = jugador.buscarPokemon(nombrePokemonObjetivo);
        if (objetivo == null) {
            return false;
        }
        boolean usado = inventario.usarObjeto(nombreObjeto, objetivo);
        if (usado) {
            estadisticas.contarObjeto();
            historial.registrar(jugador.getNombre() + " utilizó " + nombreObjeto + " en " + objetivo.getNombre() + ".");
        }
        return usado;
    }

    private void finalizarBatalla(boolean gano) {
        batallaTerminada = true;
        if (gano) {
            estadisticas.marcarVictoria();
        } else {
            estadisticas.marcarDerrota();
        }
    }

    public boolean isBatallaTerminada() {
        return batallaTerminada;
    }

    public Historial getHistorial() {
        return historial;
    }

    public EstadisticasBatalla getEstadisticas() {
        return estadisticas;
    }

    public Entrenador getJugador() {
        return jugador;
    }

    public Entrenador getRival() {
        return rival;
    }

    public void reiniciar() {
        historial.limpiar();
        estadisticas.reiniciar();
        batallaTerminada = false;
    }
}