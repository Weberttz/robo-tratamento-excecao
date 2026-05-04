package com.projetorobo.model.robo.estrategias;

import com.projetorobo.model.enums.Direcao;
import com.projetorobo.model.obstaculos.Pedra;
import javafx.geometry.Pos;

import java.util.*;

public class EstrategiaMemoria implements EstrategiaMovimento{
    private final Set<Direcao> direcoesInvalidas = new HashSet<>();
    private Random rand;
    private int posicaoXRobo;
    private int posicaoYRobo;
    private int verificarProximaPosicaoX;
    private int verificarProximaPosicaoY;
    private Direcao dir;

    record Posicao(int posicaoX, int posicaoY){}
    private HashSet<Posicao> posicoesBloqueadas;
    private HashSet<Posicao> posicoesPassadas;

    public EstrategiaMemoria(int posicaoXRobo, int posicaoYRobo){
        posicoesBloqueadas = new HashSet<>();
        posicoesPassadas = new HashSet<>();
        rand = new Random();
        this.posicaoXRobo = posicaoXRobo;
        this.posicaoYRobo = posicaoYRobo;
    }

    @Override
    public Direcao escolherDirecao(){
        posicoesPassadas.add(new Posicao(posicaoXRobo, posicaoYRobo));
        Posicao novaPosicao;
        do {
            verificarArredores();
            dir = Direcao.fromInt(rand.nextInt(4) + 1);
            verificarProximaPosicao(dir);
            novaPosicao = new Posicao(verificarProximaPosicaoX, verificarProximaPosicaoY);
        }while (posicoesBloqueadas.contains(novaPosicao) || posicoesPassadas.contains(novaPosicao));
        return dir;
    }

    @Override
    public void registrarDirecaoInvalida(Direcao dir) {
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

    private void verificarArredores(){
       int contadorBloqueadas = 0;
       int contadorPassadas = 0;
       Posicao cima = new Posicao(posicaoXRobo, posicaoYRobo + 1);
       Posicao baixo = new Posicao(posicaoXRobo, posicaoYRobo - 1);
       Posicao esquerda = new Posicao(posicaoXRobo - 1, posicaoYRobo);
       Posicao direita = new Posicao(posicaoXRobo + 1, posicaoYRobo);
       List<Posicao> posicoes = new ArrayList<>();
       posicoes.add(cima);
       posicoes.add(baixo);
       posicoes.add(esquerda);
       posicoes.add(direita);
       for (Posicao p : posicoes){
           if (posicoesBloqueadas.contains(p))
               contadorBloqueadas++;
           if(posicoesPassadas.contains(p))
               contadorPassadas++;
       }

       if (contadorBloqueadas == 4){
           for (Posicao p : posicoes){
               posicoesBloqueadas.remove(p);
           }
       }else if(contadorPassadas == 4){
           for (Posicao p : posicoes){
               posicoesPassadas.remove(p);
           }
       } else if ((contadorPassadas + contadorBloqueadas) == 4){
           for (Posicao p : posicoes){
               posicoesPassadas.removeIf(posicao -> posicao.equals(p));
           }
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
}
