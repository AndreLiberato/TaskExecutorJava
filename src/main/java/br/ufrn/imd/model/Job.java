package br.ufrn.imd.model;

/**
 * A interface Job representa uma tarefa a ser executada por threads Worker.
 * É marcada como uma interface funcional, indicando que possui apenas um método abstrato, execute().
 */
@FunctionalInterface
public interface Job {

    /**
     * Método abstrato que define a operação a ser realizada pela tarefa.
     *
     * @throws InterruptedException Exceção lançada se a execução for interrompida.
     */
    void execute() throws InterruptedException;

}