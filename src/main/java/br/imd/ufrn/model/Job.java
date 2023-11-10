package br.imd.ufrn.model;

@FunctionalInterface
public interface Job {

    void execute() throws InterruptedException;

}
