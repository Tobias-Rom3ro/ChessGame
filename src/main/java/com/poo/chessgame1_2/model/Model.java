package com.poo.chessgame1_2.model;

import com.poo.chessgame1_2.model.players.Player;
import com.poo.chessgame1_2.model.utils.PGNSaver;
import javafx.scene.paint.Color;

import com.poo.chessgame1_2.view.View;

import java.util.ArrayList;

public class Model {
    private final int BOARD_SIZE = 8;
    private  View view;
    private Board board = null;
    private final PGNSaver pgnSaver = new PGNSaver();

    private Player currentPlayerMove;
    private final Player player1 = new Player("player1", Color.WHITE);
    private Player player2 = new Player("player2", Color.BLACK);

    private boolean isSelectedPiece = false;
    private GameType gameType;
    private int selectedPieceI, selectedPieceJ;

    private boolean moveHasDone = false;

    public Model(){

    }

    /**
     *  Set view to acces view change methods
     * @param view instance of View
     */
    public void setView(View view){
        this.view = view;
    }

    /**
     *  Set game type
     *
     * @param gameType GameType.SINGLEPLAYER or GameType.MULTIPLAYER
     */
     void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    /**
     *  Get game type
     * @return GameType.SINGLEPLAYER or GameType.MULTIPLAYER
     */
    GameType getGameType(){
        return gameType;
    }


    /**
     * Get player1 Player innstance
     *
     * @return  Player instance
     */
    Player getPlayer1() {
        return player1;
    }

    /**
     * Get player2 Player instance
     * @return Player instance
     */
    Player getPlayer2() {
        return player2;
    }

    /**
     * Get pieces color of player who should make move
     *
     * @return color of pieces
     */
    Color getCurrentPlayerColor() {
        return currentPlayerMove.getPlayerColor();
    }

    /**
     * Get player1 name.
     *
     * @return  player1 name
     */
    public String getPlayer1Name(){
        return player1.getPlayerName();
    }

    /**
     * Get player2 name
     *
     * @return player2 name
     */
    public String getPlayer2Name(){
        return player2.getPlayerName();
    }

    /**
     * Set players data in multiplayer mode
     *
     * @param player1Name name of player1
     * @param player2Name name of player2
     */
    public void setPlayers(String player1Name, String player2Name){
        player1.setPlayerName(player1Name); //Color.WHITE
        player2.setPlayerName(player2Name); //Color.BLACK
    }

    /**
     *
     * Reset another players data to player1, player2
     *
     * @param player1 new player1 name
     * @param player2 new player2 name
     */
    public void setPlayers(Player player1, Player player2){
        this.player1.setPlayerColor(player1.getPlayerColor());
        this.player1.setPlayerName(player1.getPlayerName());
        this.player2.setPlayerColor(player2.getPlayerColor());
        this.player2.setPlayerName(player2.getPlayerName());
    }

    /**
     *
     * Set player, who should make next move using color of his pieces.
     *
     * @param currentPlayerColor color of piece, that player play
     */
     void setCurrentPlayerColor(Color currentPlayerColor) {
        if(currentPlayerColor == player1.getPlayerColor()){
            currentPlayerMove = player1;
        }
        else{
            currentPlayerMove = player2;
        }
    }


    /**
     * Chane right to move to another player.
     * If game is singleplayer, make computer move.
     */
    public void nextMove(){
        if(moveHasDone){
            changeCurrentPlayerMove();
            moveHasDone = false;
        }
        else{
            return;
        }
        unselectPiece();
    }
    /**
     * Change currentPlayerMove. Stop previous player timer and start currentPlayer timer.
     */
    private  void changeCurrentPlayerMove(){
        currentPlayerMove = currentPlayerMove == player1 ? player2 : player1;
    }


    /**
     * Make new instance of board
     */
    private void resetBoard(){
        board = new Board(this);
    }

