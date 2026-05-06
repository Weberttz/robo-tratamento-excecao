package com.projetorobo.view;

import com.projetorobo.controllers.TelaResultadoController;
import com.projetorobo.model.board.Tabuleiro;
import com.projetorobo.model.enums.Direcao;
import com.projetorobo.model.enums.Modo;
import com.projetorobo.model.obstaculos.Obstaculo;
import com.projetorobo.model.robo.Robo;
import com.projetorobo.service.AnimacoesService;
import com.projetorobo.util.AlertaUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

public class TabuleiroView {

    private int pixels = 35;
    private int movimento = 35;
    private int posInicialX = 0;
    private int posInicialY = 315;
    private final Image alimentoImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/pizza.png")));
    private final Image bombaImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/bomba.png")));
    private final Image pedraImg = new Image(Objects.requireNonNull(AnimacoesService.class.getResourceAsStream("/imagens/rocha2.png")));

    private final ImageView imageViewAlimento = new ImageView(alimentoImg);
    private final ImageView imageViewRobo1, imageViewRobo2;
    private final AnimacoesService animacoesService;
    private final AnchorPane containerTabuleiro;
    private final ListView<String> listaHistorico;

    public TabuleiroView(ImageView imageViewRobo1, ImageView imageViewRobo2,
                         AnchorPane containerTabuleiro, ListView<String> listaHistorico, AnimacoesService animacoesService){
        this.imageViewRobo1 = imageViewRobo1;
        this.imageViewRobo2 = imageViewRobo2;
        this.animacoesService = animacoesService;
        this.containerTabuleiro = containerTabuleiro;
        this.listaHistorico = listaHistorico;
    }

    public void moverImageView(ImageView imageViewRobo, Direcao direcao, boolean deveMover){
        if(deveMover) {
            switch (direcao) {
                case UP:
                    imageViewRobo.setLayoutY(imageViewRobo.getLayoutY() - movimento);
                    break;
                case DOWN:
                    imageViewRobo.setLayoutY(imageViewRobo.getLayoutY() + movimento);
                    break;
                case LEFT:
                    imageViewRobo.setLayoutX(imageViewRobo.getLayoutX() - movimento);
                    break;
                case RIGHT:
                    imageViewRobo.setLayoutX(imageViewRobo.getLayoutX() + movimento);
                    break;
            }
        }
    }

    public void direcionarImageViewRobo(Robo robo, ImageView imageViewRobo, Direcao direcao, boolean deveMover){
        animacoesService.coletarFrames(robo, direcao);
        animacoesService.andar(imageViewRobo, direcao, deveMover, this);
    }

    // todo elemento do jogo é um ImageView no AnchorPane

    public void settarAlimentoNoAnchorPane(Tabuleiro tabuleiro){
        imageViewAlimento.setPreserveRatio(true); // preservar o corpo da imagem
        imageViewAlimento.setSmooth(true); // preservar a qualidade
        imageViewAlimento.setFitHeight(pixels);
        imageViewAlimento.setFitWidth(pixels);

        containerTabuleiro.getChildren().add(imageViewAlimento);// adicionar a imagem no anchorPane

        AnchorPane.setLeftAnchor(imageViewAlimento, null); // não puxar a imagem para lateralEsquerda
        AnchorPane.setTopAnchor(imageViewAlimento, null); // não puxar a imagem para o topo
        imageViewAlimento.setLayoutX(posInicialX + (tabuleiro.getAlimentoX() * movimento));
        imageViewAlimento.setLayoutY(posInicialY - (tabuleiro.getAlimentoY() * movimento));
    }

    public void settarRoboNoAnchorPane(Robo robo, ImageView imageViewRobo){
        imageViewRobo.setImage(new Image(Objects.requireNonNull(TabuleiroView.class.getResourceAsStream("/imagens/robos/" +
                robo.getCor().toString().toLowerCase() + "-down-2.png"))));
        imageViewRobo.setLayoutX(posInicialX + robo.getNewX() * movimento);
        imageViewRobo.setLayoutY(posInicialY - robo.getNewY() * movimento);
    }

    public void settarObstaculoNoAnchorPane(ImageView imageView, Obstaculo obstaculo){
        containerTabuleiro.getChildren().add(imageView);
        imageView.setFitWidth(pixels);
        imageView.setFitHeight(pixels);
        imageView.setLayoutX(posInicialX + (obstaculo.getPosicaoX() * movimento));
        imageView.setLayoutY(posInicialY - (obstaculo.getPosicaoY() * movimento));
    }

    public ImageView procurarBombas(ImageView imageViewRobo){
        for (Node node : containerTabuleiro.getChildren()) {
            if(node instanceof ImageView imageViewNode &&
                    imageViewNode != imageViewRobo
                    && imageViewNode.getImage() == bombaImg){
                if (Math.abs(imageViewNode.getLayoutX() - imageViewRobo.getLayoutX()) < 1 &&
                        Math.abs(imageViewNode.getLayoutY() - imageViewRobo.getLayoutY()) < 1) {
                    return imageViewNode;
                }
            }
        }
        return null;
    }

    public void chamarJanelaResultados(Robo robo1, Robo robo2, Modo modoDeJogo){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetorobo/telaResultadoFinal.fxml"));
            Parent root = loader.load();

            TelaResultadoController telaResultadoController = loader.getController();

            telaResultadoController.receberDados(robo1, robo2, modoDeJogo);

            Stage stage = new Stage();
            stage.setTitle("Resultado de Jogo");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();

        }catch (Exception e) {
            AlertaUtil.mostrarErro(e.getMessage());
        }
    }

    public AnimacoesService getAnimacoesService() {return animacoesService;}
    public ImageView getImageViewAlimento(){ return imageViewAlimento;}
    public ImageView getImageViewRobo1(){ return imageViewRobo1;}
    public ImageView getImageViewRobo2(){ return imageViewRobo2;}
    public ListView<String> getListaHistorico(){return listaHistorico;}
    public Image getBombaImg() {return bombaImg;}
    public Image getAlimentoImg(){return alimentoImg;}
    public Image getPedraImg(){return pedraImg;}
}
