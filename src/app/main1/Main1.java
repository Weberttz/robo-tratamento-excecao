package app.main1;

import board.Tabuleiro;
import exception.MovimentoInvalidoException;
import model.Robo;

import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int alimentoX = sc.nextInt();
        int alimentoY = sc.nextInt();

        Tabuleiro tabuleiro = new Tabuleiro(4, alimentoX, alimentoY);
        Robo robo = new Robo("Azul", 4);

        tabuleiro.adicionarRobo(robo);


        while (!robo.encontruoAlimento(alimentoX, alimentoY)){
            System.out.println();
            tabuleiro.renderizar();
            System.out.println("digite oq vc quer fazer: ");
            try {
                int pos = sc.nextInt();
                robo.mover(pos);
            } catch (MovimentoInvalidoException e) {
                System.out.println("NAO PODE PORRA");
            }
        }

        System.out.println("Você ganhou!");
    }
}