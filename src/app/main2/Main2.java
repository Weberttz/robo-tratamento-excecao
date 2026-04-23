package app.main2;

import board.Tabuleiro;
import exception.MovimentoInvalidoException;
import model.enums.Direcao;
import model.robos.Robo;

import java.util.Random;

public class Main2 {

    public static void main(String[] args) {

        int tamanho = 4;

        Tabuleiro tabuleiro = new Tabuleiro(tamanho, 3, 3);

        Robo robo1 = new Robo("Vermelho");
        Robo robo2 = new Robo("Azul");

        robo1.setX(0);
        robo1.setY(0);

        robo2.setX(0);
        robo2.setY(1);

        tabuleiro.adicionarRobo(robo1);
        tabuleiro.adicionarRobo(robo2);

        Random rand = new Random();

        tabuleiro.renderizar();

        boolean acabou = false;

        while (!acabou) {

            try {
                Direcao dir = Direcao.fromInt(rand.nextInt(4) + 1);
                System.out.println("\nRobo 1 tentou: " + dir);

                tabuleiro.moverRobo(robo1, dir);

            } catch (MovimentoInvalidoException e) {
                System.out.println("Robo 1 erro: " + e.getMessage());
            }

            tabuleiro.renderizar();
            pausar();

            if (robo1.encontrouAlimento(tabuleiro.getAlimentoX(), tabuleiro.getAlimentoY())) {
                System.out.println("Robo 1 venceu!");
                acabou = true;
                break;
            }

            try {
                Direcao dir = Direcao.fromInt(rand.nextInt(4) + 1);
                System.out.println("\nRobo 2 tentou: " + dir);

                tabuleiro.moverRobo(robo2, dir);

            } catch (MovimentoInvalidoException e) {
                System.out.println("Robo 2 erro: " + e.getMessage());
            }

            tabuleiro.renderizar();
            pausar();

            if (robo2.encontrouAlimento(tabuleiro.getAlimentoX(), tabuleiro.getAlimentoY())) {
                System.out.println("🏆 Robo 2 venceu!");
                acabou = true;
            }
        }

        System.out.println("\nResultado Final");

        System.out.println("Robo 1 (" + robo1.getCor() + ")");
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