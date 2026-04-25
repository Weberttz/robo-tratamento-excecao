package model.obstaculos;

import model.robos.Robo;

public abstract class Obstaculo {
    private final int id;
    private final int posicaoX;
    private final int posicaoY;

    protected Obstaculo(int id) {
        this.id = id;

    }

    public abstract void bater(Robo robo);

    public int getId() {
        return id;
    }
}
