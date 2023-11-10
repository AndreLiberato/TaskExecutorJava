package br.imd.ufrn.model;

/**
 * A classe Result representa um resultado associado a uma tarefa ou operação.
 * É um registro imutável contendo informações como identificador, valor e tempo de execução.
 */
public record Result (long id, int value, long ms) {
}
