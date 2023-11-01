package br.imd.ufrn.model;

import java.util.LinkedList;
import java.util.Queue;

public class TaskQueue {

    private Queue<Task> tasks;

    public TaskQueue(){
        this.tasks = new LinkedList<>();
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
