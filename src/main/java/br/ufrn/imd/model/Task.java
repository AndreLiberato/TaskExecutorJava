package br.ufrn.imd.model;

/**
 * Esta classe imutável representa uma tarefa a ser executada por um Trabalhador.
 * A tarefa possui um identificador único, um custo estimado da execução, tipo e valor associado.
 */
public record Task(long id, float cost, Type type, int value){
}