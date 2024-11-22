package com.poo.chessgame1_2.model.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Clase que maneja las operaciones de entrada y salida de archivos, como leer y escribir en archivos.
 * Permite trabajar con archivos dentro de la carpeta de recursos del programa o fuera de ella.
 */
public class FilesIO {

    PrintWriter printWriter;
    Scanner scanner;

    private File file;

    /**
     * Obtiene un archivo desde la carpeta de recursos.
     *
     * @param fileName Ruta del archivo a obtener.
     * @return El archivo si se abre con éxito, null si ocurre un error.
     * @throws NullPointerException si el archivo no está disponible.
     */
    private File getFileFromResource(String fileName) throws NullPointerException {
        try {
            String filePath = getClass().getResource(fileName).getFile();
            file = new File(filePath);
            return file;
        } catch (NullPointerException e) {
            System.out.println("¡La anotación del tablero no está disponible!");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Obtiene un archivo desde una ruta proporcionada.
     *
     * @param fileName Ruta del archivo a obtener.
     * @return El archivo si se abre con éxito, null si ocurre un error.
     * @throws NullPointerException si el archivo no está disponible.
     */
    private File getFile(String fileName) throws NullPointerException {
        try {
            file = new File(fileName);
            return file;
        } catch (NullPointerException e) {
            System.out.println("¡Error al guardar el archivo!");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Configura el flujo de salida para un archivo ubicado en los recursos del programa.
     *
     * @param filePath Ruta del archivo para el flujo de salida.
     */
    void setPrintStream(String filePath) {
        file = getFileFromResource(filePath);
        try {
            assert this.file != null;
            printWriter = new PrintWriter(this.file);
        } catch (FileNotFoundException e) {
            System.out.println("¡Error al abrir el archivo " + filePath + " para escribir!");
            e.printStackTrace();
        }
    }

    /**
     * Configura el flujo de salida para un archivo ubicado en la carpeta del programa.
     *
     * @param filePath Ruta del archivo para el flujo de salida.
     */
    void setPrintStreamForUser(String filePath) {
        File file = getFile(filePath);
        createFile(filePath);

        try {
            assert file != null;
            printWriter = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            System.out.println("¡Error al abrir el archivo " + filePath + " para escribir!");
            e.printStackTrace();
        }
    }

    /**
     * Cierra el flujo de salida.
     */
    void closePrintStream() {
        printWriter.close();
        printWriter = null;
    }

    /**
     * Configura el flujo de entrada para un archivo ubicado en los recursos del programa.
     *
     * @param filePath Ruta del archivo para el flujo de entrada.
     */
    void setScanner(String filePath) {
        File file = getFileFromResource(filePath);
        try {
            assert file != null;
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("¡Error al configurar el flujo de entrada!");
            e.printStackTrace();
        }
    }

    /**
     * Crea un nuevo archivo si no existe.
     *
     * @param filePath Ruta del archivo a crear.
     */
    private void createFile(String filePath) {
        File file = getFile(filePath);
        try {
            assert file != null;
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("¡Error al crear el archivo!");
            e.printStackTrace();
        }
    }

    /**
     * Crea un directorio en la ruta especificada.
     *
     * @param directoryPath Ruta del directorio a crear.
     */
    void createDirectory(String directoryPath) {
        new File(directoryPath).mkdirs();
    }
}
