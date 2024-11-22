package com.poo.chessgame1_2.controller;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class ChessMenuBar extends MenuBar {
    private final Controller ctrl;

    public ChessMenuBar(Controller ctrl){
        this.ctrl = ctrl;
        initMenuBar();

    }

    private void initMenuBar(){
        Menu gameMenu = new Menu("Opciones");
        Menu exitMenu = new Menu("Salir");

        MenuItem newMultiplayerGame = new MenuItem("Iniciar nueva partida");
        newMultiplayerGame.setOnAction(e -> ctrl.newMultiplayerGameAction());

        MenuItem saveGameAsPGN = new MenuItem("Guardar juego (PGN)");
        saveGameAsPGN.setOnAction(e -> ctrl.saveGamePGNAction());

        MenuItem goToMenuWithoutSaving = new MenuItem("Ir al menú sin guardar");
        goToMenuWithoutSaving.setOnAction(e -> ctrl.goToMenu());

        MenuItem exitWithoutSaving = new MenuItem("Salir sin guardar");
        exitWithoutSaving.setOnAction(e -> ctrl.exitAction());


        gameMenu.getItems().addAll(newMultiplayerGame,saveGameAsPGN);
        exitMenu.getItems().addAll(goToMenuWithoutSaving,exitWithoutSaving);

        this.getMenus().addAll(gameMenu,exitMenu);

    }

}

