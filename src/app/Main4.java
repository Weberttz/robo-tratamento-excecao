package app;

import board.Tabuleiro;
import exception.ColisaoComObstaculoException;
import exception.MovimentoInvalidoException;
import model.enums.Dificuldade;
import model.enums.Direcao;
import model.robos.Robo;
import model.robos.RoboInteligente;

import java.util.Scanner;

public class Main4 {
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
        inteligente.modificarPosicaoInicial(2, 2);

        System.out.print("Dificuldade (facil / medio / dificil): ");
        tabuleiro.colocarObstaculos(Dificuldade.fromString(sc.next().trim().toLowerCase()));

        System.out.println("\nInício:");
        tabuleiro.renderizar();

        boolean acabou = false;
        while (!acabou) {

            if (!normal.isExplodiu()) {
                Direcao dirNormal = normal.escolherDirecao();
                System.out.printf("%n[%s] tentou: %s%n", normal.getCor(), dirNormal);
                try {
                    tabuleiro.moverRobo(normal, dirNormal);
                    if (!normal.isExplodiu())
                        System.out.printf("[%s] está em (%d,%d)%n", normal.getCor(), normal.getNewX(), normal.getNewY());
                } catch (MovimentoInvalidoException | ColisaoComObstaculoException e) {
                    System.out.printf("[%s] %s%n", normal.getCor(), e.getMessage());
                }
                tabuleiro.renderizar();
                pausar();

                if (tabuleiro.verificarAlimento(normal)) {
                    System.out.println("\n" + normal.getCor() + " (Normal) venceu!");
                    acabou = true;
                }
            }

            if (!acabou && !inteligente.isExplodiu()) {
                Direcao dirInteligente = inteligente.escolherDirecao();
                System.out.printf("%n[%s] tentou: %s%n", inteligente.getCor(), dirInteligente);
                try {
                    tabuleiro.moverRobo(inteligente, dirInteligente);
                    if (!inteligente.isExplodiu())
                        System.out.printf("[%s] está em (%d,%d)%n", inteligente.getCor(), inteligente.getNewX(), inteligente.getNewY());
                } catch (MovimentoInvalidoException | ColisaoComObstaculoException e) {
                    System.out.printf("[%s] %s%n", inteligente.getCor(), e.getMessage());
                }
                tabuleiro.renderizar();
                pausar();

                if (tabuleiro.verificarAlimento(inteligente)) {
                    System.out.println("\n" + inteligente.getCor() + " (Inteligente) venceu!");
                    acabou = true;
                }
            }

            if (normal.isExplodiu() && inteligente.isExplodiu()) {
                System.out.println("\nOs dois robôs explodiram. Fim de jogo!");
                acabou = true;
            }
        }

        System.out.println("\nResultado Final :");
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