package com.poo.chessgame1_2.model.pieces;

import com.poo.chessgame1_2.model.Square;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Clase que representa al rey en el juego de ajedrez.
 * El rey tiene un movimiento limitado a una casilla en cualquier dirección,
 * y además permite realizar un movimiento especial conocido como enroque.
 */
public class KingPiece extends Piece {

    /**
     * Constructor de la clase KingPiece.
     *
     * @param pieceColor El color de la pieza (blanco o negro).
     */
    public KingPiece(Color pieceColor) {
        super(pieceColor);
    }

    /**
     * Genera una lista de movimientos posibles para el rey.
     * El rey puede moverse una casilla en cualquier dirección: horizontal, vertical o diagonal.
     * Además, puede realizar un enroque si las condiciones son las adecuadas.
     *
     * @param board El tablero de ajedrez representado como una matriz de objetos Square.
     * @param fromI La coordenada I (fila) de la posición de la pieza.
     * @param fromJ La coordenada J (columna) de la posición de la pieza.
     * @return Una lista de movimientos válidos representados como pares de coordenadas.
     */
    @Override
    public ArrayList makeAvailableMovesList(Square[][] board, int fromI, int fromJ) {
        ArrayList<ArrayList> availableMoves = new ArrayList<>();

        // Movimientos en las ocho direcciones posibles.
        if (fromI + 1 < BOARD_SIZE && board[fromI + 1][fromJ].isEmpty()) {
            if (!isUnderAttack(board, fromI + 1, fromJ)) {
                availableMoves.add(makePair(fromI + 1, fromJ));
            }
        }
        if (fromI - 1 >= 0) {
            if (!isUnderAttack(board, fromI - 1, fromJ) && board[fromI - 1][fromJ].isEmpty()) {
                availableMoves.add(makePair(fromI - 1, fromJ));
            }
        }
        if (fromJ + 1 < BOARD_SIZE && board[fromI][fromJ + 1].isEmpty()) {
            if (!isUnderAttack(board, fromI, fromJ + 1)) {
                availableMoves.add(makePair(fromI, fromJ + 1));
            }
        }
        if (fromJ - 1 >= 0 && board[fromI][fromJ - 1].isEmpty()) {
            if (!isUnderAttack(board, fromI, fromJ - 1)) {
                availableMoves.add(makePair(fromI, fromJ - 1));
            }
        }
        if (fromI + 1 < BOARD_SIZE && fromJ + 1 < BOARD_SIZE && board[fromI + 1][fromJ + 1].isEmpty()) {
            if (!isUnderAttack(board, fromI + 1, fromJ + 1)) {
                availableMoves.add(makePair(fromI + 1, fromJ + 1));
            }
        }
        if (fromI + 1 < BOARD_SIZE && fromJ - 1 >= 0 && board[fromI + 1][fromJ - 1].isEmpty()) {
            if (!isUnderAttack(board, fromI + 1, fromJ - 1)) {
                availableMoves.add(makePair(fromI + 1, fromJ - 1));
            }
        }
        if (fromI - 1 >= 0 && fromJ + 1 < BOARD_SIZE && board[fromI - 1][fromJ + 1].isEmpty()) {
            if (!isUnderAttack(board, fromI - 1, fromJ + 1)) {
                availableMoves.add(makePair(fromI - 1, fromJ + 1));
            }
        }
        if (fromI - 1 >= 0 && fromJ - 1 >= 0 && board[fromI - 1][fromJ - 1].isEmpty()) {
            if (!isUnderAttack(board, fromI - 1, fromJ - 1)) {
                availableMoves.add(makePair(fromI - 1, fromJ - 1));
            }
        }

        // Verificación de enroque hacia la izquierda.
        if (!isMoved && fromI - 4 >= 0 && board[fromI - 1][fromJ].isEmpty() && board[fromI - 2][fromJ].isEmpty() &&
                board[fromI - 3][fromJ].isEmpty() && board[fromI - 4][fromJ].isRook() &&
                !board[fromI - 4][fromJ].getPieceMoved()) {
            availableMoves.add(makePair(fromI - 2, fromJ));
        }

        // Verificación de enroque hacia la derecha.
        if (!isMoved && fromI + 3 < BOARD_SIZE && board[fromI + 1][fromJ].isEmpty() && board[fromI + 2][fromJ].isEmpty() &&
                board[fromI + 3][fromJ].isRook() && !board[fromI + 3][fromJ].getPieceMoved()) {
            availableMoves.add(makePair(fromI + 2, fromJ));
        }

        return availableMoves;
    }

    /**
     * Verifica si la casilla especificada está bajo ataque por alguna pieza enemiga.
     *
     * @param board El tablero de ajedrez representado como una matriz de objetos Square.
     * @param toI La coordenada I (fila) de la casilla a verificar.
     * @param toJ La coordenada J (columna) de la casilla a verificar.
     * @return true si la casilla está bajo ataque, false en caso contrario.
     */
    private boolean isUnderAttack(Square[][] board, int toI, int toJ) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!board[i][j].isEmpty() && pieceColor != board[i][j].getPieceColor() && board[i][j].getPieceType() != PieceType.KING && board[i][j].isValidMove(board, toI, toJ)) {
                    return true;
                }
            }
        }
        return false;
    }
}
