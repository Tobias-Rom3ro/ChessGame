package com.poo.chessgame1_2.model.utils;

import com.poo.chessgame1_2.model.GameType;
import com.poo.chessgame1_2.model.pieces.PieceType;
import com.poo.chessgame1_2.model.players.Player;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class BoardWriter extends FilesIO{

    private String filePath;


    /**
     *
     * @param filePath path of file for saving game
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Print data of current board status to file.
     *
     * @param boardData Representation of board as an ArrayList of ArrayLists
     *                  in format { {Color pieceColor, PieceType pieceType, int boardI, int boardJ} ...}
     * @param currentMove Color of next move player
     */
    public void setData(ArrayList boardData, Color currentMove, GameType gameType, Player player1, Player player2){
        setPrintStream(filePath);

        for (Object boardDatum : boardData) {
            ArrayList currentPieceData = (ArrayList) boardDatum;
            printPiece(currentPieceData);
        }

        // write next move information
        String nextMoveString = currentMove == Color.BLACK ? "b" : "w";
        printWriter.println("@" + nextMoveString);

        // write gameType
        String isSinglePlayerString = gameType == GameType.MULTIPLAYER ? "s" : "m";
        printWriter.println("$" + isSinglePlayerString);

        //write players names and colors
        String playerColor1 = player1.getPlayerColor() == Color.BLACK ? "b" : "w";
        String playerColor2 = player2.getPlayerColor() == Color.BLACK ? "b" : "w";
        printWriter.println("%" + playerColor1 + "_" + player1.getPlayerName());
        printWriter.println("%" + playerColor2 + "_" + player2.getPlayerName());

        closePrintStream();
    }

    /**
     * Transform piece data to string and print it to file.
     *
     * @param currentPieceData data of current piece in format
     *                         { {Color pieceColor, PieceType pieceType, int boardI, int boardJ} ...}
     */
    private void printPiece(ArrayList currentPieceData){
        //Raw data
        Color pieceColor = (Color) currentPieceData.get(0);
        PieceType pieceType = (PieceType) currentPieceData.get(1);
        int coordinateI = (int) currentPieceData.get(2);
        int coordinateJ = (int) currentPieceData.get(3) + 1;

        //String data to print
        String pieceColorString = pieceColor == Color.BLACK ? "b" : "w";
        String pieceTypeString = "B";
        char coordinateIChar = (char) ('a' + coordinateI);

        switch (pieceType){
            case BISHOP:
                pieceTypeString = "B";
                break;
            case EMPTY:
                pieceTypeString = "E";
                break;
            case KING:
                pieceTypeString = "K";
                break;
            case KNIGHT:
                pieceTypeString = "N";
                break;
            case PAWN:
                pieceTypeString = "P";
                break;
            case QUEEN:
                pieceTypeString = "Q";
                break;
            case ROOK:
                pieceTypeString = "R";
                break;
        }
        printWriter.print(pieceColorString + pieceTypeString + coordinateIChar + coordinateJ + " ");

    }


}
