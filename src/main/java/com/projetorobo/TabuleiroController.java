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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class TabuleiroController implements Initializable {

    int movimento = 36;

    @FXML
    private Image frame1, frame2, frame3, frame4;
    private Image alimentoImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/cupcake.png")));


    private ImageView imageViewAlimento = new ImageView(alimentoImg);
    private Robo robo = new Robo("amarelo");
    private Tabuleiro tabuleiro = new Tabuleiro(14, 2, 2);

    @FXML
    private ImageView imageViewRobo;

    @FXML
    private Button buttonMovimento;

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

        imageViewRobo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/imagens/robos/" +
                robo.getCor().toString().toLowerCase() + "-down-2.png"))));

        tabuleiro.adicionarRobo(robo);
        imageViewRobo.setLayoutX(-18);
        imageViewRobo.setLayoutY(336);
        imageViewAlimento.setFitWidth(63);
        imageViewAlimento.setFitHeight(36);
        imageViewAlimento.setPreserveRatio(true);
        imageViewAlimento.setSmooth(true);
        containerTabuleiro.getChildren().add(imageViewAlimento);
        AnchorPane.setLeftAnchor(imageViewAlimento, null);
        AnchorPane.setTopAnchor(imageViewAlimento, null);
        imageViewAlimento.setLayoutX(imageViewRobo.getLayoutX() + movimento * tabuleiro.getAlimentoX());
        imageViewAlimento.setLayoutY(imageViewRobo.getLayoutY() - movimento * tabuleiro.getAlimentoY());
    }

    @FXML
    public void acaoBotaoMovimento(ActionEvent event) {
        try {
            Direcao dir = Direcao.fromString(textFieldMovimento.getText());
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
                Platform.runLater(() -> imageViewRobo.setImage(frame1));
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
                Platform.runLater(() -> imageViewRobo.setImage(frame1));

                imageViewRobo.setLayoutX(imageViewRobo.getLayoutX() + movimento);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
