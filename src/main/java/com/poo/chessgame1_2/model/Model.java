package com.poo.chessgame1_2.model;

import com.poo.chessgame1_2.model.players.Player;
import com.poo.chessgame1_2.model.utils.PGNSaver;
import javafx.scene.paint.Color;

import com.poo.chessgame1_2.view.View;

import java.util.ArrayList;

/**
 * Clase que representa el modelo del juego de ajedrez.
 * Contiene la lógica del juego, el estado del tablero, los jugadores y el manejo de movimientos.
 */
public class Model {
    private final int BOARD_SIZE = 8; // Tamaño del tablero de ajedrez
    private View view; // Vista asociada al modelo
    private Board board = null; // Tablero del juego
    private final PGNSaver pgnSaver = new PGNSaver(); // Guardar movimientos en formato PGN

    private Player currentPlayerMove; // Jugador que está realizando el movimiento actual
    private final Player player1 = new Player("player1", Color.WHITE); // Jugador 1
    private Player player2 = new Player("player2", Color.BLACK); // Jugador 2

    private boolean isSelectedPiece = false; // Indica si se ha seleccionado una pieza
    private GameType gameType; // Tipo de juego (un jugador o multijugador)
    private int selectedPieceI, selectedPieceJ; // Coordenadas de la pieza seleccionada

    private boolean moveHasDone = false; // Indica si se ha realizado un movimiento

    /**
     * Constructor de la clase Model.
     */
    public Model() {
    }

    /**
     * Establece la vista para acceder a los métodos de cambio de vista.
     *
     * @param view instancia de View
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Establece el tipo de juego.
     *
     * @param gameType GameType.SINGLEPLAYER o GameType.MULTIPLAYER
     */
    void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    /**
     * Obtiene el tipo de juego.
     *
     * @return GameType.SINGLEPLAYER o GameType.MULTIPLAYER
     */
    GameType getGameType() {
        return gameType;
    }

    /**
     * Obtiene la instancia del jugador 1.
     *
     * @return instancia de Player
     */
    Player getPlayer1() {
        return player1;
    }

    /**
     * Obtiene la instancia del jugador 2.
     *
     * @return instancia de Player
     */
    Player getPlayer2() {
        return player2;
    }

    /**
     * Obtiene el color de las piezas del jugador que debe realizar el movimiento.
     *
     * @return color de las piezas
     */
    Color getCurrentPlayerColor() {
        return currentPlayerMove.getPlayerColor();
    }

    /**
     * Obtiene el nombre del jugador 1.
     *
     * @return nombre del jugador 1
     */
    public String getPlayer1Name() {
        return player1.getPlayerName();
    }

    /**
     * Obtiene el nombre del jugador 2.
     *
     * @return nombre del jugador 2
     */
    public String getPlayer2Name() {
        return player2.getPlayerName();
    }

    /**
     * Establece los datos de los jugadores en modo multijugador.
     *
     * @param player1Name nombre del jugador 1
     * @param player2Name nombre del jugador 2
     */
    public void setPlayers(String player1Name, String player2Name) {
        player1.setPlayerName(player1Name); // Color.WHITE
        player2.setPlayerName(player2Name); // Color.BLACK
    }

    /**
     * Restablece los datos de los jugadores a los nombres y colores originales.
     *
     * @param player1 nuevo jugador 1
     * @param player2 nuevo jugador 2
     */
    public void setPlayers(Player player1, Player player2) {
        this.player1.setPlayerColor(player1.getPlayerColor());
        this.player1.setPlayerName(player1.getPlayerName());
        this.player2.setPlayerColor(player2.getPlayerColor());
        this.player2.setPlayerName(player2.getPlayerName());
    }

    /**
     * Establece el jugador que debe realizar el siguiente movimiento utilizando el color de sus piezas.
     *
     * @param currentPlayerColor color de la pieza que el jugador juega
     */
    void setCurrentPlayerColor(Color currentPlayerColor) {
        if (currentPlayerColor == player1.getPlayerColor()) {
            currentPlayerMove = player1;
        } else {
            currentPlayerMove = player2;
        }
    }

    /**
     * Cambia el turno al siguiente jugador.
     * Si el juego es de un solo jugador, realiza el movimiento de la computadora.
     */
    public void nextMove() {
        if (moveHasDone) {
            changeCurrentPlayerMove();
            moveHasDone = false;
        } else {
            return;
        }
        unselectPiece();
    }

    /**
     * Cambia el jugador actual que debe realizar el movimiento.
     * Detiene el temporizador del jugador anterior y comienza el temporizador del jugador actual.
     */
    private void changeCurrentPlayerMove() {
        currentPlayerMove = currentPlayerMove == player1 ? player2 : player1;
    }

