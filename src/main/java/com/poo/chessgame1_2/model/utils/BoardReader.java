package com.poo.chessgame1_2.model.utils;

import com.poo.chessgame1_2.model.Board;
import com.poo.chessgame1_2.model.GameType;
import com.poo.chessgame1_2.model.pieces.PieceType;
import com.poo.chessgame1_2.model.players.Player;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Clase responsable de leer y cargar el estado del tablero de ajedrez desde un archivo.
 * Esta clase interpreta el archivo de anotaciones del tablero y coloca las piezas en el tablero,
 * además de configurar los jugadores, el tipo de juego y el jugador que tiene el próximo movimiento.
 */
public class BoardReader extends FilesIO {

    private final Board board;
    private String filePath;

    /**
     * Constructor que inicializa la instancia de BoardReader con un tablero.
     *
     * @param board El tablero donde se establecerá el estado.
     */
    public BoardReader(Board board){
        this.board = board;
    }

    /**
     * Establece la ruta del archivo del cual se leerá la disposición del tablero.
     *
     * @param filePath Ruta del archivo con la anotación del tablero.
     */
    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    /**
     * Lee la información de las piezas desde el archivo de anotación y las coloca en el tablero.
     * También configura el jugador actual, el tipo de juego y los jugadores.
     */
    public void setData(){
        setScanner(filePath);

        ArrayList<String> playersData = new ArrayList<>();
        ArrayList<String> timersData = new ArrayList<>();

        while (scanner.hasNext()){
            String line = scanner.nextLine();

            // Omitir las líneas de comentarios en el archivo
            if(line.length() >= 1 && line.charAt(0) == '#'){
                continue;
            }

            String[] rowPiecesData = line.split(" ");
            for (String curPieceData : rowPiecesData) {

                if (curPieceData.length() >= 1 && curPieceData.charAt(0) == '#') {
                    break;
                } else if (curPieceData.length() >= 2 && curPieceData.charAt(0) == '@') {
                    setCurrentMoveColor(curPieceData.charAt(1));
                } else if (curPieceData.length() >= 2 && curPieceData.charAt(0) == '$') {
                    setGameType(curPieceData.charAt(1));
                } else if (curPieceData.length() >= 4 && curPieceData.charAt(0) == '%') {
                    playersData.add(curPieceData);
                } else if (curPieceData.length() >= 4 && curPieceData.charAt(0) == '&') {
                    timersData.add(curPieceData);
                } else if (curPieceData.length() == 4) {
                    setPiece(curPieceData);
                }
            }
        }

        if(playersData.size() >= 2){
            setPlayers(playersData.get(0),playersData.get(1));
        }

        scanner.close();
    }

    /**
     * Convierte los datos de la pieza en formato de texto a objetos y coloca la pieza en el tablero.
     *
     * @param pieceRowData Datos de la pieza en formato de texto (ej. wKc1 para una pieza blanca rey en la casilla c1).
     */
    private void setPiece(String pieceRowData){

        Color pieceColor;
        PieceType pieceType;
        int boardI, boardJ;

        if(pieceRowData.charAt(0) == 'w'){
            pieceColor = Color.WHITE;
        }
        else if(pieceRowData.charAt(0) == 'b'){
            pieceColor = Color.BLACK;
        }
        else {return;}

        if(pieceRowData.charAt(1) == 'B'){
            pieceType = PieceType.BISHOP;
        }
        else if(pieceRowData.charAt(1) == 'K') {
            pieceType = PieceType.KING;
        }
        else if(pieceRowData.charAt(1) == 'N') {
            pieceType = PieceType.KNIGHT;
        }
        else if(pieceRowData.charAt(1) == 'P') {
            pieceType = PieceType.PAWN;
        }
        else if(pieceRowData.charAt(1) == 'Q') {
            pieceType = PieceType.QUEEN;
        }
        else if(pieceRowData.charAt(1) == 'R') {
            pieceType = PieceType.ROOK;
        }
        else if(pieceRowData.charAt(1) == 'E'){
            pieceType = PieceType.EMPTY;
        }
        else {return;}

        if (pieceRowData.charAt(2) >= 'a' && pieceRowData.charAt(2) <= 'h'){
            boardI = pieceRowData.charAt(2) - 'a';
        }
        else{return;}

        if(pieceRowData.charAt(3) >= '1' && pieceRowData.charAt(3) <= '8'){
            boardJ = pieceRowData.charAt(3) - '1';
        }
        else{return;}

        board.setSquare(pieceColor,pieceType,boardI,boardJ);
    }

    /**
     * Establece el color del jugador que tiene el próximo movimiento.
     *
     * @param color Color del jugador (w para blanco, b para negro).
     */
    private void setCurrentMoveColor(char color){
        if(color == 'w'){
            board.setCurrentPlayerColor(Color.WHITE);
        }
        else{
            board.setCurrentPlayerColor(Color.BLACK);
        }
    }

    /**
     * Establece el tipo de juego basado en el caracter leído.
     *
     * @param gameType Tipo de juego (m para multijugador, s para un jugador).
     */
    private void setGameType(char gameType){
        board.setGameType(GameType.MULTIPLAYER);
    }

    /**
     * Decodifica la información de un jugador a un objeto Player.
     *
     * @param playerData Datos del jugador en formato de texto.
     * @return Instancia de Player con la información decodificada.
     */
    private Player getPlayerFromData(String playerData){
        Color playerColor;
        String playerName = playerData.substring(3);
        if(playerData.charAt(1) == 'w'){
            playerColor = Color.WHITE;
        }
        else if(playerData.charAt(1) == 'b'){
            playerColor = Color.BLACK;
        }
        else{
            return null;
        }
        return new Player(playerName,playerColor);
    }

    /**
     * Establece la información de los jugadores en el tablero.
     *
     * @param player1SData Datos del jugador 1.
     * @param player2Data Datos del jugador 2.
     */
    private void setPlayers(String player1SData, String player2Data){
        board.setPlayer(getPlayerFromData(player1SData), getPlayerFromData(player2Data));
    }
}
