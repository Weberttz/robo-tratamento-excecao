package model;

import exception.MovimentoInvalidoException;
import model.enums.Direcao;

public class Robo {
    private int x,y;
    private final String cor;
    private int movimentosValidos, movimentosInvalidos;

    public Robo(String cor) {
        x = 0;
        y = 0;

        movimentosValidos = 0;
        movimentosInvalidos = 0;

        //validar depois
        this.cor = cor;
    }

    public void mover(String direcao) throws MovimentoInvalidoException {
        Direcao dir = Direcao.fromString(direcao);
        moverInterno(dir);
    }

    public void mover(int direcao) throws MovimentoInvalidoException {
        Direcao dir = Direcao.fromInt(direcao);
        moverInterno(dir);
    }

    public void mover(Direcao dir) throws MovimentoInvalidoException {
        moverInterno(dir);
    }

    private void moverInterno(Direcao dir) {
        switch (dir){
            case UP -> this.y++;
            case DOWN -> this.y--;
            case RIGHT -> this.x++;
            case LEFT -> this.x--;
        }
    }

    public boolean encontruoAlimento(int posicaoAlimentoX, int posicaoAlimentoY){
        return this.x == posicaoAlimentoX && this.y == posicaoAlimentoY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getCor() {
        return cor;
    }

    public void incrementarValidos() {
        movimentosValidos++;
    }

    public void incrementarInvalidos() {
        movimentosInvalidos++;
    }

    public int getMovimentosValidos() {
        return movimentosValidos;
    }

    public int getMovimentosInvalidos() {
        return movimentosInvalidos;
    }
}
