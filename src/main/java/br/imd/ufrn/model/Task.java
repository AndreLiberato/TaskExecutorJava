package br.imd.ufrn.model;

public class Task {

    private int id;
    private float cost;
    private Type type;
    private int value;

    public Task(int id, float cost, Type type, int value) {
        this.id = id;
        this.cost = cost;
        this.type = type;
        this.value = value;
    }

    public int getId() {
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
}
