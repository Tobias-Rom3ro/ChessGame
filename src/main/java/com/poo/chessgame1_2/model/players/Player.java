package com.poo.chessgame1_2.model.players;

import com.poo.chessgame1_2.model.Square;
import javafx.scene.paint.Color;
import java.util.ArrayList;

/**
 * Clase que representa a un jugador en el juego de ajedrez.
 * Almacena información sobre el nombre del jugador y el color de sus piezas.
 */
public class Player {
    String name;
    Color playerColor;

    /**
     * Constructor que inicializa un jugador con un nombre y un color de piezas.
     *
     * @param name El nombre del jugador.
     * @param playerColor El color de las piezas del jugador (blanco o negro).
     */
    public Player(String name, Color playerColor) {
        this.name = name;
        this.playerColor = playerColor;
    }

    /**
     * Devuelve el color del jugador como una cadena de texto.
     *
     * @return "White" si el color es blanco, "Black" si el color es negro.
     */
    public String getPlayerColorAsString(){
        if(playerColor == Color.WHITE){
            return "White";
        }
        return "Black";
    }

    /**
     * Devuelve el color del jugador como un objeto {@link Color}.
     *
     * @return El color de las piezas del jugador.
     */
    public Color getPlayerColor(){
        return playerColor;
    }

    /**
     * Devuelve el nombre del jugador.
     *
     * @return El nombre del jugador.
     */
    public String getPlayerName(){
        return name;
    }

    /**
     * Establece un nuevo nombre para el jugador.
     *
     * @param newName El nuevo nombre que se asignará al jugador.
     */
    public void setPlayerName(String newName) {
        this.name = newName;
    }

    /**
     * Establece un nuevo color para las piezas del jugador.
     *
     * @param playerColor El nuevo color que se asignará a las piezas del jugador.
     */
    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }
}
