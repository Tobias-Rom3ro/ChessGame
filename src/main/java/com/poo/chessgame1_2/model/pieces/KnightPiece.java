package com.poo.chessgame1_2.model.pieces;

import com.poo.chessgame1_2.model.Square;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Clase que representa un caballo en el juego de ajedrez.
 * El caballo tiene un movimiento único en forma de "L" y puede saltar sobre otras piezas.
 */
public class KnightPiece extends Piece {

    /**
     * Constructor de la clase KnightPiece.
     *
     * @param pieceColor El color de la pieza (blanco o negro).
     */
    public KnightPiece(Color pieceColor) {
        super(pieceColor);
    }

    /**
     * Genera una lista de movimientos posibles para el caballo.
     * El caballo se mueve en forma de "L", moviéndose dos casillas en una dirección (horizontal o vertical)
     * y luego una casilla en la dirección perpendicular, o viceversa.
     *
     * @param board El tablero de ajedrez representado como una matriz de objetos Square.
     * @param fromI La coordenada I (fila) de la posición de la pieza.
     * @param fromJ La coordenada J (columna) de la posición de la pieza.
     * @return Una lista de movimientos válidos representados como pares de coordenadas.
     */
    @Override
    public ArrayList makeAvailableMovesList(Square[][] board, int fromI, int fromJ) {
        ArrayList<ArrayList> availableMovesList = new ArrayList<>();

        // Movimientos en forma de "L" hacia arriba a la derecha.
        if (fromI - 2 >= 0 && fromJ + 1 < BOARD_SIZE && board[fromI - 2][fromJ + 1].isEmptyOrOpponent(pieceColor)) {
            availableMovesList.add(makePair(fromI - 2, fromJ + 1));
        }

        // Movimientos en forma de "L" hacia arriba a la izquierda.
        if (fromI - 2 >= 0 && fromJ - 1 >= 0 && board[fromI - 2][fromJ - 1].isEmptyOrOpponent(pieceColor)) {
            availableMovesList.add(makePair(fromI - 2, fromJ - 1));
        }

        // Movimientos en forma de "L" hacia abajo a la derecha.
        if (fromI - 1 >= 0 && fromJ + 2 < BOARD_SIZE && board[fromI - 1][fromJ + 2].isEmptyOrOpponent(pieceColor)) {
            availableMovesList.add(makePair(fromI - 1, fromJ + 2));
        }

        // Movimientos en forma de "L" hacia abajo a la izquierda.
        if (fromI - 1 >= 0 && fromJ - 2 >= 0 && board[fromI - 1][fromJ - 2].isEmptyOrOpponent(pieceColor)) {
            availableMovesList.add(makePair(fromI - 1, fromJ - 2));
        }

        // Movimientos en forma de "L" hacia arriba a la derecha (de dos filas).
        if (fromI + 2 < BOARD_SIZE && fromJ + 1 < BOARD_SIZE && board[fromI + 2][fromJ + 1].isEmptyOrOpponent(pieceColor)) {
            availableMovesList.add(makePair(fromI + 2, fromJ + 1));
        }

        // Movimientos en forma de "L" hacia arriba a la izquierda (de dos filas).
        if (fromI + 2 < BOARD_SIZE && fromJ - 1 >= 0 && board[fromI + 2][fromJ - 1].isEmptyOrOpponent(pieceColor)) {
            availableMovesList.add(makePair(fromI + 2, fromJ - 1));
        }

        // Movimientos en forma de "L" hacia abajo a la derecha (de una fila).
        if (fromI + 1 < BOARD_SIZE && fromJ + 2 < BOARD_SIZE && board[fromI + 1][fromJ + 2].isEmptyOrOpponent(pieceColor)) {
            availableMovesList.add(makePair(fromI + 1, fromJ + 2));
        }

        // Movimientos en forma de "L" hacia abajo a la izquierda (de una fila).
        if (fromI + 1 < BOARD_SIZE && fromJ - 2 >= 0 && board[fromI + 1][fromJ - 2].isEmptyOrOpponent(pieceColor)) {
            availableMovesList.add(makePair(fromI + 1, fromJ - 2));
        }

        return availableMovesList;
    }
}
