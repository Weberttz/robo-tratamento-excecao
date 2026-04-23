package app.main1;

import board.Tabuleiro;
import exception.MovimentoInvalidoException;
import model.robos.Robo;
import model.enums.Direcao;

import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite a cor do robô : ");
        String corRobo = sc.next();

        Robo robo = new Robo(corRobo);

        System.out.println("Digite a posição x do alimento : ");
        int posicaoAlimentoX = sc.nextInt();

        System.out.println("Digite a posição y do alimento : ");
        int posicaoAlimentoY = sc.nextInt();

        Tabuleiro tabuleiro = new Tabuleiro(4, posicaoAlimentoX, posicaoAlimentoY);

        tabuleiro.adicionarRobo(robo);

        boolean acabouJogo = false;

        tabuleiro.renderizar();
        while (!acabouJogo){
            System.out.println("Digite a orientação : ");
            String dir = sc.next();
            try {
                tabuleiro.moverRobo(robo, Direcao.fromString(dir));
            }catch (MovimentoInvalidoException e){
                System.out.println("Erro!");
            }
            acabouJogo = robo.encontrouAlimento(posicaoAlimentoX, posicaoAlimentoY);
            tabuleiro.renderizar();
            if(acabouJogo){
                System.out.println("Movimentos válidos : " + robo.getMovimentosValidos() +
                        " Movimentos inválidos : " + robo.getMovimentosInvalidos());
            }
        }
    }
}