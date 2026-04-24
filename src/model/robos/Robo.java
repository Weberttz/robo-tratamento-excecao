package model.robos;

import model.enums.Direcao;
import java.util.Random;

public class Robo {
    private int x;
    private int y;
    private final String cor;
    private int movimentosValidos;
    private int movimentosInvalidos;
    protected final Random rand;

    public Robo(String cor) {
        this.cor = cor;
        this.x = 0;
        this.y = 0;
        this.rand = new Random();
    }

    public void mover(Direcao dir) {
        switch (dir) {
            case UP    -> y++;
            case DOWN  -> y--;
            case RIGHT -> x++;
            case LEFT  -> x--;
        }
    }

    public Direcao escolherDirecao() {
        return Direcao.fromInt(rand.nextInt(4) + 1);
    }

    public void registrarDirecaoInvalida(Direcao dir) {
        // robô normal não faz nada
    }

    public void confirmarMovimento() {
        // robô normal não faz nada
    }

    public void desfazerMovimento(int oldX, int oldY) {
        this.x = oldX;
        this.y = oldY;
    }

    public void incrementarValidos()   { movimentosValidos++; }
    public void incrementarInvalidos() { movimentosInvalidos++; }

    public int getX() { return x; }
    public int getY() { return y; }
    public String getCor() { return cor; }
    public int getMovimentosValidos()   { return movimentosValidos; }
    public int getMovimentosInvalidos() { return movimentosInvalidos; }
}