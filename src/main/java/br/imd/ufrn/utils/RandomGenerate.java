package br.imd.ufrn.utils;

import br.imd.ufrn.model.Type;

import java.util.Objects;
import java.util.Random;

/**
 * Uma classe utilitária para gerar valores e tipos aleatórios para tarefas.
 */
public class RandomGenerate {

    /**
     * O número total de tarefas do tipo WRITING.
     */
    private volatile long totalW;

    /**
     * O número total de tarefas do tipo READING.
     */
    private volatile long totalR;

    /**
     * O custo percentual para tarefas geradas.
     */
    private float percentCost;

    /**
     * O valor máximo para tarefas geradas.
     */
    private int maxValue;

    /**
     * O gerador de números aleatórios.
     */
    private Random random;

    /**
     * Constrói um novo objeto RandomGenerate com os contadores totais especificados para WRITING e READING.
     *
     * @param totalW O número total de tarefas do tipo WRITING.
     * @param totalR O número total de tarefas do tipo READING.
     */
    public RandomGenerate(long totalW, long totalR) {
        this.totalW = totalW;
        this.totalR = totalR;
        this.percentCost = 0.01f;
        this.maxValue = 10;
        this.random = new Random();
    }

    /**
     * Gera um custo aleatório para uma tarefa.
     *
     * @return O custo gerado aleatoriamente.
     */
    public float generateCost() {
        return random.nextFloat() * percentCost;
    }

    /**
     * Gera um valor aleatório para uma tarefa.
     *
     * @return O valor gerado aleatoriamente.
     */
    public int generateValue() {
        return random.nextInt(maxValue) + 1;
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
