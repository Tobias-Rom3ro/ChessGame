package com.poo.chessgame1_2.model.pieces;

import com.poo.chessgame1_2.model.Square;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Clase que representa al alfil en el juego de ajedrez.
 * El alfil se mueve en las cuatro diagonales del tablero,
 * limitándose a avanzar a casillas vacías o capturar piezas enemigas.
 */
public class BishopPiece extends Piece {

    /**
     * Constructor de la clase BishopPiece.
     *
     * @param pieceColor El color de la pieza (blanco o negro).
     */
    public BishopPiece(Color pieceColor) {
        this.pieceColor = pieceColor;
    }

    /**
     * Genera una lista de movimientos posibles para el alfil.
     * El alfil se mueve diagonalmente en todas las direcciones (noroeste, noreste, suroeste, sureste).
     * Puede capturar piezas enemigas si se encuentra en una casilla correspondiente.
     *
     * @param board El tablero de ajedrez representado como una matriz de objetos Square.
     * @param fromI La coordenada I (fila) de la posición de la pieza.
     * @param fromJ La coordenada J (columna) de la posición de la pieza.
     * @return Una lista de movimientos válidos representados como pares de coordenadas.
     */
    @Override
    public ArrayList makeAvailableMovesList(Square[][] board, int fromI, int fromJ) {
        ArrayList<ArrayList> availableMovesList = new ArrayList<>();

        // Movimiento en diagonal hacia arriba a la derecha (noreste)
        int j = fromJ + 1;
        for (int i = fromI + 1; i < BOARD_SIZE && j < BOARD_SIZE; i++) {
            if (!board[i][j].isEmpty()) {
                // Añadir pieza enemiga a los movimientos disponibles
                if (board[i][j].getPieceColor() != pieceColor) {
                    availableMovesList.add(makePair(i, j));
                }
                break;
            }
            availableMovesList.add(makePair(i, j));
            j++;
        }

        // Movimiento en diagonal hacia abajo a la izquierda (suroeste)
        j = fromJ - 1;
        for (int i = fromI - 1; i >= 0 && j >= 0; i--) {
            if (!board[i][j].isEmpty()) {
                // Añadir pieza enemiga a los movimientos disponibles
                if (board[i][j].getPieceColor() != pieceColor) {
                    availableMovesList.add(makePair(i, j));
                }
                break;
            }
            availableMovesList.add(makePair(i, j));
            j--;
        }

        // Movimiento en diagonal hacia abajo a la derecha (sureste)
        j = fromJ - 1;
        for (int i = fromI + 1; i < BOARD_SIZE && j >= 0; i++) {
            if (!board[i][j].isEmpty()) {
                // Añadir pieza enemiga a los movimientos disponibles
                if (board[i][j].getPieceColor() != pieceColor) {
                    availableMovesList.add(makePair(i, j));
                }
                break;
            }
            availableMovesList.add(makePair(i, j));
            j--;
        }

        // Movimiento en diagonal hacia arriba a la izquierda (noroeste)
        j = fromJ + 1;
        for (int i = fromI - 1; i >= 0 && j < BOARD_SIZE; i--) {
            if (!board[i][j].isEmpty()) {
                // Añadir pieza enemiga a los movimientos disponibles
                if (board[i][j].getPieceColor() != pieceColor) {
                    availableMovesList.add(makePair(i, j));
                }
                break;
            }
            availableMovesList.add(makePair(i, j));
            j++;
        }

        return availableMovesList;
    }
}
