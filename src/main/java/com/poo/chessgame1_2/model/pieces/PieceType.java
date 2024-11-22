package com.poo.chessgame1_2.model.pieces;

/**
 * Enumeración que representa los tipos de piezas en el juego de ajedrez.
 * Incluye todas las piezas estándar del ajedrez, así como un tipo para las casillas vacías.
 */
public enum PieceType {
    /**
     * Representa el tipo de pieza Obispo.
     */
    BISHOP,

    /**
     * Representa el tipo de pieza Rey.
     */
    KING,

    /**
     * Representa el tipo de pieza Caballo.
     */
    KNIGHT,

    /**
     * Representa el tipo de pieza Peón.
     */
    PAWN,

    /**
     * Representa el tipo de pieza Reina.
     */
    QUEEN,

    /**
     * Representa el tipo de pieza Torre.
     */
    ROOK,

    /**
     * Representa una casilla vacía en el tablero de ajedrez.
     */
    EMPTY
}
