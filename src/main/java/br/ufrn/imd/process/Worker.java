package br.ufrn.imd.process;

import br.ufrn.imd.model.*;
import br.ufrn.imd.utils.SharedFileManager;

import java.util.Objects;


/**
 * A classe Worker representa uma thread que executa tarefas.
 */
public class Worker extends Thread implements Job {


    private ResultQueue resultQueue;        // A fila de resultados produzidos através da execução das tarefas
    private Task task;                      // Tarefa atribuída ao Worker
    private volatile boolean isIdle;        // Flag que indica se o Worker está ocioso
    private volatile boolean stop;          // Flag que indica se o Worker deve parar
    private SharedFileManager sharedFile;   // Gerenciador de arquivo compartilhado

    /**
     * Construtor que inicializa um Worker com estado inicial ocioso.
     *
     * @param sharedFile   O gerenciador de arquivo compartilhado.
     * @param resultQueue  A fila de resultados instanciada no Executor para armazenar os resultados das tarefas executadas pelo Worker.
     */
    public Worker(SharedFileManager sharedFile, ResultQueue resultQueue) {
        this.isIdle = true;
        this.stop = false;
        this.sharedFile = sharedFile;
        this.resultQueue = resultQueue;
    }

    /**
     * O método run é chamado quando a thread Worker é iniciada.
     * Ele executa tarefas até que seja instruído a parar.
     */
    @Override
    public void run() {
        do{
            try {
                while (isIdle && !stop) {
                    synchronized (this) {
                        wait();
                    }
                }

                this.execute();
            }catch (InterruptedException e){
                System.err.println("Process error ...");
            }
        } while (!stop && isIdle);
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
            Thread.sleep((long) (task.getCost() * 1000));

            int value;
            if(task.getType().equals(Type.READING)){
                value = processRead();
            }else{
                value = processWrite();
            }

            long end = System.currentTimeMillis();

            resultQueue.add(new Result(task.getId(), value, (end - start)));
        }finally {
            isIdle = true;
        }
    }

    /**
     * Realiza o processamento de uma operação de escrita no arquivo compartilhado.
     *
     * @return O valor resultante da operação.
     */
    private int processWrite() {
        int value = processRead();
        synchronized (sharedFile){
            value += task.getValue();
            sharedFile.writeToFile(String.valueOf(value));
        }
        return value;
    }

    /**
     * Realiza o processamento de uma operação de leitura no arquivo compartilhado.
     *
     * @return O valor lido.
     */
    private int processRead() {
        int value = 0;
        synchronized (sharedFile){
            String content = sharedFile.readFromFile();
            if(Objects.nonNull(content) && !content.isEmpty()){
                value = Integer.parseInt(content);
            }
        }
        return value;
    }

    /**
     * Atribui uma nova tarefa ao Worker.
     *
     * @param task A tarefa a ser atribuída.
     */
    public void setTask(Task task) {
        this.task = task;
        isIdle = false;
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
    public void stopJob() {
        stop = true;
        synchronized (this){
            notify();
        }
    }
}
