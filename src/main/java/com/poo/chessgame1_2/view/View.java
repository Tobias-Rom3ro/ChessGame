package com.poo.chessgame1_2.view;

import com.poo.chessgame1_2.controller.Controller;
import com.poo.chessgame1_2.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public class View extends Application {
    private final int BOARD_SIZE = 8;
    private final int SQUARE_SIZE_PX = 50;

    // + 2 means add 2 squares of SQUARE_SIZE_PX for board coordinates
    private int windowSizeX = SQUARE_SIZE_PX * 16; //
    // + 2 means add 2 squares of SQUARE_SIZE_PX for board coordinates
    private int windowSizeY = SQUARE_SIZE_PX * (BOARD_SIZE + 2) + 27; //

    private static Stage stage = null;
    private static Scene menuScene = null;
    private static Scene boardScene = null;
    private static Controller ctrl = null;
    private static Model model = null;
    private static BoardPane boardPane = null;


    /**
     * Set controller to have access for Actions.
     */
    public void setController(Controller ctrl){
        View.ctrl = ctrl;
    }

    /**
     * Set model to read data from it.
     */
    public void setModel(Model model){
        View.model = model;
    }


    /**
     * Start GUI, set menu.
     */
    @Override
    public void start(Stage stage) {
        View.stage = stage;
        GUIinit();
        setMenuScene();
        stage.setTitle("Ajedrez");
        stage.setResizable(false);
        Image icon = new Image(getClass().getResourceAsStream("/icon.png"));
        stage.getIcons().add(icon);
        stage.show();
    }

    /**
     *  Initialise menu scene and board scene.
     */
    private void GUIinit(){
        initMenuScene();
        initBoardScene();
    }

    /**
     * Initialise menu scene.
     */
    private void initMenuScene(){
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource("/MenuScene.fxml"));
        try {
            menuScene = new Scene(fxmlLoader.load());
            AnchorPane root = (AnchorPane) menuScene.getRoot();
            addVideoBackground(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addVideoBackground(AnchorPane root) {
        String videoPath = getClass().getResource("/videoBackground.mp4").toExternalForm();

        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setAutoPlay(true);

        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.fitWidthProperty().bind(root.widthProperty());
        mediaView.fitHeightProperty().bind(root.heightProperty());
        mediaView.setPreserveRatio(false);

        root.getChildren().add(0, mediaView);
    }

    /**
     * Initialise board scene.
     */
    private void initBoardScene(){
        boardPane = new BoardPane(this,ctrl,BOARD_SIZE,SQUARE_SIZE_PX);
        boardScene = new Scene(boardPane,windowSizeX,windowSizeY);
    }

    /**
     * Show menu GUI.
     */
    public void setMenuScene(){
        stage.setScene(menuScene);
    }

    /**
     * Show board GUI.
     */
    public void setBoardScene(){
        stage.setScene(boardScene);
    }


    /**
     * Update board view.
     * @param list Arraylist which represents board all just changes in board
     *             in format{ { Color pieceColor, PieceType pieceType, boardI, boardJ } ...}
     */
    public void changeBoardView(ArrayList list){
        boardPane.changeBoardViewByList(list);
    }

    /**
     * Paint selected piece.
     * @param boardI I coordinate of selected square.
     * @param boardJ J coordinate of selected square.
     */
    public void selectPiece(int boardI, int boardJ){
        boardPane.paintSelected(boardI,boardJ);
    }

    /**
     * Set dialog to ask player name.
     *
     * @return player name
     */
    public String getPlayerName(){
        TextInputDialog dialog = new TextInputDialog("Nombre");
        dialog.setTitle("Nombre del jugador");
        dialog.setHeaderText("Introduce tu nombre");

        Optional<String> result = dialog.showAndWait();

        if(result.isEmpty()){
            return "Jugador desconocido";
        }

        return  result.get();
    }

    /**
     * Set players names to board GUI.
     * @param name1 name of player1
     * @param name2 name of player2
     */
    public void setPlayersNames(String name1, String name2){
        boardPane.setPlayersNames(name1,name2);
    }

    /**
     * Show Game over GUI.
     * @param winnerName name of winner player.
     */
    public void gameOver(String winnerName){
        Dialog<Object> dialog = new Dialog<>();
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("Fin del juego!");
        dialog.setHeaderText(winnerName + " ganó!");


        ButtonType newGameButton = new ButtonType("Ir al menú");
        ButtonType SavePGNButton = new ButtonType("Guardar partida (PGN)");
        ButtonType ExitButton  = new ButtonType("Salir");

        dialog.getDialogPane().getButtonTypes().addAll(newGameButton,SavePGNButton,ExitButton);
        Optional<Object> result = dialog.showAndWait();
        if(result.get() == newGameButton){
            setMenuScene();
        }
        if(result.get() == SavePGNButton){
            ctrl.saveGamePGNAction();
            setMenuScene();
        }
        if(result.get() == ExitButton){
            ctrl.exitAction();
        }

    }

}
