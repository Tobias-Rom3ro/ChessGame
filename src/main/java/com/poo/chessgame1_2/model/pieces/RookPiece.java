package com.poo.chessgame1_2.model.pieces;

import com.poo.chessgame1_2.model.Square;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Representa la pieza de la torre en el juego de ajedrez.
 * La torre se mueve en líneas rectas, tanto vertical como horizontalmente.
 */
public class RookPiece extends Piece {

    /**
     * Constructor de la pieza Torre.
     *
     * @param pieceColor El color de la pieza (blanco o negro).
     * @param boardI Coordenada I de la torre en el tablero.
     * @param boardJ Coordenada J de la torre en el tablero.
     */
    public RookPiece(Color pieceColor, int boardI, int boardJ) {
        super(pieceColor);
    }

    /**
     * Calcula y devuelve la lista de movimientos posibles de la torre desde una posición dada.
     * La torre puede moverse horizontal y verticalmente, hasta que encuentre una pieza que bloquee su camino
     * o una pieza del oponente que puede capturar.
     *
     * @param board El tablero representado como una matriz de objetos Square[][].
     * @param fromI Coordenada I de la posición de inicio de la pieza.
     * @param fromJ Coordenada J de la posición de inicio de la pieza.
     * @return Una lista de posibles movimientos disponibles en formato { {fromI, fromJ, toI, toJ}, ...}.
     */
    @Override
    public ArrayList makeAvailableMovesList(Square[][] board, int fromI, int fromJ) {
        ArrayList<ArrayList> availableMovesList = new ArrayList<>();

        // Movimiento hacia abajo (incrementando I)
        for (int i = fromI + 1; i < BOARD_SIZE; i++) {
            if (!board[i][fromJ].isEmpty()) {
                // Agregar pieza del oponente a los movimientos disponibles
                if (board[i][fromJ].getPieceColor() != pieceColor) {
                    availableMovesList.add(makePair(i, fromJ));
                }
                break; // La torre no puede seguir más allá de la pieza
            }
            availableMovesList.add(makePair(i, fromJ)); // Movimiento vacío
        }

        // Movimiento hacia arriba (decrementando I)
        for (int i = fromI - 1; i >= 0; i--) {
            if (!board[i][fromJ].isEmpty()) {
                // Agregar pieza del oponente a los movimientos disponibles
                if (board[i][fromJ].getPieceColor() != pieceColor) {
                    availableMovesList.add(makePair(i, fromJ));
                }
                break; // La torre no puede seguir más allá de la pieza
            }
            availableMovesList.add(makePair(i, fromJ)); // Movimiento vacío
        }

        // Movimiento hacia la derecha (incrementando J)
        for (int j = fromJ + 1; j < BOARD_SIZE; j++) {
            if (!board[fromI][j].isEmpty()) {
                // Agregar pieza del oponente a los movimientos disponibles
                if (board[fromI][j].getPieceColor() != pieceColor) {
                    availableMovesList.add(makePair(fromI, j));
                }
                break; // La torre no puede seguir más allá de la pieza
            }
            availableMovesList.add(makePair(fromI, j)); // Movimiento vacío
        }

        // Movimiento hacia la izquierda (decrementando J)
        for (int j = fromJ - 1; j >= 0; j--) {
            if (!board[fromI][j].isEmpty()) {
                // Agregar pieza del oponente a los movimientos disponibles
                if (board[fromI][j].getPieceColor() != pieceColor) {
                    availableMovesList.add(makePair(fromI, j));
                }
                break; // La torre no puede seguir más allá de la pieza
            }
            availableMovesList.add(makePair(fromI, j)); // Movimiento vacío
        }

        return availableMovesList;
    }
}
