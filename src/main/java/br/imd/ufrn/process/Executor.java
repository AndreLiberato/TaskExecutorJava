package br.imd.ufrn.process;

import br.imd.ufrn.model.ResultQueue;
import br.imd.ufrn.model.Task;
import br.imd.ufrn.model.TaskQueue;

import java.util.Queue;

public class Executor implements Runnable{

    private TaskQueue taskQueue;
    private ResultQueue resultQueue;
    private Worker [] workers;

    public Executor(int T, TaskQueue taskQueue){
        this.resultQueue = new ResultQueue();
        this.taskQueue = taskQueue;
        this.workers = new Worker[T];
    }


    @Override
    public void run() {
        Queue<Task> tasks = taskQueue.getTasks();

        while (!tasks.isEmpty()){
            tasks.poll();
        }

    }

    public ResultQueue getResultQueue() {
        return resultQueue;
    }
}
