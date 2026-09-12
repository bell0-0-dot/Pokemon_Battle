package pokemon_battle.vista;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pokemon_battle.usuarios.GestorUsuarios;
import pokemon_battle.usuarios.Usuario;

public class VentanaPrincipal extends Application {

    private GestorUsuarios gestor;

    @Override
    public void start(Stage escenario) {
        gestor = new GestorUsuarios();

        Label titulo = new Label("POKEMON BATTLE");
        Label detalle = new Label("JavaFX " + System.getProperty("javafx.runtime.version")
                + " sobre Java " + System.getProperty("java.version"));
        Label salida = new Label("Rivales cargados: " + gestor.totalRivales());

        Button boton = new Button("Rival aleatorio");
        boton.setOnAction(evento -> {
            Usuario rival = gestor.obtenerRivalAleatorio();
            salida.setText(rival.getNombre() + ": " + rival.getEntrenador().verEquipo());
        });

        VBox raiz = new VBox(12, titulo, detalle, boton, salida);
        raiz.setAlignment(Pos.CENTER);
        raiz.setPadding(new Insets(24));

        escenario.setTitle("Pokemon Battle");
        escenario.setScene(new Scene(raiz, 520, 240));
        escenario.show();
    }
}
