package com.projetorobo.model.enums;

import com.projetorobo.model.robo.Robo;
import com.projetorobo.model.robo.estrategias.*;

public enum CategoriaRobo {
    INTELIGENTE,
    BURRO,
    MEMORIA,
    ESTRATEGISTA;

    public EstrategiaMovimento getEstrategia(Robo robo){
    switch (this) {
            case INTELIGENTE -> {
                return new EstrategiaInteligente();
            }
            case BURRO -> {
                return new EstrategiaAleatoria();
            }case MEMORIA -> {
                return new EstrategiaMemoria(robo.getNewX(), robo.getNewY());
            }// case ESTRATEGISTA -> {}
            default -> {
            return  null;
            }
        }
    }
}
