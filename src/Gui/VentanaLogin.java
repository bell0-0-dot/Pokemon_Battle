package Gui;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class VentanaLogin extends Application {

    private TextField txtUsuario;
    private PasswordField txtPassword;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pokemon Battle - Inicio de Sesión");

        StackPane root = new StackPane();

        try {
            Image imgFondo = new Image(getClass().getResourceAsStream("/RecursosGraficos/Pokemon_Center.jpg"));
            BackgroundImage bgImage = new BackgroundImage(
                    imgFondo,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, false, true)
            );
            root.setBackground(new Background(bgImage));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        VBox tarjetaLogin = new VBox(15);
        tarjetaLogin.setMaxWidth(380);
        tarjetaLogin.setMaxHeight(300);
        tarjetaLogin.setPadding(new Insets(30));
        tarjetaLogin.setAlignment(Pos.CENTER);
        tarjetaLogin.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.92);" +
                        "-fx-background-radius: 15px;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 12, 0, 0, 4);"
        );

        Label lblTitulo = new Label("POKEMON BATTLE");
        lblTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2a75bb;");

        Label lblSubtitulo = new Label("¡Ingresa tu usuario o crea uno para jugar!");
        lblSubtitulo.setStyle("-fx-font-size: 12px; -fx-text-fill: #555555;");

        GridPane gridForm = new GridPane();
        gridForm.setHgap(10);
        gridForm.setVgap(12);
        gridForm.setAlignment(Pos.CENTER);

        Label lblUser = new Label("Usuario:");
        lblUser.setStyle("-fx-font-weight: bold;");
        txtUsuario = new TextField();
        txtUsuario.setPromptText("Nombre de usuario");

        Label lblPass = new Label("Contraseña:");
        lblPass.setStyle("-fx-font-weight: bold;");
        txtPassword = new PasswordField();

        gridForm.add(lblUser, 0, 0);
        gridForm.add(txtUsuario, 1, 0);
        gridForm.add(lblPass, 0, 1);
        gridForm.add(txtPassword, 1, 1);

        HBox boxBotonesAccion = new HBox(12);
        boxBotonesAccion.setAlignment(Pos.CENTER);
        boxBotonesAccion.setPadding(new Insets(10, 0, 0, 0));

        Button btnIniciar = new Button("Iniciar Sesión");
        Button btnRegistrar = new Button("Crear Usuario");

        btnIniciar.setStyle("-fx-background-color: #3b4cca; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnRegistrar.setStyle("-fx-background-color: #ffde00; -fx-text-fill: #3b4cca; -fx-font-weight: bold; -fx-cursor: hand;");

        boxBotonesAccion.getChildren().addAll(btnIniciar, btnRegistrar);

        tarjetaLogin.getChildren().addAll(lblTitulo, lblSubtitulo, gridForm, boxBotonesAccion);
        root.getChildren().add(tarjetaLogin);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}




