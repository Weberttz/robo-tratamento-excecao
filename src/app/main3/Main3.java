package app.main3;

import board.Tabuleiro;
import exception.MovimentoInvalidoException;
import model.enums.Direcao;
import model.robos.Robo;
import model.robos.RoboInteligente;

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

        Tabuleiro tabuleiro = new Tabuleiro(4, alimentoX, alimentoY);
        tabuleiro.adicionarRobo(normal);
        tabuleiro.adicionarRobo(inteligente);

        inteligente.desfazerMovimento(0, 1);

        System.out.println("\nInício:");
        tabuleiro.renderizar();

        boolean acabou = false;
        while (!acabou) {

            Direcao dirNormal = normal.escolherDirecao();
            System.out.printf("%n[%s] tentou: %s%n", normal.getCor(), dirNormal);
            try {
                tabuleiro.moverRobo(normal, dirNormal);
                System.out.printf("[%s] está em (%d,%d)%n", normal.getCor(), normal.getX(), normal.getY());
            } catch (MovimentoInvalidoException e) {
                System.out.printf("[%s] inválido: %s%n", normal.getCor(), e.getMovimento());
            }
            tabuleiro.renderizar();
            pausar();

            if (tabuleiro.verificarAlimento(normal)) {
                System.out.println("\n" + normal.getCor() + " (Normal) venceu!");
                acabou = true;
                break;
            }

            boolean conseguiu = false;
            while (!conseguiu) {
                Direcao dirInteligente = inteligente.escolherDirecao();
                System.out.printf("%n[%s] tentou: %s%n", inteligente.getCor(), dirInteligente);
                try {
                    tabuleiro.moverRobo(inteligente, dirInteligente);
                    System.out.printf("[%s] está em (%d,%d)%n", inteligente.getCor(), inteligente.getX(), inteligente.getY());
                    conseguiu = true;
                } catch (MovimentoInvalidoException e) {
                    System.out.printf("[%s] inválido: %s%n", inteligente.getCor(), e.getMovimento());
                }

                tabuleiro.renderizar();
                pausar();
            }
            if (tabuleiro.verificarAlimento(inteligente)) {
                System.out.println("\n" + inteligente.getCor() + " (Inteligente) venceu!");
                acabou = true;
            }
        }

        System.out.println("\n=== Resultado Final ===");
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