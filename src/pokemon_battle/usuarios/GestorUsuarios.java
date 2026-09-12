package pokemon_battle.usuarios;

import java.util.Random;
import pokemon_battle.Entrenador;

public class GestorUsuarios {

    private static final int LARGO_MINIMO_USUARIO = 3;
    private static final int LARGO_MINIMO_CLAVE = 4;

    private ListaUsuarios registrados;
    private ListaUsuarios rivales;
    private Usuario sesionActiva;
    private Random random;

    public GestorUsuarios() {
        this.registrados = new ListaUsuarios();
        this.rivales = CargadorRivales.crearRivales();
        this.sesionActiva = null;
        this.random = new Random();
    }

    public ResultadoAcceso registrar(String nombre, String clave, String confirmacion) {
        String usuario = nombre == null ? "" : nombre.trim();

        if (usuario.isEmpty() || clave == null || clave.isEmpty()) {
            return ResultadoAcceso.error("Debe llenar todos los campos.");
        }
        if (usuario.length() < LARGO_MINIMO_USUARIO) {
            return ResultadoAcceso.error("El usuario debe tener al menos " + LARGO_MINIMO_USUARIO + " caracteres.");
        }
        if (usuario.contains(" ")) {
            return ResultadoAcceso.error("El usuario no puede contener espacios.");
        }
        if (clave.length() < LARGO_MINIMO_CLAVE) {
            return ResultadoAcceso.error("La contrasena debe tener al menos " + LARGO_MINIMO_CLAVE + " caracteres.");
        }
        if (!clave.equals(confirmacion)) {
            return ResultadoAcceso.error("Las contrasenas no coinciden.");
        }
        if (registrados.existe(usuario)) {
            return ResultadoAcceso.error("Ese usuario ya esta registrado.");
        }

        Usuario nuevo = new Usuario(usuario, clave);
        registrados.insertar(nuevo);
        return ResultadoAcceso.exito("Usuario " + usuario + " creado correctamente.", nuevo);
    }

    public ResultadoAcceso iniciarSesion(String nombre, String clave) {
        String usuario = nombre == null ? "" : nombre.trim();

        if (usuario.isEmpty() || clave == null || clave.isEmpty()) {
            return ResultadoAcceso.error("Debe llenar todos los campos.");
        }

        Usuario encontrado = registrados.buscar(usuario);
        if (encontrado == null) {
            return ResultadoAcceso.error("El usuario no existe.");
        }
        if (!encontrado.claveCorrecta(clave)) {
            return ResultadoAcceso.error("Contrasena incorrecta.");
        }

        sesionActiva = encontrado;
        return ResultadoAcceso.exito("Bienvenido " + encontrado.getNombre() + ".", encontrado);
    }

    public void cerrarSesion() {
        sesionActiva = null;
    }

    public boolean haySesionActiva() {
        return sesionActiva != null;
    }

    public Usuario getUsuarioActual() {
        return sesionActiva;
    }

    public Entrenador getEntrenadorActual() {
        if (sesionActiva == null) {
            return null;
        }
        return sesionActiva.getEntrenador();
    }

    public ResultadoAcceso cambiarClave(String claveActual, String claveNueva, String confirmacion) {
        if (sesionActiva == null) {
            return ResultadoAcceso.error("No hay una sesion activa.");
        }
        if (claveNueva == null || claveNueva.length() < LARGO_MINIMO_CLAVE) {
            return ResultadoAcceso.error("La contrasena debe tener al menos " + LARGO_MINIMO_CLAVE + " caracteres.");
        }
        if (!claveNueva.equals(confirmacion)) {
            return ResultadoAcceso.error("Las contrasenas no coinciden.");
        }
        if (!sesionActiva.cambiarClave(claveActual, claveNueva)) {
            return ResultadoAcceso.error("La contrasena actual no es correcta.");
        }
        return ResultadoAcceso.exito("Contrasena actualizada.", sesionActiva);
    }

    public ResultadoAcceso eliminarCuenta(String nombre, String clave) {
        Usuario encontrado = registrados.buscar(nombre);
        if (encontrado == null) {
            return ResultadoAcceso.error("El usuario no existe.");
        }
        if (!encontrado.claveCorrecta(clave)) {
            return ResultadoAcceso.error("Contrasena incorrecta.");
        }
        registrados.eliminar(nombre);
        if (sesionActiva == encontrado) {
            sesionActiva = null;
        }
        return ResultadoAcceso.exito("Cuenta eliminada.", null);
    }

    public Usuario obtenerRivalAleatorio() {
        if (rivales.estaVacia()) {
            return null;
        }
        return rivales.obtenerPorIndice(random.nextInt(rivales.contar()));
    }

    public Usuario obtenerRivalPorNombre(String nombre) {
        return rivales.buscar(nombre);
    }

    public Usuario obtenerRivalPorIndice(int indice) {
        return rivales.obtenerPorIndice(indice);
    }

    public int totalRivales() {
        return rivales.contar();
    }

    public int totalUsuarios() {
        return registrados.contar();
    }

    public Usuario obtenerUsuarioPorIndice(int indice) {
        return registrados.obtenerPorIndice(indice);
    }

    public boolean existeUsuario(String nombre) {
        return registrados.existe(nombre);
    }
}
