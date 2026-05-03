package com.projetorobo.model.robo;

import com.projetorobo.model.enums.*;
import com.projetorobo.model.robo.estrategias.EstrategiaMovimento;

import java.util.Random;

public class Robo {
    private int newX;
    private int newY;
    private int oldX;
    private int oldY;
    private boolean achouAlimento;
    private Cor cor;
    private boolean explodiu;
    private int movimentosValidos;
    private int movimentosInvalidos;
    private EstrategiaMovimento estrategiaMovimento;

    public Robo(String cor, EstrategiaMovimento estrategiaMovimento) {
        this.cor = Cor.fromString(cor);
        this.newX = 0;
        this.newY = 0;
        this.oldX = 0;
        this.oldY = 0;
        this.estrategiaMovimento = estrategiaMovimento;
        this.explodiu = false;
        achouAlimento = false;
    }

    public void mover(Direcao dir) {
        oldX = newX;
        oldY = newY;
        switch (dir) {
            case UP    -> newY++;
            case DOWN  -> newY--;
            case RIGHT -> newX++;
            case LEFT  -> newX--;
        }
    }

    public void combustaoInstantanea(){
        this.explodiu = true;
    }

    public void desfazerMovimento() {
        this.newX = oldX;
        this.newY = oldY;
    }

    public void incrementarValidos()   { movimentosValidos++; }
    public void incrementarInvalidos() { movimentosInvalidos++; }
    public void modificarPosicaoInicial(int newX, int newY){
        this.newX = newX;
        this.newY = newY;
        this.oldX = newX;
        this.oldY = newY;
    }
    public boolean isExplodiu() { return explodiu; }
    public void setExplodiu(boolean explodiu) { this.explodiu = explodiu;}
    public boolean getAchouAlimento(){
        return achouAlimento;
    }
    public void setAchouAlimento(boolean achouAlimento){
        this.achouAlimento = achouAlimento;
    }
    public int getNewX() { return newX; }
    public int getNewY() { return newY; }
    public Cor getCor() { return cor; }
    public int getMovimentosValidos()   { return movimentosValidos; }
    public int getMovimentosInvalidos() { return movimentosInvalidos; }
    public EstrategiaMovimento getEstrategiaMovimento() { return estrategiaMovimento; }

    public void setEstrategiaMovimento(EstrategiaMovimento estrategiaMovimento) { this.estrategiaMovimento = estrategiaMovimento; }
}