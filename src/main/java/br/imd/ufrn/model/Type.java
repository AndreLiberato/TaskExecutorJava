package br.imd.ufrn.model;

public enum Type {
    READ("Leitura"),
    WRITING("Escrita");

    private final String description;

    Type(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
