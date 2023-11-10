package br.imd.ufrn.utils;

import java.io.*;
import java.nio.file.Path;

public class SharedFileManager {

    private File file;

    public SharedFileManager(Path path, String  name) {
        this.file = new File(path.resolve(name).toString());
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
                content.append(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }

        return content.toString();
    }
}
