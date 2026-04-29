package com.projetorobo;

import com.projetorobo.board.Tabuleiro;
import com.projetorobo.exception.ColisaoComObstaculoException;
import com.projetorobo.exception.MovimentoInvalidoException;
import com.projetorobo.model.enums.Direcao;
import com.projetorobo.model.robos.Robo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class TabuleiroController {

    int movimento = 36;
    int posInicialX = 0;
    int posInicialY = 307;

    private Image frame1, frame2, frame3, frame4;
    private Image alimentoImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/cupcake.png")));

    private ImageView imageViewAlimento = new ImageView(alimentoImg);
    private Tabuleiro tabuleiro;

    Robo robo;
    @FXML
    private ImageView imageViewRobo;

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

    //Age como se fosse um initialize, configura o tabuleiro com base no que precisamos
    public void receberDados(int posicaoX, int posicaoY, String cor){
        tabuleiro = new Tabuleiro(10, posicaoX, posicaoY);
        robo = new Robo(cor);
        tabuleiro.adicionarRobo(robo);


        imageViewRobo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/robos/" +
                robo.getCor().toString().toLowerCase() + "-down-2.png"))));

        imageViewRobo.setLayoutX(posInicialX);
        imageViewRobo.setLayoutY(posInicialY);

        imageViewAlimento.setPreserveRatio(true); // preservar o corpo da imagem
        imageViewAlimento.setSmooth(true); // preservar a qualidade

        imageViewAlimento.setFitWidth(63); // pixel da imagem - largura
        imageViewAlimento.setFitHeight(36); // pixel da imagem - altura

        containerTabuleiro.getChildren().add(imageViewAlimento); // adicionar a imagem no anchorPane

        AnchorPane.setLeftAnchor(imageViewAlimento, null); // não puxar a imagem para lateralEsquerda
        AnchorPane.setTopAnchor(imageViewAlimento, null); // não puxar a imagem para o topo
        imageViewAlimento.setLayoutX(imageViewRobo.getLayoutX() + movimento * tabuleiro.getAlimentoX());
        imageViewAlimento.setLayoutY(imageViewRobo.getLayoutY() - movimento * tabuleiro.getAlimentoY());
    }

    @FXML
    public void movimentar(ActionEvent event) {
        buttonMover.setDisable(true); // desabilitar botão após o clique
        try {
            Direcao dir = null;
            if(textFieldMovimento.getText().matches("\\d+")){
                dir = Direcao.fromInt(Integer.parseInt(textFieldMovimento.getText()));
            }else {
                dir = Direcao.fromString(textFieldMovimento.getText());
            }
            tabuleiro.moverRobo(robo, dir);
            direcionarImageViewRobo(dir);
            String linha = String.format("%s - Robô em (%d,%d)%n", dir.toString().toLowerCase(), robo.getNewX(), robo.getNewY());
            listaHistorico.add(linha);
        } catch (MovimentoInvalidoException | ColisaoComObstaculoException e) {
            System.out.printf("[%s] %s%n", robo.getCor(), e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Direção desconhecida. Use: up, down, left, right");
        }

        obsHistorico = FXCollections.observableArrayList(listaHistorico);
        listViewHistorico.setItems(obsHistorico);

        if (tabuleiro.verificarAlimento(robo)) {
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                    Platform.runLater(() -> imageViewAlimento.setVisible(false));
                    System.out.println("Achou a comida");
                    System.out.println("Movimentos válidos: " + robo.getMovimentosValidos());
                    System.out.println("Movimentos inválidos: " + robo.getMovimentosInvalidos());

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
    public void direcionarImageViewRobo(Direcao dir){
        switch (dir) {
            case Direcao.LEFT: coletarFrames("left"); andarHorizontalmente(-1 * movimento); break;
            case Direcao.RIGHT:coletarFrames("right"); andarHorizontalmente(movimento); break;
            case Direcao.UP: coletarFrames("up"); andarVerticalmente( movimento); break;
            case Direcao.DOWN: coletarFrames("down"); andarVerticalmente( -1 * movimento); break;
        }
    }

    public void coletarFrames(String strDir){
        frame1 = imageViewRobo.getImage();
        frame2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/robos/" + robo.getCor().toString().toLowerCase() +
                "-" + strDir + "-1.png")));
        frame3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/robos/" + robo.getCor().toString().toLowerCase()
                + "-"  + strDir + "-2.png")));
        frame4 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/robos/" + robo.getCor().toString().toLowerCase()
                + "-"  + strDir + "-3.png")));
    }

    public void andarVerticalmente(int movimento){
        new Thread(() -> {
            try {
                Thread.sleep(333);
                Platform.runLater(() -> imageViewRobo.setImage(frame2));

                Thread.sleep(333);
                Platform.runLater(() -> imageViewRobo.setImage(frame3));

                Thread.sleep(333);
                Platform.runLater(() -> imageViewRobo.setImage(frame4));

                Thread.sleep(333);
                Platform.runLater(() -> imageViewRobo.setImage(frame3));
                imageViewRobo.setLayoutY(imageViewRobo.getLayoutY() - movimento);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public void andarHorizontalmente(int movimento){
        new Thread(() -> {
            try {

                Thread.sleep(333);
                Platform.runLater(() -> imageViewRobo.setImage(frame2));


                Thread.sleep(333);
                Platform.runLater(() -> imageViewRobo.setImage(frame3));


                Thread.sleep(333);
                Platform.runLater(() -> imageViewRobo.setImage(frame4));

                Thread.sleep(333);
                Platform.runLater(() -> imageViewRobo.setImage(frame3));

                imageViewRobo.setLayoutX(imageViewRobo.getLayoutX() + movimento);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
