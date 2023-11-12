package br.ufrn.imd.model;

/**
 * A classe Task representa uma tarefa a ser executada por um Worker.
 */
public class Task {

    private long id;         // Identificador único da tarefa.
    private float cost;      // Custo estimado da execução da tarefa.
    private Type type;       // Tipo da tarefa (leitura ou escrita).
    private int value;       // Valor associado à tarefa.

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
     * Sobrescreve o método toString para fornecer uma representação de string da instância da classe.
     *
     * @return Uma string representando a tarefa.
     */
    @Override
    public String toString() {
        System.out.println("----------------------------------------------------------");
        return "Task [id="+ id +", cost="+ cost +", type="+ type +", value="+ value +"]";
    }
}
