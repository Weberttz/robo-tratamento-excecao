package com.projetorobo.app;

import com.projetorobo.board.Tabuleiro;
import com.projetorobo.exception.ColisaoComObstaculoException;
import com.projetorobo.exception.MovimentoInvalidoException;
import com.projetorobo.model.enums.Direcao;
import com.projetorobo.model.robos.Robo;

import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Cor do robô: ");
        Robo robo = new Robo(sc.next());

        System.out.print("X do alimento (0-3): ");
        int alimentoX = sc.nextInt();
        System.out.print("Y do alimento (0-3): ");
        int alimentoY = sc.nextInt();

        Tabuleiro tabuleiro = new Tabuleiro(4, alimentoX, alimentoY);
        tabuleiro.adicionarRobo(robo);
        tabuleiro.renderizar();

        while (!tabuleiro.verificarAlimento(robo)) {
            System.out.print("Direção (up/down/left/right): ");
            try {
                Direcao dir = Direcao.fromString(sc.next());
                tabuleiro.moverRobo(robo, dir);
                System.out.printf("Robô em (%d,%d)%n", robo.getNewX(), robo.getNewY());
            } catch (MovimentoInvalidoException | ColisaoComObstaculoException e){
                System.out.printf("[%s] %s%n", robo.getCor(), e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Direção desconhecida. Use: up, down, left, right");
            }
            tabuleiro.renderizar();
        }

        System.out.println("Alimento encontrado!");
        System.out.printf("Válidos: %d | Inválidos: %d%n",
                robo.getMovimentosValidos(), robo.getMovimentosInvalidos());

        sc.close();
    }
}