    /**
     *
     * Player has clicked on board square.
     *
     * Method has 4 options.
     *  1. Make move if it's possible
     *  2. Select piece if it is CurrentPlayer piece
     *  3. Reselect piece or unselect if it's not CurrentPlayer piece
     *  4. Ignore
     *
     * @param boardI I coordinate of clicked square in Board system
     * @param boardJ J coordinate of clicked square in Board system
     */
    public void squareWasClicked(int boardI, int boardJ){

        // make move
        if(isSelectedPiece && board.isEmptySquare(boardI,boardJ) || isSelectedPiece && board.isOpponentPiece(boardI,boardJ,currentPlayerMove.getPlayerColor())){
            if(board.isValidMove(selectedPieceI,selectedPieceJ,boardI,boardJ) && !moveHasDone){
                makeMove(boardI,boardJ);
                System.out.println("Move has done!");
            }
        }
        //select piece
        else if(!isSelectedPiece && !board.isEmptySquare(boardI,boardJ) && !board.isOpponentPiece(boardI,boardJ,currentPlayerMove.getPlayerColor()) && !moveHasDone){
            selectPiece(boardI, boardJ);
        }
        //reselect piece
        else if(isSelectedPiece && !board.isEmptySquare(boardI,boardJ) && !board.isOpponentPiece(boardI, boardJ,currentPlayerMove.getPlayerColor())){
            unselectPiece();
            selectPiece(boardI, boardJ);
        }
//        board.printBoard();

    }

    /**
     * Make move from selectedI selectedJ to toI toJ, change the board and update board in view.
     * @param toI piece I destination coordinate in Board system
     * @param toJ piece J destination coordinate in Board system
     */
    private void makeMove(int toI, int toJ){
        boolean isKingCaptured = board.isKing(toI,toJ);

        addMoveToPGN(selectedPieceI,selectedPieceJ,toI,toJ);
        ArrayList changesList = board.makeMove(selectedPieceI,selectedPieceJ,toI,toJ);
        moveHasDone = true;

        unselectPiece();
        view.changeBoardView(changesList);

        if(isKingCaptured){
            view.gameOver(currentPlayerMove.getPlayerName());
        }

    }

    /**
     *
     * Select piece and paint selection in view.
     *
     * @param boardI I coordinate to select
     * @param boardJ J coordinate to select
     */
    private void selectPiece(int boardI, int boardJ){
        System.out.println("Piece have selected!");
        isSelectedPiece = true;
        selectedPieceI = boardI;
        selectedPieceJ = boardJ;
        view.selectPiece(boardI,boardJ);
    }

    /**
     * Unselect selectedI selectedJ coordinate and delete selection in view.
     */
    private void unselectPiece(){
        System.out.println("Selecеeed piece is clear!");
        if(selectedPieceI != -1 && selectedPieceJ != -1) {
            view.selectPiece(selectedPieceI, selectedPieceJ);
            isSelectedPiece = false;
            selectedPieceI = -1;
            selectedPieceJ = -1;
        }
    }

    /**
     *  Get ArrayList of ArrayLists which represents board in format
     *  { { Color pieceColor, PieceType pieceType, boardI, boardJ } ...}
     * @return  ArrayList board representation
     */
    private ArrayList getBoardAsArrayList(){
        return board.getBoardAsArrayList();
    }

    /**
     * Prepare all for starrting game and start it, update board
     *
     * @param gameType type of started game
     */
    public void startGame(GameType gameType){
        this.gameType = gameType;
        moveHasDone = false;
        resetBoard();

        view.changeBoardView(getBoardAsArrayList());

    }

    /**
     * Save game in PGN format.
     */
    public void saveGameAsPGN(){
        pgnSaver.savePGN(player1,player2);
    }

    /**
     * Add move to PGN file.
     *
     * @param fromI piece I start coordinate
     * @param fromJ piece J start coordinate
     * @param toI piece I destination coordinate
     * @param toJ piece J destination coordinate
     */
    private void addMoveToPGN(int fromI, int fromJ, int toI, int toJ){
        pgnSaver.addMove(board.getSquareStatus(fromI,fromJ),board.getSquareStatus(toI,toJ));
    }

}
