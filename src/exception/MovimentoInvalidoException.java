package exception;

public class MovimentoInvalidoException extends Exception {

    private final String movimento;

    // a gente usa essa mensagem????
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





// ideia: fazer um exception pai para ser a generica, e essas duas herdam dela, ai no main 1 so chamar a exception generica e nas outras chama as duas