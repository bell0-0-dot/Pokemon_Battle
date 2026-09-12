package pokemon_battle.batalla;

public class NodoRegistro {

    private Registro registro;
    private NodoRegistro siguiente;

    public NodoRegistro(Registro registro, NodoRegistro siguiente) {
        this.registro = registro;
        this.siguiente = siguiente;
    }

    public Registro getRegistro() {
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public NodoRegistro getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoRegistro siguiente) {
        this.siguiente = siguiente;
    }
}
