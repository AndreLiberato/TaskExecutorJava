package br.imd.ufrn.model;

import br.imd.ufrn.utils.RandomGenerate;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * A classe TaskQueue representa uma fila de tarefas a serem executadas por threads Worker.
 * Ela suporta operações de carregamento de tarefas, adição de tarefas, obtenção de tarefas e produção de tarefas.
 */
public class TaskQueue {

    private Queue<Task> tasks;
    private int N;

    /**
     * Construtor para criar uma TaskQueue com um determinado valor N.
     *
     * @param N O valor N associado à TaskQueue.
     */
    public TaskQueue(int N){
        this.N = N;
    }

    /**
     * Carrega a TaskQueue com um número específico de tarefas, geradas aleatoriamente.
     *
     * @param E O valor E que influencia a probabilidade de uma tarefa ser de escrita.
     */
    public void load(int E){
        this.tasks = new LinkedList<>();
        final long size = (long) Math.pow(10, N);

        for(long id = 0; id < size; id++){
            tasks.add(produce(id, E));
        }
    }

    public void add(Task task){
        tasks.add(task);
    }

    public Queue<Task> getTasks() {
        return tasks;
    }

    /**
     * Obtém a próxima tarefa da fila de tarefas de maneira sincronizada.
     *
     * @return A próxima tarefa na fila, ou null se a fila estiver vazia.
     */
    public synchronized Task getNextTask() {
        return tasks.poll();
    }

    /**
     * Produz uma tarefa com base no identificador e valor E fornecido.
     *
     * @param id O identificador único da tarefa a ser produzida.
     * @param E  O valor E que influencia a probabilidade de uma tarefa ser de escrita.
     * @return A tarefa produzida.
     */
    private Task produce(long id, int E){
        int random = new Random().nextInt(100) + 1;
        Type type = random <= E ? Type.WRITING : Type.READING;

        return new Task(id, RandomGenerate.generateCost(), type, RandomGenerate.generateValue());
    }
}
