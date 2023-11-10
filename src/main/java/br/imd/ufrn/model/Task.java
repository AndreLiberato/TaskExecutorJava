package br.imd.ufrn.model;

import java.util.Objects;

/**
 * A classe Task representa uma tarefa a ser executada por um Worker.
 */
public class Task implements ITask{

    private long id;
    private float cost;
    private Type type;
    private int value;


    /**
     * Construtor para criar uma instância de Task.
     *
     * @param id    Identificador único da tarefa.
     * @param cost  Custo estimado da execução da tarefa.
     * @param type  Tipo da tarefa (leitura ou escrita).
     * @param value Valor associado à tarefa.
     */
    public Task(long id, float cost, Type type, int value) {
        this.id = id;
        this.cost = cost;
        this.type = type;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public float getCost() {
        return cost;
    }

    public Type getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    /**
     * Executa a tarefa...
     */
    @Override
    public synchronized void execute() {
        if(Objects.equals(type, Type.READING)){
            System.out.println("\nExecutando leitura --> "+this);
        }else{
            System.out.println("\nExecutando escrita --> "+this);
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", cost=" + cost +
                ", type=" + type +
                ", value=" + value +
                '}';
    }
}
