package com.projetorobo.model.obstaculos;

import com.projetorobo.model.enums.ResultadoBater;
import com.projetorobo.model.robo.Robo;

public class Bomba extends Obstaculo{

    public Bomba(int id, int posicaoX, int posicaoY) {
        super(id, posicaoX, posicaoY, 'B');
    }

    @Override
    public ResultadoBater bater(Robo robo) {
        robo.combustaoInstantanea();
        System.out.println("BOOOOOOOOOOOOOOOOM o " + robo.getCor() + " EXPLODIU");
        return ResultadoBater.EXPLODIU;
    }

}
