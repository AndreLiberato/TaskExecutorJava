package br.ufrn.imd.utils;

import br.ufrn.imd.model.Type;

import java.util.Objects;
import java.util.Random;

/**
 * Uma classe utilitária para gerar valores e tipos aleatórios para tarefas.
 */
public class RandomGenerate {

    private volatile long totalW;   // O número total de tarefas do tipo de escrita (WRITING).
    private volatile long totalR;   // O número total de tarefas do tipo de leitura (READING).
    private final Random random;    // O gerador de números aleatórios.

    /**
     * Constrói um novo objeto RandomGenerate com os contadores totais especificados para WRITING e READING.
     *
     * @param totalW O número total de tarefas do tipo WRITING.
     * @param totalR O número total de tarefas do tipo READING.
     */
    public RandomGenerate(long totalW, long totalR) {
        this.totalW = totalW;
        this.totalR = totalR;
        this.random = new Random();
    }

    /**
     * Gera um custo variado de (0 a 0.01) de execução para uma tarefa.
     *
     * @return O custo gerado aleatoriamente.
     */
    public float generateCost() {
        return (float) (random.nextInt(101) / 10000.0);
    }

    /**
     * Gera um valor aleatório de 0 a 10 para uma tarefa.
     *
     * @return O valor gerado aleatoriamente.
     */
    public int generateValue() {
        return random.nextInt(11);
    }

    /**
     * Obtém um tipo de forma aleatória para uma tarefa.
     *
     * @return O tipo gerado aleatoriamente (WRITING ou READING).
     */
    public Type getRandomType() {
        int typeId = random.nextInt(2);
        Type type;

        if (typeId == 0) {
            type = (totalR > 0) ? Type.getTypeById(typeId) : Type.getOppositeTypeById(typeId);
        } else {
            type = (totalW > 0) ? Type.getTypeById(typeId) : Type.getOppositeTypeById(typeId);
        }

        updateCount(type);
        return type;
    }

    /**
     * Atualiza o contador de acordo com o tipo de tarefa gerado.
     *
     * @param type O tipo de tarefa gerado.
     */
    private void updateCount(Type type) {
        if(Objects.equals(type, Type.READING)){
            this.totalR = this.totalR - 1;
        } else {
            this.totalW = this.totalW - 1;
        }
    }
}
