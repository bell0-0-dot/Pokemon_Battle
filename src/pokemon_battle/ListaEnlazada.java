package pokemon_battle;

public class ListaEnlazada {

    private NodoPokemon cabeza;
    private NodoPokemon activo;
    private int tamanio;

    public ListaEnlazada() {
        this.cabeza = null;
        this.activo = null;
        this.tamanio = 0;
    }

    public void insertar(Pokemon pokemon) {
        if (pokemon == null) {
            return;
        }
        NodoPokemon nuevo = new NodoPokemon(pokemon, null);
        if (cabeza == null) {
            cabeza = nuevo;
            activo = nuevo;
        } else {
            NodoPokemon actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        tamanio++;
    }

    public Pokemon buscar(String nombre) {
        NodoPokemon nodo = buscarNodo(nombre);
        if (nodo == null) {
            return null;
        }
        return nodo.getPokemon();
    }

    public boolean eliminar(String nombre) {
        if (cabeza == null || nombre == null) {
            return false;
        }

        if (coincide(cabeza, nombre)) {
            NodoPokemon eliminado = cabeza;
            cabeza = cabeza.getSiguiente();
            eliminado.setSiguiente(null);
            tamanio--;
            if (activo == eliminado) {
                activo = primerNodoDisponible();
            }
            return true;
        }

        NodoPokemon anterior = cabeza;
        while (anterior.getSiguiente() != null) {
            NodoPokemon actual = anterior.getSiguiente();
            if (coincide(actual, nombre)) {
                anterior.setSiguiente(actual.getSiguiente());
                actual.setSiguiente(null);
                tamanio--;
                if (activo == actual) {
                    activo = primerNodoDisponible();
                }
                return true;
            }
            anterior = actual;
        }

        return false;
    }

    public String recorrer() {
        if (cabeza == null) {
            return "null";
        }
        String recorrido = "";
        NodoPokemon actual = cabeza;
        while (actual != null) {
            recorrido += actual.getPokemon().getNombre() + " -> ";
            actual = actual.getSiguiente();
        }
        return recorrido + "null";
    }

    public Pokemon obtenerPorIndice(int indice) {
        if (indice < 0 || indice >= tamanio) {
            return null;
        }
        NodoPokemon actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getPokemon();
    }

    public int contar() {
        return tamanio;
    }

    public int contarDisponibles() {
        int disponibles = 0;
        NodoPokemon actual = cabeza;
        while (actual != null) {
            if (!estaDerrotado(actual.getPokemon())) {
                disponibles++;
            }
            actual = actual.getSiguiente();
        }
        return disponibles;
    }

    public Pokemon obtenerActivo() {
        if (activo == null) {
            return null;
        }
        return activo.getPokemon();
    }

    public Pokemon siguienteDisponible() {
        NodoPokemon encontrado = nodoSiguienteDisponible();
        if (encontrado == null) {
            return null;
        }
        return encontrado.getPokemon();
    }

    public boolean cambiarActivo(String nombre) {
        NodoPokemon nodo = buscarNodo(nombre);
        if (nodo == null || estaDerrotado(nodo.getPokemon())) {
            return false;
        }
        activo = nodo;
        return true;
    }

    public boolean avanzarASiguienteDisponible() {
        NodoPokemon siguiente = nodoSiguienteDisponible();
        if (siguiente == null) {
            return false;
        }
        activo = siguiente;
        return true;
    }

    public boolean modificar(String nombre, int nuevoNivel, EnumTipo nuevoTipo, int nuevoHp, int nuevoAtaque, int nuevaDefensa) {
        Pokemon pokemon = buscar(nombre);
        if (pokemon == null) {
            return false;
        }
        if (nuevoNivel > 0) {
            pokemon.setNivel(nuevoNivel);
        }
        if (nuevoTipo != null) {
            pokemon.setTipo(nuevoTipo);
        }
        if (nuevoHp > 0) {
            pokemon.setHpMaximo(nuevoHp);
            if (pokemon.getHp() > nuevoHp) {
                pokemon.setHp(nuevoHp);
            }
        }
        if (nuevoAtaque > 0) {
            pokemon.setAtaque(nuevoAtaque);
        }
        if (nuevaDefensa > 0) {
            pokemon.setDefensa(nuevaDefensa);
        }
        return true;
    }

    public boolean moverAlFrente(String nombre) {
        if (cabeza == null || nombre == null) {
            return false;
        }
        if (coincide(cabeza, nombre)) {
            return true;
        }

        NodoPokemon anterior = cabeza;
        while (anterior.getSiguiente() != null) {
            NodoPokemon actual = anterior.getSiguiente();
            if (coincide(actual, nombre)) {
                anterior.setSiguiente(actual.getSiguiente());
                actual.setSiguiente(cabeza);
                cabeza = actual;
                return true;
            }
            anterior = actual;
        }

        return false;
    }

    public void restaurarEquipo() {
        NodoPokemon actual = cabeza;
        while (actual != null) {
            actual.getPokemon().setHp(actual.getPokemon().getHpMaximo());
            actual = actual.getSiguiente();
        }
        activo = cabeza;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public boolean hayDisponibles() {
        return contarDisponibles() > 0;
    }

    private NodoPokemon buscarNodo(String nombre) {
        if (nombre == null) {
            return null;
        }
        NodoPokemon actual = cabeza;
        while (actual != null) {
            if (coincide(actual, nombre)) {
                return actual;
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    private NodoPokemon nodoSiguienteDisponible() {
        if (cabeza == null) {
            return null;
        }

        NodoPokemon actual;
        if (activo == null) {
            actual = cabeza;
        } else {
            actual = activo.getSiguiente();
        }

        while (actual != null) {
            if (!estaDerrotado(actual.getPokemon())) {
                return actual;
            }
            actual = actual.getSiguiente();
        }

        actual = cabeza;
        while (actual != null && actual != activo) {
            if (!estaDerrotado(actual.getPokemon())) {
                return actual;
            }
            actual = actual.getSiguiente();
        }

        return null;
    }

    private NodoPokemon primerNodoDisponible() {
        NodoPokemon actual = cabeza;
        while (actual != null) {
            if (!estaDerrotado(actual.getPokemon())) {
                return actual;
            }
            actual = actual.getSiguiente();
        }
        return cabeza;
    }

    private boolean estaDerrotado(Pokemon pokemon) {
        return pokemon.getHp() <= 0;
    }

    private boolean coincide(NodoPokemon nodo, String nombre) {
        return nodo.getPokemon().getNombre().equalsIgnoreCase(nombre.trim());
    }
}
