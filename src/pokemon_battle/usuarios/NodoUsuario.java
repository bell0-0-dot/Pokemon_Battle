package pokemon_battle.usuarios;

public class NodoUsuario {

    private Usuario usuario;
    private NodoUsuario siguiente;

    public NodoUsuario(Usuario usuario, NodoUsuario siguiente) {
        this.usuario = usuario;
        this.siguiente = siguiente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public NodoUsuario getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoUsuario siguiente) {
        this.siguiente = siguiente;
    }
}
