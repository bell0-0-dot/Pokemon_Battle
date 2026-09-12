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
/**
 *
 * @author vasqu
 */
public class LogicaJuego {
    private Entrenador jugador;
    private Entrenador rival;
    private ListaObjetos inventario;
    private Historial historial;
    private EstadisticasBatalla estadisticas;
    private boolean batallaTerminada;

    public LogicaJuego(Entrenador jugador, Entrenador rival, ListaObjetos inventario, Historial historial, EstadisticasBatalla estadisticas, boolean batallaTerminada) {
        this.jugador = jugador;
        this.rival = rival;
        this.inventario = inventario;
        this.historial = historial;
        this.estadisticas = estadisticas;
        this.batallaTerminada = batallaTerminada;
    }
    
    private int calcularDaño(Pokemon atacante, Pokemon defensor){
        int damage=atacante.getAtaque()-(defensor.getDefensa()/2);
        return Math.max(damage, 1);
    }
    
    private String ataca(){
        if(!batallaTerminada){
            return "La Batalla ha terminado";
        }
        Pokemon JugadorActivo=jugador.getPokemonActivo();
        Pokemon rivalActivo=jugador.getPokemonActivo();
        
        if(JugadorActivo==null||rivalActivo==null){
            return "Ya no quedan pokemons activos";
        }
        //ayuda :<
        StringBuilder resultado=new StringBuilder();
        
        int damageJugador=calcularDaño(JugadorActivo, rivalActivo);
        rivalActivo.setHp(Math.max(0, rivalActivo.getHp()-damageJugador));
        estadisticas.contarAtaqueJugador(damageJugador);
        
        historial.registrar(JugadorActivo.getNombre() + " atacó a " + rivalActivo.getNombre() + ".");
        historial.registrar(rivalActivo.getNombre() + " perdió " + damageJugador + " HP.");
        resultado.append(JugadorActivo.getNombre()).append(" atacó a ").append(rivalActivo.getNombre())
                 .append(". ").append(rivalActivo.getNombre()).append(": ")
                 .append(rivalActivo.getHp()).append("/").append(rivalActivo.getHpMaximo()).append(" HP\n");
        
        if(JugadorActivo.getHp()<=0){
            historial.registrar(rivalActivo.getNombre() + " fue derrotado.");
            estadisticas.contarRivalDebilitado();
            resultado.append(rivalActivo.getNombre()).append(" fue derrotado.\n");
                if (rival.estaDerrotado()) {
                finalizarBatalla(true);
                resultado.append("¡Has ganado la batalla!");
                return resultado.toString();
            }
        }else{
            Pokemon rivalEstadoActualizado=rival.getPokemonActivo();
            int damageRival=calcularDaño(rivalEstadoActualizado, JugadorActivo);
            JugadorActivo.setHp(Math.max(0, JugadorActivo.getHp() - damageRival));
            estadisticas.contarAtaqueRival(damageRival);
            
            historial.registrar(rivalEstadoActualizado.getNombre() + " atacó a " + JugadorActivo.getNombre() + ".");
            historial.registrar(JugadorActivo.getNombre() + " perdió " + damageRival + " HP.");
            resultado.append(rivalEstadoActualizado.getNombre()).append(" atacó a ").append(JugadorActivo.getNombre())
                     .append(". ").append(JugadorActivo.getNombre()).append(": ")
                     .append(JugadorActivo.getHp()).append("/").append(JugadorActivo.getHpMaximo()).append(" HP\n");

             if (JugadorActivo.getHp() <= 0) {
                historial.registrar(JugadorActivo.getNombre() + " fue derrotado.");
                estadisticas.contarPropioDebilitado();
                resultado.append(JugadorActivo.getNombre()).append(" fue derrotado.\n");

                if (jugador.estaDerrotado()) {
                    finalizarBatalla(false);
                    resultado.append("Has perdido la batalla.");
                }
            }
            
        }
         historial.siguienteTurno();
        estadisticas.contarTurno();
        return resultado.toString();
    }
    
     public boolean cambiarPokemon(String nombre) {
        boolean cambiado = jugador.cambiarPokemon(nombre);
        if (cambiado) {
            estadisticas.contarCambio();
            historial.registrar(jugador.getNombre() + " cambió a " + nombre + ".");
        }
        return cambiado;
    }
     
     public boolean usarObjeto(String nombreObjeto, String nombrePokemonObjetivo) {
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

