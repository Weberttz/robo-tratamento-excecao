package com.projetorobo.exception;

public class CoresDuplicadasException extends RuntimeException {
    public CoresDuplicadasException() {
        super("Os robôs não podem ter a mesma cor!");
    }
}
