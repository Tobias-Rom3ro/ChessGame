package com.poo.chessgame1_2.model.utils;

import com.poo.chessgame1_2.model.players.Player;
import com.poo.chessgame1_2.model.pieces.PieceType;
import javafx.stage.FileChooser;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Clase encargada de guardar una partida de ajedrez en formato PGN.
 * Permite agregar los movimientos de las piezas, generar un archivo PGN
 * y guardar los detalles de la partida (jugadores, movimientos, etc.).
 */
public class PGNSaver extends FilesIO {
    private final String PGN_DIRECTORY_PATH = "/Saved PGN games/";
    private ArrayList<String> moves = new ArrayList<>();

    /**
     * Constructor que inicializa la creación del directorio para guardar archivos PGN.
     */
    public PGNSaver() {
        createDirectory(System.getProperty("user.dir") + PGN_DIRECTORY_PATH);
    }

    /**
     * Añade un movimiento a la lista de movimientos guardados en formato PGN.
     *
     * @param from Estado de la casilla de inicio del movimiento, formato
     *             {Color pieceColor, PieceType pieceType, int boardI, int boardJ}.
     * @param to   Estado de la casilla de destino del movimiento, formato
     *             {Color pieceColor, PieceType pieceType, int boardI, int boardJ}.
     */
    public void addMove(ArrayList<Object> from, ArrayList<Object> to) {
        PieceType fromPieceType = (PieceType) from.get(1);
        PieceType toPieceType = (PieceType) to.get(1);

        int fromI = (int) from.get(2);
        int fromJ = (int) from.get(3);
        int toI = (int) to.get(2);
        int toJ = (int) to.get(3);
        boolean isCapture = toPieceType != PieceType.EMPTY;

        saveMoveAsString(fromPieceType, fromI, fromJ, toI, toJ, isCapture);
    }

    /**
     * Guarda el movimiento como una cadena de texto en la lista de movimientos.
     *
     * @param pieceType  Tipo de pieza que se mueve.
     * @param fromI      Coordenada I de la casilla de inicio.
     * @param fromJ      Coordenada J de la casilla de inicio.
     * @param toI        Coordenada I de la casilla de destino.
     * @param toJ        Coordenada J de la casilla de destino.
     * @param isCapture  Indica si el movimiento es una captura de pieza.
     */
    private void saveMoveAsString(PieceType pieceType, int fromI, int fromJ, int toI, int toJ, boolean isCapture) {
        // Datos a imprimir
        String pieceTypeString = "B";
        char toIChar = (char) ('a' + toI);
        char fromIChar = (char) ('a' + fromI);
        toJ++; // La coordenada J comienza desde 1
        fromJ++;

        switch (pieceType) {
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
        if (!isCapture) {
            moves.add(pieceTypeString + toIChar + toJ);
        } else {
            moves.add(fromIChar + "x" + pieceTypeString + toIChar + toJ);
        }
    }

    /**
     * Imprime las etiquetas de la partida (evento, sitio, fecha y jugadores).
     *
     * @param player1 Jugador 1, instancia de la clase Player.
     * @param player2 Jugador 2, instancia de la clase Player.
     */
    private void printTags(Player player1, Player player2) {
        printWriter.println("[Event \"Partida de prueba :) \"]");
        printWriter.println("[Site \"Santa Marta, Colombia\"]");
        printWriter.println("[Date \"" + new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()) + "\"]");
        printWriter.println("[" + player1.getPlayerColorAsString() + " \"" + player1.getPlayerName() + "\"]");
        printWriter.println("[" + player2.getPlayerColorAsString() + " \"" + player2.getPlayerName() + "\"]");
    }

    /**
     * Imprime todos los movimientos guardados en formato PGN.
     */
    private void printMovesData() {
        int counter = 1;
        int lineSize = 0;
        int maxLineSize = 255;
        for (int i = 0; i < moves.size(); i++) {
            // Imprimir número de movimiento
            if (i % 2 == 0) {
                printWriter.print(counter + ". ");
                counter++;
                lineSize += 3;
            }

            printWriter.print(moves.get(i));
            printWriter.print(" ");

            lineSize += moves.get(i).length();

            if (lineSize >= maxLineSize) {
                printWriter.print("\n");
                lineSize = 0;
            }
        }
    }

    /**
     * Guarda todos los datos de la partida en un archivo PGN.
     *
     * @param player1 Jugador 1, instancia de la clase Player.
     * @param player2 Jugador 2, instancia de la clase Player.
     */
    public void savePGN(Player player1, Player player2) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar partida PGN");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PGN Files", "*.pgn"));

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            setPrintStreamForUser(file.getAbsolutePath());

            printTags(player1, player2);
            printMovesData();

            closePrintStream();
        }
    }
}
