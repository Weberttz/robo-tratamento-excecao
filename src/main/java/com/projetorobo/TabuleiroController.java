package com.projetorobo;

import com.almasb.fxgl.net.TCPReaderFactory;
import com.projetorobo.board.Tabuleiro;
import com.projetorobo.exception.ColisaoComObstaculoException;
import com.projetorobo.exception.MovimentoInvalidoException;
import com.projetorobo.model.enums.CategoriaRobo;
import com.projetorobo.model.enums.Dificuldade;
import com.projetorobo.model.enums.Direcao;
import com.projetorobo.model.enums.Modo;
import com.projetorobo.model.obstaculos.Obstaculo;
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
    private int posInicialX = 0;
    private int posInicialY = 315;
    private int turno = 1;
    private int tamanhoTabuleiro = 10;

    private int tempoTimeLine = 200;
    private int tempoTrocaFrame = (int) (.17 * tempoTimeLine);

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

        containerTabuleiro.getChildren().add(imageViewAlimento); // adicionar a imagem no anchorPane

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

        if (tabuleiro.verificarAlimento(robo1)) {
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                    Platform.runLater(() -> imageViewAlimento.setVisible(false));
                    listaHistorico.add("Achou a comida");
                    listaHistorico.add("Movimentos válidos: " + robo1.getMovimentosValidos());
                    listaHistorico.add("Movimentos inválidos: " + robo1.getMovimentosInvalidos());

                    Thread.sleep(1000);
                    Platform.runLater(() -> {
                        Stage stage = (Stage) imageViewAlimento.getScene().getWindow();
                        stage.close();
                    });

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

        if(robo1.isExplodiu()){
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                    Platform.runLater(() -> {
                        listaHistorico.add(robo1.toString() + " explodiu!");
                    });

                    Thread.sleep(1000);
                    Platform.runLater(() -> {
                        Stage stage = (Stage) imageViewRobo1.getScene().getWindow();
                        stage.close();
                    });

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }

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
        coletarFrames(robo, imageViewRobo, direcao);
        switch (direcao) {
            case Direcao.LEFT, Direcao.RIGHT: andarHorizontalmente(imageViewRobo, direcao, deveMover); break;
            case Direcao.UP, Direcao.DOWN: andarVerticalmente(imageViewRobo, direcao, deveMover); break;
        }
    }

    public void coletarFrames(Robo robo, ImageView imageViewRobo, Direcao direcao){
        frame1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/robos/" + robo.getCor().toString().toLowerCase() +
                "-" + direcao.name().toLowerCase() + "-1.png")));
        frame2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/robos/" + robo.getCor().toString().toLowerCase()
                + "-"  + direcao.name().toLowerCase() + "-2.png")));
        frame3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/robos/" + robo.getCor().toString().toLowerCase()
                + "-"  + direcao.name().toLowerCase() + "-3.png")));
    }

    public void andarVerticalmente(ImageView imageViewRobo, Direcao direcao, boolean deveMover){
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

    public void andarHorizontalmente(ImageView imageViewRobo, Direcao direcao, boolean deveMover){
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
        try {
            tabuleiro.moverRobo(robo, dir);
            direcionarImageViewRobo(robo, imageViewRobo, dir, true);
            String linha = String.format("%s - Robô %s em (%d,%d)%n", dir.toString().toLowerCase(), robo.getCor().toString().toLowerCase()
                    ,robo.getNewX(), robo.getNewY());
            listaHistorico.add(linha);
        } catch (MovimentoInvalidoException | ColisaoComObstaculoException e) {
            String linha = String.format("%s - Robô %s colidiu", dir.toString().toLowerCase(), robo.getCor().toString().toLowerCase());
            listaHistorico.add(linha);
            direcionarImageViewRobo(robo, imageViewRobo, dir, false);
        }
        tabuleiro.renderizar(); System.out.println("\n");
    }

    public void verificarFinalizacaoDeJogo(Timeline timeline){
        if (robo1.getAchouAlimento() && modoDeJogo == Modo.COMPETITIVO || robo2.getAchouAlimento() && modoDeJogo == Modo.COMPETITIVO) {
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    Platform.runLater(() -> imageViewAlimento.setVisible(false));
                    Thread.sleep(1000);
                    Platform.runLater(() -> {
                        Stage stage = (Stage) imageViewAlimento.getScene().getWindow();
                        stage.close();
                    });
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                }
            }).start();
            timeline.stop();
            System.out.printf("%n[%s] válidos: %d | inválidos: %d%n",
                    robo1.getCor(), robo1.getMovimentosValidos(), robo1.getMovimentosInvalidos());
            System.out.printf("%n[%s] válidos: %d | inválidos: %d%n",
                    robo2.getCor(), robo2.getMovimentosValidos(), robo2.getMovimentosInvalidos());
        }
        else if(robo1.getAchouAlimento() && robo2.getAchouAlimento() && modoDeJogo == Modo.COOPERATIVO) {
            //Mesma coisa lá de cima, criar método
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    Platform.runLater(() -> imageViewAlimento.setVisible(false));
                    Thread.sleep(1000);
                    Platform.runLater(() -> {
                        Stage stage = (Stage) imageViewAlimento.getScene().getWindow();
                        stage.close();
                    });
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                }
            }).start();
            timeline.stop();
            System.out.printf("%n[%s] válidos: %d | inválidos: %d%n",
                    robo1.getCor(), robo1.getMovimentosValidos(), robo1.getMovimentosInvalidos());
            System.out.printf("%n[%s] válidos: %d | inválidos: %d%n",
                    robo2.getCor(), robo2.getMovimentosValidos(), robo2.getMovimentosInvalidos());
        }
        else if(robo1.isExplodiu() && robo2.isExplodiu()) {
            Stage janela = (Stage) imageViewRobo1.getScene().getWindow();
            janela.close();
            timeline.stop();
        }
    }

    public void settarObstaculosNoAnchorPane(){
        tabuleiro.colocarObstaculos(dificuldade);
        for(Obstaculo obstaculo: tabuleiro.getObstaculos()){
            if(obstaculo.getId() == 1){

                ImageView imageViewPedra = new ImageView(pedraImg);
                containerTabuleiro.getChildren().add(imageViewPedra);
                imageViewPedra.setFitWidth(pixels);
                imageViewPedra.setFitHeight(pixels);
                imageViewPedra.setLayoutX(posInicialX + (obstaculo.getPosicaoX() * movimento));
                imageViewPedra.setLayoutY(posInicialY - (obstaculo.getPosicaoY() * movimento));

            }else if(obstaculo.getId() == 0){

                ImageView imageViewBomba = new ImageView(bombaImg);
                //Mesma coisa lá de cima, criar método
                containerTabuleiro.getChildren().add(imageViewBomba);
                imageViewBomba.setFitWidth(pixels);
                imageViewBomba.setFitHeight(pixels);
                imageViewBomba.setLayoutX(posInicialX + (obstaculo.getPosicaoX() * movimento));
                imageViewBomba.setLayoutY(posInicialY - (obstaculo.getPosicaoY() * movimento));

            }
        }
    }
}