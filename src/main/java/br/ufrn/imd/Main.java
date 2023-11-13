package br.ufrn.imd;

import br.ufrn.imd.model.TaskQueue;
import br.ufrn.imd.process.Executor;

import java.io.FileNotFoundException;

/**
 * A classe `Main` é a classe principal que inicia a execução do programa.
 */
public class Main {

    /**
     * O método principal que inicia a execução do programa.
     *
     * @param args Os argumentos de linha de comando: [N, E, T], onde 10^N é o tamanho da fila de tarefas,
     *             E é o fator probabilístico de uma tarefa ser de escrita e T é o número de threads trabalhadoras.
     * @throws FileNotFoundException Se o arquivo de entrada não puder ser encontrado durante o carregamento inicial de tarefas.
     */
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        int N = 0, T = 0, E = 0;

        try {
            N = Integer.parseInt(args[0]);
            E = Integer.parseInt(args[1]);
            T = Integer.parseInt(args[2]);
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Argument must be an integer.");
            System.exit(1);
        }

        TaskQueue taskQueue = new TaskQueue(N);
        taskQueue.load(E);

        Executor executor = new Executor(T, taskQueue);
        Thread thread = new Thread(executor, "Executor");
        thread.start();
        thread.join();

        long totalTime = executor.getResultQueue().getTotalExecutionTime();

        System.out.println("---------------------------------------------------------------------------");
        System.out.printf(">> Para N = %d, E = %d e T = %d o tempo total de processamento foi %d ms. \n", N, E, T, totalTime);
        System.out.println("---------------------------------------------------------------------------");
    }
}
