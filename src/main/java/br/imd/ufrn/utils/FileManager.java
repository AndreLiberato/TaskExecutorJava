package br.imd.ufrn.utils;

import java.io.*;

public class FileManager {

    private File file;

    public FileManager(File file) {
        this.file = file;
    }

    public synchronized void writeToFile(String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(content);
        } catch (IOException e) {
            System.err.println("Erro de escrita no arquivo: " + e.getMessage());
        }
    }

    public synchronized String readFromFile() {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }

        return content.toString();
    }
}
