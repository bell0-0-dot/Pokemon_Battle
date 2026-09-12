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

public class VentanaBatalla {

    private Entrenador jugador;
    private Entrenador rival;

    private Label lblNombreRival, lblHpRival, lblNivelRival, lblTipoRival;
    private ImageView imgRival;

    private Label lblNombreJugador, lblHpJugador, lblNivelJugador, lblTipoJugador;
    private ImageView imgJugador;

    private TextArea txtHistorial;
    private Button btnAtacar, btnCambiar, btnObjetos, btnEquipo;

    public VentanaBatalla(Entrenador jugador, Entrenador rival) {
        this.jugador = jugador;
        this.rival = rival;
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
        layoutBatalla.setStyle("-fx-background-color: rgba(255, 255, 255, 0.85);");


        GridPane campo = new GridPane();
        campo.setAlignment(Pos.CENTER);
        campo.setHgap(40);
        campo.setVgap(20);

        VBox cardRival = crearTarjetaEntrenador("ENTRENADOR RIVAL");
        lblNombreRival = new Label();
        lblNivelRival = new Label();
        lblTipoRival = new Label();
        lblHpRival = new Label();
        lblHpRival.setStyle("-fx-font-weight: bold; -fx-text-fill: #cc0000;");

        imgRival = new ImageView();
        imgRival.setFitWidth(120);
        imgRival.setFitHeight(120);
        imgRival.setPreserveRatio(true);

        cardRival.getChildren().addAll(lblNombreRival, lblNivelRival, lblTipoRival, lblHpRival, imgRival);

        VBox cardJugador = crearTarjetaEntrenador("TU ENTRENADOR");
        lblNombreJugador = new Label();
        lblNivelJugador = new Label();
        lblTipoJugador = new Label();
        lblHpJugador = new Label();
        lblHpJugador.setStyle("-fx-font-weight: bold; -fx-text-fill: #008800;");

        imgJugador = new ImageView();
        imgJugador.setFitWidth(120);
        imgJugador.setFitHeight(120);
        imgJugador.setPreserveRatio(true);

        cardJugador.getChildren().addAll(imgJugador, lblNombreJugador, lblNivelJugador, lblTipoJugador, lblHpJugador);

        Label lblVS = new Label("⚔️ VS ⚔️");
        lblVS.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #3b4cca;");

        campo.add(cardRival, 1, 0);
        campo.add(lblVS, 1, 1);
        campo.add(cardJugador, 0, 2);

        layoutBatalla.setCenter(campo);


        HBox panelBotones = new HBox(15);
        panelBotones.setAlignment(Pos.CENTER);
        panelBotones.setPadding(new Insets(15, 0, 15, 0));

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
        lblHistorialHeader.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");

        txtHistorial = new TextArea();
        txtHistorial.setEditable(false);
        txtHistorial.setPrefRowCount(5);
        txtHistorial.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");

        panelHistorial.getChildren().addAll(lblHistorialHeader, txtHistorial);

        VBox contenedorInferior = new VBox(10);
        contenedorInferior.getChildren().addAll(panelBotones, panelHistorial);
        layoutBatalla.setBottom(contenedorInferior);

        root.getChildren().add(layoutBatalla);


        btnEquipo.setOnAction(e -> {
            VentanaEquipo vEquipo = new VentanaEquipo(jugador);
            vEquipo.start(new Stage());
        });

        btnAtacar.setOnAction(e -> ejecutarAtaquePrueba());

        actualizarEstadoBatalla();

        Scene scene = new Scene(root, 800, 650);
        stage.setScene(scene);
        stage.show();
    }

    private VBox crearTarjetaEntrenador(String titulo) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(12));
        card.setAlignment(Pos.CENTER);
        card.setMinWidth(200);
        card.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-border-color: #2a75bb;" +
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.15), 6, 0, 0, 2);"
        );

        Label lblHeader = new Label(titulo);
        lblHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 11px; -fx-text-fill: #777777;");
        card.getChildren().add(lblHeader);

        return card;
    }

    public void actualizarEstadoBatalla() {
        Pokemon pJugador = jugador.getPokemonActivo();
        Pokemon pRival = rival.getPokemonActivo();

        if (pJugador != null) {
            lblNombreJugador.setText(pJugador.getNombre().toUpperCase());
            lblNivelJugador.setText("Nivel: " + pJugador.getNivel());
            lblTipoJugador.setText("Tipo: " + pJugador.getTipo());
            lblHpJugador.setText("❤️ " + pJugador.getHp() + " HP");
            cargarImagen(imgJugador, pJugador.getNombre());
        }

        if (pRival != null) {
            lblNombreRival.setText(pRival.getNombre().toUpperCase());
            lblNivelRival.setText("Nivel: " + pRival.getNivel());
            lblTipoRival.setText("Tipo: " + pRival.getTipo());
            lblHpRival.setText("❤️ " + pRival.getHp() + " HP");
            cargarImagen(imgRival, pRival.getNombre());
        }
    }

    private void cargarImagen(ImageView view, String nombrePokemon) {
        try {
            Image img = new Image(getClass().getResourceAsStream("/RecursosGraficos/" + nombrePokemon.toLowerCase() + ".png"));
            view.setImage(img);
        } catch (Exception e) {
            view.setImage(null);
        }
    }

    private void ejecutarAtaquePrueba() {
        Pokemon pJugador = jugador.getPokemonActivo();
        Pokemon pRival = rival.getPokemonActivo();

        if (pJugador != null && pRival != null) {
            txtHistorial.appendText(pJugador.getNombre() + " atacó a " + pRival.getNombre() + ".\n");
            actualizarEstadoBatalla();
        }
    }
}
