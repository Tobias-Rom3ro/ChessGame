package com.poo.chessgame1_2.model.players;

import com.poo.chessgame1_2.model.Square;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Player {
    String name;
    Color playerColor;

    public Player(String name, Color playerColor) {
        this.name = name;
        this.playerColor = playerColor;
    }

    /**
     *  Return player color as String
     * @return String colorString
     */
    public String getPlayerColorAsString(){
        if(playerColor == Color.WHITE){
            return "White";
        }
        return "Black";
    }

    /**
     * Return color of player's pieces
     * @return Color playerColor
     */
    public Color getPlayerColor(){
        return playerColor;
    }

    /**
     * Return player name
     * @return String player name
     */
    public String getPlayerName(){
        return name;
    }

    /**
     * Set new name to player.
     * @param newName new name
     */
    public void setPlayerName(String newName) {
        this.name = newName;
    }


    /**
     * Set new color to player
     *
     * @param playerColor new color
     */
    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }

}
