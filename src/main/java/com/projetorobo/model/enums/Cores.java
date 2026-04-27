package com.projetorobo.model.enums;

public enum Cores {
    RESET("\u001B[0m"),
    VERMELHO("\u001B[31m"),
    VERDE("\u001B[32m"),
    AMARELO("\u001B[33m"),
    AZUL("\u001B[34m"),
    MARROM("\u001B[38;5;94m");

    public static Cores fromString(String s) {
        return switch (s.trim().toLowerCase()) {
            case "vermelho" -> VERMELHO; 
            case "verde"  -> VERDE;
            case "amarelo" -> AMARELO;
            case "azul"  -> AZUL;
            case "marrom" -> MARROM;
            default      -> throw new IllegalArgumentException(s);
        };
    }

    private final String ansi;
    Cores(String ansi){
        this.ansi = ansi;
    }

    public String getAnsi() {
        return ansi;
    }
}
