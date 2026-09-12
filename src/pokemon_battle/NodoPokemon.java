/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon_battle;

/**
 *
 * @author vasqu
 */
public class NodoPokemon {
    private Pokemon pokemon;
    private NodoPokemon siguiente;

    public NodoPokemon(Pokemon pokemon, NodoPokemon siguiente) {
        this.pokemon = pokemon;
        this.siguiente = siguiente;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public NodoPokemon getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPokemon siguiente) {
        this.siguiente = siguiente;
    }
    
    
    
}
