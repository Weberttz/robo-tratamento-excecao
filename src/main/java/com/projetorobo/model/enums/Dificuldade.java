package com.projetorobo.model.enums;

public enum Dificuldade {
    FASSILIMO, FACIL, MEDIO, DIFICIL;

    public static Dificuldade fromString(String dificuldade){
        return switch (dificuldade.trim().toLowerCase()){
            case "fassilimo" -> FASSILIMO;
            case "facil" -> FACIL;
            case "medio" -> MEDIO;
            case "dificil" -> DIFICIL;
            default -> throw new IllegalArgumentException();
        };
    }
}
