package br.imd.ufrn.process;

import br.imd.ufrn.model.ITask;
import br.imd.ufrn.model.Result;
import br.imd.ufrn.model.ResultQueue;
import br.imd.ufrn.model.Task;

public class Worker implements Runnable{

    private Task task;
    private ResultQueue resultQueue;

    public Worker(Task task, ResultQueue resultQueue){
        this.task = task;
        this.resultQueue = resultQueue;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        task.execute();
        long end = System.currentTimeMillis();

        resultQueue.add(new Result(task.getId(), task.getValue(), (end - start)));
    }

}
