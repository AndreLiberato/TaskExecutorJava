package br.imd.ufrn.model;

public class Task implements ITask{

    private long id;
    private float cost;
    private Type type;
    private int value;

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

    @Override
    public void execute() {
        System.out.println("Executando ...");
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
