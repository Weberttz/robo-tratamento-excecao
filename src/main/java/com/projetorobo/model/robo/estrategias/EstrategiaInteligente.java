package com.projetorobo.model.robo.estrategias;

import com.projetorobo.model.enums.Direcao;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class EstrategiaInteligente implements EstrategiaMovimento{
    private final Set<Direcao> direcoesInvalidas = new HashSet<>();
    private final Random rand = new Random();

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
    public void registrarDirecaoInvalida(Direcao dir) { direcoesInvalidas.add(dir); }

    @Override
    public void confirmarMovimento() {direcoesInvalidas.clear();}
}
