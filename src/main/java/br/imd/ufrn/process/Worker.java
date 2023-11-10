package br.imd.ufrn.process;

import br.imd.ufrn.model.Task;

/**
 * A classe Worker representa uma thread que executa tarefas.
 */
public class Worker extends Thread {

    private Task task;
    private volatile boolean isIdle;
    private volatile boolean stop;

    /**
     * Construtor que inicializa um Worker com estado inicial ocioso.
     */
    public Worker() {
        this.isIdle = true;
        this.stop = false;
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
                long duration = process();
            }catch (InterruptedException e){
                System.err.println("Process error ...");
            }
        } while (!stop && isIdle);

        System.out.println(this.getName() + " finalizado ...");
    }

    /**
     * Processa a tarefa atribuída, aguardando o tempo especificado em getCost().
     *
     * @return A duração total do processamento da tarefa.
     * @throws InterruptedException Se a thread for interrompida durante o processamento.
     */
    private long process() throws InterruptedException {
        try {
            long start = System.currentTimeMillis();
            Thread.sleep((long) (task.getCost() * 1000));
            task.execute();
            long end = System.currentTimeMillis();

            return  end - start;
        }finally {
            isIdle = true;
        }
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
