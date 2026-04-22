package model.enums;

import exception.MovimentoInvalidoException;

public enum Direcao {
    UP, DOWN, RIGHT, LEFT;

    public static Direcao fromString(String s) throws MovimentoInvalidoException {
        return switch (s.trim().toLowerCase()) {
            case "up"    -> UP;
            case "down"  -> DOWN;
            case "right" -> RIGHT;
            case "left"  -> LEFT;
            default      -> throw new MovimentoInvalidoException(s);
        };
    }

    public static Direcao fromInt(int n) throws MovimentoInvalidoException {
        return switch (n) {
            case 1 -> UP;
            case 2 -> DOWN;
            case 3 -> RIGHT;
            case 4 -> LEFT;
            default -> throw new MovimentoInvalidoException(String.valueOf(n));
        };
    }
}
