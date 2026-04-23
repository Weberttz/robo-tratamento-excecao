package board;

import exception.MovimentoInvalidoException;
import model.enums.Direcao;
import model.robos.Robo;
import model.robos.RoboInteligente;
import java.util.Random;
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

    public void moverRobo(Robo robo, Direcao dir) throws MovimentoInvalidoException {

        int oldX = robo.getX();
        int oldY = robo.getY();
        if(robo instanceof RoboInteligente inteligente){
            boolean moveuValido = false;

            while (!moveuValido) {

                while (inteligente.getDirecoesInvalidas().contains(dir)) {
                    dir = Direcao.fromInt(inteligente.getRand().nextInt(4) + 1);
                }

                inteligente.mover(dir);

                if (foraDoLimite(inteligente.getX(), inteligente.getY())) {
                    inteligente.desfazerMovimento(oldX, oldY);
                    inteligente.incrementarInvalidos();
                    inteligente.getDirecoesInvalidas().add(dir);
                    throw new MovimentoInvalidoException(dir.name());
                }else if (temOutroRobo(inteligente, inteligente.getX(), inteligente.getY())) {
                    inteligente.desfazerMovimento(oldX, oldY);
                    inteligente.incrementarInvalidos();
                    inteligente.getDirecoesInvalidas().add(dir);
                    throw new MovimentoInvalidoException("COLISAO");
                }else{
                    inteligente.incrementarValidos();
                    moveuValido = true;
                }

            }
        }else {
            robo.mover(dir);
            if (foraDoLimite(robo.getX(), robo.getY())) {
                robo.desfazerMovimento(oldX, oldY);
                robo.incrementarInvalidos();
                throw new MovimentoInvalidoException(dir.name());
            }

            if (temOutroRobo(robo, robo.getX(), robo.getY())) {
                robo.desfazerMovimento(oldX, oldY);
                robo.incrementarInvalidos();
                throw new MovimentoInvalidoException("COLISAO");
            }
            robo.incrementarValidos();
        }
    }

    private boolean foraDoLimite(int x, int y) {
        return x < 0 || x >= tamanho || y < 0 || y >= tamanho;
    }

    private boolean temOutroRobo(Robo atual, int x, int y) {
        for (Robo r : robos) {
            if (r != atual && r.getX() == x && r.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private Robo getRoboNaPosicao(int x, int y) {
        for (Robo r : robos) {
            if (r.getX() == x && r.getY() == y) {
                return r;
            }
        }
        return null;
    }

    public void renderizar() {
        for (int y = tamanho - 1; y >= 0; y--) {
            for (int x = 0; x < tamanho; x++) {

                if (getRoboNaPosicao(x, y) != null) {
                    System.out.print(" R ");
                }
                else if (x == alimentoX && y == alimentoY) {
                    System.out.print(" A ");
                }
                else {
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }
    }

    public void adicionarRobo(Robo robo) {
        robos.add(robo);
    }

    public void setAlimento(int x, int y) {
        this.alimentoX = x;
        this.alimentoY = y;
    }

    public int getTamanho() { return tamanho; }
    public List<Robo> getRobos() { return robos; }
    public int getAlimentoX() { return alimentoX; }
    public int getAlimentoY() { return alimentoY; }
}