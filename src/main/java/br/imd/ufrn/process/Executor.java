package br.imd.ufrn.process;

import br.imd.ufrn.model.ResultQueue;
import br.imd.ufrn.model.Task;
import br.imd.ufrn.model.TaskQueue;

import java.util.Arrays;
import java.util.Random;

/**
 * O Executor é responsável por coordenar a execução de tarefas entre várias threads Worker.
 */
public class Executor implements Runnable {

    private TaskQueue taskQueue;
    private ResultQueue resultQueue;
    private Worker[] workers;

    /**
     * Construtor para criar um Executor com um número específico de threads Worker.
     *
     * @param T             Número de threads Worker a serem criadas.
     * @param taskQueue     A fila de tarefas a serem executadas pelas threads Worker.
     */
    public Executor(int T, TaskQueue taskQueue) {
        this.resultQueue = new ResultQueue();
        this.taskQueue = taskQueue;
        this.workers = new Worker[T];

        for (int i = 0; i < T; i++) {
            this.workers[i] = new Worker();
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

            assignTaskToWorker(idleWorker, task);
        }

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

}

