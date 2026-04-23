package model.robos;

import exception.MovimentoInvalidoException;
import model.enums.Direcao;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RoboInteligente extends Robo{
    private final Set<Direcao> direcoesInvalidas;
    private final Random rand;

    public RoboInteligente(String cor) {
        super(cor);
        this.direcoesInvalidas = new HashSet<>();
        rand = new Random();
    }


    public Set<Direcao> getDirecoesInvalidas() {
        return direcoesInvalidas;
    }
    public Random getRand() {
        return rand;
    }

}