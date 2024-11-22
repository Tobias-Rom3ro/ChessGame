package com.poo.chessgame1_2.view;

import com.poo.chessgame1_2.controller.Controller;
import com.poo.chessgame1_2.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Clase principal de la vista de la aplicación de ajedrez, que maneja la interfaz gráfica de usuario (GUI).
 * La clase se encarga de mostrar el tablero, el menú, la configuración de la ventana, y las interacciones con el usuario.
 * Además, gestiona el flujo de la interfaz, como mostrar la pantalla del menú, cambiar entre escenas,
 * y mostrar información relevante durante el juego.
 */
public class View extends Application {

    private final int BOARD_SIZE = 8; // Tamaño del tablero (8x8)
    private final int SQUARE_SIZE_PX = 50; // Tamaño de cada casilla en píxeles

    // Tamaño de la ventana basado en el tamaño del tablero y las casillas
    private int windowSizeX = SQUARE_SIZE_PX * 16;
    private int windowSizeY = SQUARE_SIZE_PX * (BOARD_SIZE + 2) + 27;

    private static Stage stage = null; // Ventana principal de la aplicación
    private static Scene menuScene = null; // Escena del menú principal
    private static Scene boardScene = null; // Escena del tablero de ajedrez
    private static Controller ctrl = null; // Controlador de la aplicación
    private static Model model = null; // Modelo de datos del juego
    private static BoardPane boardPane = null; // Componente que representa el tablero

    /**
     * Asigna el controlador a la vista para poder acceder a las acciones y lógicas del juego.
     *
     * @param ctrl El controlador de la aplicación.
     */
    public void setController(Controller ctrl) {
        View.ctrl = ctrl;
    }

    /**
     * Asigna el modelo a la vista para poder acceder a los datos del juego.
     *
     * @param model El modelo de la aplicación.
     */
    public void setModel(Model model) {
        View.model = model;
    }

    /**
     * Inicializa la GUI, mostrando la pantalla del menú principal.
     *
     * @param stage La ventana principal de la aplicación.
     */
    @Override
    public void start(Stage stage) {
        View.stage = stage;
        GUIinit(); // Inicializa las escenas
        setMenuScene(); // Muestra la escena del menú
        stage.setTitle("Ajedrez"); // Establece el título de la ventana
        stage.setResizable(false); // La ventana no es redimensionable
        Image icon = new Image(getClass().getResourceAsStream("/icon.png")); // Carga el ícono de la ventana
        stage.getIcons().add(icon); // Establece el ícono de la ventana
        stage.show(); // Muestra la ventana
    }

    /**
     * Inicializa las escenas del menú y del tablero de ajedrez.
     */
    private void GUIinit() {
        initMenuScene(); // Inicializa la escena del menú
        initBoardScene(); // Inicializa la escena del tablero
    }

