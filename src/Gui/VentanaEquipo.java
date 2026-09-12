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
import pokemon_battle.EnumTipo;
import pokemon_battle.Pokemon;

public class VentanaEquipo {

    private Entrenador entrenador;
    private ListView<String> listaEquipoView;
    private Label lblDisponibles;
    private ImageView imgVistaPrevia;
    private Label lblDetallesPokemon;

    public VentanaEquipo(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public void start(Stage stage) {
        stage.setTitle("Gestión de Equipo - " + entrenador.getNombre());

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

        Label lblTitulo = new Label("EQUIPO DE ENTRENADOR: " + entrenador.getNombre().toUpperCase());
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

        boxBotones.getChildren().addAll(btnAgregar, btnMoverFrente, btnBuscar, btnEliminar, btnCerrar);

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
            if (idx >= 0) {
                Pokemon p = entrenador.obtenerPorIndice(idx);
                if (p != null && entrenador.moverAlFrente(p.getNombre())) {
                    mostrarAlerta("Éxito", p.getNombre() + " ahora es el Pokémon principal del equipo.");
                    actualizarLista();
                }
            } else {
                mostrarAlerta("Atención", "Selecciona un Pokémon de la lista.");
            }
        });

        btnAgregar.setOnAction(e -> abrirDialogoAgregar());
        btnBuscar.setOnAction(e -> abrirDialogoBuscar());

        btnEliminar.setOnAction(e -> {
            int idx = listaEquipoView.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                Pokemon p = entrenador.obtenerPorIndice(idx);
                if (p != null && entrenador.eliminarPokemon(p.getNombre())) {
                    mostrarAlerta("Éxito", p.getNombre() + " fue eliminado del equipo.");
                    actualizarLista();
                }
            } else {
                mostrarAlerta("Atención", "Selecciona un Pokémon para eliminar.");
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
                String estado = p.getHp() <= 0 ? "DERROTADO" : "DISPONIBLE";
                listaEquipoView.getItems().add((i + 1) + ". " + p.getNombre() + " [Nvl " + p.getNivel() + "] - HP: " + p.getHp() + " (" + estado + ")");
            }
        }
        lblDisponibles.setText("Pokémon disponibles: " + entrenador.pokemonDisponibles() + " / " + entrenador.totalPokemon());

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
                        "\nHP: " + p.getHp() +
                        "\nAtaque: " + p.getAtaque() +
                        "\nDefensa: " + p.getDefensa()
        );

        try {
            if (p.getRutaImagen() != null && !p.getRutaImagen().isEmpty()) {
                imgVistaPrevia.setImage(new Image(getClass().getResourceAsStream(p.getRutaImagen())));
            } else {
                imgVistaPrevia.setImage(new Image(getClass().getResourceAsStream("/RecursosGraficos/" + p.getNombre().toLowerCase() + ".png")));
            }
        } catch (Exception e) {
            imgVistaPrevia.setImage(null);
        }
    }

    private void abrirDialogoAgregar() {
        TextInputDialog dialog = new TextInputDialog("Pikachu,15,Electrico,100,55,40");
        dialog.setTitle("Agregar Pokémon al Equipo");
        dialog.setHeaderText("Formato: Nombre,Nivel,Tipo,HP,Ataque,Defensa\nTipos válidos: Agua, Tierra, Electrico, Fuego, Hielo, Planta, Acero, Dragon");
        dialog.setContentText("Datos:");

        dialog.showAndWait().ifPresent(input -> {
            try {
                String[] p = input.split(",");
                String nombre = p[0].trim();
                int nivel = Integer.parseInt(p[1].trim());
                EnumTipo tipo = EnumTipo.valueOf(p[2].trim());
                int hp = Integer.parseInt(p[3].trim());
                int ataque = Integer.parseInt(p[4].trim());
                int defensa = Integer.parseInt(p[5].trim());

                String ruta = "/Imagenes/" + nombre.toLowerCase() + ".png";
                Pokemon nuevo = new Pokemon(ruta, nombre, nivel, tipo, hp, ataque, defensa);

                if (entrenador.agregarPokemon(nuevo)) {
                    actualizarLista();
                } else {
                    mostrarAlerta("Error", "El Pokémon ya existe en el equipo.");
                }
            } catch (Exception ex) {
                mostrarAlerta("Error de Formato", "Asegúrate de ingresar los datos correctamente y utilizar un tipo válido.");
            }
        });
    }

    private void abrirDialogoBuscar() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Buscar Pokémon");
        dialog.setHeaderText(null);
        dialog.setContentText("Ingresa el nombre del Pokémon:");

        dialog.showAndWait().ifPresent(nombre -> {
            Pokemon p = entrenador.buscarPokemon(nombre.trim());
            if (p != null) {
                mostrarDetalles(p);
                mostrarAlerta("Pokémon Encontrado", "Se encontró a " + p.getNombre() + " en tu equipo.");
            } else {
                mostrarAlerta("No Encontrado", "El Pokémon '" + nombre + "' no está en tu equipo.");
            }
        });
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}