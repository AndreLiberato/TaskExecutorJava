package br.ufrn.imd.model;

/**
    Constante que representa o tipo da tarefa.
 */
public enum Type {
    READING(0),
    WRITING(1);

    private final int id;

    Type(int id) {
        this.id = id;
    }

    /**
     * Obtém o tipo com base no ID fornecido.
     *
     * @param id O ID do tipo desejado.
     * @return O tipo correspondente ao ID.
     */
    public static Type getTypeById(int id) {
        if (id == 0) {
            return READING;
        } else if (id == 1) {
            return WRITING;
        } else {
            throw new IllegalArgumentException("ID inválido: " + id);
        }
    }

    /**
     * Obtém o tipo oposto com base no ID fornecido.
     *
     * @param id O ID do tipo desejado.
     * @return O tipo oposto ao ID fornecido.
     */
    public static Type getOppositeTypeById(int id) {
        if (id == 0) {
            return WRITING;
        } else if (id == 1) {
            return READING;
        } else {
            throw new IllegalArgumentException("ID inválido: " + id);
        }
    }
}
