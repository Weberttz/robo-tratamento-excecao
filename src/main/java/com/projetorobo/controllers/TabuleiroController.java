package com.projetorobo.controllers;

import com.projetorobo.model.board.Tabuleiro;
import com.projetorobo.exception.ColisaoComObstaculoException;
import com.projetorobo.exception.MovimentoInvalidoException;
import com.projetorobo.model.enums.CategoriaRobo;
import com.projetorobo.model.enums.Dificuldade;
import com.projetorobo.model.enums.Direcao;
import com.projetorobo.model.enums.Modo;
import com.projetorobo.model.obstaculos.Obstaculo;
import com.projetorobo.model.obstaculos.Pedra;
import com.projetorobo.model.robos.Robo;
import com.projetorobo.model.robos.RoboInteligente;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class TabuleiroController implements Initializable {
    private int pixels = 35;
    private int movimento = 35;
    private int quantidadeDeImagens = 103;
    private int posInicialX = 0;
    private int posInicialY = 315;
    private int turno = 1;
    private int tamanhoTabuleiro = 10;
    private int tempoTimeLine = 1000;
    private int tempoTrocaFrame = (int) (.17 * tempoTimeLine);  //6 frames

    private Image frame1, frame2, frame3;
    private Image alimentoImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/pizza.png")));
    private Image bombaImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/bomba.png")));
    private Image pedraImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/pedra.png")));

    private ImageView imageViewAlimento = new ImageView(alimentoImg);
    private Tabuleiro tabuleiro;
    private Modo modoDeJogo;
    private Dificuldade dificuldade;

    private Robo robo1;
    private Robo robo2;

    private Thread t1;
    
    @FXML
    private ImageView imageViewRobo1;
    @FXML
    private ImageView imageViewRobo2;

    @FXML
    private Button buttonMover;

    @FXML
    private AnchorPane containerTabuleiro;

    @FXML
    private TextField textFieldMovimento;

    @FXML
    private ListView<String> listViewHistorico;
    private ArrayList<String> listaHistorico = new ArrayList<>();
    private ObservableList<String> obsHistorico;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageViewAlimento.setPreserveRatio(true); // preservar o corpo da imagem
        imageViewAlimento.setSmooth(true); // preservar a qualidade

        imageViewAlimento.setFitHeight(pixels);
        imageViewAlimento.setFitWidth(pixels);

        containerTabuleiro.getChildren().add(imageViewAlimento);// adicionar a imagem no anchorPane
        quantidadeDeImagens++;

        AnchorPane.setLeftAnchor(imageViewAlimento, null); // não puxar a imagem para lateralEsquerda
        AnchorPane.setTopAnchor(imageViewAlimento, null); // não puxar a imagem para o topo
    }

    //Age como se fosse um initialize, configura o tabuleiro com base no que precisamos
    public void receberDados(int posicaoX, int posicaoY, String cor, Dificuldade dificuldade, Modo modoDeJogo){
        tabuleiro = new Tabuleiro(tamanhoTabuleiro, posicaoX, posicaoY);
        this.dificuldade = dificuldade;
        this.modoDeJogo = modoDeJogo;
        robo1 = new Robo(cor);
        tabuleiro.adicionarRobo(robo1);
        settarObstaculosNoAnchorPane();

        imageViewRobo1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/robos/" +
                robo1.getCor().toString().toLowerCase() + "-down-2.png"))));

        imageViewRobo1.setLayoutX(posInicialX);
        imageViewRobo1.setLayoutY(posInicialY);

        imageViewRobo2.setVisible(false);

        imageViewAlimento.setLayoutX(posInicialX + (tabuleiro.getAlimentoX() * movimento));
        imageViewAlimento.setLayoutY(posInicialY - (tabuleiro.getAlimentoY() * movimento));
    }

    //Main2, Main3, Main4
    public void receberDados(int posicaoX, int posicaoY, String corRobo1, String corRobo2,
                             Dificuldade dificuldade, CategoriaRobo categoriaRobo1, CategoriaRobo categoriaRobo2, Modo modoDeJogo){
        buttonMover.setDisable(true);
        textFieldMovimento.setText("Robôs criaram vida!");
        textFieldMovimento.setDisable(true);
        this.dificuldade = dificuldade;
        this.modoDeJogo = modoDeJogo;

        tabuleiro = new Tabuleiro(tamanhoTabuleiro, posicaoX, posicaoY);

        switch (categoriaRobo1){
            case BURRO -> robo1 = new Robo(corRobo1);
            case INTELIGENTE -> robo1 = new RoboInteligente(corRobo1);
        }

        switch (categoriaRobo2){
            case BURRO -> robo2 = new Robo(corRobo2);
            case INTELIGENTE -> robo2 = new RoboInteligente(corRobo2);
        }

        tabuleiro.adicionarRobo(robo1);
        tabuleiro.adicionarRobo(robo2);

        robo2.modificarPosicaoInicial(0, 1);

        settarObstaculosNoAnchorPane();

        imageViewRobo1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/robos/" +
                robo1.getCor().toString().toLowerCase() + "-down-2.png"))));

        imageViewRobo1.setLayoutX(posInicialX);
        imageViewRobo1.setLayoutY(posInicialY);

        imageViewRobo2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/robos/" +
                robo2.getCor().toString().toLowerCase() + "-down-2.png"))));

        imageViewRobo2.setLayoutX(posInicialX + robo2.getNewX() * movimento);
        imageViewRobo2.setLayoutY(posInicialY - robo2.getNewY() * movimento);

        imageViewAlimento.setLayoutX(posInicialX + (tabuleiro.getAlimentoX() * movimento));
        imageViewAlimento.setLayoutY(posInicialY - (tabuleiro.getAlimentoY() * movimento));
        controlarRobos();
    }

    @FXML
    public void movimentar(ActionEvent event) { //Main1
        buttonMover.setDisable(true); // desabilitar botão após o clique
        Direcao direcao = null;
        try {
            if(textFieldMovimento.getText().matches("\\d+")){
                direcao = Direcao.fromInt(Integer.parseInt(textFieldMovimento.getText()));
            }else {
                direcao = Direcao.fromString(textFieldMovimento.getText());
            }

            tabuleiro.moverRobo(robo1, direcao);
            direcionarImageViewRobo(robo1, imageViewRobo1, direcao, true);//animar sprite e só depois mover
            String linha = String.format("%s - Robô em (%d,%d)%n", direcao.toString().toLowerCase(), robo1.getNewX(), robo1.getNewY());
            listaHistorico.add(linha);

            tabuleiro.renderizar();
            System.out.println();
        } catch (MovimentoInvalidoException | ColisaoComObstaculoException e) {
            assert direcao != null;
            String linha = String.format("%s - Robô  colidiu", direcao.toString().toLowerCase());
            listaHistorico.add(linha);
            direcionarImageViewRobo(robo1, imageViewRobo1, direcao, false);
        } catch (IllegalArgumentException e) {
            System.out.println("Direção desconhecida. Use: up, down, left, right  ou  1,2,3,4");
        }

        if(robo1.isExplodiu() || robo1.getAchouAlimento())
            finalizarJogo();

        new Thread(() -> {
            try {
                Thread.sleep(1500);
                Platform.runLater(() -> buttonMover.setDisable(false));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        obsHistorico = FXCollections.observableArrayList(listaHistorico);
        listViewHistorico.setItems(obsHistorico);
    }

    @FXML
    public void direcionarImageViewRobo(Robo robo, ImageView imageViewRobo, Direcao direcao, boolean deveMover){
        coletarFrames(robo, direcao);
        andar(imageViewRobo, direcao, deveMover);
    }

    public void coletarFrames(Robo robo, Direcao direcao){
        frame1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/robos/" + robo.getCor().toString().toLowerCase() +
                "-" + direcao.name().toLowerCase() + "-1.png")));
        frame2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/robos/" + robo.getCor().toString().toLowerCase()
                + "-"  + direcao.name().toLowerCase() + "-2.png")));
        frame3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/robos/" + robo.getCor().toString().toLowerCase()
                + "-"  + direcao.name().toLowerCase() + "-3.png")));
    }

    public void andar(ImageView imageViewRobo, Direcao direcao, boolean deveMover){
        t1 = new Thread(() -> {
            try {
                Thread.sleep(tempoTrocaFrame);
                Platform.runLater(() -> imageViewRobo.setImage(frame1));

                Thread.sleep(tempoTrocaFrame);
                Platform.runLater(() -> imageViewRobo.setImage(frame2));

                Thread.sleep(tempoTrocaFrame);
                Platform.runLater(() -> imageViewRobo.setImage(frame3));

                Thread.sleep(tempoTrocaFrame);
                Platform.runLater(() -> imageViewRobo.setImage(frame2));

                moverImageView(imageViewRobo, direcao, deveMover);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        t1.start();
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

    public void controlarRobos(){
        tabuleiro.renderizar();
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(tempoTimeLine), e ->{

            if(turno%2 == 1 && !robo1.isExplodiu() && !robo1.getAchouAlimento())
                jogarTurno(robo1, imageViewRobo1);
            else if(turno%2 == 0 && !robo2.isExplodiu() && !robo2.getAchouAlimento())
                jogarTurno(robo2, imageViewRobo2);
            else
                verificarFinalizacaoDeJogo(timeline);

            obsHistorico = FXCollections.observableArrayList(listaHistorico);
            listViewHistorico.setItems(obsHistorico);
            turno++;
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void jogarTurno(Robo robo, ImageView imageViewRobo) {
        Direcao dir = robo.escolherDirecao();
        String linha;
        try {
            tabuleiro.moverRobo(robo, dir);
            direcionarImageViewRobo(robo, imageViewRobo, dir, true);
            linha = String.format("%s - Robô %s em (%d,%d)", dir.toString().toLowerCase(), robo.getCor().toString().toLowerCase()
                    ,robo.getNewX(), robo.getNewY());
        } catch (MovimentoInvalidoException | ColisaoComObstaculoException e) {
            linha = String.format("%s - Robô %s colidiu", dir.toString().toLowerCase(), robo.getCor().toString().toLowerCase());
            direcionarImageViewRobo(robo, imageViewRobo, dir, false);
        }
        listaHistorico.add(linha);
        tabuleiro.renderizar(); System.out.println("\n");


        new Thread(() -> {
            try {
                Thread.sleep(1000);
                if(robo.isExplodiu()) {
                    ImageView objeto = procurarBombas(imageViewRobo);
                    imageViewRobo.setVisible(false);
                    objeto.setVisible(false);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void verificarFinalizacaoDeJogo(Timeline timeline){
        if ((robo1.getAchouAlimento() || robo2.getAchouAlimento()) && modoDeJogo == Modo.COMPETITIVO)
            finalizarJogo();

        if(robo1.getAchouAlimento() && robo2.getAchouAlimento() && modoDeJogo == Modo.COOPERATIVO)
            finalizarJogo();
        else if(robo1.isExplodiu() && (robo2.isExplodiu() || robo2.getAchouAlimento()))
            finalizarJogo();
        else if(robo2.isExplodiu() && (robo1.isExplodiu() || robo1.getAchouAlimento()))
           finalizarJogo();
    }

    public void settarObstaculosNoAnchorPane(){
        tabuleiro.colocarObstaculos(dificuldade);
        ImageView imageView = null;
        for(Obstaculo obstaculo: tabuleiro.getObstaculos()){
            if(obstaculo instanceof Pedra)
                imageView = new ImageView(pedraImg);
            else
                imageView = new ImageView(bombaImg);

            colocarImageView(imageView, obstaculo);
        }
    }

    public void colocarImageView(ImageView imageView, Obstaculo obstaculo){
        containerTabuleiro.getChildren().add(imageView);
        quantidadeDeImagens++;
        imageView.setFitWidth(pixels);
        imageView.setFitHeight(pixels);
        imageView.setLayoutX(posInicialX + (obstaculo.getPosicaoX() * movimento));
        imageView.setLayoutY(posInicialY - (obstaculo.getPosicaoY() * movimento));
    }

    public void finalizarJogo(){
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
        chamarCreditos();
    }

    public void chamarCreditos(){
        System.out.printf("%n[%s] válidos: %d | inválidos: %d%n",
                robo1.getCor(), robo1.getMovimentosValidos(), robo1.getMovimentosInvalidos());
    }

    public ImageView procurarBombas(ImageView imageViewRobo){
        for (Node node : containerTabuleiro.getChildren()) {
            if(node instanceof ImageView imageViewNode && imageViewNode != imageViewRobo && imageViewNode.getImage() == bombaImg){
                if (Math.abs(imageViewNode.getLayoutX() - imageViewRobo.getLayoutX()) < 1 &&
                        Math.abs(imageViewNode.getLayoutY() - imageViewRobo.getLayoutY()) < 1) {
                    return imageViewNode;
                }
            }
        }

        return null;
    }
}