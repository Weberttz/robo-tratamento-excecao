package com.projetorobo.service;

import com.projetorobo.controllers.TabuleiroController;
import com.projetorobo.model.board.Tabuleiro;
import com.projetorobo.model.enums.Dificuldade;
import com.projetorobo.model.obstaculos.Obstaculo;
import com.projetorobo.model.obstaculos.Pedra;
import com.projetorobo.view.TabuleiroView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class ObstaculoService {
    public void calcularObstaculos(Tabuleiro tabuleiro, Dificuldade dificuldade, AnchorPane containerTabuleiro,
                                   TabuleiroView tabuleiroView, Image imagePedra, Image imageBomba){
        tabuleiro.colocarObstaculos(dificuldade);
        ImageView imageView;
        for(Obstaculo obstaculo: tabuleiro.getObstaculos()){
            if(obstaculo instanceof Pedra)
                imageView = new ImageView(imagePedra);
            else
                imageView = new ImageView(imageBomba);

            tabuleiroView.settarObstaculoNoAnchorPane(containerTabuleiro, imageView, obstaculo);
        }
    }
}
