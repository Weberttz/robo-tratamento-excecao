package board;

import exception.ColisaoComObstaculoException;
import exception.MovimentoInvalidoException;
import model.enums.Dificuldade;
import model.enums.Direcao;
import model.enums.Cores;
import model.obstaculos.Obstaculo;
import model.robos.Robo;
import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
    private final int tamanho;
    private final List<Robo> robos;
    private int alimentoX;
    private int alimentoY;
    private final List<Obstaculo> obstaculos;

    public Tabuleiro(int tamanho, int alimentoX, int alimentoY) {
        this.tamanho = tamanho;
        this.robos = new ArrayList<>();
        setAlimento(alimentoX, alimentoY);
        this.obstaculos = new ArrayList<>();
    }

    public void moverRobo(Robo robo, Direcao dir) throws MovimentoInvalidoException, ColisaoComObstaculoException {
        robo.mover(dir);

        if (foraDoLimite(robo.getNewX(), robo.getNewY())) {
            robo.desfazerMovimento();
            robo.incrementarInvalidos();
            robo.registrarDirecaoInvalida(dir); // Robo ignora, RoboInteligente usa
            throw new MovimentoInvalidoException(dir.name());
        }

        if (temOutroRobo(robo, robo.getNewX(), robo.getNewY())) {
            robo.desfazerMovimento();
            robo.incrementarInvalidos();
            robo.registrarDirecaoInvalida(dir); // Robo ignora, RoboInteligente usa
            throw new ColisaoComObstaculoException(dir.name());
        }
        // fazer o if rocha e bomba

        robo.incrementarValidos();
        robo.confirmarMovimento(); // Robo ignora, RoboInteligente limpa o set
    }

    private boolean foraDoLimite(int x, int y) {
        return x < 0 || x >= tamanho || y < 0 || y >= tamanho;
    }

    private boolean temOutroRobo(Robo atual, int x, int y) {
        for (Robo r : robos) {
            if (r != atual && r.getNewX() == x && r.getNewY() == y) return true;
        }
        return false;
    }

    private Robo getRoboNaPosicao(int x, int y) {
        for (Robo r : robos) {
            if (r.getNewX() == x && r.getNewY() == y) return r;
        }
        return null;
    }

    public void renderizar() {
        for (int y = tamanho - 1; y >= 0; y--){
            for (int x = 0; x < tamanho; x++) {
                Robo r = getRoboNaPosicao(x, y);
                if (r != null) System.out.print(r.getCor().getAnsi() + " R " + Cores.RESET.getAnsi());
                else if (x == alimentoX && y == alimentoY) System.out.print(" A ");
                else                                     System.out.print(" . ");
            }
            System.out.println();
        }
    }

     public void colocarObstaculos(Dificuldade dificuldade){

     }

    public boolean verificarAlimento(Robo robo) {
        return robo.getNewX() == alimentoX && robo.getNewY() == alimentoY;
    }

    public void adicionarRobo(Robo robo) { robos.add(robo); }
    public void setAlimento(int x, int y) { this.alimentoX = x; this.alimentoY = y; }
    public int getTamanho()    { return tamanho; }
    public List<Robo> getRobos() { return robos; }
    public int getAlimentoX()  { return alimentoX; }
    public int getAlimentoY()  { return alimentoY; }
}