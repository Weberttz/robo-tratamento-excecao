package com.projetorobo.exception;

public class CoresDuplicadasException extends Exception {
    public CoresDuplicadasException() {
        super("Os robôs não podem ter a mesma cor!");
    }
}
