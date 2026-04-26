package model.enums;

public enum Direcao {
    UP, DOWN, RIGHT, LEFT;

    public static Direcao fromString(String s) {
        return switch (s.trim().toLowerCase()) {
            case "up"    -> UP;
            case "down"  -> DOWN;
            case "right" -> RIGHT;
            case "left"  -> LEFT;
            default      -> throw new IllegalArgumentException(s);
        };
    }

    public static Direcao fromInt(int n) {
        return switch (n) {
            case 1 -> UP;
            case 2 -> DOWN;
            case 3 -> RIGHT;
            case 4 -> LEFT;
            default -> throw new IllegalArgumentException(String.valueOf(n));
        };
    }
}
