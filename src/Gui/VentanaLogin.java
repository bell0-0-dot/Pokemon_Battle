package Gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pokemon_battle.usuarios.GestorUsuarios;
import pokemon_battle.usuarios.ResultadoAcceso;

public class VentanaLogin extends Application {

    private GestorUsuarios gestorUsuarios;
    private TextField txtUsuario;
    private PasswordField txtPassword;

    public VentanaLogin() {
        this.gestorUsuarios = new GestorUsuarios();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pokémon Battle - Inicio de Sesión");

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
            System.out.println("Error al cargar imagen: " + e.getMessage());
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

        Label lblTitulo = new Label("POKÉMON BATTLE");
        lblTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2a75bb;");

        Label lblSubtitulo = new Label("Ingrese sus credenciales para continuar");
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

        btnIniciar.setOnAction(e -> ejecutarInicioSesion(primaryStage));
        btnRegistrar.setOnAction(e -> ejecutarRegistro());

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void ejecutarInicioSesion(Stage stage) {
        String user = txtUsuario.getText();
        String pass = txtPassword.getText();

        ResultadoAcceso resultado = gestorUsuarios.iniciarSesion(user, pass);

        if (resultado.esExitoso()) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", resultado.getMensaje());
            VentanaSeleccionPokemon seleccion = new VentanaSeleccionPokemon(gestorUsuarios);
            seleccion.start(stage);
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Inicio de Sesión", resultado.getMensaje());
        }
    }

    private void ejecutarRegistro() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Crear Nuevo Usuario");
        dialog.setHeaderText("Ingrese los datos del nuevo entrenador:");

        ButtonType btnCrear = new ButtonType("Registrar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnCrear, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nuevoUser = new TextField();
        nuevoUser.setPromptText("Usuario");
        PasswordField nuevaPass = new PasswordField();
        PasswordField confirmPass = new PasswordField();

        grid.add(new Label("Usuario:"), 0, 0);
        grid.add(nuevoUser, 1, 0);
        grid.add(new Label("Contraseña:"), 0, 1);
        grid.add(nuevaPass, 1, 1);
        grid.add(new Label("Confirmar:"), 0, 2);
        grid.add(confirmPass, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.showAndWait().ifPresent(response -> {
            if (response == btnCrear) {
                ResultadoAcceso res = gestorUsuarios.registrar(
                        nuevoUser.getText(),
                        nuevaPass.getText(),
                        confirmPass.getText()
                );

                if (res.esExitoso()) {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Registro Exitoso", res.getMensaje());
                    txtUsuario.setText(nuevoUser.getText());
                    txtPassword.setText("");
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error de Registro", res.getMensaje());
                }
            }
        });
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}