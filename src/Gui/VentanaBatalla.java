package Gui;

import java.util.Optional;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pokemon_battle.Entrenador;
import pokemon_battle.Pokemon;
import pokemon_battle.batalla.Batalla;
import pokemon_battle.usuarios.GestorUsuarios;
import pokemon_battle.usuarios.Usuario;

public class VentanaBatalla {

    private Batalla batalla;
    private Entrenador jugador;
    private Entrenador rival;
    private GestorUsuarios gestorUsuarios;
    private Stage stageActual;

    private Label lblCabeceraRival;
    private Label lblNombreRival, lblHpRival, lblNivelRival, lblTipoRival;
    private ImageView imgRival;

    private Label lblNombreJugador, lblHpJugador, lblNivelJugador, lblTipoJugador;
    private ImageView imgJugador;

    private TextArea txtHistorial;
    private Button btnAtacar, btnCambiar, btnObjetos, btnEquipo, btnHistorial;

    public VentanaBatalla(Entrenador jugador, Entrenador rival, GestorUsuarios gestorUsuarios) {
        this.jugador = jugador;
        this.rival = rival;
        this.gestorUsuarios = gestorUsuarios;
        this.batalla = new Batalla(jugador, rival);
    }

    public void start(Stage stage) {
        this.stageActual = stage;
        stage.setTitle("Pokémon Battle - Campo de Batalla");

        StackPane root = new StackPane();

        try {
            Image imgFondo = new Image(getClass().getResourceAsStream("/RecursosGraficos/CampoBatalla.jpg"));
            BackgroundImage bgImage = new BackgroundImage(
                    imgFondo,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(100, 100, true, true, false, true)
            );
            root.setBackground(new Background(bgImage));
        } catch (Exception e) {
            System.out.println("Error al cargar fondo: " + e.getMessage());
        }

        BorderPane layoutBatalla = new BorderPane();
        layoutBatalla.setPadding(new Insets(20));
        layoutBatalla.setStyle("-fx-background-color: transparent;");

        GridPane campo = new GridPane();
        campo.setAlignment(Pos.CENTER);
        campo.setHgap(60);
        campo.setVgap(10);
        campo.setStyle("-fx-background-color: transparent;");

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(40);
        col1.setHalignment(HPos.CENTER);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(20);
        col2.setHalignment(HPos.CENTER);

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(40);
        col3.setHalignment(HPos.CENTER);

        campo.getColumnConstraints().addAll(col1, col2, col3);

        lblCabeceraRival = new Label("ENTRENADOR RIVAL: " + rival.getNombre().toUpperCase());
        lblCabeceraRival.setStyle("-fx-font-weight: bold; -fx-font-size: 10px; -fx-text-fill: #555555;");
        VBox cardRival = crearCajaDatos(lblCabeceraRival);
        lblNombreRival = new Label();
        lblNivelRival = new Label();
        lblTipoRival = new Label();
        lblHpRival = new Label();
        lblHpRival.setStyle("-fx-font-weight: bold; -fx-text-fill: #cc0000;");
        cardRival.getChildren().addAll(lblNombreRival, lblNivelRival, lblTipoRival, lblHpRival);

        Label lblCabeceraJugador = new Label("TU ENTRENADOR: " + jugador.getNombre().toUpperCase());
        lblCabeceraJugador.setStyle("-fx-font-weight: bold; -fx-font-size: 10px; -fx-text-fill: #555555;");
        VBox cardJugador = crearCajaDatos(lblCabeceraJugador);
        lblNombreJugador = new Label();
        lblNivelJugador = new Label();
        lblTipoJugador = new Label();
        lblHpJugador = new Label();
        lblHpJugador.setStyle("-fx-font-weight: bold; -fx-text-fill: #008800;");
        cardJugador.getChildren().addAll(lblNombreJugador, lblNivelJugador, lblTipoJugador, lblHpJugador);

        imgJugador = new ImageView();
        imgJugador.setFitWidth(160);
        imgJugador.setFitHeight(160);
        imgJugador.setPreserveRatio(true);

        imgRival = new ImageView();
        imgRival.setFitWidth(160);
        imgRival.setFitHeight(160);
        imgRival.setPreserveRatio(true);

        Label lblVS = new Label("⚔️ VS ⚔️");
        lblVS.setStyle("-fx-font-size: 30px; -fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 8, 0, 0, 2);");

        campo.add(cardJugador, 0, 0);
        campo.add(cardRival, 2, 0);

        campo.add(imgJugador, 0, 1);
        campo.add(lblVS, 1, 1);
        campo.add(imgRival, 2, 1);

        layoutBatalla.setCenter(campo);

        HBox panelBotones = new HBox(20);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.setPadding(new Insets(10, 0, 10, 0));

        btnAtacar = new Button("⚔️ ATACAR");
        btnCambiar = new Button("🔄 CAMBIAR");
        btnObjetos = new Button("🎒 OBJETOS");
        btnEquipo = new Button("📋 MI EQUIPO");
        btnHistorial = new Button("📜 HISTORIAL");

        btnAtacar.setStyle(estiloBoton("#ff4e50", "#f9d423", "#ffffff"));
        btnCambiar.setStyle(estiloBoton("#2193b0", "#6dd5ed", "#ffffff"));
        btnObjetos.setStyle(estiloBoton("#11998e", "#38ef7d", "#ffffff"));
        btnEquipo.setStyle(estiloBoton("#3b4cca", "#5c6bc0", "#ffffff"));
        btnHistorial.setStyle(estiloBoton("#616161", "#9e9e9e", "#ffffff"));

        panelBotones.getChildren().addAll(btnAtacar, btnCambiar, btnObjetos, btnEquipo, btnHistorial);

        VBox panelHistorial = new VBox(5);
        Label lblHistorialHeader = new Label("HISTORIAL DE BATALLA:");
        lblHistorialHeader.setStyle("-fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(one-pass-box, black, 4, 0, 0, 1);");

        txtHistorial = new TextArea();
        txtHistorial.setEditable(false);
        txtHistorial.setPrefRowCount(6);
        txtHistorial.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px; -fx-control-inner-background: rgba(255, 255, 255, 0.9);");

        panelHistorial.getChildren().addAll(lblHistorialHeader, txtHistorial);

        VBox contenedorInferior = new VBox(8);
        contenedorInferior.getChildren().addAll(panelBotones, panelHistorial);
        layoutBatalla.setBottom(contenedorInferior);

        root.getChildren().add(layoutBatalla);

        btnAtacar.setOnAction(e -> {
            if (batalla.terminada()) {
                return;
            }
            batalla.atacar();
            actualizarEstadoBatalla();
            comprobarFinDeJuego();
        });

        btnCambiar.setOnAction(e -> abrirDialogoCambio());

        btnObjetos.setOnAction(e -> {
            if (batalla.terminada()) {
                return;
            }
            VentanaInventario vInventario = new VentanaInventario(batalla, () -> {
                actualizarEstadoBatalla();
                comprobarFinDeJuego();
            });
            vInventario.start(new Stage());
        });

        btnEquipo.setOnAction(e -> {
            VentanaEquipo vEquipo = new VentanaEquipo(jugador, true);
            vEquipo.start(new Stage());
        });

        btnHistorial.setOnAction(e -> abrirHistorialCompleto());

        actualizarEstadoBatalla();

        Scene scene = new Scene(root, 900, 650);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    private String estiloBoton(String desde, String hasta, String texto) {
        return "-fx-background-color: linear-gradient(to bottom, " + desde + ", " + hasta + ");"
                + "-fx-text-fill: " + texto + ";"
                + "-fx-font-weight: bold;"
                + "-fx-font-size: 14px;"
                + "-fx-background-radius: 8px;"
                + "-fx-padding: 8px 20px;"
                + "-fx-cursor: hand;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);";
    }

    private VBox crearCajaDatos(Label cabecera) {
        VBox card = new VBox(4);
        card.setPadding(new Insets(8, 12, 8, 12));
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(200);
        card.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.92);" +
                        "-fx-border-color: #2a75bb;" +
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 8px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 6, 0, 0, 2);"
        );
        card.getChildren().add(cabecera);
        return card;
    }

    private void abrirDialogoCambio() {
        if (batalla.terminada()) {
            return;
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>();
        dialog.setTitle("Seleccionar Pokémon");
        dialog.setHeaderText("Elige el Pokémon con el que deseas pelear:");

        for (int i = 0; i < jugador.totalPokemon(); i++) {
            Pokemon p = jugador.obtenerPorIndice(i);
            if (p != null && p.getHp() > 0 && p != jugador.getPokemonActivo()) {
                dialog.getItems().add(p.getNombre() + " (" + p.getHp() + "/" + p.getHpMaximo() + ")");
            }
        }

        if (dialog.getItems().isEmpty()) {
            mostrarAviso("No tienes otros Pokémon con vida disponibles para cambiar.");
            return;
        }

        dialog.setSelectedItem(dialog.getItems().get(0));
        Optional<String> resultado = dialog.showAndWait();

        resultado.ifPresent(seleccion -> {
            String nombrePokemon = seleccion.split(" \\(")[0].trim();
            String mensaje = batalla.cambiarPokemon(nombrePokemon);
            if (!batalla.ultimaAccionAplicada()) {
                mostrarAviso(mensaje.trim());
                return;
            }
            actualizarEstadoBatalla();
            comprobarFinDeJuego();
        });
    }

    private void abrirHistorialCompleto() {
        TextArea area = new TextArea(batalla.getHistorial().textoCompleto()
                + "\n----------------------------------------\n"
                + batalla.getEstadisticas().resumen());
        area.setEditable(false);
        area.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");

        VBox caja = new VBox(10, new Label("HISTORIAL DE BATALLA"), area);
        caja.setPadding(new Insets(15));
        VBox.setVgrow(area, Priority.ALWAYS);

        Stage ventana = new Stage();
        ventana.setTitle("Historial de Batalla");
        ventana.setScene(new Scene(caja, 480, 520));
        ventana.show();
    }

    public void actualizarEstadoBatalla() {
        Pokemon pJugador = jugador.getPokemonActivo();
        Pokemon pRival = rival.getPokemonActivo();

        if (pJugador != null) {
            lblNombreJugador.setText(pJugador.getNombre().toUpperCase());
            lblNivelJugador.setText("Nivel: " + pJugador.getNivel());
            lblTipoJugador.setText("Tipo: " + (pJugador.getTipo() != null ? pJugador.getTipo() : "Desconocido"));
            lblHpJugador.setText("❤️ " + pJugador.getHp() + " / " + pJugador.getHpMaximo());
            cargarImagenPokemon(imgJugador, pJugador);
        }

        if (pRival != null) {
            lblNombreRival.setText(pRival.getNombre().toUpperCase());
            lblNivelRival.setText("Nivel: " + pRival.getNivel());
            lblTipoRival.setText("Tipo: " + (pRival.getTipo() != null ? pRival.getTipo() : "Desconocido"));
            lblHpRival.setText("❤️ " + pRival.getHp() + " / " + pRival.getHpMaximo());
            cargarImagenPokemon(imgRival, pRival);
        }

        txtHistorial.setText(batalla.getHistorial().ultimasLineas(6));
        txtHistorial.positionCaret(txtHistorial.getLength());
        txtHistorial.setScrollTop(Double.MAX_VALUE);
    }

    private void comprobarFinDeJuego() {
        if (!batalla.terminada()) {
            return;
        }

        habilitarControles(false);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin de la Batalla");
        alert.setHeaderText(batalla.getEstado() == Batalla.Estado.VICTORIA
                ? "¡Ganaste la batalla!"
                : "Has perdido la batalla");
        alert.setContentText(batalla.getEstadisticas().resumen());

        ButtonType btnOtra = new ButtonType("Jugar otra vez");
        ButtonType btnMenu = new ButtonType("Volver a mi equipo", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(btnOtra, btnMenu);

        Optional<ButtonType> respuesta = alert.showAndWait();

        if (respuesta.isPresent() && respuesta.get() == btnOtra) {
            reiniciarPartida();
        } else {
            batalla.reiniciar();
            if (gestorUsuarios != null && stageActual != null) {
                VentanaSeleccionPokemon seleccion = new VentanaSeleccionPokemon(gestorUsuarios);
                seleccion.start(stageActual);
            }
        }
    }

    private void reiniciarPartida() {
        batalla.reiniciar();

        if (gestorUsuarios != null) {
            Usuario nuevoRival = gestorUsuarios.obtenerRivalAleatorio();
            if (nuevoRival != null) {
                rival = nuevoRival.getEntrenador();
                rival.reiniciarEquipo();
                batalla = new Batalla(jugador, rival);
                lblCabeceraRival.setText("ENTRENADOR RIVAL: " + rival.getNombre().toUpperCase());
            }
        }

        habilitarControles(true);
        actualizarEstadoBatalla();
    }

    private void habilitarControles(boolean activos) {
        btnAtacar.setDisable(!activos);
        btnCambiar.setDisable(!activos);
        btnObjetos.setDisable(!activos);
    }

    private void mostrarAviso(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING, mensaje, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void cargarImagenPokemon(ImageView view, Pokemon p) {
        try {
            if (p.getRutaImagen() != null && !p.getRutaImagen().isEmpty()) {
                Image img = new Image(getClass().getResourceAsStream(p.getRutaImagen()));
                if (img.getWidth() > 0) {
                    view.setImage(img);
                    return;
                }
            }
        } catch (Exception ignored) {
        }

        String[] intentos = {
                "/Imagenes/" + p.getNombre().toLowerCase() + ".png",
                "/Imagenes/" + p.getNombre() + ".png",
                "/Imagenes/" + p.getNombre() + ".jpg"
        };

        for (String ruta : intentos) {
            try {
                Image img = new Image(getClass().getResourceAsStream(ruta));
                if (img.getWidth() > 0) {
                    view.setImage(img);
                    return;
                }
            } catch (Exception ignored) {
            }
        }
        view.setImage(null);
    }
}
