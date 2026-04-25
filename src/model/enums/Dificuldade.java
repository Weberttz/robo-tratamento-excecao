package model.enums;

public enum Dificuldade {
    FACIL, MEDIO, DIFICIL;

    public static Dificuldade fromString(String dificuldade){
        return switch (dificuldade.trim().toLowerCase()){
            case "facil" -> FACIL;
            case "medio" -> MEDIO;
            case "dificil" -> DIFICIL;
            default -> throw new IllegalArgumentException();
        };
    }
}
