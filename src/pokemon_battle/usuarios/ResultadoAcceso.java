package pokemon_battle.usuarios;

public class ResultadoAcceso {

    private boolean exitoso;
    private String mensaje;
    private Usuario usuario;

    private ResultadoAcceso(boolean exitoso, String mensaje, Usuario usuario) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        this.usuario = usuario;
    }

    public static ResultadoAcceso exito(String mensaje, Usuario usuario) {
        return new ResultadoAcceso(true, mensaje, usuario);
    }

    public static ResultadoAcceso error(String mensaje) {
        return new ResultadoAcceso(false, mensaje, null);
    }

    public boolean esExitoso() {
        return exitoso;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
