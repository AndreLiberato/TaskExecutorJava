package br.ufrn.imd.model;

/**
 * A classe Result representa um resultado associado a uma tarefa operada.
 * É um registro imutável contendo informações como identificador, valor e tempo de execução da tarefa.
 */
public record Result (long id, int value, long ms) {
}
