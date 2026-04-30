package com.projetorobo;

import com.projetorobo.board.Tabuleiro;
import com.projetorobo.exception.ColisaoComObstaculoException;
import com.projetorobo.exception.MovimentoInvalidoException;
import com.projetorobo.model.enums.CategoriaRobo;
import com.projetorobo.model.enums.Dificuldade;
import com.projetorobo.model.enums.Direcao;
import com.projetorobo.model.enums.Modo;
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
import java.util.concurrent.atomic.AtomicInteger;

public class TabuleiroController implements Initializable {

    private int movimento = 35;
    private int posInicialX = 0;
    private int posInicialY = 315;
    private int turno = 1;

    private int tempoTimeLine = 100;
    private int tempoTrocaFrame  = (int) 0.25 * tempoTimeLine;

    private Image frame1, frame2, frame3;
    private Image alimentoImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/pizza.png")));
    private Image bombaImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/bomb.png")));
    private Image pedraImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/pizza.png")));

    private ImageView imageViewAlimento = new ImageView(alimentoImg);
    private Tabuleiro tabuleiro;
    private Modo modoDeJogo;

    private Robo robo1;
    private Robo robo2;
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

        imageViewAlimento.setFitHeight(35);
        imageViewAlimento.setFitWidth(35);

        containerTabuleiro.getChildren().add(imageViewAlimento); // adicionar a imagem no anchorPane

