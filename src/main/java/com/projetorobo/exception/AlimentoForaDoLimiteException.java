package com.projetorobo.exception;

public class AlimentoForaDoLimiteException extends RuntimeException {
    public AlimentoForaDoLimiteException() {
        super("Alimento fora do tabuleiro");
    }
}
