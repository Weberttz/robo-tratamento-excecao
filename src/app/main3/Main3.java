package app.main3;

import board.Tabuleiro;
import model.enums.Direcao;
import model.robos.Robo;
import model.robos.RoboInteligente;

import java.util.Random;
import java.util.Scanner;

public class Main3 {
    static void main() {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        System.out.println("Digite a cor do robô inteligente : ");
        String corRoboInteligente = sc.next();
        System.out.println("Digite a cor do robô BURRO : ");
        String corRoboBURRO = sc.next();

        Robo roboInteligente = new RoboInteligente(corRoboInteligente);
        Robo roboBurro = new Robo(corRoboBURRO);

        System.out.println("Digite a posição x do alimento : ");
        int posicaoAlimentoX = sc.nextInt();

        System.out.println("Digite a posição y do alimento : ");
        int posicaoAlimentoY = sc.nextInt();

        Tabuleiro tabuleiro = new Tabuleiro(4, posicaoAlimentoX, posicaoAlimentoY);

        tabuleiro.adicionarRobo(roboInteligente);
        tabuleiro.adicionarRobo(roboBurro);

        tabuleiro.renderizar();

        boolean burroAchou = false;
        boolean inteligenteAchou = false;

        while (!burroAchou || !inteligenteAchou) {
            if (!burroAchou) {
                try {
                    Direcao dir = Direcao.fromInt(rand.nextInt(4) + 1);
                    tabuleiro.moverRobo(roboBurro, dir);
                } catch (Exception e) {
                    System.out.println("Robô burro errou.");
                }
                burroAchou = roboBurro.encontruoAlimento(posicaoAlimentoX, posicaoAlimentoY);
            }

            if (!inteligenteAchou) {
                try {
                    Direcao dir = Direcao.fromInt(rand.nextInt(4) + 1);
                    tabuleiro.moverRobo(roboInteligente, dir);
                } catch (Exception e) {
                    System.out.println("Robô inteligente errou.");
                }
                inteligenteAchou = roboInteligente.encontruoAlimento(posicaoAlimentoX, posicaoAlimentoY);
            }

            tabuleiro.renderizar();
            pausar();
        }

        System.out.println("\nRobo 1 (" + roboInteligente.getCor() + ")");
        System.out.println("Válidos: " + roboInteligente.getMovimentosValidos());
        System.out.println("Inválidos: " + roboInteligente.getMovimentosInvalidos());

        System.out.println("\nRobo 2 (" + roboBurro.getCor() + ")");
        System.out.println("Válidos: " + roboBurro.getMovimentosValidos());
        System.out.println("Inválidos: " + roboBurro.getMovimentosInvalidos());
    }

    private static void pausar() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
