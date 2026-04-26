package model.obstaculos;

import model.robos.Robo;

public abstract class Obstaculo {
    private final int id;
    private final char inicial;
    private int posicaoX;
    private int posicaoY;

    protected Obstaculo(int id, int posicaoX, int posicaoY, char inicial) {
        this.id = id;
        this.posicaoX = posicaoX;
        this.posicaoY = posicaoY;
        this.inicial = inicial;
    }

    public abstract void bater(Robo robo);

    public char getInicial() {
        return inicial;
    }
    public int getPosicaoX() {
        return posicaoX;
    }
    public int getPosicaoY() {
        return posicaoY;
    }
    public int getId() {
        return id;
    }

}
