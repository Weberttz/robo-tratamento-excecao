package com.projetorobo.model.robo.estrategias;

import com.projetorobo.model.enums.Direcao;
import com.projetorobo.model.robo.estrategias.EstrategiaMovimento;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class EstrategiaEstrategica implements EstrategiaMovimento {
    private final int posicaoAlimentoX;
    private final int posicaoAlimentoY;
    private final Set<Direcao> direcoesInvalidas = new HashSet<>();
    private final Random rand = new Random();

    public EstrategiaEstrategica(int posicaoAlimentoX, int posicaoAlimentoY) {
        this.posicaoAlimentoX = posicaoAlimentoX;
        this.posicaoAlimentoY = posicaoAlimentoY;
    }

    @Override
    public Direcao escolherDirecao() {
        Direcao dir;
        do {
            if (direcoesInvalidas.size() == 4){
                direcoesInvalidas.clear();
            }
            dir = Direcao.fromInt(rand.nextInt(4) + 1);
        } while (direcoesInvalidas.contains(dir));
        return dir;
    }

    @Override
    public void registrarDirecaoInvalida(Direcao dir) {

    }

    @Override
    public void confirmarMovimento() {

    }

    public int getPosicaoAlimentoX() {
        return posicaoAlimentoX;
    }

    public int getPosicaoAlimentoY() {
        return posicaoAlimentoY;
    }

}