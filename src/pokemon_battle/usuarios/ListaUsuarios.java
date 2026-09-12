package pokemon_battle.usuarios;

public class ListaUsuarios {

    private NodoUsuario cabeza;
    private int tamanio;

    public ListaUsuarios() {
        this.cabeza = null;
        this.tamanio = 0;
    }

    public boolean insertar(Usuario usuario) {
        if (usuario == null || existe(usuario.getNombre())) {
            return false;
        }
        NodoUsuario nuevo = new NodoUsuario(usuario, null);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoUsuario actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        tamanio++;
        return true;
    }

    public Usuario buscar(String nombre) {
        if (nombre == null) {
            return null;
        }
        NodoUsuario actual = cabeza;
        while (actual != null) {
            if (coincide(actual, nombre)) {
                return actual.getUsuario();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public boolean existe(String nombre) {
        return buscar(nombre) != null;
    }

    public boolean eliminar(String nombre) {
        if (cabeza == null || nombre == null) {
            return false;
        }

        if (coincide(cabeza, nombre)) {
            NodoUsuario eliminado = cabeza;
            cabeza = cabeza.getSiguiente();
            eliminado.setSiguiente(null);
            tamanio--;
            return true;
        }

        NodoUsuario anterior = cabeza;
        while (anterior.getSiguiente() != null) {
            NodoUsuario actual = anterior.getSiguiente();
            if (coincide(actual, nombre)) {
                anterior.setSiguiente(actual.getSiguiente());
                actual.setSiguiente(null);
                tamanio--;
                return true;
            }
            anterior = actual;
        }

        return false;
    }

    public Usuario obtenerPorIndice(int indice) {
        if (indice < 0 || indice >= tamanio) {
            return null;
        }
        NodoUsuario actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getUsuario();
    }

    public int contar() {
        return tamanio;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public String recorrer() {
        if (cabeza == null) {
            return "null";
        }
        String recorrido = "";
        NodoUsuario actual = cabeza;
        while (actual != null) {
            recorrido += actual.getUsuario().getNombre() + " -> ";
            actual = actual.getSiguiente();
        }
        return recorrido + "null";
    }

    private boolean coincide(NodoUsuario nodo, String nombre) {
        return nodo.getUsuario().getNombre().equalsIgnoreCase(nombre.trim());
    }
}
