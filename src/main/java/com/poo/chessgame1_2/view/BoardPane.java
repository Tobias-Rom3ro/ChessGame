package com.poo.chessgame1_2.view;

import com.poo.chessgame1_2.controller.ChessMenuBar;
import com.poo.chessgame1_2.controller.Controller;
import com.poo.chessgame1_2.model.pieces.PieceType;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Clase que representa el panel del tablero de ajedrez.
 * Esta clase se encarga de la visualización del tablero, las piezas y la interacción con el usuario.
 */
public class BoardPane extends GridPane {
    private final Controller ctrl; // Controlador de la aplicación
    private final int BOARD_SIZE; // Tamaño del tablero
    private final int SQUARE_SIZE_PX; // Tamaño de cada casilla en píxeles
    private final Rectangle[][] piecesArray; // Matriz que contiene las piezas del tablero
    private final Rectangle[][] boardSquaresArray; // Matriz que contiene las casillas del tablero
    private Image[][] images = null; // Matriz que almacena las imágenes de las piezas

    private final Label player1Name = new Label(), player2Name = new Label(); // Etiquetas para los nombres de los jugadores

    private final Color selectedSquareColor = Color.LAWNGREEN; // Color de la casilla seleccionada
    private final Color lightSquareColor = Color.web("#7C93BF"); // Color de las casillas claras
    private final Color darkSquareColor = Color.web("#EFEEEE"); // Color de las casillas oscuras
    private final Color transparentColor = Color.TRANSPARENT; // Color transparente
    private final Color backgroundColor = Color.web("#3E495F"); // Color de fondo del tablero

    /**
     * Constructor de la clase BoardPane.
     * Inicializa el panel del tablero, carga las imágenes y genera las casillas.
     *
     * @param view La vista de la aplicación.
     * @param ctrl El controlador de la aplicación.
     * @param BOARD_SIZE Tamaño del tablero.
     * @param SQUARE_SIZE_PX Tamaño de cada casilla en píxeles.
     */
    BoardPane(View view, Controller ctrl, int BOARD_SIZE, int SQUARE_SIZE_PX) {
        this.ctrl = ctrl;
        this.BOARD_SIZE = BOARD_SIZE;
        this.SQUARE_SIZE_PX = SQUARE_SIZE_PX;

        this.piecesArray = new Rectangle[BOARD_SIZE][BOARD_SIZE];
        this.boardSquaresArray = new Rectangle[BOARD_SIZE][BOARD_SIZE];
        this.setBackground(Background.fill(backgroundColor)); // Establece el color de fondo del tablero

        loadImages(); // Carga las imágenes de las piezas
        setChessMenuBar(); // Establece la barra de menú en el tablero
        generateSquares(); // Genera las casillas del tablero
        setConfirmButton(); // Establece el botón de confirmación de movimiento
        initPlayerNameLabels(); // Inicializa las etiquetas con los nombres de los jugadores
    }

    /**
     * Carga las imágenes de las piezas desde la carpeta de recursos y las almacena en un array.
     */
    private void loadImages() {
        images = new Image[2][6]; // Dos colores (blanco y negro) y seis tipos de piezas

        try {
            // Carga las imágenes de las piezas blancas
            images[0][0] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/White/bishop.png")).getFile()));
            images[0][1] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/White/king.png")).getFile()));
            images[0][2] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/White/knight.png")).getFile()));
            images[0][3] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/White/pawn.png")).getFile()));
            images[0][4] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/White/queen.png")).getFile()));
            images[0][5] = new Image(new FileInputStream( Objects.requireNonNull(getClass().getResource("/piecesImages/White/rook.png")).getFile()));

            // Carga las imágenes de las piezas negras
            images[1][0] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/Black/bishop.png")).getFile()));
            images[1][1] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/Black/king.png")).getFile()));
            images[1][2] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/Black/knight.png")).getFile()));
            images[1][3] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/Black/pawn.png")).getFile()));
            images[1][4] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/Black/queen.png")).getFile()));
            images[1][5] = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/piecesImages/Black/rook.png")).getFile()));
        } catch (Exception e) {
            System.out.println("Las imágenes no se encuentran\n");
            e.printStackTrace();
        }
    }

    /**
     * Establece la barra de menú en la GUI del tablero.
     */
    private void setChessMenuBar() {
        ChessMenuBar chessMenuBar = new ChessMenuBar(ctrl); // Crea la barra de menú
        this.add(chessMenuBar, 0, 0, 20, 1); // Añade la barra de menú al panel
    }

