package model.obstaculos;

import model.robos.Robo;

public class Bomba extends Obstaculo{

    private boolean explodiu;

    public Bomba(int id, int posicaoX, int posicaoY) {
        super(id, posicaoX, posicaoY, 'B');
        this.explodiu = false;
    }

    @Override
    public void bater(Robo robo) {
        robo.combustaoInstantanea();
        System.out.println("BOOOOOOOOOOOOOOOOM o " + robo.getCor() + " EXPLODIU");
        this.explodiu = true;
    }

    // como usar?
    public boolean isExplodiu() {
        return explodiu;
    }

}
