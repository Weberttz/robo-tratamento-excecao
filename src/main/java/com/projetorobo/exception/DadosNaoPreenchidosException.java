package com.projetorobo.exception;

public class DadosNaoPreenchidosException extends RuntimeException {
    public DadosNaoPreenchidosException() {
        super("Preencha todos os dados!");
    }
}
