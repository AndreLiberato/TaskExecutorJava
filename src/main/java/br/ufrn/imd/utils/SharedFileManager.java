package br.ufrn.imd.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A classe SharedFileManager é responsável por gerenciar operações de leitura e escrita em um arquivo compartilhado.
 */
public class SharedFileManager {

    private File file; // Arquivo compartilhado

    // Constantes para o caminho e nome do arquivo
    private final String RESOURCES_PATH = "src/main/resources/";
    private final String FILE_NAME = "arquivo.txt";

    /**
     * Construtor padrão que inicializa o arquivo compartilhado.
     *
     * @throws FileNotFoundException Se o arquivo compartilhado não for encontrado.
     */
    public SharedFileManager() throws FileNotFoundException {
        initFile();
    }

    /**
     * Escreve o conteúdo especificado no arquivo compartilhado.
     *
     * @param content O conteúdo a ser escrito no arquivo.
     */
    public synchronized void writeToFile(String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(content);
        } catch (IOException e) {
            System.err.println("Erro de escrita no arquivo: " + e.getMessage());
        }
    }

    /**
     * Lê o conteúdo do arquivo compartilhado.
     *
     * @return O conteúdo lido do arquivo como uma string.
     */
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

    /**
     * Inicializa o arquivo compartilhado com base no diretório atual do usuário e verifica a existência do arquivo.
     *
     * @throws FileNotFoundException Se o arquivo compartilhado não for encontrado.
     */
    private void initFile() throws FileNotFoundException {
        Path basePath = Paths.get(System.getProperty("user.dir"));
        Path path = basePath.resolve(RESOURCES_PATH);

        if (Files.exists(path.resolve(FILE_NAME))) {
            this.file = new File(path.resolve(FILE_NAME).toString());
        } else {
            throw new FileNotFoundException("Não foi possível localizar o arquivo compartilhado.");
        }
    }
}
