# TaskExecutorJava
Implementação do projeto com java

## Como buildar

```
mvn package
```

Esse comando criará um arquivo `.jar` no diretório `./target/TaskExecutorJava-1.0.jar`.

## Como executar

```
java -jar ./target/TaskExecutor-1.0.jar [N] [E] [T]
```

Sendo `N` aplicado a 10^N elementos, `E` fator probabilístico de uma tarefa ser de escrita e `T` o número de threads criada.
