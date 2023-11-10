package br.imd.ufrn;

import br.imd.ufrn.model.TaskQueue;
import br.imd.ufrn.process.Executor;

public class Main {

    public static void main(String[] args) {
        int N = 2;
        int E = 10;
        int T = 3;

        TaskQueue taskQueue = new TaskQueue(N);
        taskQueue.load(E);

        Executor executor = new Executor(T, taskQueue);
        Thread thread = new Thread(executor, "Executor");
        thread.start();
    }
}
