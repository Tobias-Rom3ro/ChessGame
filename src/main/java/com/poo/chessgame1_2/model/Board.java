package com.poo.chessgame1_2.model;

import com.poo.chessgame1_2.model.pieces.PieceType;
import com.poo.chessgame1_2.model.players.Player;
import com.poo.chessgame1_2.model.utils.BoardReader;
import com.poo.chessgame1_2.model.utils.BoardWriter;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Clase que representa el tablero de ajedrez.
 * Maneja la posición de las piezas, las reglas del juego y la interacción con el modelo.
 */
public class Board {
    private final String START_BOARD_FILE = "/boardData/startBoard.txt"; // Archivo para la configuración inicial del tablero
    private final String SAVED_BOARD_FILE = "/boardData/savedBoard.txt"; // Archivo para guardar el estado del tablero
    private final int BOARD_SIZE = 8; // Tamaño del tablero (8x8)

    private final com.poo.chessgame1_2.model.Model model; // Modelo asociado al tablero
    private final com.poo.chessgame1_2.model.Square[][] board; // Matriz que representa las casillas del tablero
    private BoardReader br; // Lector de datos del tablero
    private final BoardWriter bw; // Escritor de datos del tablero

    /**
     * Constructor de la clase Board.
     * Inicializa el tablero y carga la configuración inicial.
     *
     * @param model modelo del juego
     */
    public Board(com.poo.chessgame1_2.model.Model model) {
        this.model = model;

        board = new com.poo.chessgame1_2.model.Square[BOARD_SIZE][BOARD_SIZE];
        bw = new BoardWriter();

        initBoardReader(); // Inicializa el lector de tablero
        loadBoard(); // Carga la configuración inicial del tablero
    }

    /**
     * Obtiene la matriz del tablero.
     *
     * @return matriz de casillas del tablero
     */
    public com.poo.chessgame1_2.model.Square[][] getBoard() {
        return board;
    }

    /**
     * Inicializa el lector de tablero y establece el archivo de configuración inicial.
     */
    private void initBoardReader() {
        br = new BoardReader(this);
        br.setFilePath(START_BOARD_FILE);
    }

    /**
     * Obtiene los datos de una casilla específica.
     *
     * @param boardI coordenada I de la casilla en el sistema del tablero
     * @param boardJ coordenada J de la casilla en el sistema del tablero
     * @return ArrayList con los datos de la casilla en el formato {Color pieceColor, PieceType pieceType, int boardI, int boardJ}
     */
    public ArrayList getSquareStatus(int boardI, int boardJ) {
        ArrayList list = new ArrayList();
        list.add(board[boardI][boardJ].getPieceColor());
        list.add(board[boardI][boardJ].getPieceType());
        list.add(boardI);
        list.add(boardJ);
        return list;
    }

    /**
     * Verifica si en la casilla especificada hay una pieza Rey.
     *
     * @param boardI coordenada I de la casilla
     * @param boardJ coordenada J de la casilla
     * @return true si hay un Rey en la casilla, false en caso contrario
     */
    public boolean isKing(int boardI, int boardJ) {
        return board[boardI][boardJ].isKing();
    }

    /**
     * Verifica si la casilla especificada está vacía.
     *
     * @param boardI coordenada I de la casilla
     * @param boardJ coordenada J de la casilla
     * @return true si la casilla está vacía, false en caso contrario
     */
    public boolean isEmptySquare(int boardI, int boardJ) {
        return board[boardI][boardJ].isEmpty();
    }

    /**
     * Verifica si en la casilla especificada hay una pieza del oponente.
     *
     * @param boardI coordenada I de la casilla
     * @param boardJ coordenada J de la casilla
     * @param currentPlayerColor color de la pieza del jugador actual
     * @return true si hay una pieza del oponente en la casilla, false en caso contrario
     */
    public boolean isOpponentPiece(int boardI, int boardJ, Color currentPlayerColor) {
        return board[boardI][boardJ].isOpponent(currentPlayerColor);
    }

