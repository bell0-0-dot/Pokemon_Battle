package pokemon_battle.batalla;

public class Registro {

    private int turno;
    private String mensaje;

    public Registro(int turno, String mensaje) {
        this.turno = turno;
        this.mensaje = mensaje;
    }

    public int getTurno() {
        return turno;
    }

    public String getMensaje() {
        return mensaje;
    }

    @Override
    public String toString() {
        return mensaje;
    }
}
