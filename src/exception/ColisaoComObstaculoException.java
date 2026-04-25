package exception;

public class ColisaoComObstaculoException extends Exception {
    private final String movimento;

    public ColisaoComObstaculoException(String movimento, String oQue) {
        super("Movimento invalido: "
                + movimento +
                " resultaria em uma colisão com um obstaculo (" + oQue + ")");
        this.movimento = movimento;
    }
    public String getMovimento() {
        return movimento;
    }
}
