package com.projetorobo.app;

import com.projetorobo.model.board.Tabuleiro;
import com.projetorobo.exception.ColisaoComObstaculoException;
import com.projetorobo.exception.MovimentoInvalidoException;
import com.projetorobo.model.enums.Direcao;
import com.projetorobo.model.robo.Robo;
import com.projetorobo.model.robo.estrategias.EstrategiaAleatoria;
import com.projetorobo.model.robo.estrategias.EstrategiaMovimento;

public class Main2 {
    public static void main(String[] args) {
        Tabuleiro tabuleiro = new Tabuleiro(4, 3, 3);

        EstrategiaMovimento estrategiaMovimento = new EstrategiaAleatoria();

        Robo robo1 = new Robo("Vermelho", estrategiaMovimento);
        Robo robo2 = new Robo("Azul", estrategiaMovimento);

        robo2.modificarPosicaoInicial(0, 1);

        tabuleiro.adicionarRobo(robo1);
        tabuleiro.adicionarRobo(robo2);

        tabuleiro.renderizar();

        boolean acabou = false;
        while (!acabou) {
            if (jogarTurno(robo1, tabuleiro)) {
                System.out.println(robo1.getCor() + " venceu!");
                acabou = true;
            } else if (jogarTurno(robo2, tabuleiro)) {
                System.out.println(robo2.getCor() + " venceu!");
                acabou = true;
            }
        }

        imprimirResultado(robo1);
        imprimirResultado(robo2);
    }

    private static boolean jogarTurno(Robo robo, Tabuleiro tabuleiro) {
        Direcao dir = robo.getEstrategiaMovimento().escolherDirecao();
        System.out.printf("%n[%s] tentou: %s%n", robo.getCor(), dir);
        try {
            tabuleiro.moverRobo(robo, dir);
            System.out.printf("[%s] está em (%d,%d)%n", robo.getCor(), robo.getNewX(), robo.getNewY());
        } catch (MovimentoInvalidoException | ColisaoComObstaculoException e) {
            System.out.printf("[%s] %s%n", robo.getCor(), e.getMessage());
        }
        tabuleiro.renderizar();
        pausar();
        return tabuleiro.verificarAlimento(robo);
    }

    private static void imprimirResultado(Robo robo) {
        System.out.printf("%n[%s] válidos: %d | inválidos: %d%n",
                robo.getCor(), robo.getMovimentosValidos(), robo.getMovimentosInvalidos());
    }

    private static void pausar() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}