    /**
     * Inicializa la escena del menú principal.
     */
    private void initMenuScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/MenuScene.fxml")); // Carga el archivo FXML del menú
        try {
            menuScene = new Scene(fxmlLoader.load()); // Crea la escena a partir del archivo FXML
            AnchorPane root = (AnchorPane) menuScene.getRoot(); // Obtiene el nodo raíz de la escena
            addVideoBackground(root); // Añade un fondo de video al menú
        } catch (IOException e) {
            throw new RuntimeException(e); // Lanza una excepción si hay un error al cargar el FXML
        }
    }

    /**
     * Añade un fondo de video a la escena del menú.
     *
     * @param root El nodo raíz de la escena.
     */
    private void addVideoBackground(AnchorPane root) {
        String videoPath = getClass().getResource("/videoBackground.mp4").toExternalForm(); // Ruta del video de fondo
        Media media = new Media(videoPath); // Crea un objeto Media con la ruta del video
        MediaPlayer mediaPlayer = new MediaPlayer(media); // Crea un reproductor de medios
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Reproduce el video indefinidamente
        mediaPlayer.setAutoPlay(true); // Inicia la reproducción automáticamente

        MediaView mediaView = new MediaView(mediaPlayer); // Crea una vista para el video
        mediaView.fitWidthProperty().bind(root.widthProperty()); // Ajusta el ancho de la vista al ancho del nodo raíz
        mediaView.fitHeightProperty().bind(root.heightProperty()); // Ajusta la altura de la vista a la altura del nodo raíz
        mediaView.setPreserveRatio(false); // No preserva la relación de aspecto

        root.getChildren().add(0, mediaView); // Añade la vista de video al nodo raíz
    }

    /**
     * Inicializa la escena del tablero de ajedrez.
     */
    private void initBoardScene() {
        boardPane = new BoardPane(this, ctrl, BOARD_SIZE, SQUARE_SIZE_PX); // Crea el panel del tablero
        boardScene = new Scene(boardPane, windowSizeX, windowSizeY); // Crea la escena del tablero
    }

    /**
     * Muestra la escena del menú principal.
     */
    public void setMenuScene() {
        stage.setScene(menuScene); // Establece la escena del menú en la ventana
    }

    /**
     * Muestra la escena del tablero de ajedrez.
     */
    public void setBoardScene() {
        stage.setScene(boardScene); // Establece la escena del tablero en la ventana
    }

    /**
     * Actualiza la vista del tablero de ajedrez con los nuevos movimientos o cambios.
     *
     * @param list Una lista de cambios en el tablero representados como coordenadas de piezas y su tipo.
     */
    public void changeBoardView(ArrayList list) {
        boardPane.changeBoardViewByList(list); // Actualiza la vista del tablero con la lista de cambios
    }

    /**
     * Resalta la pieza seleccionada en el tablero.
     *
     * @param boardI La coordenada I (fila) de la casilla seleccionada.
     * @param boardJ La coordenada J (columna) de la casilla seleccionada.
     */
    public void selectPiece(int boardI, int boardJ) {
        boardPane.paintSelected(boardI, boardJ); // Resalta la pieza seleccionada en el tablero
    }

    /**
     * Muestra un cuadro de diálogo para ingresar el nombre del jugador.
     *
     * @return El nombre del jugador.
     */
    public String getPlayerName() {
        TextInputDialog dialog = new TextInputDialog("Nombre"); // Crea un cuadro de diálogo para ingresar el nombre
        dialog.setTitle("Nombre del jugador"); // Establece el título del diálogo
        dialog.setHeaderText("Introduce tu nombre"); // Establece el texto del encabezado

        Optional<String> result = dialog.showAndWait(); // Muestra el diálogo y espera la entrada del usuario

        if (result.isEmpty()) {
            return "Jugador desconocido"; // Devuelve un nombre por defecto si no se ingresa nada
        }

        return result.get(); // Devuelve el nombre ingresado por el usuario
    }

    /**
     * Establece los nombres de los jugadores en la interfaz del tablero.
     *
     * @param name1 El nombre del jugador 1.
     * @param name2 El nombre del jugador 2.
     */
    public void setPlayersNames(String name1, String name2) {
        boardPane.setPlayersNames(name1, name2); // Establece los nombres de los jugadores en el tablero
    }

    /**
     * Muestra un cuadro de diálogo al final del juego para mostrar al ganador y permitir opciones de reiniciar o salir.
     *
     * @param winnerName El nombre del jugador ganador.
     */
    public void gameOver(String winnerName) {
        Dialog<Object> dialog = new Dialog<>(); // Crea un cuadro de diálogo
        dialog.initStyle(StageStyle.UTILITY); // Establece el estilo del diálogo
        dialog.setTitle("Fin del juego!"); // Establece el título del diálogo
        dialog.setHeaderText(winnerName + " ganó!"); // Establece el texto del encabezado con el nombre del ganador

        // Botones para las opciones del diálogo
        ButtonType newGameButton = new ButtonType("Ir al menú");
        ButtonType SavePGNButton = new ButtonType("Guardar partida (PGN)");
        ButtonType ExitButton = new ButtonType("Salir");

        dialog.getDialogPane().getButtonTypes().addAll(newGameButton, SavePGNButton, ExitButton); // Añade los botones al diálogo
        Optional<Object> result = dialog.showAndWait(); // Muestra el diálogo y espera la entrada del usuario
        if (result.get() == newGameButton) {
            setMenuScene(); // Si se selecciona "Ir al menú", muestra la escena del menú
        }
        if (result.get() == SavePGNButton) {
            ctrl.saveGamePGNAction(); // Si se selecciona "Guardar partida (PGN)", guarda la partida
            setMenuScene(); // Luego muestra la escena del menú
        }
        if (result.get() == ExitButton) {
            ctrl.exitAction(); // Si se selecciona "Salir", ejecuta la acción de salir
        }
    }
}