package br.imd.ufrn.model;

import java.util.LinkedList;
import java.util.Queue;

public class ResultQueue {

    private Queue<Result> results;

    public ResultQueue(){
        this.results = new LinkedList<>();
    }

    public synchronized void add(Result result){
        results.add(result);
    }

}
