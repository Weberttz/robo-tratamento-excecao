package com.projetorobo.model.robo.estrategias;

import com.projetorobo.model.enums.Direcao;

import java.util.Random;

public class EstrategiaAleatoria implements EstrategiaMovimento {
    private final Random rand = new Random();

    @Override
    public Direcao escolherDirecao() {
        return Direcao.fromInt(rand.nextInt(4) + 1);
    }

    @Override
    public void registrarDirecaoInvalida(Direcao dir) {

    }

    @Override
    public void confirmarMovimento() {

    }
}