    /**
     * Inicializa las etiquetas con los nombres de los jugadores.
     */
    private void initPlayerNameLabels() {
        player1Name.setAlignment(Pos.CENTER); // Alinea el texto al centro
        player1Name.setFont(Font.font(null, FontWeight.BOLD, 25)); // Establece la fuente y el tamaño
        player1Name.setTextAlignment(TextAlignment.CENTER); // Alinea el texto
        this.add(player1Name, 10, 10, 14, 10); // Añade la etiqueta al panel
        GridPane.setHalignment(player1Name, HPos.CENTER); // Alinea horizontalmente
        player1Name.setTextFill(Color.WHITE); // Establece el color del texto

        player2Name.setMaxWidth(4 * SQUARE_SIZE_PX); // Establece el ancho máximo
        player2Name.setMaxHeight(SQUARE_SIZE_PX); // Establece la altura máxima
        player2Name.setAlignment(Pos.CENTER); // Alinea el texto al centro
        player2Name.setFont(Font.font(null, FontWeight.BOLD, 25)); // Establece la fuente y el tamaño
        player2Name.setTextAlignment(TextAlignment.CENTER); // Alinea el texto
        this.add(player2Name, 10, 1, 14, 1); // Añade la etiqueta al panel
        GridPane.setHalignment(player2Name, HPos.CENTER); // Alinea horizontalmente
        player2Name.setTextFill(Color.WHITE); // Establece el color del texto
    }

    /**
     * Establece los nombres de los jugadores en las etiquetas de la GUI del tablero.
     *
     * @param name1 Nombre del jugador 1.
     * @param name2 Nombre del jugador 2.
     */
    void setPlayersNames(String name1, String name2) {
        player1Name.setText(name1); // Establece el nombre del jugador 1
        player2Name.setText(name2); // Establece el nombre del jugador 2
    }

    /**
     * Crea un botón de confirmación que cambia el movimiento y detiene/inicia el temporizador.
     */
    private void setConfirmButton() {
        Button confirmButton = new Button("Confirmar movimiento"); // Crea el botón
        confirmButton.setMaxHeight(SQUARE_SIZE_PX); // Establece la altura máxima
        confirmButton.setMaxWidth(SQUARE_SIZE_PX * 4); // Establece el ancho máximo
        confirmButton.setOnMouseClicked(e -> ctrl.confirmButtonAction()); // Establece la acción al hacer clic
        this.add(confirmButton, 11, 4, 14, 4); // Añade el botón al panel
    }

    /**
     * Genera todas las casillas de fondo y del tablero. Hace que las casillas del tablero sean clicables.
     */
    private void generateSquares() {
        for (int i = 0; i <= BOARD_SIZE + 1; i++ ) {
            for (int j = 0; j <= BOARD_SIZE + 1; j++) {
                // Genera el marco del tablero con coordenadas
                if (i == 0 || j == 0 || i == BOARD_SIZE + 1 || j == BOARD_SIZE + 1) {
                    char coordinateOnBoard = 0;
                    if (j == 0 && i != 0 && i != BOARD_SIZE + 1 || j == BOARD_SIZE + 1 && i != 0 && i != BOARD_SIZE + 1) {
                        coordinateOnBoard = (char) ('A' + (i - 1));
                    } else if (i == 0 && j != 0 && j != BOARD_SIZE + 1 || i == BOARD_SIZE + 1 && j != 0 && j != BOARD_SIZE + 1) {
                        coordinateOnBoard = (char) ('9' - j);
                    }

                    Rectangle backgroundRectangle = new Rectangle(SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX);
                    backgroundRectangle.setFill(Color.WHITE);
                    Text text = new Text(String.valueOf(coordinateOnBoard));
                    text.setFont(Font.font(null, FontWeight.BOLD, 25));

                    this.add(backgroundRectangle, i, j + 1);
                    this.add(text, i, j + 1);
                    GridPane.setHalignment(text, HPos.CENTER);
                    continue;
                }

                // Genera las casillas clicables del tablero
                int shiftedI = i - 1;
                int shiftedJ = j - 1;

                piecesArray[shiftedI][shiftedJ] = new Rectangle(SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX);
                Rectangle r = piecesArray[shiftedI][shiftedJ];
                boardSquaresArray[shiftedI][shiftedJ] = new Rectangle(SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX);
                Rectangle backgroundRectangle = boardSquaresArray[shiftedI][shiftedJ];

                r.setId(shiftedI + String.valueOf(shiftedJ));
                r.setFill(transparentColor);
                r.setOnMouseClicked(t -> {
                    int viewI = Integer.parseInt(r.getId()) / 10;
                    int viewJ = Integer.parseInt(r.getId()) % 10;

                    ArrayList boardCoordinates = transformView2Board(viewI, viewJ);
                    int boardI = (int) boardCoordinates.get(0);
                    int boardJ = (int) boardCoordinates.get(1);

                    ctrl.boardSquareWasClicked(boardI, boardJ); // Notifica al controlador que se hizo clic en una casilla
                });
                if ((i + j) % 2 == 0) {
                    backgroundRectangle.setFill(lightSquareColor); // Establece el color de la casilla clara
                } else {
                    backgroundRectangle.setFill(darkSquareColor); // Establece el color de la casilla oscura
                }

                this.add(backgroundRectangle, i, j + 1); // Añade la casilla al panel
                this.add(r, i, j + 1); // Añade la pieza al panel
            }
        }

        // Genera las casillas de fondo (lado derecho de la GUI del tablero)
        for (int i = BOARD_SIZE + 2; i < BOARD_SIZE + 2 + 6; i++) {
            for (int j = 0; j <= BOARD_SIZE + 1; j++) {
                Rectangle backgroundRectangle = new Rectangle(SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX, SQUARE_SIZE_PX);
                backgroundRectangle.setFill(backgroundColor); // Establece el color de fondo
                this.add(backgroundRectangle, i, j + 1); // Añade la casilla de fondo al panel
            }
        }
    }

