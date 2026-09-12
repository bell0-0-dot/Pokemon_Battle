package Gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pokemon_battle.Entrenador;
import pokemon_battle.ListaObjetos;
import pokemon_battle.Objeto;
import pokemon_battle.Pokemon;
import pokemon_battle.batalla.Batalla;

public class VentanaInventario {

    private Batalla batalla;
    private Entrenador entrenador;
    private ListaObjetos inventario;
    private ListView<String> listaObjetosView;
    private ComboBox<String> comboPokemonObjetivo;
    private Label lblDescripcion;
    private Runnable alUsarObjeto;

    public VentanaInventario(Batalla batalla, Runnable alUsarObjeto) {
        this.batalla = batalla;
        this.entrenador = batalla.getJugador();
        this.inventario = entrenador.getInventario();
        this.alUsarObjeto = alUsarObjeto;
    }

    public void start(Stage stage) {
        stage.setTitle("Mochila de Objetos");

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
            System.out.println("Error al cargar fondo: " + e.getMessage());
        }

        VBox contenedor = new VBox(15);
        contenedor.setMaxWidth(480);
        contenedor.setMaxHeight(420);
        contenedor.setPadding(new Insets(20));
        contenedor.setAlignment(Pos.CENTER);
        contenedor.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.95);" +
                        "-fx-background-radius: 15px;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 12, 0, 0, 4);"
        );

        Label lblTitulo = new Label("MOCHILA DE OBJETOS");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2a75bb;");

        listaObjetosView = new ListView<>();
        listaObjetosView.setPrefHeight(130);

        lblDescripcion = new Label("Selecciona un objeto para ver su descripción.");
        lblDescripcion.setStyle("-fx-font-size: 11px; -fx-text-fill: #555555;");

        HBox boxObjetivo = new HBox(10);
        boxObjetivo.setAlignment(Pos.CENTER);
        Label lblObjetivo = new Label("Usar en:");
        lblObjetivo.setStyle("-fx-font-weight: bold;");
        comboPokemonObjetivo = new ComboBox<>();
        boxObjetivo.getChildren().addAll(lblObjetivo, comboPokemonObjetivo);

        HBox boxBotones = new HBox(12);
        boxBotones.setAlignment(Pos.CENTER);

        Button btnUsar = new Button("UTILIZAR");
        Button btnCancelar = new Button("CERRAR");

        btnUsar.setStyle("-fx-background-color: #3b4cca; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnCancelar.setStyle("-fx-background-color: #777777; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

        boxBotones.getChildren().addAll(btnUsar, btnCancelar);

        contenedor.getChildren().addAll(lblTitulo, listaObjetosView, lblDescripcion, boxObjetivo, boxBotones);
        root.getChildren().add(contenedor);

        listaObjetosView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                Objeto obj = inventario.buscar(newVal.split(" x")[0].trim());
                if (obj != null) {
                    lblDescripcion.setText(obj.getDescripcion());
                }
            }
        });

        btnUsar.setOnAction(e -> {
            String seleccionObjeto = listaObjetosView.getSelectionModel().getSelectedItem();
            String seleccionPokemon = comboPokemonObjetivo.getValue();

            if (seleccionObjeto == null || seleccionPokemon == null) {
                mostrarAlerta("Atención", "Debes seleccionar un objeto y un Pokémon.");
                return;
            }

            String nombreObjeto = seleccionObjeto.split(" x")[0].trim();
            String nombrePokemon = seleccionPokemon.split(" -")[0].trim();

            String resultado = batalla.usarObjeto(nombreObjeto, nombrePokemon);

            if (batalla.ultimaAccionAplicada()) {
                if (alUsarObjeto != null) {
                    alUsarObjeto.run();
                }
                stage.close();
            } else {
                mostrarAlerta("Acción inválida", resultado.trim());
                cargarDatos();
            }
        });

        btnCancelar.setOnAction(e -> stage.close());

        cargarDatos();

        Scene scene = new Scene(root, 600, 480);
        stage.setScene(scene);
        stage.show();
    }

    private void cargarDatos() {
        listaObjetosView.getItems().clear();
        for (int i = 0; i < inventario.contar(); i++) {
            Objeto obj = inventario.obtenerPorIndice(i);
            if (obj != null && obj.getCantidad() > 0) {
                listaObjetosView.getItems().add(obj.getNombre() + " x" + obj.getCantidad());
            }
        }
        if (!listaObjetosView.getItems().isEmpty()) {
            listaObjetosView.getSelectionModel().selectFirst();
        }

        comboPokemonObjetivo.getItems().clear();
        for (int i = 0; i < entrenador.totalPokemon(); i++) {
            Pokemon p = entrenador.obtenerPorIndice(i);
            if (p != null) {
                comboPokemonObjetivo.getItems().add(p.getNombre() + " - " + p.getHp() + "/" + p.getHpMaximo()
                        + (p.getHp() <= 0 ? " DERROTADO" : ""));
            }
        }
        if (!comboPokemonObjetivo.getItems().isEmpty()) {
            comboPokemonObjetivo.getSelectionModel().selectFirst();
        }
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
