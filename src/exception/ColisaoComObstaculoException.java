package exception;

public class ColisaoComObstaculoException extends Exception {
    private final String movimento;

    public ColisaoComObstaculoException(String movimento) {
        super("Movimento invalido: "
                + movimento +
                " resultaria em uma colisão com um obstaculo");
        this.movimento = movimento;
    }

    public String getMovimento() {
        return movimento;
    }
}