    /**
     * Pinta la casilla seleccionada de otro color en la GUI del tablero.
     *
     * @param boardI Coordenada I de la casilla en el sistema del tablero.
     * @param boardJ Coordenada J de la casilla en el sistema del tablero.
     */
    void paintSelected(int boardI, int boardJ) {
        ArrayList viewCoordinates = transformBoard2View(boardI, boardJ);
        int viewI = (int) viewCoordinates.get(0);
        int viewJ = (int) viewCoordinates.get(1);

        if (boardSquaresArray[viewI][viewJ].getFill() == selectedSquareColor) {
            Color originalSquareColor = ((viewI + viewJ) % 2 == 0) ? lightSquareColor : darkSquareColor; // Determina el color original de la casilla
            boardSquaresArray[viewI][viewJ].setFill(originalSquareColor); // Restaura el color original
        } else if (piecesArray[viewI][viewJ].getFill() != transparentColor) {
            boardSquaresArray[viewI][viewJ].setFill(selectedSquareColor); // Cambia el color de la casilla seleccionada
        }
    }

    /**
     * Transforma las coordenadas del sistema del tablero al sistema de la vista.
     *
     * @param boardI Coordenada I en el sistema del tablero.
     * @param boardJ Coordenada J en el sistema del tablero.
     * @return Coordenadas en el sistema de la vista.
     */
    private ArrayList transformBoard2View(int boardI, int boardJ) {
        ArrayList coordinates = new ArrayList();
        coordinates.add(boardI);
        coordinates.add((BOARD_SIZE - 1) - boardJ); // Inversa de la coordenada J
        return coordinates;
    }

    /**
     * Transforma las coordenadas del sistema de la vista al sistema del tablero.
     *
     * @param viewI Coordenada I en el sistema de la vista.
     * @param viewJ Coordenada J en el sistema de la vista.
     * @return Coordenadas en el sistema del tablero.
     */
    private ArrayList transformView2Board(int viewI, int viewJ) {
        ArrayList coordinates = new ArrayList();
        coordinates.add(viewI);
        coordinates.add((BOARD_SIZE - 1) - viewJ); // Inversa de la coordenada J
        return coordinates;
    }

    /**
     * Cambia la vista del tablero utilizando la representación del tablero como una lista de listas (datos de piezas)
     * en el formato { {Color pieceColor, PieceType pieceType, int boardI, int boardJ} ... }.
     *
     * @param changesList Representación del tablero como una lista de listas.
     */
    void changeBoardViewByList(ArrayList changesList) {
        for (Object o : changesList) {
            ArrayList squareToChange = (ArrayList) o;
            ArrayList coordinates = transformBoard2View((int) squareToChange.get(2), (int) squareToChange.get(3));
            setImage((Color) squareToChange.get(0), (PieceType) squareToChange.get(1), (Integer) coordinates.get(0), (Integer) coordinates.get(1));
        }
    }

    /**
     * Establece la imagen de la pieza en la casilla.
     *
     * @param pieceColor Color de la pieza.
     * @param pieceType Tipo de la pieza.
     * @param viewI Coordenada I en el sistema de la vista.
     * @param viewJ Coordenada J en el sistema de la vista.
     */
    private void setImage(Color pieceColor, PieceType pieceType, int viewI, int viewJ) {
        if (pieceType == PieceType.EMPTY) {
            piecesArray[viewI][viewJ].setFill(transparentColor); // Si la pieza es vacía, establece el color transparente
            return;
        }
        int colorIndex = (pieceColor == Color.WHITE) ? 0 : 1; // Determina el índice de color
        int pieceIndex = -1; // Inicializa el índice de la pieza
        switch (pieceType) {
            case BISHOP:
                pieceIndex = 0;
                break;
            case KING:
                pieceIndex = 1;
                break;
            case KNIGHT:
                pieceIndex = 2;
                break;
            case PAWN:
                pieceIndex = 3;
                break;
            case QUEEN:
                pieceIndex = 4;
                break;
            case ROOK:
                pieceIndex = 5;
                break;
        }
        piecesArray[viewI][viewJ].setFill(new ImagePattern(images[colorIndex][pieceIndex])); // Establece la imagen de la pieza
    }
}