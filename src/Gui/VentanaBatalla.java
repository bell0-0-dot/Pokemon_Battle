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
import pokemon_battle.Objeto;
import pokemon_battle.Pokemon;
import pokemon_battle.batalla.Historial;

public class VentanaBatalla {

    private Entrenador jugador;
    private Entrenador rival;
    private Historial historial;
    private ListaObjetos inventarioJugador;

    private Label lblNombreRival, lblHpRival, lblNivelRival, lblTipoRival;
    private ImageView imgRival;

    private Label lblNombreJugador, lblHpJugador, lblNivelJugador, lblTipoJugador;
    private ImageView imgJugador;

    private TextArea txtHistorial;
    private Button btnAtacar, btnCambiar, btnObjetos, btnEquipo;

    public VentanaBatalla(Entrenador jugador, Entrenador rival) {
        this.jugador = jugador;
        this.rival = rival;
        this.historial = new Historial();

        this.inventarioJugador = new ListaObjetos();
        this.inventarioJugador.insertar(new Objeto("Pocion", "Restaura 20 HP", 3, Objeto.TipoEfecto.CURAR, 20));
        this.inventarioJugador.insertar(new Objeto("Superpocion", "Restaura 50 HP", 1, Objeto.TipoEfecto.CURAR, 50));
        this.inventarioJugador.insertar(new Objeto("Revivir", "Revive con la mitad de HP", 1, Objeto.TipoEfecto.REVIVIR, 0));
    }

    public void start(Stage stage) {
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
            System.out.println("Error al cargar imagen de fondo: " + e.getMessage());
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
        lblVS.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2a75bb; -fx-effect: dropshadow(one-pass-box, white, 5, 0, 0, 0);");

        campo.add(cardJugador, 0, 0);
        campo.add(cardRival, 2, 0);

        campo.add(imgJugador, 0, 1);
        campo.add(lblVS, 1, 1);
        campo.add(imgRival, 2, 1);

        layoutBatalla.setCenter(campo);

        HBox panelBotones = new HBox(15);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.setPadding(new Insets(10, 0, 10, 0));

        btnAtacar = new Button("ATACAR");
        btnCambiar = new Button("CAMBIAR");
        btnObjetos = new Button("OBJETOS");
        btnEquipo = new Button("MI EQUIPO");

        String estiloAccion = "-fx-background-color: #3b4cca; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-cursor: hand; -fx-min-width: 100px;";
        btnAtacar.setStyle("-fx-background-color: #ffde00; -fx-text-fill: #3b4cca; -fx-font-weight: bold; -fx-font-size: 13px; -fx-cursor: hand; -fx-min-width: 100px;");
        btnCambiar.setStyle(estiloAccion);
        btnObjetos.setStyle(estiloAccion);
        btnEquipo.setStyle("-fx-background-color: #4caf50; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-cursor: hand; -fx-min-width: 100px;");

        panelBotones.getChildren().addAll(btnAtacar, btnCambiar, btnObjetos, btnEquipo);


        VBox panelHistorial = new VBox(5);
        Label lblHistorialHeader = new Label("HISTORIAL DE BATALLA:");
        lblHistorialHeader.setStyle("-fx-font-weight: bold; -fx-text-fill: #111111; -fx-effect: dropshadow(one-pass-box, white, 3, 0, 0, 0);");

        txtHistorial = new TextArea();
        txtHistorial.setEditable(false);
        txtHistorial.setPrefRowCount(4);
        txtHistorial.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px; -fx-control-inner-background: rgba(255, 255, 255, 0.9);");

        panelHistorial.getChildren().addAll(lblHistorialHeader, txtHistorial);

        VBox contenedorInferior = new VBox(8);
        contenedorInferior.getChildren().addAll(panelBotones, panelHistorial);
        layoutBatalla.setBottom(contenedorInferior);

        root.getChildren().add(layoutBatalla);


        btnCambiar.setOnAction(e -> abrirDialogoCambio());
        btnEquipo.setOnAction(e -> abrirDialogoCambio());

        btnObjetos.setOnAction(e -> {
            VentanaInventario vInventario = new VentanaInventario(jugador, inventarioJugador, () -> {
                historial.registrar(jugador.getNombre() + " usó un objeto del inventario.");
                actualizarEstadoBatalla();
            });
            vInventario.start(new Stage());
        });

        btnAtacar.setOnAction(e -> ejecutarAtaquePrueba());

        historial.registrar("¡Empieza la batalla entre " + jugador.getNombre() + " y " + rival.getNombre() + "!");
        actualizarEstadoBatalla();

        Scene scene = new Scene(root, 900, 650);
        stage.setScene(scene);
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
            if (jugador.cambiarPokemon(nombrePokemon)) {
                historial.registrar(jugador.getNombre() + " cambió a " + nombrePokemon + ".");
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

        txtHistorial.setText(historial.textoCompleto());
        txtHistorial.appendText("");
        txtHistorial.setScrollTop(Double.MAX_VALUE);
        txtHistorial.selectRange(txtHistorial.getLength(), txtHistorial.getLength());
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

    private void ejecutarAtaquePrueba() {
        Pokemon pJugador = jugador.getPokemonActivo();
        Pokemon pRival = rival.getPokemonActivo();

        if (pJugador != null && pRival != null) {
            historial.registrar(pJugador.getNombre() + " atacó a " + pRival.getNombre() + ".");
            historial.siguienteTurno();
            actualizarEstadoBatalla();
        }
    }
}