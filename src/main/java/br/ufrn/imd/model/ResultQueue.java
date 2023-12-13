package br.ufrn.imd.model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A classe ResultQueue representa uma fila de resultados produzidos por threads Worker.
 * Ela suporta a operação de adição de resultados de maneira sincronizada.
 */
public class ResultQueue {

    private Queue<Result> results;  // Fila de resultados

    /**
     * Construtor para criar uma ResultQueue vazia.
     */
    public ResultQueue(){
        this.results = new LinkedList<>();
    }

    /**
     * Adiciona um resultado à fila de maneira sincronizada.
     *
     * @param result O resultado a ser adicionado à fila.
     */
    public synchronized void add(Result result){
        results.add(result);
    }

    /**
     * Calcula o tempo de execução total do processamento realizando a soma do processamento de cada tarefa.
     *
     * @return tempo total do processamento do conjunto de tarefas.
     */
    public long getTotalExecutionTime(){
        long msTotal = 0;
        for(Result result : results){
            msTotal+=result.ms();
        }
        return msTotal;
    }

}
