package com.poo.chessgame1_2.model.pieces;

import com.poo.chessgame1_2.model.Square;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Clase abstracta que representa una pieza en el juego de ajedrez.
 * Cada pieza tiene un color, un estado de movimiento, y métodos comunes para validar y generar los movimientos posibles.
 */
public abstract class Piece {
    Color pieceColor;
    int BOARD_SIZE = 8;
    boolean isLeftSide = true;

    boolean isMoved = false;
    boolean isTwoSquareMove = false;

    /**
     * Constructor de la clase Piece con un color específico.
     *
     * @param pieceColor Color de la pieza (blanco o negro).
     */
    Piece(Color pieceColor){
        this.pieceColor = pieceColor;
    }

    /**
     * Constructor vacío para la clase Piece, utilizado en subclases.
     */
    Piece(){}

    /**
     * Marca la pieza como movida.
     */
    public void setPieceMoved(){
        isMoved = true;
    }

    /**
     * Obtiene el estado de si la pieza ha sido movida.
     *
     * @return true si la pieza ha sido movida, false en caso contrario.
     */
    public boolean getPieceMoved(){
        return isMoved;
    }

    /**
     * Obtiene el estado de si la pieza puede realizar un movimiento de dos casillas (como en el caso de los peones).
     *
     * @return true si la pieza puede moverse dos casillas, false en caso contrario.
     */
    public boolean getTwoSquareMove() {
        return isTwoSquareMove;
    }

    /**
     * Establece que la pieza puede realizar un movimiento de dos casillas.
     */
    public void setTwoSquareMove() {
        isTwoSquareMove = true;
    }

    /**
     * Crea un par de coordenadas a partir de dos valores, representando una posición en el tablero.
     *
     * @param boardI Coordenada I de la pieza en el tablero.
     * @param boardJ Coordenada J de la pieza en el tablero.
     * @return Un ArrayList con las coordenadas {boardI, boardJ}.
     */
    ArrayList<Integer> makePair(int boardI, int boardJ){
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(boardI);
        list.add(boardJ);
        return list;
    }

    /**
     * Verifica si un movimiento es válido.
     *
     * @param board El tablero representado como una matriz de objetos Square[][].
     * @param fromI Coordenada I de la posición de inicio de la pieza.
     * @param fromJ Coordenada J de la posición de inicio de la pieza.
     * @param toI Coordenada I de la posición de destino de la pieza.
     * @param toJ Coordenada J de la posición de destino de la pieza.
     * @return true si el movimiento es válido, false en caso contrario.
     */
    public boolean isValidMove(Square[][] board, int fromI, int fromJ, int toI, int toJ){
        ArrayList availableMovesList = makeAvailableMovesList(board, fromI, fromJ);
        for (Object o : availableMovesList) {
            int indexI = (int) ((ArrayList<?>) o).get(0);
            int indexJ = (int) ((ArrayList<?>) o).get(1);
            if (toI == indexI && toJ == indexJ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Genera una lista de los movimientos disponibles para una pieza en una posición dada.
     *
     * @param board El tablero representado como una matriz de objetos Square[][].
     * @param fromI Coordenada I de la posición de inicio de la pieza.
     * @param fromJ Coordenada J de la posición de inicio de la pieza.
     * @return Una lista de movimientos disponibles en formato { {fromI, fromJ, toI, toJ}, ...}.
     */
    public abstract ArrayList makeAvailableMovesList(Square[][] board, int fromI, int fromJ);

}
