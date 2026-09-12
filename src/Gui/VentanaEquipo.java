package Gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import pokemon_battle.Entrenador;
import pokemon_battle.EnumTipo;
import pokemon_battle.Pokemon;

public class VentanaEquipo {

    private Entrenador entrenador;
    private ListView<String> listaEquipoView;
    private Label lblDisponibles;

    public VentanaEquipo(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public void start(Stage stage) {
        stage.setTitle("Elige tu Equipo:");

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
        contenedor.setMaxWidth(600);
        contenedor.setMaxHeight(500);
        contenedor.setPadding(new Insets(25));
        contenedor.setAlignment(Pos.CENTER);
        contenedor.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.94);" +
                        "-fx-background-radius: 15px;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 12, 0, 0, 4);"
        );

        Label lblTitulo = new Label("MI EQUIPO - " + entrenador.getNombre());
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2a75bb;");

        lblDisponibles = new Label();
        lblDisponibles.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        listaEquipoView = new ListView<>();
        listaEquipoView.setPrefHeight(220);

        HBox boxBotones = new HBox(10);
        boxBotones.setAlignment(Pos.CENTER);

        Button btnAgregar = new Button("Agregar");
        Button btnBuscar = new Button("Buscar");
        Button btnMoverFrente = new Button("Mover al Frente");
        Button btnEliminar = new Button("Eliminar");
        Button btnCerrar = new Button("Cerrar");

        String estiloBtn = "-fx-background-color: #3b4cca; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;";
        btnAgregar.setStyle(estiloBtn);
        btnBuscar.setStyle(estiloBtn);
        btnMoverFrente.setStyle("-fx-background-color: #ffde00; -fx-text-fill: #3b4cca; -fx-font-weight: bold; -fx-cursor: hand;");
        btnEliminar.setStyle("-fx-background-color: #cc0000; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");
        btnCerrar.setStyle("-fx-background-color: #777777; -fx-text-fill: white; -fx-font-weight: bold; -fx-cursor: hand;");

        boxBotones.getChildren().addAll(btnAgregar, btnBuscar, btnMoverFrente, btnEliminar, btnCerrar);

        contenedor.getChildren().addAll(lblTitulo, listaEquipoView, lblDisponibles, boxBotones);
        root.getChildren().add(contenedor);

        // --- ACCIONES ---

        btnAgregar.setOnAction(e -> abrirDialogoAgregar());
        btnBuscar.setOnAction(e -> abrirDialogoBuscar());

        // Reto 4 Integrantes: Reorganización mediante moverAlFrente
        btnMoverFrente.setOnAction(e -> {
            String seleccion = listaEquipoView.getSelectionModel().getSelectedItem();
            if (seleccion != null) {
                String nombre = extraerNombreSeleccionado(seleccion);
                if (entrenador.moverAlFrente(nombre)) {
                    mostrarAlerta("Éxito", nombre + " movido al primer lugar del equipo.");
                    actualizarLista();
                }
            } else {
                mostrarAlerta("Atención", "Selecciona un Pokémon de la lista.");
            }
        });

        btnEliminar.setOnAction(e -> {
            String seleccion = listaEquipoView.getSelectionModel().getSelectedItem();
            if (seleccion != null) {
                String nombre = extraerNombreSeleccionado(seleccion);
                if (entrenador.eliminarPokemon(nombre)) {
                    mostrarAlerta("Éxito", nombre + " eliminado del equipo.");
                    actualizarLista();
                }
            } else {
                mostrarAlerta("Atención", "Selecciona un Pokémon de la lista para eliminar.");
            }
        });

        btnCerrar.setOnAction(e -> stage.close());

        actualizarLista();

        Scene scene = new Scene(root, 750, 580);
        stage.setScene(scene);
        stage.show();
    }

    private void actualizarLista() {
        listaEquipoView.getItems().clear();
        for (int i = 0; i < entrenador.totalPokemon(); i++) {
            Pokemon p = entrenador.obtenerPorIndice(i);
            if (p != null) {
                String estado = p.getHp() <= 0 ? "DERROTADO" : "DISPONIBLE";
                listaEquipoView.getItems().add(
                        p.getNombre() + " | Nvl: " + p.getNivel() + " | Tipo: " + p.getTipo() +
                                " | HP: " + p.getHp() + " | Atq: " + p.getAtaque() + " | Def: " + p.getDefensa() + " [" + estado + "]"
                );
            }
        }
        lblDisponibles.setText("Pokemon disponibles: " + entrenador.pokemonDisponibles() + " / " + entrenador.totalPokemon());
    }

    private void abrirDialogoAgregar() {
        TextInputDialog dialog = new TextInputDialog("Pikachu,15,Electrico,100,55,40");
        dialog.setTitle("Agregar Pokémon");
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

                Pokemon nuevo = new Pokemon(nombre, nivel, tipo, hp, ataque, defensa);
                if (entrenador.agregarPokemon(nuevo)) {
                    actualizarLista();
                } else {
                    mostrarAlerta("Error", "El Pokémon ya existe o los datos son inválidos.");
                }
            } catch (Exception ex) {
                mostrarAlerta("Error", "Verifica el formato o el tipo ingresado.");
            }
        });
    }

    private void abrirDialogoBuscar() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Buscar Pokemon");
        dialog.setHeaderText(null);
        dialog.setContentText("Ingresa el nombre del Pokémon:");

        dialog.showAndWait().ifPresent(nombre -> {
            Pokemon p = entrenador.buscarPokemon(nombre.trim());
            if (p != null) {
                mostrarAlerta("Pokemon Encontrado",
                        "Nombre: " + p.getNombre() +
                                "\nNivel: " + p.getNivel() +
                                "\nTipo: " + p.getTipo() +
                                "\nHP: " + p.getHp() +
                                "\nAtaque: " + p.getAtaque() +
                                "\nDefensa: " + p.getDefensa());
            } else {
                mostrarAlerta("No Encontrado", "El Pokémon '" + nombre + "' no existe en el equipo.");
            }
        });
    }

    private String extraerNombreSeleccionado(String item) {
        return item.split(" \\| ")[0].trim();
    }

    private void mostrarAlerta(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}
