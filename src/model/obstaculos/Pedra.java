package model.obstaculos;

import model.robos.Robo;

public class Pedra extends Obstaculo{

    public Pedra(int id, int posicaoX, int posicaoY) {
        super(id, posicaoX, posicaoY, 'P');
    }

    @Override
    public void bater(Robo robo){
//        throw new ColisaoComObstaculoException();
        robo.desfazerMovimento();
    }
}
