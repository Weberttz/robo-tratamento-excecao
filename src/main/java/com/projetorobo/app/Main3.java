package com.projetorobo.app;

import com.projetorobo.board.Tabuleiro;
import com.projetorobo.exception.ColisaoComObstaculoException;
import com.projetorobo.exception.MovimentoInvalidoException;
import com.projetorobo.model.enums.Direcao;
import com.projetorobo.model.robos.Robo;
import com.projetorobo.model.robos.RoboInteligente;

import java.util.Scanner;

public class Main3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("X do alimento (0-3): ");
        int alimentoX = sc.nextInt();
        System.out.print("Y do alimento (0-3): ");
        int alimentoY = sc.nextInt();

        Robo normal = new Robo("Azul");
        RoboInteligente inteligente = new RoboInteligente("Verde");

        Tabuleiro tabuleiro = new Tabuleiro(7, alimentoX, alimentoY);
        tabuleiro.adicionarRobo(normal);
        tabuleiro.adicionarRobo(inteligente);

        inteligente.modificarPosicaoInicial(0, 1);

        System.out.println("\nInício:");
        tabuleiro.renderizar();

        boolean acabou = false;
        while (!acabou) {
            if(!tabuleiro.verificarAlimento(normal)) {
                Direcao dirNormal = normal.escolherDirecao();
                System.out.printf("%n[%s] tentou: %s%n", normal.getCor(), dirNormal);
                try {
                    tabuleiro.moverRobo(normal, dirNormal);
                    System.out.printf("[%s] está em (%d,%d)%n", normal.getCor(), normal.getNewX(), normal.getNewY());
                } catch (MovimentoInvalidoException | ColisaoComObstaculoException e) {
                    System.out.printf("[%s] %s%n", normal.getCor(), e.getMessage());
                }
                tabuleiro.renderizar();
                pausar();

                if (tabuleiro.verificarAlimento(normal)) {
                    System.out.println("\n" + normal.getCor() + " (Normal) achou o alimento!");
                    tabuleiro.getRobos().remove(normal);
                }
            }

            if(!tabuleiro.verificarAlimento(inteligente)) {
                //mover essa lógica de !conseguir para Tabuleiro
                boolean conseguiu = false;
                while (!conseguiu) {
                    Direcao dirInteligente = inteligente.escolherDirecao();
                    System.out.printf("%n[%s] tentou: %s%n", inteligente.getCor(), dirInteligente);
                    try {
                        tabuleiro.moverRobo(inteligente, dirInteligente);
                        System.out.printf("[%s] está em (%d,%d)%n", inteligente.getCor(), inteligente.getNewX(), inteligente.getNewY());
                        conseguiu = true;
                    } catch (MovimentoInvalidoException | ColisaoComObstaculoException e) {
                        System.out.printf("[%s] %s%n", inteligente.getCor(), e.getMessage());
                    }

                    tabuleiro.renderizar();
                    pausar();
                }
                if (tabuleiro.verificarAlimento(inteligente)) {
                    System.out.println("\n" + inteligente.getCor() + " (Inteligente) achou o alimento!");
                    tabuleiro.getRobos().remove(inteligente);
                }
            }
            if(tabuleiro.verificarAlimento(normal) && tabuleiro.verificarAlimento(inteligente)){
                acabou = true;
            }
        }

        System.out.println("\nResultado Final");
        System.out.printf("[%s] válidos: %d | inválidos: %d%n",
                normal.getCor(), normal.getMovimentosValidos(), normal.getMovimentosInvalidos());
        System.out.printf("[%s] válidos: %d | inválidos: %d%n",
                inteligente.getCor(), inteligente.getMovimentosValidos(), inteligente.getMovimentosInvalidos());

        sc.close();
    }

    private static void pausar() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}