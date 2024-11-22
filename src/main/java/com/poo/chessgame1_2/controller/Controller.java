package com.poo.chessgame1_2.controller;

import com.poo.chessgame1_2.model.GameType;
import com.poo.chessgame1_2.model.Model;
import com.poo.chessgame1_2.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import static java.lang.System.exit;

public class Controller {
    static View view = null;
    static Model model = null;

    public void startGame(){
        view = new View();
        model = new Model();

        view.setController(this);
        view.setModel(model);
        model.setView(view);
        GUIStart();

    }

    private void GUIStart(){
        View.launch(view.getClass());
    }

    private void setPlayersNameView(){
        view.setPlayersNames(model.getPlayer1Name(),model.getPlayer2Name());
    }

    public void newMultiplayerGameAction(){
        model.setPlayers(view.getPlayerName(),view.getPlayerName());
        model.startGame(GameType.MULTIPLAYER);

        view.setBoardScene();
        setPlayersNameView();
    }

    public void exitAction(){
        exit(0);
    }


    public void boardSquareWasClicked(int indexX, int indexY){
        System.out.println(indexX + " " + indexY);
        model.squareWasClicked(indexX, indexY);
    }

    public void saveGamePGNAction() {
        model.saveGameAsPGN();
    }

    public void timerButtonAction(){
        model.nextMove();
    }

    public void goToMenu(){
        view.setMenuScene();
    }

    public void openJarFile() throws IOException, URISyntaxException {
        File jarFile = new File(getClass().getResource("/Ajedrez2.0.jar").toURI());
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", jarFile.getAbsolutePath());
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
