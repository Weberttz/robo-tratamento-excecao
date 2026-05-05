package com.projetorobo.model.robo.estrategias;

import com.projetorobo.model.board.Tabuleiro;
import com.projetorobo.model.enums.Direcao;
import com.projetorobo.model.robo.Robo;

import java.util.*;

public class EstrategiaEstrategica implements EstrategiaMovimento {
    private int posicaoAlimentoX;
    private int posicaoAlimentoY;
    private int posicaoXRobo;
    private int posicaoYRobo;
    private int verificarProximaPosicaoX;
    private int verificarProximaPosicaoY;
    private final Set<Direcao> direcoesInvalidas = new HashSet<>();
    private final Random rand = new Random();
    private Direcao dir;
    record Posicao(int posicaoX, int posicaoY){}
    private HashSet<Posicao> posicoesBloqueadas;

    public EstrategiaEstrategica(Robo robo, Tabuleiro tabuleiro) {
        this.posicoesBloqueadas = new HashSet<>();
        this.posicaoAlimentoX = tabuleiro.getAlimentoX();
        this.posicaoAlimentoY = tabuleiro.getAlimentoY();
        this.posicaoXRobo = robo.getNewX();
        this.posicaoYRobo = robo.getNewY();
    }

    @Override
    public Direcao escolherDirecao(){
        boolean aproximarAlimentoEmX, aproximarAlimentoEmY;
        Posicao novaPosicao;

        boolean taNaLinhaEtemPedra = (verificarArredores() == 1 && (posicaoAlimentoX == posicaoXRobo
                || posicaoAlimentoY == posicaoYRobo));

        if(verificarArredores() >= 2 || taNaLinhaEtemPedra) {
            posicoesBloqueadas.add(new Posicao(posicaoXRobo, posicaoYRobo));
            do {
                dir = Direcao.fromInt(rand.nextInt(4) + 1);
                verificarProximaPosicao(dir);
                novaPosicao = new Posicao(verificarProximaPosicaoX, verificarProximaPosicaoY);
            } while (posicoesBloqueadas.contains(novaPosicao));
            return dir;
        }

        do {
            if(posicaoAlimentoX == posicaoXRobo)
                dir = Direcao.fromInt(rand.nextInt(1, 3));
            else if(posicaoAlimentoY == posicaoYRobo)
                dir = Direcao.fromInt(rand.nextInt(3,5));
            else
                dir = Direcao.fromInt(rand.nextInt(4) + 1);

            verificarProximaPosicao(dir);

            aproximarAlimentoEmX = Math.abs(posicaoAlimentoX - verificarProximaPosicaoX) < Math.abs(posicaoAlimentoX - posicaoXRobo);
            aproximarAlimentoEmY = Math.abs(posicaoAlimentoY - verificarProximaPosicaoY) < Math.abs(posicaoAlimentoY - posicaoYRobo);
            novaPosicao = new Posicao(verificarProximaPosicaoX, verificarProximaPosicaoY);
        } while (posicoesBloqueadas.contains(novaPosicao) || (!aproximarAlimentoEmX && !aproximarAlimentoEmY));
        return dir;
    }

    @Override
    public void registrarDirecaoInvalida(Direcao dir) {
        //posicoesBloqueadas.add(new Posicao(verificarProximaPosicaoX, verificarProximaPosicaoY));
        switch (dir){
            case UP -> posicoesBloqueadas.add(new Posicao(posicaoXRobo, posicaoYRobo + 1));
            case DOWN -> posicoesBloqueadas.add(new Posicao(posicaoXRobo, posicaoYRobo - 1));
            case LEFT -> posicoesBloqueadas.add(new Posicao(posicaoXRobo - 1, posicaoYRobo));
            case RIGHT -> posicoesBloqueadas.add(new Posicao(posicaoXRobo + 1, posicaoYRobo));
        }
    }

    @Override
    public void confirmarMovimento() {
        proximaPosicao(dir);
    }

    private int verificarArredores(){
        int contadorBloqueadas = 0;
        Posicao cima = new Posicao(posicaoXRobo, posicaoYRobo + 1);
        Posicao baixo = new Posicao(posicaoXRobo, posicaoYRobo - 1);
        Posicao esquerda = new Posicao(posicaoXRobo - 1, posicaoYRobo);
        Posicao direita = new Posicao(posicaoXRobo + 1, posicaoYRobo);
        List<Posicao> posicoes = new ArrayList<>();
        posicoes.add(cima);
        posicoes.add(baixo);
        posicoes.add(esquerda);
        posicoes.add(direita);

        for(Posicao posicao: posicoes){
            if(posicoesBloqueadas.contains(posicao))
                contadorBloqueadas++;
        }
        if(contadorBloqueadas == 4) {
            for (Posicao p : posicoes) {
                posicoesBloqueadas.remove(p);
            }
        }

        return contadorBloqueadas;
    }

    private void verificarProximaPosicao(Direcao dir){
        verificarProximaPosicaoX = posicaoXRobo;
        verificarProximaPosicaoY = posicaoYRobo;
        switch (dir){
            case UP -> verificarProximaPosicaoY = posicaoYRobo + 1;
            case DOWN -> verificarProximaPosicaoY = posicaoYRobo - 1;
            case LEFT -> verificarProximaPosicaoX = posicaoXRobo - 1;
            case RIGHT -> verificarProximaPosicaoX = posicaoXRobo + 1;
        }
    }

    private void proximaPosicao(Direcao dir){
        switch (dir){
            case UP -> posicaoYRobo++;
            case DOWN -> posicaoYRobo--;
            case LEFT -> posicaoXRobo--;
            case RIGHT -> posicaoXRobo++;
        }
    }

    public int getPosicaoAlimentoX() {
        return posicaoAlimentoX;
    }

    public int getPosicaoAlimentoY() {
        return posicaoAlimentoY;
    }

}