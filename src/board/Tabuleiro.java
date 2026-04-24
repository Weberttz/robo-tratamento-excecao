package board;

import exception.ColisaoComObstaculoException;
import exception.MovimentoInvalidoException;
import model.enums.Direcao;
import model.robos.Robo;
import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
    private final int tamanho;
    private final List<Robo> robos;
    private int alimentoX;
    private int alimentoY;

    public Tabuleiro(int tamanho, int alimentoX, int alimentoY) {
        this.tamanho = tamanho;
        this.robos = new ArrayList<>();
        setAlimento(alimentoX, alimentoY);
    }

    public void moverRobo(Robo robo, Direcao dir) throws MovimentoInvalidoException, ColisaoComObstaculoException {
        int oldX = robo.getX();
        int oldY = robo.getY();

        robo.mover(dir);

        if (foraDoLimite(robo.getX(), robo.getY())) {
            robo.desfazerMovimento(oldX, oldY);
            robo.incrementarInvalidos();
            robo.registrarDirecaoInvalida(dir); // Robo ignora, RoboInteligente usa
            throw new MovimentoInvalidoException(dir.name());
        }

        if (temOutroRobo(robo, robo.getX(), robo.getY())) {
            robo.desfazerMovimento(oldX, oldY);
            robo.incrementarInvalidos();
            robo.registrarDirecaoInvalida(dir); // Robo ignora, RoboInteligente usa
            throw new ColisaoComObstaculoException(dir.name());
        }

        robo.incrementarValidos();
        robo.confirmarMovimento(); // Robo ignora, RoboInteligente limpa o set
    }

    private boolean foraDoLimite(int x, int y) {
        return x < 0 || x >= tamanho || y < 0 || y >= tamanho;
    }

    private boolean temOutroRobo(Robo atual, int x, int y) {
        for (Robo r : robos) {
            if (r != atual && r.getX() == x && r.getY() == y) return true;
        }
        return false;
    }

    private Robo getRoboNaPosicao(int x, int y) {
        for (Robo r : robos) {
            if (r.getX() == x && r.getY() == y) return r;
        }
        return null;
    }

    public void renderizar() {
        for (int y = tamanho - 1; y >= 0; y--) {
            for (int x = 0; x < tamanho; x++) {
                Robo r = getRoboNaPosicao(x, y);
                if (r != null)                        System.out.print("[" + r.getCor().charAt(0) + "]");
                else if (x == alimentoX && y == alimentoY) System.out.print(" A ");
                else                                       System.out.print(" . ");
            }
            System.out.println();
        }
    }

    public boolean verificarAlimento(Robo robo) {
        return robo.getX() == alimentoX && robo.getY() == alimentoY;
    }

    public void adicionarRobo(Robo robo) { robos.add(robo); }
    public void setAlimento(int x, int y) { this.alimentoX = x; this.alimentoY = y; }
    public int getTamanho()    { return tamanho; }
    public List<Robo> getRobos() { return robos; }
    public int getAlimentoX()  { return alimentoX; }
    public int getAlimentoY()  { return alimentoY; }
}