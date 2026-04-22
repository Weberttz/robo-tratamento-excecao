package app;

import board.Tabuleiro;
import exception.MovimentoInvalidoException;
import model.Robo;

import java.util.Random;
import java.util.Scanner;

public class Main2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int alimentoX = sc.nextInt();
        int alimentoY = sc.nextInt();

        Tabuleiro tabuleiro = new Tabuleiro(4,
                alimentoX, alimentoY);
        Robo robo1 = new Robo("Azul", 4);
        Robo robo2 = new Robo("Azul", 4);

        tabuleiro.adicionarRobo(robo1);
        tabuleiro.adicionarRobo(robo2);

        robo2.setX(3);
        robo2.setX(3);

        boolean ganhou = false;
        while (!ganhou){
            tabuleiro.renderizar();

            Random random = new Random();

            int robo1Movimento = random.nextInt(4) + 1;

            try {
                System.out.println("Robo 1 -> " + robo1Movimento);
                robo1.mover(robo1Movimento);
                if(robo1.encontruoAlimento(alimentoX, alimentoY))  ganhou = true;
            }catch (MovimentoInvalidoException e){
                System.out.println("Inválido.");
            }

            int robo2Movimento = random.nextInt(4) + 1;

            try {
                System.out.println("Robo 2 -> " + robo2Movimento);
                robo2.mover(robo2Movimento);
                if(robo2.encontruoAlimento(alimentoX, alimentoY)) ganhou = true;
            }catch (MovimentoInvalidoException e){
                System.out.println("Inválido.");
            }

            System.out.println();
        }

        System.out.println("Robo 1 - > Válidos : " + robo1.getMovimentosValidos() + " | " + "Robo 1 -> Inválidos " + robo1.getMovimentosInvalidos());
        System.out.println("Robo 2 - > Válidos : " + robo2.getMovimentosValidos() + " | " + "Robo 2 -> Inválidos " + robo2.getMovimentosInvalidos());

    }
}
