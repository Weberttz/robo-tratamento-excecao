package model.robos;

import exception.MovimentoInvalidoException;
import model.enums.Direcao;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RoboInteligente extends Robo{
    private Set<Direcao> direcoesInvalidas;
    private final Random rand;

    public RoboInteligente(String cor) {
        super(cor);
        this.direcoesInvalidas = new HashSet<>();
        rand = new Random();
    }

    @Override
    public void mover(Direcao dir) throws MovimentoInvalidoException {
        boolean moveuValido = false;

        while (!moveuValido) {

            while (direcoesInvalidas.contains(dir)) {
                dir = Direcao.fromInt(rand.nextInt(4) + 1);
            }

            try {
                super.mover(dir);
                direcoesInvalidas.clear();
                moveuValido = true;
            } catch (MovimentoInvalidoException e) {
                direcoesInvalidas.add(dir);
            }

        }
    }
}
