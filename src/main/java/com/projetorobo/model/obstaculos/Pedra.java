package com.projetorobo.model.obstaculos;

import com.projetorobo.model.enums.ResultadoBater;
import com.projetorobo.model.robo.Robo;

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
