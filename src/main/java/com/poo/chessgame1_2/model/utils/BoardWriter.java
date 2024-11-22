package com.poo.chessgame1_2.model.utils;

import com.poo.chessgame1_2.model.GameType;
import com.poo.chessgame1_2.model.pieces.PieceType;
import com.poo.chessgame1_2.model.players.Player;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Clase que maneja la escritura del estado del tablero de ajedrez en un archivo.
 * Permite guardar la información sobre las piezas en el tablero, el jugador que tiene el próximo movimiento,
 * el tipo de juego (multijugador o no), y los jugadores involucrados.
 */
public class BoardWriter extends FilesIO {

    private String filePath;

    /**
     * Establece la ruta del archivo donde se guardará el estado del juego.
     *
     * @param filePath Ruta del archivo para guardar el estado del juego.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Imprime el estado actual del tablero de ajedrez en un archivo, incluyendo:
     * - La posición de todas las piezas
     * - El jugador que tiene el próximo movimiento
     * - El tipo de juego (multijugador o no)
     * - Los nombres y colores de los jugadores
     *
     * @param boardData Representación del tablero como una lista de listas de objetos, con cada pieza representada por:
     *                  { {Color pieceColor, PieceType pieceType, int boardI, int boardJ} ...}
     * @param currentMove Color del jugador que tiene el próximo movimiento.
     * @param gameType Tipo de juego (multijugador o no).
     * @param player1 Jugador 1, instancia de la clase Player.
     * @param player2 Jugador 2, instancia de la clase Player.
     */
    public void setData(ArrayList boardData, Color currentMove, GameType gameType, Player player1, Player player2) {
        setPrintStream(filePath);

        // Imprimir la posición de las piezas en el tablero
        for (Object boardDatum : boardData) {
            ArrayList currentPieceData = (ArrayList) boardDatum;
            printPiece(currentPieceData);
        }

        // Escribir información sobre el próximo movimiento
        String nextMoveString = currentMove == Color.BLACK ? "b" : "w";
        printWriter.println("@" + nextMoveString);

        // Escribir tipo de juego
        String isSinglePlayerString = gameType == GameType.MULTIPLAYER ? "s" : "m";
        printWriter.println("$" + isSinglePlayerString);

        // Escribir los nombres y colores de los jugadores
        String playerColor1 = player1.getPlayerColor() == Color.BLACK ? "b" : "w";
        String playerColor2 = player2.getPlayerColor() == Color.BLACK ? "b" : "w";
        printWriter.println("%" + playerColor1 + "_" + player1.getPlayerName());
        printWriter.println("%" + playerColor2 + "_" + player2.getPlayerName());

        closePrintStream();
    }

    /**
     * Transforma los datos de una pieza en una cadena de texto y la imprime en el archivo.
     *
     * @param currentPieceData Datos de la pieza en formato:
     *                         { {Color pieceColor, PieceType pieceType, int boardI, int boardJ} ...}
     */
    private void printPiece(ArrayList currentPieceData) {
        // Obtener los datos crudos de la pieza
        Color pieceColor = (Color) currentPieceData.get(0);
        PieceType pieceType = (PieceType) currentPieceData.get(1);
        int coordinateI = (int) currentPieceData.get(2);
        int coordinateJ = (int) currentPieceData.get(3) + 1;

        // Convertir los datos a cadenas para imprimir
        String pieceColorString = pieceColor == Color.BLACK ? "b" : "w";
        String pieceTypeString = "B";  // Valor por defecto
        char coordinateIChar = (char) ('a' + coordinateI);

        // Asignar la abreviatura de la pieza según su tipo
        switch (pieceType) {
            case BISHOP:
                pieceTypeString = "B";
                break;
            case EMPTY:
                pieceTypeString = "E";
                break;
            case KING:
                pieceTypeString = "K";
                break;
            case KNIGHT:
                pieceTypeString = "N";
                break;
            case PAWN:
                pieceTypeString = "P";
                break;
            case QUEEN:
                pieceTypeString = "Q";
                break;
            case ROOK:
                pieceTypeString = "R";
                break;
        }

        // Imprimir la pieza en formato de texto
        printWriter.print(pieceColorString + pieceTypeString + coordinateIChar + coordinateJ + " ");
    }
}
