package br.ufrn.imd.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A classe SharedFileManager é responsável por gerenciar operações de leitura e escrita em um arquivo compartilhado.
 */
public class SharedFileManager {

    private File file; // Arquivo compartilhado

    private ReadWriteLock lock;

    // Constantes para o caminho e nome do arquivo
    private final String RESOURCES_PATH = "src/main/resources/";
    private final String FILE_NAME = "arquivo.txt";

    /**
     * Construtor padrão que inicializa o arquivo compartilhado.
     *
     * @throws FileNotFoundException Se o arquivo compartilhado não for encontrado.
     */
    public SharedFileManager() throws FileNotFoundException {
        this.lock = new ReentrantReadWriteLock();
        initFile();
    }


    /**
     * Escreve o conteúdo especificado no arquivo compartilhado.
     *
     * @param value O conteúdo a ser escrito no arquivo.
     */
    public void writeToFile(int value) {
        lock.writeLock().lock();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(String.valueOf(value));
        } catch (IOException e) {
            System.err.println("Erro de escrita no arquivo: " + e.getMessage());
        }finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Lê o conteúdo do arquivo compartilhado.
     *
     * @return O conteúdo lido do arquivo como uma string.
     */
    public String readFromFile() {
        lock.readLock().lock();
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Erro na leitura do arquivo: " + e.getMessage());
        }finally {
            lock.readLock().unlock();
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
