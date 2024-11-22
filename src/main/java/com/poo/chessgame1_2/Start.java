package com.poo.chessgame1_2;

import com.poo.chessgame1_2.controller.Controller;

/**
 * Clase principal que inicia la aplicación de ajedrez.
 *
 * La clase `Start` es el punto de entrada de la aplicación. Al ejecutar el programa,
 * crea una instancia del controlador y lanza el juego a través del método `startGame()`.
 *
 * Esta clase no contiene lógica compleja, ya que su único propósito es inicializar el
 * controlador que gestionará el flujo del juego y las interacciones con el modelo y la vista.
 */
public class Start {

    /**
     * Método principal que inicia la aplicación de ajedrez.
     * Crea una instancia del controlador y comienza el juego.
     *
     * @param args Argumentos de línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        Controller ctrl = new Controller();
        ctrl.startGame();
    }
}
