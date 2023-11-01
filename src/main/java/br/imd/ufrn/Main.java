package br.imd.ufrn;

import br.imd.ufrn.model.Task;
import br.imd.ufrn.model.Type;
import br.imd.ufrn.utils.RandomGenerate;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    private BlockingQueue<Task> tasks;

    public Main(){
        this.tasks = new LinkedBlockingQueue<>();
    }


    public static void main(String[] args) {
        int N = 2;
        int E = 10;

        Main main = new Main();
        main.loadTasks(N, E);

        main.getTasks().forEach(System.out::println);

    }

    public void loadTasks(int N, int E){
        final long size = (long) Math.pow(10, N);

        final long xEscrita = (long) ((size / 100.0) * E);

        for(int i = 0; i < size - xEscrita; i++){
            tasks.add(produce(i, Type.READ));
        }

        for(long j = (size - xEscrita); j < size; j++){
            tasks.add(produce(j, Type.WRITING));
        }
    }

    private Task produce(long id, Type type){
        float cost = RandomGenerate.generateCost();
        int value = RandomGenerate.generateValue();

        return new Task(id, cost, type, value);
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }
}
