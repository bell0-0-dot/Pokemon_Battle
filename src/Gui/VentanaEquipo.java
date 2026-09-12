package Gui;

import java.util.Optional;
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

public class VentanaEquipo {

    private Entrenador entrenador;
    private boolean modoBatalla;
    private Pokedex pokedex;
    private ListView<String> listaEquipoView;
    private Label lblDisponibles;
    private ImageView imgVistaPrevia;
    private Label lblDetallesPokemon;

    public VentanaEquipo(Entrenador entrenador) {
        this(entrenador, false);
    }

    public VentanaEquipo(Entrenador entrenador, boolean modoBatalla) {
        this.entrenador = entrenador;
        this.modoBatalla = modoBatalla;
        this.pokedex = new Pokedex();
    }

    public void start(Stage stage) {
        stage.setTitle("Mi Equipo - " + entrenador.getNombre());

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
            System.out.println("No se pudo cargar la imagen de fondo: " + e.getMessage());
        }

        VBox contenedor = new VBox(15);
        contenedor.setMaxWidth(700);
        contenedor.setMaxHeight(520);
        contenedor.setPadding(new Insets(20));
        contenedor.setAlignment(Pos.CENTER);
        contenedor.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.94);" +
                        "-fx-background-radius: 15px;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 12, 0, 0, 4);"
        );

        Label lblTitulo = new Label("EQUIPO DE " + entrenador.getNombre().toUpperCase());
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2a75bb;");

        lblDisponibles = new Label();
        lblDisponibles.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        HBox centroBox = new HBox(15);
        centroBox.setAlignment(Pos.CENTER);

        listaEquipoView = new ListView<>();
        listaEquipoView.setPrefWidth(380);
        listaEquipoView.setPrefHeight(260);

        VBox panelDetalles = new VBox(10);
        panelDetalles.setAlignment(Pos.CENTER);
        panelDetalles.setPrefWidth(220);
        panelDetalles.setPadding(new Insets(10));
        panelDetalles.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #cccccc; -fx-border-radius: 8px; -fx-background-radius: 8px;");

        imgVistaPrevia = new ImageView();
        imgVistaPrevia.setFitWidth(100);
        imgVistaPrevia.setFitHeight(100);
        imgVistaPrevia.setPreserveRatio(true);

        lblDetallesPokemon = new Label("Selecciona un Pokémon\npara ver sus detalles.");
        lblDetallesPokemon.setStyle("-fx-font-size: 11px; -fx-text-alignment: center;");

        panelDetalles.getChildren().addAll(imgVistaPrevia, lblDetallesPokemon);
        centroBox.getChildren().addAll(listaEquipoView, panelDetalles);

        HBox boxBotones = new HBox(10);
        boxBotones.setAlignment(Pos.CENTER);

        Button btnAgregar = new Button("Agregar");
        Button btnMoverFrente = new Button("Mover al Frente");
        Button btnBuscar = new Button("Buscar");
        Button btnEliminar = new Button("Eliminar");
        Button btnCerrar = new Button("Cerrar");

        String estiloBtn = "-fx-background-color: #3b4cca; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;";
        btnAgregar.setStyle(estiloBtn);
        btnBuscar.setStyle(estiloBtn);
        btnMoverFrente.setStyle("-fx-background-color: #ffde00; -fx-text-fill: #3b4cca; -fx-font-weight: bold; -fx-cursor: hand;");
        btnEliminar.setStyle("-fx-background-color: #cc0000; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnCerrar.setStyle("-fx-background-color: #777777; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

        if (modoBatalla) {
            boxBotones.getChildren().addAll(btnBuscar, btnCerrar);
        } else {
            boxBotones.getChildren().addAll(btnAgregar, btnMoverFrente, btnBuscar, btnEliminar, btnCerrar);
        }

        contenedor.getChildren().addAll(lblTitulo, lblDisponibles, centroBox, boxBotones);
        root.getChildren().add(contenedor);

        listaEquipoView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && newVal.intValue() >= 0) {
                Pokemon p = entrenador.obtenerPorIndice(newVal.intValue());
                if (p != null) {
                    mostrarDetalles(p);
                }
            }
        });

        btnMoverFrente.setOnAction(e -> {
            int idx = listaEquipoView.getSelectionModel().getSelectedIndex();
            if (idx < 0) {
                mostrarAlerta("Atención", "Selecciona un Pokémon de la lista.");
                return;
            }
            Pokemon p = entrenador.obtenerPorIndice(idx);
            if (p != null && entrenador.moverAlFrente(p.getNombre())) {
                mostrarAlerta("Listo", p.getNombre() + " ahora es el primero del equipo.");
                actualizarLista();
            }
        });

        btnAgregar.setOnAction(e -> abrirDialogoAgregar());
        btnBuscar.setOnAction(e -> abrirDialogoBuscar());

        btnEliminar.setOnAction(e -> {
            int idx = listaEquipoView.getSelectionModel().getSelectedIndex();
            if (idx < 0) {
                mostrarAlerta("Atención", "Selecciona un Pokémon para eliminar.");
                return;
            }
            Pokemon p = entrenador.obtenerPorIndice(idx);
            if (p != null && entrenador.eliminarPokemon(p.getNombre())) {
                mostrarAlerta("Listo", p.getNombre() + " fue eliminado del equipo.");
                actualizarLista();
            }
        });

        btnCerrar.setOnAction(e -> stage.close());

        actualizarLista();

        Scene scene = new Scene(root, 750, 560);
        stage.setScene(scene);
        stage.show();
    }

    private void actualizarLista() {
        listaEquipoView.getItems().clear();
        for (int i = 0; i < entrenador.totalPokemon(); i++) {
            Pokemon p = entrenador.obtenerPorIndice(i);
            if (p != null) {
                String estado = p.getHp() <= 0 ? "  DERROTADO" : "";
                String activo = p == entrenador.getPokemonActivo() ? "  (en combate)" : "";
                listaEquipoView.getItems().add((i + 1) + ". " + p.getNombre()
                        + "   Nivel " + p.getNivel()
                        + "   " + p.getHp() + "/" + p.getHpMaximo()
                        + estado + activo);
            }
        }
        lblDisponibles.setText("Pokémon disponibles: " + entrenador.pokemonDisponibles() + " de " + entrenador.totalPokemon());

        if (entrenador.totalPokemon() == 0) {
            imgVistaPrevia.setImage(null);
            lblDetallesPokemon.setText("Tu equipo está vacío.");
        }
    }

    private void mostrarDetalles(Pokemon p) {
        String tipo = p.getTipo() != null ? p.getTipo().toString() : "Ninguno";
        lblDetallesPokemon.setText(
                "Nombre: " + p.getNombre() +
                        "\nTipo: " + tipo +
                        "\nNivel: " + p.getNivel() +
                        "\nHP: " + p.getHp() + "/" + p.getHpMaximo() +
                        "\nAtaque: " + p.getAtaque() +
                        "\nDefensa: " + p.getDefensa()
        );
        cargarImagen(p);
    }

    private void abrirDialogoAgregar() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>();
        dialog.setTitle("Agregar Pokémon");
        dialog.setHeaderText("Elige un Pokémon de la Pokédex:");
        dialog.setContentText("Pokémon:");

        for (int i = 0; i < pokedex.contar(); i++) {
            Pokemon p = pokedex.verPlantilla(i);
            if (p != null && entrenador.buscarPokemon(p.getNombre()) == null) {
                dialog.getItems().add(p.getNombre() + " - " + p.getTipo() + " - Nivel " + p.getNivel());
            }
        }

        if (dialog.getItems().isEmpty()) {
            mostrarAlerta("Atención", "Ya tienes todos los Pokémon de la Pokédex.");
            return;
        }

        dialog.setSelectedItem(dialog.getItems().get(0));
        Optional<String> resultado = dialog.showAndWait();

        resultado.ifPresent(seleccion -> {
            String nombre = seleccion.split(" - ")[0].trim();
            if (entrenador.agregarPokemon(pokedex.crear(nombre))) {
                actualizarLista();
            } else {
                mostrarAlerta("Error", "Ese Pokémon ya está en tu equipo.");
            }
        });
    }

    private void abrirDialogoBuscar() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Buscar Pokémon");
        dialog.setHeaderText(null);
        dialog.setContentText("Nombre del Pokémon:");

        dialog.showAndWait().ifPresent(nombre -> {
            Pokemon p = entrenador.buscarPokemon(nombre.trim());
            if (p != null) {
                mostrarDetalles(p);
                mostrarAlerta("Encontrado", p.getNombre() + " esta en tu equipo con "
                        + p.getHp() + "/" + p.getHpMaximo() + " HP.");
            } else {
                mostrarAlerta("No encontrado", "El Pokémon '" + nombre + "' no está en tu equipo.");
            }
        });
    }

    private void cargarImagen(Pokemon p) {
        String[] intentos = {
                p.getRutaImagen(),
                "/Imagenes/" + p.getNombre().toLowerCase() + ".png",
                "/Imagenes/" + p.getNombre() + ".png",
                "/Imagenes/" + p.getNombre() + ".jpg"
        };

        for (String ruta : intentos) {
            if (ruta == null || ruta.isEmpty()) {
                continue;
            }
            try {
                Image img = new Image(getClass().getResourceAsStream(ruta));
                if (img.getWidth() > 0) {
                    imgVistaPrevia.setImage(img);
                    return;
                }
            } catch (Exception ignored) {
            }
        }
        imgVistaPrevia.setImage(null);
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
