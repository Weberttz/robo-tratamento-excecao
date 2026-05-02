package com.projetorobo.model.robos;

import com.projetorobo.model.enums.Direcao;
import java.util.HashSet;
import java.util.Set;

public class RoboInteligente extends Robo {
    private final Set<Direcao> direcoesInvalidas;

    public RoboInteligente(String cor) {
        super(cor);
        this.direcoesInvalidas = new HashSet<>();
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
        direcoesInvalidas.add(dir);
    }

    @Override
    public void confirmarMovimento() {
        direcoesInvalidas.clear();
    }
}