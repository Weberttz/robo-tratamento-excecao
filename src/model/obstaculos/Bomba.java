package model.obstaculos;

import model.robos.Robo;

public class Bomba extends Obstaculo{

    private boolean explodiu;

    protected Bomba(int id) {
        super(id);
        this.explodiu = false;
    }

    @Override
    public void bater(Robo robo) {
        //fazer algo no robô para ele ficar inativo
        // robo.explodir() -> boolean ativo = false
        this.explodiu = true;
    }

    public boolean isExplodiu() {
        return explodiu;
    }

}
