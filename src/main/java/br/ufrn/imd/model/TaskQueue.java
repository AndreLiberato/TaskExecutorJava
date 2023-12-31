package br.ufrn.imd.model;

import br.ufrn.imd.utils.RandomGenerate;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A classe TaskQueue representa uma fila de tarefas a serem executadas por threads Worker.
 * Ela suporta operações de carregamento de tarefas, obtenção de tarefas e produção de tarefas.
 */
public class TaskQueue {

    private Queue<Task> tasks;  // Fila de tarefas
    private int N;              // Valor associado à TaskQueue

    /**
     * Construtor para criar uma TaskQueue com um determinado valor N.
     *
     * @param N O valor N associado à TaskQueue.
     */
    public TaskQueue(int N){
        this.N = N;
    }

    /**
     * Carrega uma fila de tarefas geradas aleatoriamente.
     *
     * @param E O valor E que influencia a probabilidade de uma tarefa ser de escrita.
     */
    public void load(int E){
        System.out.println("Carregando ...");
        final long size = (long) Math.pow(10, N);
        this.tasks = new LinkedList<>();

        long sizeW = (long) ((E / 100f) * 100);
        long sizeR = size - sizeW;

        RandomGenerate random = new RandomGenerate(sizeW, sizeR);
        for(long id = 0; id < size; id++){
            tasks.add(new Task(id, random.generateCost(), random.getRandomType(), random.generateValue()));
        }
    }

    /**
     * Obtém a fila de tarefas.
     *
     * @return A fila de tarefas.
     */
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

}
