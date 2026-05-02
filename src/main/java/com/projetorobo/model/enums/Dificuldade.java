package com.projetorobo.model.enums;

public enum Dificuldade {
    FACILIMO, FACIL, MEDIO, DIFICIL;

    public static Dificuldade fromString(String dificuldade){
        return switch (dificuldade.trim().toLowerCase()){
            case "facilimo" -> FACILIMO;
            case "facil" -> FACIL;
            case "medio" -> MEDIO;
            case "dificil" -> DIFICIL;
            default -> throw new IllegalArgumentException();
        };
    }
}
