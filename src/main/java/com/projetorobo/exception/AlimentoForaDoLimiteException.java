package com.projetorobo.exception;

public class AlimentoForaDoLimiteException extends RuntimeException {
    public AlimentoForaDoLimiteException() {
        super("Alimento não pode ser colocado nessa posição!");
    }
}