        AnchorPane.setLeftAnchor(imageViewAlimento, null); // não puxar a imagem para lateralEsquerda
        AnchorPane.setTopAnchor(imageViewAlimento, null); // não puxar a imagem para o topo
    }

    //Age como se fosse um initialize, configura o tabuleiro com base no que precisamos
    public void receberDados(int posicaoX, int posicaoY, String cor, Dificuldade dificuldade, Modo modoDeJogo){
        tabuleiro = new Tabuleiro(10, posicaoX, posicaoY);

        this.modoDeJogo = modoDeJogo;

        robo1 = new Robo(cor);
        tabuleiro.adicionarRobo(robo1);
        tabuleiro.colocarObstaculos(dificuldade);

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

        this.modoDeJogo = modoDeJogo;
        tabuleiro = new Tabuleiro(10, posicaoX, posicaoY);
        tabuleiro.colocarObstaculos(dificuldade);

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
        try {
            Direcao dir;
            if(textFieldMovimento.getText().matches("\\d+")){
                dir = Direcao.fromInt(Integer.parseInt(textFieldMovimento.getText()));
            }else {
                dir = Direcao.fromString(textFieldMovimento.getText());
            }

            tabuleiro.moverRobo(robo1, dir);
            direcionarImageViewRobo(robo1, imageViewRobo1, dir); //animar sprite e só depois mover

            String linha = String.format("%s - Robô em (%d,%d)%n", dir.toString().toLowerCase(), robo1.getNewX(), robo1.getNewY());
            listaHistorico.add(linha);

            tabuleiro.renderizar();
            System.out.println();
        } catch (MovimentoInvalidoException | ColisaoComObstaculoException e) {
            System.out.printf("[%s] %s%n", robo1.getCor(), e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Direção desconhecida. Use: up, down, left, right  ou  1,2,3,4");
        }

        obsHistorico = FXCollections.observableArrayList(listaHistorico);
        listViewHistorico.setItems(obsHistorico);

        if (tabuleiro.verificarAlimento(robo1)) {
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                    Platform.runLater(() -> imageViewAlimento.setVisible(false));
                    System.out.println("Achou a comida");
                    System.out.println("Movimentos válidos: " + robo1.getMovimentosValidos());
                    System.out.println("Movimentos inválidos: " + robo1.getMovimentosInvalidos());

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

        new Thread(() -> {
            try {
                Thread.sleep(1500);
                Platform.runLater(() -> buttonMover.setDisable(false));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    @FXML
    public void direcionarImageViewRobo(Robo robo, ImageView imageViewRobo, Direcao dir){
        coletarFrames(robo, imageViewRobo, dir);
        switch (dir) {
            case Direcao.LEFT, Direcao.RIGHT: andarHorizontalmente(imageViewRobo, dir); break;
            case Direcao.UP, Direcao.DOWN: andarVerticalmente(imageViewRobo, dir); break;
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

    public void andarVerticalmente(ImageView imageViewRobo, Direcao direcao){
        new Thread(() -> {
            try {
                Thread.sleep(tempoTrocaFrame);
                Platform.runLater(() -> imageViewRobo.setImage(frame1));

                Thread.sleep(tempoTrocaFrame);
                Platform.runLater(() -> imageViewRobo.setImage(frame2));

                Thread.sleep(tempoTrocaFrame);
                Platform.runLater(() -> imageViewRobo.setImage(frame3));

                Thread.sleep(tempoTrocaFrame);
                Platform.runLater(() -> imageViewRobo.setImage(frame2));
                moverImageView(imageViewRobo, direcao);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public void andarHorizontalmente(ImageView imageViewRobo, Direcao direcao){
        new Thread(() -> {
            try {
                Thread.sleep(tempoTrocaFrame);
                Platform.runLater(() -> imageViewRobo.setImage(frame1));


                Thread.sleep(tempoTrocaFrame);
                Platform.runLater(() -> imageViewRobo.setImage(frame2));


                Thread.sleep(tempoTrocaFrame);
                Platform.runLater(() -> imageViewRobo.setImage(frame3));

                Thread.sleep(tempoTrocaFrame);
                Platform.runLater(() -> imageViewRobo.setImage(frame2));
                moverImageView(imageViewRobo, direcao);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public void moverImageView(ImageView imageViewRobo, Direcao direcao){
        switch (direcao){
            case UP: imageViewRobo.setLayoutY(imageViewRobo.getLayoutY() - movimento); break;
            case DOWN: imageViewRobo.setLayoutY(imageViewRobo.getLayoutY() + movimento); break;
            case LEFT: imageViewRobo.setLayoutX(imageViewRobo.getLayoutX() - movimento); break;
            case RIGHT: imageViewRobo.setLayoutX(imageViewRobo.getLayoutX() + movimento); break;
        }
    }

    public void controlarRobos(){
        tabuleiro.renderizar();
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(tempoTimeLine), e ->{

            if(turno%2 == 1)
                jogarTurno(robo1, imageViewRobo1, tabuleiro);
            else
                jogarTurno(robo2, imageViewRobo2, tabuleiro);

            verificarEFinalizarJogo(timeline);



        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private boolean jogarTurno(Robo robo, ImageView imageViewRobo, Tabuleiro tabuleiro) {
        Direcao dir = robo.escolherDirecao();
        System.out.printf("%n[%s] tentou: %s%n", robo.getCor(), dir);
        try {
            tabuleiro.moverRobo(robo, dir);
            direcionarImageViewRobo(robo, imageViewRobo, dir);
            String linha = String.format("%s - Robô em (%d,%d)%n", dir.toString().toLowerCase(), robo.getNewX(), robo.getNewY());
            listaHistorico.add(linha);
            System.out.printf("[%s] está em (%d,%d)%n", robo.getCor(), robo.getNewX(), robo.getNewY());
        } catch (MovimentoInvalidoException | ColisaoComObstaculoException e) {
            System.out.printf("[%s] %s%n", robo.getCor(), e.getMessage());
        }
        tabuleiro.renderizar();
        turno++;
        return tabuleiro.verificarAlimento(robo);
    }

    public void verificarEFinalizarJogo(Timeline timeline){
        if (tabuleiro.verificarAlimento(robo1) || tabuleiro.verificarAlimento(robo2) && modoDeJogo == Modo.COMPETITIVO) {
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
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
        else if(tabuleiro.verificarAlimento(robo1) && tabuleiro.verificarAlimento(robo2) && modoDeJogo == Modo.COOPERATIVO) {
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
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
    }
}