package model.robos;

import model.enums.Direcao;

public class Robo {

    protected int x, y;
    private final String cor;

    private int movimentosValidos;
    private int movimentosInvalidos;

    public Robo(String cor) {
        this.x = 0;
        this.y = 0;
        this.cor = cor;
        this.movimentosValidos = 0;
        this.movimentosInvalidos = 0;
    }

    //adicionar sobrecarga depois
    public void mover(Direcao dir) {
        switch (dir) {
            case UP -> y++;
            case DOWN -> y--;
            case RIGHT -> x++;
            case LEFT -> x--;
        }
    }

    public void desfazerMovimento(int oldX, int oldY) {
        this.x = oldX;
        this.y = oldY;
    }

    public void incrementarValidos() {
        movimentosValidos++;
    }

    public void incrementarInvalidos() {
        movimentosInvalidos++;
    }

    public boolean encontrouAlimento(int ax, int ay) {
        return this.x == ax && this.y == ay;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public String getCor() { return cor; }

    public int getMovimentosValidos() { return movimentosValidos; }
    public int getMovimentosInvalidos() { return movimentosInvalidos; }
}