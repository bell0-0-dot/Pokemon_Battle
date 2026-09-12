
package pokemon_battle;

import Gui.VentanaLogin;
import javafx.application.Application;
import javafx.stage.Stage;

public class Pokemon_Battle extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        VentanaLogin login = new VentanaLogin();
        login.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}