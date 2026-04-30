package com.projetorobo.app;

import com.projetorobo.board.Tabuleiro;
import com.projetorobo.exception.AlimentoForaDoLimiteException;
import com.projetorobo.exception.ColisaoComObstaculoException;
import com.projetorobo.exception.MovimentoInvalidoException;
import com.projetorobo.model.enums.Direcao;
import com.projetorobo.model.robos.Robo;
import com.sun.tools.javac.Main;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int tamanhoTabuleiro = 4;
        int alimentoX = 0;
        int alimentoY = 0;

        System.out.print("Cor do robô: ");
        Robo robo = new Robo(sc.next());
        boolean alimentoPosicionado = false;
        while(!alimentoPosicionado) {
            try {
                System.out.print("X do alimento (0-3): ");
                alimentoX = sc.nextInt();
                System.out.print("Y do alimento (0-3): ");
                alimentoY = sc.nextInt();

                if(alimentoX < tamanhoTabuleiro && alimentoY < tamanhoTabuleiro)
                    alimentoPosicionado = true;
                else
                    throw new AlimentoForaDoLimiteException();

            } catch (InputMismatchException exception) {
                System.out.println("Valores inválidos");
                sc.nextLine();  // Limpa o buffer
            } catch (AlimentoForaDoLimiteException exception){
                System.out.println(exception.getMessage());
            }
        }

        Tabuleiro tabuleiro = new Tabuleiro(tamanhoTabuleiro, alimentoX, alimentoY);
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