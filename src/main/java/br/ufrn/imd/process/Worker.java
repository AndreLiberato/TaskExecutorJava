package br.ufrn.imd.process;

import br.ufrn.imd.model.*;
import br.ufrn.imd.utils.SharedFileManager;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * A classe Worker representa uma thread que executa tarefas.
 */
public class Worker extends Thread implements Job {


    private ResultQueue resultQueue;        // A fila de resultados produzidos através da execução das tarefas
    private Task task;                      // Tarefa atribuída ao Worker
    private volatile boolean isIdle;        // Flag que indica se o Worker está ocioso
    private volatile boolean stop;          // Flag que indica se o Worker deve parar
    private SharedFileManager sharedFile;   // Gerenciador de arquivo compartilhado
    private Semaphore semaphore;            // Semáforo para o controle do Executor.

    /**
     * Construtor que inicializa um Worker com estado inicial ocioso.
     *
     * @param sharedFile   O gerenciador de arquivo compartilhado.
     * @param resultQueue  A fila de resultados instanciada no Executor para armazenar os resultados das tarefas executadas pelo Worker.
     * @param semaphore    O semáforo para controle do Executor.
     */
    public Worker(SharedFileManager sharedFile, ResultQueue resultQueue, Semaphore semaphore) {
        this.isIdle = true;
        this.stop = false;
        this.sharedFile = sharedFile;
        this.resultQueue = resultQueue;
        this.semaphore = semaphore;
    }

    /**
     * O método run é chamado quando a thread Worker é iniciada.
     * Ele executa tarefas até que seja instruído a parar.
     */
    @Override
    public void run() {
        Lock lock = new ReentrantLock();
        while (!this.stop) {
            try {
                lock.lock();
                if(task != null) this.execute();
            } catch (InterruptedException e) {
                System.err.println("Process error ...");
            }finally {
                lock.unlock();
            }
        }
    }


    /**
     * Implementa um Job para execução da tarefa atribuída, aguardando o tempo especificado em getCost().
     *
     * @throws InterruptedException Se a thread for interrompida durante o processamento.
     */
    @Override
    public void execute() throws InterruptedException {
        try {
            long start = System.currentTimeMillis();

            sleep(0L, (int) (task.cost() * 1_000_000));

            int value;
            if(task.type().equals(Type.READING)){
                value = processRead();
            }else{
                value = processWrite();
            }

            long end = System.currentTimeMillis();

            this.resultQueue.add(new Result(task.id(), value, (end - start)));
        }finally {
            this.isIdle = true;
            this.semaphore.release();
        }
    }

    /**
     * Realiza o processamento de uma operação de escrita no arquivo compartilhado.
     *
     * @return O valor resultante da operação.
     */
    private int processWrite() {
        int value = processRead();
        value += task.value();

        sharedFile.writeToFile(value);

        return value;
    }

    /**
     * Realiza o processamento de uma operação de leitura no arquivo compartilhado.
     *
     * @return O valor lido.
     */
    private int processRead() {
        return Integer.parseInt(sharedFile.readFromFile());
    }

    /**
     * Atribui uma nova tarefa ao Worker.
     *
     * @param task A tarefa a ser atribuída.
     */
    public void setTask(Task task) {
        this.task = task;
        this.isIdle = false;
    }

    /**
     * Retorna o estado de ociosidade do o Worker.
     *
     * @return true se o Worker estiver ocioso, false caso contrário.
     */
    public boolean isIdle() {
        return isIdle;
    }

    /**
     * Instrui o Worker a parar quando não há mais tarefas para serem executadas.
     */
    public void pause() {
        this.stop = true;
    }

}
