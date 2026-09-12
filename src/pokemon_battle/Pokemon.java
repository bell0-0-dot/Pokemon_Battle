
package pokemon_battle;

/**
 *
 * @author vasqu
 */
public class Pokemon {
    private String rutaImagen;
    private String nombre;
    private int nivel;
    private EnumTipo tipo;
    private int hp;
    private int HpMaximo;
    private int ataque;
    private int defensa;

    public Pokemon(String ruta,String nombre, int nivel, EnumTipo tipo, int hp, int ataque, int defensa) {
        this.rutaImagen=ruta;
        this.nombre = nombre;
        this.nivel = nivel;
        this.tipo = tipo;
        this.hp = hp;
        this.ataque = ataque;
        this.defensa = defensa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int niveel) {
        this.nivel = niveel;
    }

    public EnumTipo getTipo() {
        return tipo;
    }

    public void setTipo(EnumTipo tipo) {
        this.tipo = tipo;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getHpMaximo() {
        return HpMaximo;
    }

    public void setHpMaximo(int HpMaximo) {
        this.HpMaximo = HpMaximo;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
    
      @Override
    public String toString() {
        return "Pokemon{" +
                "nombre='" + nombre + '\'' +
                ", nivel=" + nivel +
                ", tipo='" + tipo + '\'' +
                ", hp=" + hp +
                ", ataque=" + ataque +
                ", defensa=" + defensa +
                '}';
    }
    
}
