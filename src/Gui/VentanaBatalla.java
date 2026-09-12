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
import pokemon_battle.ListaObjetos;
import pokemon_battle.LogicaJuego;
import pokemon_battle.Objeto;
import pokemon_battle.Pokemon;
import pokemon_battle.batalla.EstadisticasBatalla;
import pokemon_battle.batalla.Historial;
import pokemon_battle.usuarios.GestorUsuarios;

public class VentanaBatalla {

    private LogicaJuego logica;
    private Entrenador jugador;
    private Entrenador rival;
    private ListaObjetos inventarioJugador;
    private GestorUsuarios gestorUsuarios;
    private Stage stageActual;

    private Label lblNombreRival, lblHpRival, lblNivelRival, lblTipoRival;
    private ImageView imgRival;

    private Label lblNombreJugador, lblHpJugador, lblNivelJugador, lblTipoJugador;
    private ImageView imgJugador;

    private TextArea txtHistorial;
    private Button btnAtacar, btnCambiar, btnObjetos;

    public VentanaBatalla(Entrenador jugador, Entrenador rival, GestorUsuarios gestorUsuarios) {
        this.jugador = jugador;
        this.rival = rival;
        this.gestorUsuarios = gestorUsuarios;

        this.inventarioJugador = new ListaObjetos();
        this.inventarioJugador.insertar(new Objeto("Pocion", "Restaura 20 HP", 3, Objeto.TipoEfecto.CURAR, 20));
        this.inventarioJugador.insertar(new Objeto("Superpocion", "Restaura 50 HP", 1, Objeto.TipoEfecto.CURAR, 50));
        this.inventarioJugador.insertar(new Objeto("Revivir", "Revive con la mitad de HP", 1, Objeto.TipoEfecto.REVIVIR, 0));

        this.logica = new LogicaJuego(
                this.jugador,
                this.rival,
                this.inventarioJugador,
                new Historial(),
                new EstadisticasBatalla()
        );
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

        VBox cardRival = crearCajaDatos("ENTRENADOR RIVAL: " + rival.getNombre().toUpperCase());
        lblNombreRival = new Label();
        lblNivelRival = new Label();
        lblTipoRival = new Label();
        lblHpRival = new Label();
        lblHpRival.setStyle("-fx-font-weight: bold; -fx-text-fill: #cc0000;");
        cardRival.getChildren().addAll(lblNombreRival, lblNivelRival, lblTipoRival, lblHpRival);

        VBox cardJugador = crearCajaDatos("TU ENTRENADOR: " + jugador.getNombre().toUpperCase());
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

        btnAtacar.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ff4e50, #f9d423);" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-padding: 8px 20px;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);"
        );

