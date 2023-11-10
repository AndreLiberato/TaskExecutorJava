package br.imd.ufrn;

import br.imd.ufrn.utils.SharedFileManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Teste {

    static final String RESOURCES_PATH = "src/main/resources/";
    static final String FILE_NAME = "arquivo.txt";

    public static void main(String[] args) {
        Path path = Paths.get(System.getProperty("user.dir")).resolve(RESOURCES_PATH);

        if(Files.exists(path.resolve(FILE_NAME))){
            SharedFileManager manager = new SharedFileManager(path, FILE_NAME);

            String saida = manager.readFromFile();
            System.out.println("Saída: "+saida.trim());
        }else {
            System.out.println("não existe");
        }

    }
}
