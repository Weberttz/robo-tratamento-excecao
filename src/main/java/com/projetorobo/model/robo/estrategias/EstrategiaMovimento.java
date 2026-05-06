package com.projetorobo.model.robo.estrategias;

import com.projetorobo.model.enums.Direcao;

public interface EstrategiaMovimento {
    public Direcao escolherDirecao();
    public void registrarDirecaoInvalida(Direcao dir);
    public void confirmarMovimento();
}