        btnCambiar.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #2193b0, #6dd5ed);" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-padding: 8px 20px;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);"
        );

        btnObjetos.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #11998e, #38ef7d);" +
                        "-fx-text-fill: #ffffff;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-padding: 8px 20px;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);"
        );

        panelBotones.getChildren().addAll(btnAtacar, btnCambiar, btnObjetos);

        VBox panelHistorial = new VBox(5);
        Label lblHistorialHeader = new Label("HISTORIAL DE BATALLA:");
        lblHistorialHeader.setStyle("-fx-font-weight: bold; -fx-text-fill: #ffffff; -fx-effect: dropshadow(one-pass-box, black, 4, 0, 0, 1);");

        txtHistorial = new TextArea();
        txtHistorial.setEditable(false);
        txtHistorial.setPrefRowCount(4);
        txtHistorial.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px; -fx-control-inner-background: rgba(255, 255, 255, 0.9);");

        panelHistorial.getChildren().addAll(lblHistorialHeader, txtHistorial);

        VBox contenedorInferior = new VBox(8);
        contenedorInferior.getChildren().addAll(panelBotones, panelHistorial);
        layoutBatalla.setBottom(contenedorInferior);

        root.getChildren().add(layoutBatalla);

        btnAtacar.setOnAction(e -> {
            if (!logica.isBatallaTerminada()) {
                logica.ataca();
                actualizarEstadoBatalla();
                comprobarFinDeJuego();
            }
        });

        btnCambiar.setOnAction(e -> abrirDialogoCambio());

        btnObjetos.setOnAction(e -> {
            if (logica.isBatallaTerminada()) return;
            VentanaInventario vInventario = new VentanaInventario(jugador, inventarioJugador, () -> {
                actualizarEstadoBatalla();
            });
            vInventario.start(new Stage());
        });

        logica.getHistorial().registrar("¡Empieza la batalla entre " + jugador.getNombre() + " y " + rival.getNombre() + "!");
        actualizarEstadoBatalla();

        Scene scene = new Scene(root, 900, 650);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    private VBox crearCajaDatos(String titulo) {
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

        Label lblHeader = new Label(titulo);
        lblHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 10px; -fx-text-fill: #555555;");
        card.getChildren().add(lblHeader);

        return card;
    }

    private void abrirDialogoCambio() {
        if (logica.isBatallaTerminada()) return;

        ChoiceDialog<String> dialog = new ChoiceDialog<>();
        dialog.setTitle("Mi Equipo - Seleccionar Pokémon");
        dialog.setHeaderText("Elige el Pokémon con el que deseas pelear:");

        for (int i = 0; i < jugador.totalPokemon(); i++) {
            Pokemon p = jugador.obtenerPorIndice(i);
            if (p != null && p.getHp() > 0 && p != jugador.getPokemonActivo()) {
                dialog.getItems().add(p.getNombre() + " (HP: " + p.getHp() + ")");
            }
        }

        if (dialog.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No tienes otros Pokémon con vida disponible para cambiar.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        dialog.setSelectedItem(dialog.getItems().get(0));
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(seleccion -> {
            String nombrePokemon = seleccion.split(" \\(")[0].trim();
            if (logica.cambiarPokemon(nombrePokemon)) {
                actualizarEstadoBatalla();
            }
        });
    }

    public void actualizarEstadoBatalla() {
        Pokemon pJugador = jugador.getPokemonActivo();
        Pokemon pRival = rival.getPokemonActivo();

        if (pJugador != null) {
            lblNombreJugador.setText(pJugador.getNombre().toUpperCase());
            lblNivelJugador.setText("Nivel: " + pJugador.getNivel());
            lblTipoJugador.setText("Tipo: " + (pJugador.getTipo() != null ? pJugador.getTipo() : "Desconocido"));
            lblHpJugador.setText("❤️ " + pJugador.getHp() + " HP");
            cargarImagenPokemon(imgJugador, pJugador);
        }

        if (pRival != null) {
            lblNombreRival.setText(pRival.getNombre().toUpperCase());
            lblNivelRival.setText("Nivel: " + pRival.getNivel());
            lblTipoRival.setText("Tipo: " + (pRival.getTipo() != null ? pRival.getTipo() : "Desconocido"));
            lblHpRival.setText("❤️ " + pRival.getHp() + " HP");
            cargarImagenPokemon(imgRival, pRival);
        }

        txtHistorial.setText(logica.getHistorial().textoCompleto());
        txtHistorial.appendText("");
        txtHistorial.setScrollTop(Double.MAX_VALUE);
        txtHistorial.selectRange(txtHistorial.getLength(), txtHistorial.getLength());
    }

    private void comprobarFinDeJuego() {
        if (logica.isBatallaTerminada()) {
            btnAtacar.setDisable(true);
            btnCambiar.setDisable(true);
            btnObjetos.setDisable(true);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fin de la Batalla");
            alert.setHeaderText("Resumen de Partida");
            alert.setContentText(logica.getEstadisticas().resumen());
            alert.showAndWait();

            if (gestorUsuarios != null && stageActual != null) {
                VentanaSeleccionPokemon seleccion = new VentanaSeleccionPokemon(gestorUsuarios);
                seleccion.start(stageActual);
            }
        }
    }

    private void cargarImagenPokemon(ImageView view, Pokemon p) {
        try {
            if (p.getRutaImagen() != null && !p.getRutaImagen().isEmpty()) {
                view.setImage(new Image(getClass().getResourceAsStream(p.getRutaImagen())));
                if (view.getImage() != null) return;
            }
        } catch (Exception ignored) {}

        String[] intentos = {
                "/Imagenes/" + p.getNombre().toLowerCase() + ".png",
                "/Imagenes/" + p.getNombre() + ".png",
                "/RecursosGraficos/" + p.getNombre().toLowerCase() + ".png"
        };

        for (String ruta : intentos) {
            try {
                Image img = new Image(getClass().getResourceAsStream(ruta));
                if (img.getWidth() > 0) {
                    view.setImage(img);
                    return;
                }
            } catch (Exception ignored) {}
        }
        view.setImage(null);
    }
}