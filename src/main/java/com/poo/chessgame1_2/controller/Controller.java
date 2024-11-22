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

/**
 * Clase que actúa como controlador principal para gestionar la lógica del juego de ajedrez.
 * Se encarga de coordinar la comunicación entre el modelo, la vista y las acciones del usuario.
 */
public class Controller {
    static View view = null;
    static Model model = null;

    /**
     * Inicia el juego configurando la vista, el modelo y lanzando la interfaz gráfica.
     * Este método configura la vista y el modelo, y establece la comunicación entre ambos
     * antes de mostrar la interfaz gráfica al usuario.
     */
    public void startGame(){
        view = new View();
        model = new Model();

        view.setController(this);
        view.setModel(model);
        model.setView(view);
        GUIStart();
    }

    /**
     * Inicia la interfaz gráfica de usuario.
     * Llama al método para mostrar la vista inicial.
     */
    private void GUIStart(){
        View.launch(view.getClass());
    }

    /**
     * Configura los nombres de los jugadores en la vista.
     * Utiliza los nombres de los jugadores desde el modelo y los asigna a la vista.
     */
    private void setPlayersNameView(){
        view.setPlayersNames(model.getPlayer1Name(), model.getPlayer2Name());
    }

    /**
     * Inicia una nueva partida multijugador.
     * Obtiene los nombres de los jugadores desde la vista, los establece en el modelo,
     * y comienza un nuevo juego multijugador.
     * Luego configura la escena del tablero y muestra los nombres de los jugadores.
     */
    public void newMultiplayerGameAction(){
        model.setPlayers(view.getPlayerName(), view.getPlayerName());
        model.startGame(GameType.MULTIPLAYER);

        view.setBoardScene();
        setPlayersNameView();
    }

    /**
     * Finaliza el juego y cierra la aplicación.
     * Llama al método {@link System#exit(int)} para salir de la aplicación con el código 0.
     */
    public void exitAction(){
        exit(0);
    }

    /**
     * Maneja el clic en un cuadrado del tablero.
     * Recibe las coordenadas del cuadrado clickeado, y pasa la información al modelo
     * para que se realice la acción correspondiente.
     *
     * @param indexX Índice en el eje X del cuadrado clickeado.
     * @param indexY Índice en el eje Y del cuadrado clickeado.
     */
    public void boardSquareWasClicked(int indexX, int indexY){
        System.out.println(indexX + " " + indexY);
        model.squareWasClicked(indexX, indexY);
    }

    /**
     * Guarda el estado actual del juego en un archivo PGN.
     * Llama al método {@link Model#saveGameAsPGN()} para guardar el juego en formato PGN.
     */
    public void saveGamePGNAction() {
        model.saveGameAsPGN();
    }

    /**
     * Confirma la acción del jugador, realizando el siguiente movimiento en el juego.
     * Llama al método {@link Model#nextMove()} para avanzar al siguiente movimiento.
     */
    public void confirmButtonAction(){
        model.nextMove();
    }

    /**
     * Muestra la escena del menú principal.
     * Cambia la vista para mostrar el menú principal sin guardar el juego.
     */
    public void goToMenu(){
        view.setMenuScene();
    }

    /**
     * Abre el archivo JAR de la aplicación en una nueva instancia de Java.
     * Llama al JAR de la aplicación usando el comando `java -jar` y redirige la salida de la consola.
     *
     * @throws IOException Si ocurre un error al intentar ejecutar el JAR.
     * @throws URISyntaxException Si ocurre un error al obtener la URI del archivo JAR.
     */
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
