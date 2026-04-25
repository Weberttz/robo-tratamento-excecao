package model.robos;

import model.enums.Direcao;
import java.util.Random;

public class Robo {
    private int newX;
    private int newY;
    private int oldX;
    private int oldY;
    private final String cor;
    private int movimentosValidos;
    private int movimentosInvalidos;
    protected final Random rand;

    public Robo(String cor) {
        this.cor = cor;
        this.newX = 0;
        this.newY = 0;
        this.oldX = 0;
        this.oldY = 0;
        this.rand = new Random();
    }

    public void mover(Direcao dir) {
        oldX = newX;
        oldY = newY;
        switch (dir) {
            case UP    -> newY++;
            case DOWN  -> newY--;
            case RIGHT -> newX++;
            case LEFT  -> newX--;
        }
    }

    public Direcao escolherDirecao() {
        return Direcao.fromInt(rand.nextInt(4) + 1);
    }

    public void explodir() {
        //implementar
    }

    public void registrarDirecaoInvalida(Direcao dir) {
        // robô normal não faz nada
    }

    public void confirmarMovimento() {
        // robô normal não faz nada
    }

    public void desfazerMovimento() {
        this.newX = oldX;
        this.newY = oldY;
    }

    public void incrementarValidos()   { movimentosValidos++; }
    public void incrementarInvalidos() { movimentosInvalidos++; }
    public void modificarPosicaoInicial(int newX, int newY){
        this.newX = newX;
        this.newY = newY;
        this.oldX = newX;
        this.oldY = newY;
    }
    public int getNewX() { return newX; }
    public int getNewY() { return newY; }
    public String getCor() { return cor; }
    public int getMovimentosValidos()   { return movimentosValidos; }
    public int getMovimentosInvalidos() { return movimentosInvalidos; }
}