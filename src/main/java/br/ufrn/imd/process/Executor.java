package br.ufrn.imd.process;

import br.ufrn.imd.model.ResultQueue;
import br.ufrn.imd.model.Task;
import br.ufrn.imd.model.TaskQueue;
import br.ufrn.imd.utils.SharedFileManager;

import java.io.FileNotFoundException;
import java.util.concurrent.Semaphore;

/**
 * O Executor é responsável por coordenar a execução de tarefas entre várias threads Worker.
 */
public class Executor implements Runnable {

    private TaskQueue taskQueue;        // Fila de tarefas a serem executadas pelas threads Worker.
    private ResultQueue resultQueue;    // Fila de resultados produzidos pelas threads Worker.
    private Worker[] workers;           // Array de threads Worker.
    private Semaphore semaphore;        // Semáforo para controlar a atribuição de tarefas aos Workers.

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

        this.semaphore = new Semaphore(T); // Inicializa o Semaphore com um número de permissões igual ao número de threads Worker.

        SharedFileManager sharedFile = new SharedFileManager();
        for (int i = 0; i < T; i++) {
            this.workers[i] = new Worker(sharedFile, resultQueue, semaphore);
            workers[i].setName("Worker-"+i);
            workers[i].start();
        }
    }

    /**
     * Método responsável por coordenar a execução de tarefas entre as threads Worker.
     *
     * Ele adquire permissões do semáforo para controlar a atribuição de tarefas aos Workers. Enquanto houver tarefas na fila,
     * identifica um Worker ocioso, atribui uma tarefa e inicia sua execução. Ao finalizar as tarefas, encerra as threads Worker.
     */
    @Override
    public void run() {
        System.out.println("Processando ...");

        while (!taskQueue.getTasks().isEmpty()) {
            try {
                semaphore.acquire();
                Worker idleWorker = getIdleWorker();

                if (idleWorker != null) {
                    Task task = taskQueue.getNextTask();
                    assignTaskToWorker(idleWorker, task);
                }else{
                    semaphore.release();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

        try {
            stopWorkers();
        } catch (InterruptedException e) {
            System.err.println("Erro no encerramento dos workers: "+e.getMessage());
        }

        System.out.println("Processamento finalizado!");
    }

    /**
     * Encerra a execução das threads Worker.
     *
     * @throws InterruptedException Se ocorrer um erro durante a interrupção das threads.
     */
    private void stopWorkers() throws InterruptedException {
        for (Worker worker : workers) {
            worker.pause();
            worker.join();
        }
    }

    /**
     * Encontra um Worker ocioso entre as threads Worker disponíveis.
     *
     * @return Um Worker ocioso, ou null se nenhum estiver disponível.
     */
    private Worker getIdleWorker() {
        for(Worker worker : workers){
            if(worker.isIdle()){
                return worker;
            }
        }
        return null;
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

