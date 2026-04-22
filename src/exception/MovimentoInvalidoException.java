package exception;

public class MovimentoInvalidoException extends Exception {

    private final String movimento;

    public MovimentoInvalidoException(String movimento) {
        super("Movimento inválido: "
                + movimento +
                " levaria o robô a uma zona fora do tabuleiro.");
        this.movimento = movimento;
    }

    public String getMovimento() {
        return movimento;
    }
}