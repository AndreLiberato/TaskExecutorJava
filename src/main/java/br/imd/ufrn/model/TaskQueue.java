package br.imd.ufrn.model;

import java.util.LinkedList;
import java.util.Queue;

public class TaskQueue {

    private Queue<Task> tasks;
    private int N;

    public TaskQueue(int N){
        this.N = N;
    }

    public void load(int E){
        this.tasks = new LinkedList<>();
        final long size = (long) Math.pow(10, N);

    }

    public void add(Task task){
        tasks.add(task);
    }

    public Queue<Task> getTasks() {
        return tasks;
    }

    public synchronized Task getCurrentTask() {
        while (tasks.size() == 0) {
            System.out.print("Não há tarefas disponíveis para execução no momento.");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Task current = tasks.remove();

        System.out.println("Tarefa para execução da vez: "+current.getId());
        notify();

        return current;
    }
}
