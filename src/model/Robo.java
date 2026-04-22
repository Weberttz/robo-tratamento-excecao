package model;

import exception.MovimentoInvalidoException;
import model.enums.Direcao;

public class Robo {
    private int x,y;
    private final String cor;
    private int limiteTabuleiro;
    private int movimentosValidos, movimentosInvalidos;

    public Robo(String cor, int limiteTabuleiro) {
        x = 0;
        y = 0;

        movimentosValidos = 0;
        movimentosInvalidos = 0;

        this.limiteTabuleiro = limiteTabuleiro;
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

    private void moverInterno(Direcao dir) throws MovimentoInvalidoException{
        boolean foiValido = false;
        switch (dir){
            case Direcao.UP -> {
                if(this.y + 1 > limiteTabuleiro) {
                    movimentosInvalidos++;
                    throw new MovimentoInvalidoException(String.valueOf(dir));
                }
                else {
                    this.y++;
                    movimentosValidos++;
                }
            }
            case Direcao.DOWN -> {
                if(this.y - 1 < 0) {
                    movimentosInvalidos++;
                    throw new MovimentoInvalidoException(String.valueOf(dir));
                }
                else {
                    this.y--;
                    movimentosValidos++;
                }
            }
            case Direcao.RIGHT -> {
                if(this.x + 1 > limiteTabuleiro) {
                    movimentosInvalidos++;

                    throw new MovimentoInvalidoException(String.valueOf(dir));
                }
                else{
                    this.x++;
                    movimentosValidos++;
                }
            }
            case Direcao.LEFT -> {
                if(this.x - 1 < 0) {
                    movimentosInvalidos++;
                    throw new MovimentoInvalidoException(String.valueOf(dir));
                }
                else {
                    this.x--;
                    movimentosValidos++;
                }
            }
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

    public int getMovimentosValidos() {
        return movimentosValidos;
    }

    public int getMovimentosInvalidos() {
        return movimentosInvalidos;
    }
}
