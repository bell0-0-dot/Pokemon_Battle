package Gui;

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
import pokemon_battle.datos.Pokedex;
import pokemon_battle.usuarios.GestorUsuarios;
import pokemon_battle.usuarios.Usuario;

public class VentanaSeleccionPokemon {

    private static final int LIMITE_EQUIPO = 3;

    private GestorUsuarios gestorUsuarios;
    private Pokedex pokedex;
    private ListView<String> listPokedex;
    private ListView<String> listMiEquipo;
    private ImageView imgVistaPrevia;
    private Label lblDetalles;

    public VentanaSeleccionPokemon(GestorUsuarios gestorUsuarios) {
        this.gestorUsuarios = gestorUsuarios;
        this.pokedex = new Pokedex();
    }

    public void start(Stage stage) {
        Entrenador jugador = gestorUsuarios.getEntrenadorActual();
        stage.setTitle("Selección de Pokémon - " + jugador.getNombre());

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
            System.out.println("No se cargó el fondo: " + e.getMessage());
        }

        VBox contenedor = new VBox(15);
        contenedor.setMaxWidth(750);
        contenedor.setMaxHeight(550);
        contenedor.setPadding(new Insets(20));
        contenedor.setAlignment(Pos.CENTER);
        contenedor.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.95);" +
                        "-fx-background-radius: 15px;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 12, 0, 0, 4);"
        );

        Label lblTitulo = new Label("SELECCIONA TU EQUIPO (MÁXIMO 3 POKÉMON)");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2a75bb;");

        HBox listasBox = new HBox(20);
        listasBox.setAlignment(Pos.CENTER);

        VBox boxPokedex = new VBox(8);
        boxPokedex.getChildren().add(new Label("Pokédex (Disponibles):"));
        listPokedex = new ListView<>();
        listPokedex.setPrefWidth(220);
        listPokedex.setPrefHeight(250);
        boxPokedex.getChildren().add(listPokedex);

        VBox boxDetalles = new VBox(10);
        boxDetalles.setAlignment(Pos.CENTER);
        boxDetalles.setPrefWidth(200);

        imgVistaPrevia = new ImageView();
        imgVistaPrevia.setFitWidth(110);
        imgVistaPrevia.setFitHeight(110);
        imgVistaPrevia.setPreserveRatio(true);

        lblDetalles = new Label("Selecciona un Pokémon");
        lblDetalles.setStyle("-fx-font-size: 11px; -fx-text-alignment: center;");

        Button btnAgregar = new Button("Añadir al Equipo ➔");
        btnAgregar.setStyle("-fx-background-color: #3b4cca; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

        boxDetalles.getChildren().addAll(imgVistaPrevia, lblDetalles, btnAgregar);

        VBox boxEquipo = new VBox(8);
        boxEquipo.getChildren().add(new Label("Tu Equipo Seleccionado:"));
        listMiEquipo = new ListView<>();
        listMiEquipo.setPrefWidth(220);
        listMiEquipo.setPrefHeight(250);
        boxEquipo.getChildren().add(listMiEquipo);

        listasBox.getChildren().addAll(boxPokedex, boxDetalles, boxEquipo);

        Button btnIrABatalla = new Button("¡IR A LA BATALLA!");
        btnIrABatalla.setStyle("-fx-background-color: #ffde00; -fx-text-fill: #3b4cca; -fx-font-weight: bold; -fx-font-size: 15px; -fx-cursor: hand; -fx-padding: 10 25 10 25;");

        Button btnGestionarEquipo = new Button("GESTIONAR MI EQUIPO");
        btnGestionarEquipo.setStyle("-fx-background-color: #3b4cca; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px; -fx-cursor: hand; -fx-padding: 10 20 10 20;");

        HBox boxAcciones = new HBox(15, btnGestionarEquipo, btnIrABatalla);
        boxAcciones.setAlignment(Pos.CENTER);

        btnGestionarEquipo.setOnAction(e -> {
            Stage ventanaEquipo = new Stage();
            VentanaEquipo vEquipo = new VentanaEquipo(jugador);
            vEquipo.start(ventanaEquipo);
            ventanaEquipo.setOnHidden(ev -> actualizarListaEquipo());
        });

        contenedor.getChildren().addAll(lblTitulo, listasBox, boxAcciones);
        root.getChildren().add(contenedor);

        listPokedex.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && newVal.intValue() >= 0) {
                Pokemon p = pokedex.verPlantilla(newVal.intValue());
                if (p != null) {
                    lblDetalles.setText(p.getNombre() + "\nTipo: " + p.getTipo()
                            + "\nNvl: " + p.getNivel() + " | HP: " + p.getHpMaximo()
                            + "\nAtq: " + p.getAtaque() + " | Def: " + p.getDefensa());
                    cargarImagenPokemon(imgVistaPrevia, p);
                }
            }
        });

        btnAgregar.setOnAction(e -> {
            if (jugador.totalPokemon() >= LIMITE_EQUIPO) {
                mostrarAlerta("Límite Alcanzado", "Solo puedes llevar un máximo de 3 Pokémon a la batalla.");
                return;
            }

            int idx = listPokedex.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                Pokemon seleccionado = pokedex.crearPorIndice(idx);
                if (jugador.agregarPokemon(seleccionado)) {
                    actualizarListaEquipo();
                } else {
                    mostrarAlerta("Atención", "Este Pokémon ya está en tu equipo.");
                }
            }
        });

        btnIrABatalla.setOnAction(e -> {
            if (!jugador.tieneEquipo()) {
                mostrarAlerta("Equipo Vacío", "Debes agregar al menos 1 Pokémon a tu equipo para empezar.");
                return;
            }

            Usuario rivalUsuario = gestorUsuarios.obtenerRivalAleatorio();
            VentanaBatalla batalla = new VentanaBatalla(jugador, rivalUsuario.getEntrenador(), gestorUsuarios);
            batalla.start(stage);
        });

        cargarPokedex();
        actualizarListaEquipo();

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    private void cargarPokedex() {
        listPokedex.getItems().clear();
        for (int i = 0; i < pokedex.contar(); i++) {
            Pokemon p = pokedex.verPlantilla(i);
            if (p != null) {
                listPokedex.getItems().add(p.getNombre() + "  [" + p.getTipo() + "]  Nvl " + p.getNivel());
            }
        }
    }

    private void actualizarListaEquipo() {
        listMiEquipo.getItems().clear();
        Entrenador jugador = gestorUsuarios.getEntrenadorActual();
        for (int i = 0; i < jugador.totalPokemon(); i++) {
            Pokemon p = jugador.obtenerPorIndice(i);
            if (p != null) {
                listMiEquipo.getItems().add((i + 1) + ". " + p.getNombre() + "  [" + p.getTipo() + "]  "
                        + p.getHp() + "/" + p.getHpMaximo());
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

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}