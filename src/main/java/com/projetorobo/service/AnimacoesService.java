package com.projetorobo.service;

import com.projetorobo.model.enums.Direcao;
import com.projetorobo.model.robo.Robo;
import com.projetorobo.view.TabuleiroView;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Objects;

public class AnimacoesService {
    private Image frame1, frame2, frame3;
    private int tempoTrocaDeFrame;

    public AnimacoesService(int tempoTrocaDeFrame){
        this.tempoTrocaDeFrame = tempoTrocaDeFrame;
    }

    public void coletarFrames(Robo robo, Direcao direcao){
        frame1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/robos/" +
                robo.getCor().toString().toLowerCase() + "-" + direcao.name().toLowerCase() + "-1.png")));
        frame2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/robos/" +
                robo.getCor().toString().toLowerCase() + "-"  + direcao.name().toLowerCase() + "-2.png")));
        frame3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/robos/" +
                robo.getCor().toString().toLowerCase() + "-"  + direcao.name().toLowerCase() + "-3.png")));
    }

    public void andar(ImageView imageViewRobo, Direcao direcao, boolean deveMover, TabuleiroView tabuleiroView){
        new Thread(() -> {
            try {
                Thread.sleep(tempoTrocaDeFrame);
                Platform.runLater(() -> imageViewRobo.setImage(frame1));

                Thread.sleep(tempoTrocaDeFrame);
                Platform.runLater(() -> imageViewRobo.setImage(frame2));

                Thread.sleep(tempoTrocaDeFrame);
                Platform.runLater(() -> imageViewRobo.setImage(frame3));

                Thread.sleep(tempoTrocaDeFrame);
                Platform.runLater(() -> imageViewRobo.setImage(frame2));
                tabuleiroView.moverImageView(imageViewRobo, direcao, deveMover);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public void habilitarBotao(Button botao){
        new Thread(() -> {
            try {
                Thread.sleep(1500);
                Platform.runLater(() -> botao.setDisable(false));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public void limparColisaoComBomba(TabuleiroView tabuleiroView, ImageView imageViewRobo){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                ImageView objeto = tabuleiroView.procurarBombas(imageViewRobo);
                imageViewRobo.setVisible(false);
                objeto.setVisible(false);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void limparColisaoComAlimento(ImageView imageViewRobo){
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                imageViewRobo.setVisible(false);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void finalizarJogo(ImageView imageViewAlimento){
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                Platform.runLater(() -> imageViewAlimento.setVisible(false));
                Thread.sleep(1000);
                Platform.runLater(() -> ((Stage) imageViewAlimento.getScene().getWindow()).close());
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
