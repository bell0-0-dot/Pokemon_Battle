package pokemon_battle.batalla;

public class EstadisticasBatalla {

    private int turnosJugados;
    private int ataquesJugador;
    private int ataquesRival;
    private int danioInfligido;
    private int danioRecibido;
    private int objetosUsados;
    private int cambiosDePokemon;
    private int rivalesDebilitados;
    private int propiosDebilitados;
    private String resultado;

    public EstadisticasBatalla() {
        reiniciar();
    }

    public void reiniciar() {
        turnosJugados = 0;
        ataquesJugador = 0;
        ataquesRival = 0;
        danioInfligido = 0;
        danioRecibido = 0;
        objetosUsados = 0;
        cambiosDePokemon = 0;
        rivalesDebilitados = 0;
        propiosDebilitados = 0;
        resultado = "En curso";
    }

    public void contarTurno() {
        turnosJugados++;
    }

    public void contarAtaqueJugador(int danio) {
        ataquesJugador++;
        danioInfligido += danio;
    }

    public void contarAtaqueRival(int danio) {
        ataquesRival++;
        danioRecibido += danio;
    }

    public void contarObjeto() {
        objetosUsados++;
    }

    public void contarCambio() {
        cambiosDePokemon++;
    }

    public void contarRivalDebilitado() {
        rivalesDebilitados++;
    }

    public void contarPropioDebilitado() {
        propiosDebilitados++;
    }

    public void marcarVictoria() {
        resultado = "Victoria";
    }

    public void marcarDerrota() {
        resultado = "Derrota";
    }

    public int getTurnosJugados() {
        return turnosJugados;
    }

    public int getAtaquesJugador() {
        return ataquesJugador;
    }

    public int getAtaquesRival() {
        return ataquesRival;
    }

    public int getDanioInfligido() {
        return danioInfligido;
    }

    public int getDanioRecibido() {
        return danioRecibido;
    }

    public int getObjetosUsados() {
        return objetosUsados;
    }

    public int getCambiosDePokemon() {
        return cambiosDePokemon;
    }

    public int getRivalesDebilitados() {
        return rivalesDebilitados;
    }

    public int getPropiosDebilitados() {
        return propiosDebilitados;
    }

    public String getResultado() {
        return resultado;
    }

    public int promedioDanioPorAtaque() {
        if (ataquesJugador == 0) {
            return 0;
        }
        return danioInfligido / ataquesJugador;
    }

    public String resumen() {
        StringBuilder texto = new StringBuilder();
        texto.append("Resultado: ").append(resultado).append("\n");
        texto.append("Turnos jugados: ").append(turnosJugados).append("\n");
        texto.append("Ataques realizados: ").append(ataquesJugador).append("\n");
        texto.append("Ataques recibidos: ").append(ataquesRival).append("\n");
        texto.append("Danio infligido: ").append(danioInfligido).append("\n");
        texto.append("Danio recibido: ").append(danioRecibido).append("\n");
        texto.append("Promedio por ataque: ").append(promedioDanioPorAtaque()).append("\n");
        texto.append("Objetos usados: ").append(objetosUsados).append("\n");
        texto.append("Cambios de Pokemon: ").append(cambiosDePokemon).append("\n");
        texto.append("Pokemon rivales debilitados: ").append(rivalesDebilitados).append("\n");
        texto.append("Pokemon propios debilitados: ").append(propiosDebilitados);
        return texto.toString();
    }
}
