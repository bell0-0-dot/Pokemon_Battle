package pokemon_battle.batalla;

public class Historial {

    private NodoRegistro cabeza;
    private NodoRegistro ultimo;
    private int tamanio;
    private int turnoActual;

    public Historial() {
        this.cabeza = null;
        this.ultimo = null;
        this.tamanio = 0;
        this.turnoActual = 1;
    }

    public void registrar(String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            return;
        }
        NodoRegistro nuevo = new NodoRegistro(new Registro(turnoActual, mensaje), null);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            ultimo.setSiguiente(nuevo);
        }
        ultimo = nuevo;
        tamanio++;
    }

    public void siguienteTurno() {
        turnoActual++;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public int contar() {
        return tamanio;
    }

    public boolean estaVacio() {
        return cabeza == null;
    }

    public Registro obtenerPorIndice(int indice) {
        if (indice < 0 || indice >= tamanio) {
            return null;
        }
        NodoRegistro actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getRegistro();
    }

    public String textoCompleto() {
        if (cabeza == null) {
            return "Aun no hay acciones registradas.";
        }

        StringBuilder texto = new StringBuilder();
        int turnoImpreso = 0;
        NodoRegistro actual = cabeza;

        while (actual != null) {
            Registro registro = actual.getRegistro();
            if (registro.getTurno() != turnoImpreso) {
                if (turnoImpreso != 0) {
                    texto.append("\n");
                }
                texto.append("Turno ").append(registro.getTurno()).append("\n");
                turnoImpreso = registro.getTurno();
            }
            texto.append(registro.getMensaje()).append("\n");
            actual = actual.getSiguiente();
        }

        return texto.toString();
    }

    public String ultimasLineas(int cantidad) {
        if (cabeza == null || cantidad <= 0) {
            return "";
        }

        int inicio = tamanio - cantidad;
        if (inicio < 0) {
            inicio = 0;
        }

        StringBuilder texto = new StringBuilder();
        NodoRegistro actual = cabeza;
        int indice = 0;

        while (actual != null) {
            if (indice >= inicio) {
                texto.append(actual.getRegistro().getMensaje()).append("\n");
            }
            indice++;
            actual = actual.getSiguiente();
        }

        return texto.toString();
    }

    public String ultimoMensaje() {
        if (ultimo == null) {
            return "";
        }
        return ultimo.getRegistro().getMensaje();
    }

    public void limpiar() {
        cabeza = null;
        ultimo = null;
        tamanio = 0;
        turnoActual = 1;
    }
}
