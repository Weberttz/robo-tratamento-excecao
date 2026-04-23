package app.main2;

import board.Tabuleiro;
import model.robos.Robo;
import model.enums.Direcao;

import java.util.Random;
import java.util.Scanner;

public class Main2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        System.out.println("Digite a cor do robô 1 : ");
        String corRobo1 = sc.next();
        System.out.println("Digite a cor do robô 2 : ");
        String corRobo2 = sc.next();

        Robo robo1 = new Robo(corRobo1);
        Robo robo2 = new Robo(corRobo2);

        System.out.println("Digite a posição x do alimento : ");
        int posicaoAlimentoX = sc.nextInt();

        System.out.println("Digite a posição y do alimento : ");
        int posicaoAlimentoY = sc.nextInt();

        Tabuleiro tabuleiro = new Tabuleiro(4, posicaoAlimentoX, posicaoAlimentoY);

        tabuleiro.adicionarRobo(robo1);
        tabuleiro.adicionarRobo(robo2);

        boolean acabou = false;
        Robo vencedor = null;

        tabuleiro.renderizar();

        while (!acabou){
            try {
                Direcao dir = Direcao.fromInt(rand.nextInt(4) + 1);
                tabuleiro.moverRobo(robo1, dir);
            } catch (Exception e) {
                System.out.println("Errou.");
            }
            if (robo1.encontruoAlimento(posicaoAlimentoX, posicaoAlimentoY)) {
                vencedor = robo1;
                acabou = true;
            }

            tabuleiro.renderizar();
            System.out.println();
            pausar();

            if (acabou) break;

            try {
                Direcao dir = Direcao.fromInt(rand.nextInt(4) + 1);
                tabuleiro.moverRobo(robo2, dir);
            } catch (Exception e) {
                System.out.println("Errou.");
            }

            if (robo2.encontruoAlimento(posicaoAlimentoX, posicaoAlimentoY)) {
                vencedor = robo2;
                acabou = true;
            }

            tabuleiro.renderizar();
            System.out.println();
            pausar();
        }
        System.out.println("Vencedor: " + vencedor.getCor());

        System.out.println("\nRobo 1 (" + robo1.getCor() + ")");
        System.out.println("Válidos: " + robo1.getMovimentosValidos());
        System.out.println("Inválidos: " + robo1.getMovimentosInvalidos());

        System.out.println("\nRobo 2 (" + robo2.getCor() + ")");
        System.out.println("Válidos: " + robo2.getMovimentosValidos());
        System.out.println("Inválidos: " + robo2.getMovimentosInvalidos());
    }

    private static void pausar() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}



