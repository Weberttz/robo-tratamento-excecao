package model.obstaculos;

import model.enums.ResultadoBater;
import model.robos.Robo;

public class Pedra extends Obstaculo{

    public Pedra(int id, int posicaoX, int posicaoY) {
        super(id, posicaoX, posicaoY, 'P');
    }

    @Override
    public ResultadoBater bater(Robo robo) {
        robo.desfazerMovimento();
        return ResultadoBater.VOLTOU;
    }
}
