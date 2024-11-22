package com.poo.chessgame1_2.controller;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Clase que representa la barra de menú de la aplicación de ajedrez.
 * Extiende {@link MenuBar} y gestiona la creación y funcionalidad de los menús de opciones y salida.
 * Los menús permiten realizar acciones como iniciar una nueva partida, guardar el juego en formato PGN,
 * o salir de la aplicación sin guardar los cambios.
 */
public class ChessMenuBar extends MenuBar {
    private final Controller ctrl;

    /**
     * Constructor que crea una instancia de {@link ChessMenuBar} y establece el controlador.
     *
     * @param ctrl El controlador que maneja las acciones asociadas a los elementos del menú.
     */
    public ChessMenuBar(Controller ctrl){
        this.ctrl = ctrl;
        initMenuBar();
    }

    /**
     * Inicializa la barra de menú, creando los menús "Opciones" y "Salir" con sus respectivos elementos.
     * Configura las acciones que se ejecutan cuando se seleccionan los elementos del menú.
     */
    private void initMenuBar(){
        // Menú de opciones
        Menu gameMenu = new Menu("Opciones");

        // Menú de salida
        Menu exitMenu = new Menu("Salir");

        // Elementos del menú de opciones
        MenuItem newMultiplayerGame = new MenuItem("Iniciar nueva partida");
        newMultiplayerGame.setOnAction(e -> ctrl.newMultiplayerGameAction());

        MenuItem saveGameAsPGN = new MenuItem("Guardar juego (PGN)");
        saveGameAsPGN.setOnAction(e -> ctrl.saveGamePGNAction());

        // Elementos del menú de salida
        MenuItem goToMenuWithoutSaving = new MenuItem("Ir al menú sin guardar");
        goToMenuWithoutSaving.setOnAction(e -> ctrl.goToMenu());

        MenuItem exitWithoutSaving = new MenuItem("Salir sin guardar");
        exitWithoutSaving.setOnAction(e -> ctrl.exitAction());

        // Añade los elementos a los menús correspondientes
        gameMenu.getItems().addAll(newMultiplayerGame, saveGameAsPGN);
        exitMenu.getItems().addAll(goToMenuWithoutSaving, exitWithoutSaving);

        // Añade los menús a la barra de menú
        this.getMenus().addAll(gameMenu, exitMenu);
    }
}
