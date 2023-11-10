package br.imd.ufrn.process;

import br.imd.ufrn.model.Job;
import br.imd.ufrn.model.Result;
import br.imd.ufrn.model.Task;
import br.imd.ufrn.model.Type;
import br.imd.ufrn.utils.SharedFileManager;

import java.util.Objects;


/**
 * A classe Worker representa uma thread que executa tarefas.
 */
public class Worker extends Thread implements Job {

    /**
     * Tarefa atribuída ao Worker
     */
    private Task task;

    /**
     * Flag que indica se o Worker está ocioso
     */
    private volatile boolean isIdle;

    /**
     * Flag que indica se o Worker deve parar
     */
    private volatile boolean stop;

    /**
     * Gerenciador de arquivos compartilhados
     */
    private SharedFileManager sharedFile;

    /**
     * Construtor que inicializa um Worker com estado inicial ocioso.
     */
    public Worker(SharedFileManager sharedFile) {
        this.isIdle = true;
        this.stop = false;
        this.sharedFile = sharedFile;
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

        System.out.println(this.getName() + " finalizado ...");
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

            System.out.println("Executando "+task.toString()+" ...");

            int value;
            if(task.getType().equals(Type.READING)){
                value = processRead();
            }else{
                value = processWrite();
            }

            long end = System.currentTimeMillis();
            //new Result(task.getId(), value, (end - start));
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
        System.out.println("Escrevendo ...");

        int value = processRead();
        synchronized (sharedFile){
            value += task.getValue();
            sharedFile.writeToFile(String.valueOf(value));
        }

        System.out.println("Valor após escrita: "+value);
        return value;
    }

    /**
     * Realiza o processamento de uma operação de leitura no arquivo compartilhado.
     *
     * @return O valor lido.
     */
    private int processRead() {
        System.out.println("Lendo ...");

        int value = 0;
        synchronized (sharedFile){
            String content = sharedFile.readFromFile();
            if(Objects.nonNull(content) && !content.isEmpty()){
                value = Integer.parseInt(content);
            }
        }

        System.out.println("Valor: "+value);
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
