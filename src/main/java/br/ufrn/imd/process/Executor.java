package br.ufrn.imd.process;

import br.ufrn.imd.model.ResultQueue;
import br.ufrn.imd.model.Task;
import br.ufrn.imd.model.TaskQueue;
import br.ufrn.imd.utils.SharedFileManager;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;

/**
 * O Executor é responsável por coordenar a execução de tarefas entre várias threads Worker.
 */
public class Executor implements Runnable {

    private TaskQueue taskQueue;        // Fila de tarefas a serem executadas pelas threads Worker.
    private ResultQueue resultQueue;    // Fila de resultados produzidos pelas threads Worker.
    private Worker[] workers;           // Array de threads Worker.

    /**
     * Construtor para criar um Executor com um número específico de threads Worker.
     *
     * @param T             Número de threads Worker a serem criadas.
     * @param taskQueue     A fila de tarefas a serem executadas pelas threads Worker.
     * @throws FileNotFoundException Se o arquivo compartilhado não for encontrado.
     */
    public Executor(int T, TaskQueue taskQueue) throws FileNotFoundException {
        this.resultQueue = new ResultQueue();
        this.taskQueue = taskQueue;
        this.workers = new Worker[T];

        SharedFileManager sharedFile = new SharedFileManager();
        for (int i = 0; i < T; i++) {
            this.workers[i] = new Worker(sharedFile, resultQueue);
            workers[i].setName("Worker-"+i);
            workers[i].start();
        }
    }

    /**
     * O método run é chamado quando o Executor é executado como uma thread.
     * Ele coordena a execução de tarefas entre as threads Worker.
     */
    @Override
    public void run() {
        while (!taskQueue.getTasks().isEmpty()) {
            Task task = taskQueue.getNextTask();
            Worker idleWorker = getIdleWorker();

            while (idleWorker == null){
                try {
                    waitUntilWorkerAvailable();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }finally {
                    idleWorker = getIdleWorker();
                }
            }

            assignTaskToWorker(idleWorker, task); // Atribui a tarefa ao Worker disponível
        }

        // Encerra as threads Worker após a conclusão das tarefas
        for (Worker worker : workers) {
            worker.stopJob();
        }
    }

    /**
     * Aguarda um tempo aleatório de 1 a 1000 milissegundos até que um Worker esteja disponível.
     *
     * @throws InterruptedException Se a espera for interrompida.
     */
    private void waitUntilWorkerAvailable() throws InterruptedException {
        synchronized (this){
            int randomMsTime = new Random().nextInt(1000) + 1;
            wait(randomMsTime);
        }
    }

    /**
     * Encontra um Worker ocioso entre as threads Worker disponíveis.
     *
     * @return Um Worker ocioso, ou null se nenhum estiver disponível.
     */
    private Worker getIdleWorker() {
        return Arrays.stream(workers).filter(Worker::isIdle).findFirst().orElse(null);
    }

    /**
     * Atribui uma tarefa a um Worker específico.
     *
     * @param idleWorker O Worker ocioso ao qual a tarefa será atribuída.
     * @param task       A tarefa a ser atribuída.
     */
    private void assignTaskToWorker(Worker idleWorker, Task task){
        synchronized (idleWorker){
            idleWorker.setTask(task);
            idleWorker.notify();
        }
    }

    public ResultQueue getResultQueue() {
        return resultQueue;
    }
}

