
package pokemon_battle;

import Gui.VentanaBatalla;
import javafx.application.Application;
import javafx.stage.Stage;

public class Pokemon_Battle extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Entrenador jugador = new Entrenador("Ash");
        Pokemon pikachu = new Pokemon("Pikachu", 15, EnumTipo.Electrico, 100, 55, 40);
        jugador.agregarPokemon(pikachu);

        Entrenador rival = new Entrenador("Gary");
        Pokemon charizard = new Pokemon("Charizard", 18, EnumTipo.Fuego, 120, 60, 45);
        rival.agregarPokemon(charizard);

        VentanaBatalla batalla = new VentanaBatalla(jugador, rival);
        batalla.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}