    /**
     * Obtiene los datos de todo el tablero.
     *
     * * @return ArrayList de ArrayLists en el formato { {Color pieceColor, PieceType pieceType, int boardI, int boardJ} ...}
     */
    public ArrayList getBoardAsArrayList() {
        ArrayList boardArrayList = new ArrayList();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                boardArrayList.add(getSquareStatus(i, j));
            }
        }
        return boardArrayList;
    }

    /**
     * Establece una nueva casilla en el tablero.
     *
     * @param pieceColor color de la pieza
     * @param pieceType tipo de pieza a establecer en la casilla
     * @param boardI coordenada I de la casilla
     * @param boardJ coordenada J de la casilla
     */
    public void setSquare(Color pieceColor, PieceType pieceType, int boardI, int boardJ) {
        board[boardI][boardJ] = new com.poo.chessgame1_2.model.Square(pieceColor, pieceType, boardI, boardJ);
    }

    /**
     * Establece el jugador que debe realizar el siguiente movimiento utilizando el color de sus piezas.
     *
     * @param color color de la pieza que juega el jugador
     */
    public void setCurrentPlayerColor(Color color) {
        model.setCurrentPlayerColor(color);
    }

    /**
     * Establece el tipo de juego en el modelo.
     *
     * @param gameType tipo de juego
     */
    public void setGameType(GameType gameType) {
        model.setGameType(gameType);
    }

    /**
     * Establece los datos de los jugadores.
     *
     * @param player1 instancia del jugador 1
     * @param player2 instancia del jugador 2
     */
    public void setPlayer(Player player1, Player player2) {
        model.setPlayers(player1, player2);
    }

    /**
     * Carga los datos del tablero desde un archivo.
     */
    private void loadBoard() {
        br.setData();
    }

    /**
     * Verifica si el movimiento es válido.
     *
     * @param fromI coordenada I de inicio de la pieza a mover
     * @param fromJ coordenada J de inicio de la pieza a mover
     * @param toI coordenada I de destino de la pieza a mover
     * @param toJ coordenada J de destino de la pieza a mover
     * @return true si el movimiento es válido, false en caso contrario
     */
    public boolean isValidMove(int fromI, int fromJ, int toI, int toJ) {
        return board[fromI][fromJ].isValidMove(board, toI, toJ);
    }

    /**
     * Verifica si el movimiento del rey fue parte de un enroque.
     *
     * @param fromI posición inicial del rey en coordenada I
     * @param fromJ posición inicial del rey en coordenada J
     * @param toI coordenada I donde se mueve el rey
     * @param toJ coordenada J donde se mueve el rey
     * @return true si el movimiento fue parte de un enroque, false en caso contrario
     */
    private boolean isRoque(int fromI, int fromJ, int toI, int toJ) {
        return board[fromI][fromJ].isKing() && Math.abs(fromI - toI) == 2;
    }

    /**
     * Realiza el movimiento de la torre si el rey realiza un enroque.
     *
     * @param fromI posición inicial del rey en coordenada I
     * @param fromJ posición inicial del rey en coordenada J
     * @param toI coordenada I donde se mueve el rey
     * @param toJ coordenada J donde se mueve el rey
     * @return ArrayList con los cambios en el tablero
     */
    private ArrayList makeRoqueMove(int fromI, int fromJ, int toI, int toJ) {
        ArrayList roqueMove = new ArrayList();
        if (fromI - toI == 2) {
            roqueMove = makeMove(fromI - 4, fromJ, fromI - 1, toJ);
        } else {
            roqueMove = makeMove(fromI + 3, fromJ, fromI + 1, toJ);
        }

        return roqueMove;
    }

    /**
     * Verifica si el movimiento actual fue una promoción.
     *
     * @param fromI coordenada I de inicio de la pieza
     * * @param fromJ coordenada J de inicio de la pieza
     * @param toI coordenada I de destino de la pieza
     * @param toJ coordenada J de destino de la pieza
     * @return true si el movimiento fue una promoción, false en caso contrario
     */
    private boolean isPromotion(int fromI, int fromJ, int toI, int toJ) {
        if (!board[fromI][fromJ].isPawn()) {
            return false;
        }
        return toJ == 0 || toJ == BOARD_SIZE - 1;
    }

    /**
     * Realiza la promoción, cambiando un peón por una reina.
     *
     * @param fromI coordenada I de inicio de la pieza
     * @param fromJ coordenada J de inicio de la pieza
     * @param toI coordenada I de destino de la pieza
     * @param toJ coordenada J de destino de la pieza
     */
    private void makePromotion(int fromI, int fromJ, int toI, int toJ) {
        System.out.println("PROMOCIÓN");
        board[fromI][fromJ].setQuinInsteadOfPawn();
    }

    /**
     * Verifica si un peón se movió dos casillas hacia adelante.
     *
     * @param fromI coordenada I de inicio de la pieza
     * @param fromJ coordenada J de inicio de la pieza
     * @param toI coordenada I de destino de la pieza
     * @param toJ coordenada J de destino de la pieza
     * @return true si el peón se movió dos casillas hacia adelante, false en caso contrario
     */
    private boolean isTwoSquareMove(int fromI, int fromJ, int toI, int toJ) {
        return board[fromI][fromJ].isPawn() && Math.abs(fromJ - toJ) == 2;
    }

    /**
     * Establece el movimiento de dos casillas (el peón se movió dos casillas hacia adelante).
     *
     * @param fromI coordenada I de inicio de la pieza
     * @param fromJ coordenada J de inicio de la pieza
     * @param toI coordenada I de destino de la pieza
     * @param toJ coordenada J de destino de la pieza
     */
    private void setTwoSquareMove(int fromI, int fromJ, int toI, int toJ) {
        board[fromI][fromJ].setTwoSquareMove();
    }

    /**
     * Verifica si el movimiento actual es en Passant.
     *
     * @param fromI coordenada I de inicio de la pieza
     * @param fromJ coordenada J de inicio de la pieza
     * @param toI coordenada I de destino de la pieza
     * @param toJ coordenada J de destino de la pieza
     * @return true si el movimiento actual es en Passant, false en caso contrario
     */
    private boolean isEnPassant(int fromI, int fromJ, int toI, int toJ) {
        if (!board[fromI][fromJ].isPawn()) {
            return false;
        }
        return Math.abs(fromI - toI) == 1 && Math.abs(fromJ - toJ) == 1 && board[toI][toJ].isEmpty();
    }

    /**
     * Realiza el movimiento en Passant (captura el peón del oponente).
     *
     * @param fromI coordenada I de inicio de la pieza
     * @param fromJ coordenada J de inicio de la pieza
     * @param toI coordenada I de destino de la pieza
     * @param toJ coordenada J de destino de la pieza
     * @return ArrayList con los cambios en formato
     *         { { Color pieceColor, PieceType pieceType, boardI, boardJ } ...}
     */
    private ArrayList makeEnPassant(int fromI, int fromJ, int toI, int toJ) {
        ArrayList enPassantMove;
        if (fromI < toI) {
            board[fromI + 1][fromJ].setEmpty();
            enPassantMove = getSquareStatus(fromI + 1, fromJ);
        } else {
            board[fromI - 1][fromJ].setEmpty();
            enPassantMove = getSquareStatus(fromI - 1, fromJ);
        }
        return enPassantMove;
    }

    /**
     * Realiza un movimiento (verifica enroque, en Passant y promoción).
     *
     * @param fromI coordenada I de inicio de la pieza
     * @param fromJ coordenada J de inicio de la pieza
     * @param toI coordenada I de destino de la pieza
     * @param toJ coordenada J de destino de la pieza
     * @return ArrayList con los cambios en formato
     *         { { Color pieceColor, PieceType pieceType, boardI, boardJ } ...}
     */
    public ArrayList makeMove(int fromI, int fromJ, int toI, int toJ) {
        /**
         * Realiza el movimiento si es válido (cambia el estado del tablero)
         */

        ArrayList globalList = new ArrayList();

        // Verifica enroque
        if (isRoque(fromI, fromJ, toI, toJ)) {
            globalList = makeRoqueMove(fromI, fromJ, toI, toJ);
        }
        // Verifica promoción
        if (isPromotion(fromI, fromJ, toI, toJ)) {
            makePromotion(fromI, fromJ, toI, toJ);
        }
        if (isTwoSquareMove(fromI, fromJ, toI, toJ)) {
            setTwoSquareMove(fromI, fromJ, toI, toJ);
        }
        // Verifica en Passant
        if (isEnPassant(fromI, fromJ, toI, toJ)) {
            globalList.add(makeEnPassant(fromI, fromJ, toI, toJ));
        }

        board[toI][toJ].setPieceFromSquare(board[fromI][fromJ]);
        board[fromI][fromJ].setEmpty();

        board[toI][toJ].pieceHasMoved();

        globalList.add(getSquareStatus(toI, toJ));
        globalList.add(getSquareStatus(fromI, fromJ));

        return globalList;
    }
}