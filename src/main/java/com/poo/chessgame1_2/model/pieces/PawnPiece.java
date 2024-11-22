package com.poo.chessgame1_2.model.pieces;

import com.poo.chessgame1_2.model.Square;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Clase que representa un peón en el juego de ajedrez.
 * El peón tiene reglas especiales de movimiento, incluyendo el movimiento de dos casillas en su primer movimiento,
 * las capturas diagonales, y la regla de en passant.
 */
public class PawnPiece extends Piece {
    /**
     * Indica si el peón está en su posición inicial.
     */
    boolean isStartPosition = true;

    /**
     * Coordenadas iniciales del peón en el tablero.
     */
    int startBoardI, startBoardJ;

    /**
     * Constructor de la clase PawnPiece.
     *
     * @param pieceColor El color de la pieza (blanco o negro).
     * @param boardI     La coordenada I (fila) en el tablero.
     * @param boardJ     La coordenada J (columna) en el tablero.
     */
    public PawnPiece(Color pieceColor, int boardI, int boardJ) {
        super(pieceColor);
        this.startBoardI = boardI;
        this.startBoardJ = boardJ;

        // Determina de qué lado del tablero está el peón.
        if (boardJ < BOARD_SIZE / 2) {
            isLeftSide = true;
        } else {
            isLeftSide = false;
        }
    }

    /**
     * Genera una lista de movimientos posibles para el peón.
     * Considera movimientos normales, movimientos de captura en diagonal, y la regla de en passant.
     *
     * @param board El tablero de ajedrez representado como una matriz de objetos Square.
     * @param fromI La coordenada I (fila) de la posición de la pieza.
     * @param fromJ La coordenada J (columna) de la posición de la pieza.
     * @return Una lista de movimientos válidos representados como pares de coordenadas.
     */
    @Override
    public ArrayList makeAvailableMovesList(Square[][] board, int fromI, int fromJ) {
        // Si el peón no está en su posición inicial, cambia el estado.
        if (fromI != startBoardI || fromJ != startBoardJ) {
            isStartPosition = false;
        }

        ArrayList<ArrayList> availableMovesList = new ArrayList<>();

        // Lógica de movimientos para el lado izquierdo del tablero (peones blancos).
        if (isLeftSide) {
            // Movimiento normal de una casilla hacia adelante.
            if (fromJ + 1 < BOARD_SIZE && board[fromI][fromJ + 1].isEmpty()) {
                availableMovesList.add(makePair(fromI, fromJ + 1));
            }

            // Movimiento de dos casillas hacia adelante desde la posición inicial.
            if (isStartPosition && fromJ + 2 < BOARD_SIZE && board[fromI][fromJ + 1].isEmpty() && board[fromI][fromJ + 2].isEmpty()) {
                availableMovesList.add(makePair(fromI, fromJ + 2));
            }

            // Captura diagonal hacia la izquierda (oponente).
            if (fromI - 1 >= 0 && fromJ + 1 < BOARD_SIZE && !board[fromI - 1][fromJ + 1].isEmpty() && board[fromI - 1][fromJ + 1].isOpponent(pieceColor)) {
                availableMovesList.add(makePair(fromI - 1, fromJ + 1));
            }

            // Captura diagonal hacia la derecha (oponente).
            if (fromI + 1 < BOARD_SIZE && fromJ + 1 < BOARD_SIZE && !board[fromI + 1][fromJ + 1].isEmpty() && board[fromI + 1][fromJ + 1].isOpponent(pieceColor)) {
                availableMovesList.add(makePair(fromI + 1, fromJ + 1));
            }

            // Regla de en passant a la izquierda.
            if (fromI - 1 >= 0 && fromJ + 1 < BOARD_SIZE && board[fromI - 1][fromJ + 1].isEmpty() &&
                    !board[fromI - 1][fromJ].isEmpty() && board[fromI - 1][fromJ].isOpponent(pieceColor) &&
                    board[fromI - 1][fromJ].getTwoSquareMove()) {
                availableMovesList.add(makePair(fromI - 1, fromJ + 1));
            }

            // Regla de en passant a la derecha.
            if (fromI + 1 < BOARD_SIZE && fromJ + 1 < BOARD_SIZE && board[fromI + 1][fromJ + 1].isEmpty() &&
                    !board[fromI + 1][fromJ].isEmpty() && board[fromI + 1][fromJ].isOpponent(pieceColor) &&
                    board[fromI + 1][fromJ].getTwoSquareMove()) {
                availableMovesList.add(makePair(fromI + 1, fromJ + 1));
            }

        } else {
            // Lógica de movimientos para el lado derecho del tablero (peones negros).
            // Movimiento normal de una casilla hacia adelante.
            if (fromJ - 1 >= 0 && board[fromI][fromJ - 1].isEmpty()) {
                availableMovesList.add(makePair(fromI, fromJ - 1));
            }

            // Movimiento de dos casillas hacia adelante desde la posición inicial.
            if (isStartPosition && fromJ - 2 >= 0 && board[fromI][fromJ - 1].isEmpty() && board[fromI][fromJ - 2].isEmpty()) {
                availableMovesList.add(makePair(fromI, fromJ - 2));
            }

            // Captura diagonal hacia la izquierda (oponente).
            if (fromI + 1 < BOARD_SIZE && fromJ - 1 >= 0 && !board[fromI + 1][fromJ - 1].isEmpty() && board[fromI + 1][fromJ - 1].isOpponent(pieceColor)) {
                availableMovesList.add(makePair(fromI + 1, fromJ - 1));
            }

            // Captura diagonal hacia la derecha (oponente).
            if (fromI - 1 >= 0 && fromJ - 1 >= 0 && !board[fromI - 1][fromJ - 1].isEmpty() && board[fromI - 1][fromJ - 1].isOpponent(pieceColor)) {
                availableMovesList.add(makePair(fromI - 1, fromJ - 1));
            }

            // Regla de en passant a la izquierda.
            if (fromI + 1 < BOARD_SIZE && fromJ - 1 >= 0 && board[fromI + 1][fromJ - 1].isEmpty() &&
                    !board[fromI + 1][fromJ - 1].isEmpty() && board[fromI + 1][fromJ - 1].isOpponent(pieceColor) &&
                    board[fromI + 1][fromJ - 1].getTwoSquareMove()) {
                availableMovesList.add(makePair(fromI + 1, fromJ - 1));
            }

            // Regla de en passant a la derecha.
            if (fromI - 1 >= 0 && fromJ - 1 >= 0 && board[fromI - 1][fromJ - 1].isEmpty() &&
                    !board[fromI - 1][fromJ - 1].isEmpty() && board[fromI - 1][fromJ - 1].isOpponent(pieceColor) &&
                    board[fromI - 1][fromJ - 1].getTwoSquareMove()) {
                availableMovesList.add(makePair(fromI - 1, fromJ - 1));
            }
        }

        return availableMovesList;
    }
}
