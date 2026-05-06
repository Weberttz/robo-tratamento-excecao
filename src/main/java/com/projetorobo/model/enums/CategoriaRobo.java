package com.projetorobo.model.enums;

import com.projetorobo.model.board.Tabuleiro;
import com.projetorobo.model.robo.Robo;
import com.projetorobo.model.robo.estrategias.*;

public enum CategoriaRobo {
    INTELIGENTE,
    BURRO,
    MEMORIA,
    ESTRATEGISTA;

    public EstrategiaMovimento getEstrategia(Robo robo, Tabuleiro tabuleiro){
    switch (this) {
            case INTELIGENTE -> {
                return new EstrategiaInteligente();
            }
            case BURRO -> {
                return new EstrategiaAleatoria();
            }case MEMORIA -> {
                return new EstrategiaMemoria(robo);
            }
            case ESTRATEGISTA -> {
                return new EstrategiaEstrategica(robo, tabuleiro);
            }
            default -> {
                return  null;
            }
        }
    }
}