    /**
     * Crea una nueva instancia del tablero.
     */
    private void resetBoard() {
        board = new Board(this);
    }

    /**
     * Método que se ejecuta cuando un jugador hace clic en una casilla del tablero.
     * Tiene 4 opciones:
     *  1. Realizar un movimiento si es posible.
     *  2. Seleccionar una pieza si es la pieza del jugador actual.
     *  3. Re-seleccionar una pieza o deseleccionar si no es la pieza del jugador actual.
     *  4. Ignorar.
     *
     * @param boardI coordenada I de la casilla clicada en el sistema del tablero
     * @param boardJ coordenada J de la casilla clicada en el sistema del tablero
     */
    public void squareWasClicked(int boardI, int boardJ) {
        // Realizar movimiento
        if (isSelectedPiece && board.isEmptySquare(boardI, boardJ) || isSelectedPiece && board.isOpponentPiece(boardI, boardJ, currentPlayerMove.getPlayerColor())) {
            if (board.isValidMove(selectedPieceI, selectedPieceJ, boardI, boardJ) && !moveHasDone) {
                makeMove(boardI, boardJ);
                System.out.println("Movimiento hecho :)");
            }
        }
        // Seleccionar pieza
        else if (!isSelectedPiece && !board.isEmptySquare(boardI, boardJ) && !board.isOpponentPiece(boardI, boardJ, currentPlayerMove.getPlayerColor()) && !moveHasDone) {
            selectPiece(boardI, boardJ);
        }
        // Re-seleccionar pieza
        else if (isSelectedPiece && !board.isEmptySquare(boardI, boardJ) && !board.isOpponentPiece(boardI, boardJ, currentPlayerMove.getPlayerColor())) {
            unselectPiece();
            selectPiece(boardI, boardJ);
        }
    }

    /**
     * Realiza un movimiento de la pieza seleccionada a la nueva posición,
     * cambia el estado del tablero y actualiza la vista.
     *
     * @param toI coordenada I de destino en el sistema del tablero
     * @param toJ coordenada J de destino en el sistema del tablero
     */
    private void makeMove(int toI, int toJ) {
        boolean isKingCaptured = board.isKing(toI, toJ);

        addMoveToPGN(selectedPieceI, selectedPieceJ, toI, toJ);
        ArrayList changesList = board.makeMove(selectedPieceI, selectedPieceJ, toI, toJ);
        moveHasDone = true;

        unselectPiece();
        view.changeBoardView(changesList);

        if (isKingCaptured) {
            view.gameOver(currentPlayerMove.getPlayerName());
        }
    }

    /**
     * Selecciona una pieza y pinta la selección en la vista.
     *
     * @param boardI coordenada I para seleccionar
     * @param boardJ coordenada J para seleccionar
     */
    private void selectPiece(int boardI, int boardJ) {
        System.out.println("Pieza seleccionada");
        isSelectedPiece = true;
        selectedPieceI = boardI;
        selectedPieceJ = boardJ;
        view.selectPiece(boardI, boardJ);
    }

    /**
     * Deselecciona la pieza seleccionada y elimina la selección en la vista.
     */
    private void unselectPiece() {
        System.out.println("Pieza deseleccionada");
        if (selectedPieceI != -1 && selectedPieceJ != -1) {
            view.selectPiece(selectedPieceI, selectedPieceJ);
            isSelectedPiece = false;
            selectedPieceI = -1;
            selectedPieceJ = -1;
        }
    }

    /**
     * Obtiene un ArrayList de ArrayLists que representa el tablero en formato
     * { { Color pieceColor, PieceType pieceType, boardI, boardJ } ...}
     *
     * @return * ArrayList representación del tablero
     */
    private ArrayList getBoardAsArrayList() {
        return board.getBoardAsArrayList();
    }

    /**
     * Prepara todo para iniciar el juego y lo comienza, actualizando el tablero.
     *
     * @param gameType tipo de juego que se inicia
     */
    public void startGame(GameType gameType) {
        this.gameType = gameType;
        moveHasDone = false;
        resetBoard();

        view.changeBoardView(getBoardAsArrayList());
    }

    /**
     * Guarda el juego en formato PGN.
     */
    public void saveGameAsPGN() {
        pgnSaver.savePGN(player1, player2);
    }

    /**
     * Agrega un movimiento al archivo PGN.
     *
     * @param fromI coordenada I de inicio de la pieza
     * @param fromJ coordenada J de inicio de la pieza
     * @param toI coordenada I de destino de la pieza
     * @param toJ coordenada J de destino de la pieza
     */
    private void addMoveToPGN(int fromI, int fromJ, int toI, int toJ) {
        pgnSaver.addMove(board.getSquareStatus(fromI, fromJ), board.getSquareStatus(toI, toJ));
    }
}