package com.poo.chessgame1_2.model;

import com.poo.chessgame1_2.model.pieces.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Clase que representa un cuadrado en el tablero de ajedrez.
 * Cada cuadrado contiene una pieza (si está ocupada) y su ubicación en el tablero.
 */
public class Square {
    private final int boardI;
    private final int boardJ;
    private Piece piece = null;
    private PieceType pieceType = null;
    private Color pieceColor = null;

    /**
     * Constructor que inicializa el cuadrado con un color de pieza, un tipo de pieza y su ubicación en el tablero.
     *
     * @param pieceColor El color de la pieza (blanco o negro).
     * @param pieceType El tipo de la pieza en este cuadrado (torre, rey, peón, etc.).
     * @param boardI La coordenada I (fila) del cuadrado en el tablero.
     * @param boardJ La coordenada J (columna) del cuadrado en el tablero.
     */
    public Square(Color pieceColor, PieceType pieceType, int boardI, int boardJ) {
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
        this.boardI = boardI;
        this.boardJ = boardJ;

        initPiece();
    }

    /**
     * Inicializa la pieza en este cuadrado según su tipo.
     */
    private void initPiece(){
        switch (pieceType){
            case BISHOP:
                piece = new BishopPiece(pieceColor);
                break;
            case KING:
                piece = new KingPiece(pieceColor);
                break;
            case KNIGHT:
                piece = new KnightPiece(pieceColor);
                break;
            case PAWN:
                piece = new PawnPiece(pieceColor, boardI, boardJ);
                break;
            case QUEEN:
                piece = new QueenPiece(pieceColor, boardI, boardJ);
                break;
            case ROOK:
                piece = new RookPiece(pieceColor, boardI, boardJ);
                break;
        }
    }

    /**
     * Devuelve el tipo de la pieza en este cuadrado.
     *
     * @return El tipo de la pieza.
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * Devuelve el color de la pieza en este cuadrado.
     *
     * @return El color de la pieza.
     */
    public Color getPieceColor() {
        return pieceColor;
    }

    /**
     * Devuelve true si el cuadrado está vacío (sin pieza).
     *
     * @return true si el cuadrado está vacío, false en caso contrario.
     */
    public boolean isEmpty(){
        return pieceType == PieceType.EMPTY;
    }

    /**
     * Devuelve true si el cuadrado contiene una pieza del oponente (de color diferente).
     *
     * @param color El color de la pieza que está en otro cuadrado.
     * @return true si la pieza en este cuadrado es del oponente.
     */
    public boolean isOpponent(Color color){
        return !isEmpty() && color != getPieceColor();
    }

    /**
     * Devuelve true si el cuadrado está vacío o contiene una pieza del oponente.
     *
     * @param color El color de la pieza que está en otro cuadrado.
     * @return true si el cuadrado está vacío o tiene una pieza del oponente.
     */
    public boolean isEmptyOrOpponent(Color color){
        if(isEmpty()){
            return true;
        }
        return isOpponent(color);
    }

    /**
     * Devuelve true si una pieza de tipo Rey está colocada en este cuadrado.
     *
     * @return true si la pieza es un Rey.
     */
    boolean isKing(){
        return pieceType == PieceType.KING;
    }

    /**
     * Devuelve true si una pieza de tipo Torre está colocada en este cuadrado.
     *
     * @return true si la pieza es una Torre.
     */
    public boolean isRook(){
        return pieceType == PieceType.ROOK;
    }

    /**
     * Devuelve true si una pieza de tipo Peón está colocada en este cuadrado.
     *
     * @return true si la pieza es un Peón.
     */
    public boolean isPawn(){
        return pieceType == PieceType.PAWN;
    }

    /**
     * Marca la pieza en este cuadrado como movida.
     */
    public void pieceHasMoved(){
        piece.setPieceMoved();
    }

    /**
     * Devuelve true si la pieza ha sido movida o false si está en su posición inicial.
     *
     * @return true si la pieza ha sido movida, false si está en la posición inicial.
     */
    public boolean getPieceMoved(){
        return piece.getPieceMoved();
    }

    /**
     * Devuelve true si el peón se movió dos casillas hacia adelante.
     *
     * @return true si el peón ha sido movido dos casillas hacia adelante.
     */
    public boolean getTwoSquareMove(){
        return piece.getTwoSquareMove();
    }

    /**
     * Establece que el peón en este cuadrado se movió dos casillas hacia adelante.
     */
    public void setTwoSquareMove(){
        piece.setTwoSquareMove();
    }

    /**
     * Copia la información de la pieza de otro cuadrado a este cuadrado.
     *
     * @param sq El otro cuadrado del que se copiará la pieza.
     */
    void setPieceFromSquare(Square sq){
        pieceColor = sq.pieceColor;
        pieceType = sq.pieceType;
        piece = sq.piece;
    }

    /**
     * Elimina la pieza de este cuadrado y lo deja vacío.
     */
    void setEmpty(){
        pieceType = PieceType.EMPTY;
        pieceColor = Color.WHITE;
        piece = null;
    }

    /**
     * Establece una Reina en lugar de un Peón (promoción).
     */
    void setQuinInsteadOfPawn(){
        piece = new QueenPiece(pieceColor, boardI, boardJ);
        pieceType = PieceType.QUEEN;
    }

    /**
     * Verifica si el movimiento de este cuadrado a otro destino es válido.
     *
     * @param board El arreglo de cuadrados que representa el tablero.
     * @param toI La coordenada I (fila) del destino.
     * @param toJ La coordenada J (columna) del destino.
     * @return true si el movimiento es válido, false en caso contrario.
     */
    public boolean isValidMove(Square[][] board, int toI, int toJ){
        return this.piece.isValidMove(board, this.boardI, this.boardJ, toI, toJ);
    }

    /**
     * Obtiene los movimientos disponibles de la pieza en este cuadrado.
     *
     * @param board El arreglo de cuadrados que representa el tablero.
     * @param fromI La coordenada I (fila) de la casilla de origen.
     * @param fromJ La coordenada J (columna) de la casilla de origen.
     * @return Una lista de los movimientos disponibles en formato
     *         { { Color pieceColor, PieceType pieceType, boardI, boardJ } ...}
     */
    public ArrayList getPieceAvailableMoves(Square[][] board, int fromI, int fromJ){
        if(board[fromI][fromJ].isEmpty()){
            return new ArrayList();
        }
        return this.piece.makeAvailableMovesList(board, fromI, fromJ);
    }
}
