package com.projetorobo.controllers;

import com.projetorobo.exception.AlimentoForaDoLimiteException;
import com.projetorobo.exception.DadosNaoPreenchidosException;
import com.projetorobo.model.enums.Cor;
import com.projetorobo.model.enums.Dificuldade;
import com.projetorobo.model.enums.Modo;
import com.projetorobo.service.AnimacoesService;
import com.projetorobo.util.AlertaUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TelaUsuarioController {

    private List<Cor> cores = new ArrayList<>();
    private List<Dificuldade> dificuldades = new ArrayList<>();
    private Modo modoDeJogo;
    private int tamanhoTabuleiro = 10;

    @FXML
    private Button buttonIniciar;

    @FXML
    private ComboBox<Cor> comboBoxCorRobo;
    @FXML
    private ComboBox<Dificuldade> comboBoxDificuldade;

    @FXML
    private TextField textPosX;

    @FXML
    private TextField textPosY;

    @FXML
    private VBox TelaPrincipal;

    @FXML
    public void initialize() {
        carregarComboBoxes();
    }

    @FXML
    public void comecarJogo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetorobo/tabuleiro.fxml"));
            Parent root = loader.load();

            if (textPosX.getText().equals("William") && textPosY.getText().equals("Afton")) {
                URL resource = getClass().getResource("/videos/ISeeYou.mp4");
                if (resource == null) throw new RuntimeException("Vídeo não encontrado!");

                Media media = new Media(resource.toExternalForm());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                MediaView mediaView = new MediaView(mediaPlayer);

                Stage stage = (Stage) TelaPrincipal.getScene().getWindow();
                stage.setWidth(600);
                stage.setHeight(550);

                mediaView.fitWidthProperty().bind(stage.widthProperty());
                mediaView.fitHeightProperty().bind(stage.heightProperty());

                TelaPrincipal.getChildren().clear();
                TelaPrincipal.getChildren().add(mediaView);
                mediaPlayer.setOnReady(mediaPlayer::play);
                mediaPlayer.setOnEndOfMedia(Platform::exit);
                return;
            }
            else if(textPosX.getText() == null || textPosY.getText() == null || comboBoxCorRobo.getValue() == null ||
                    comboBoxDificuldade.getValue() == null) {
                    throw new DadosNaoPreenchidosException();
            }

            TabuleiroController controller = loader.getController();

            int posicaoXAlimento = Integer.parseInt(textPosX.getText());
            int posicaoYAlimento = Integer.parseInt(textPosY.getText());

            if(posicaoXAlimento >= tamanhoTabuleiro || posicaoYAlimento >= tamanhoTabuleiro){
                throw new AlimentoForaDoLimiteException();
            }else if(posicaoXAlimento == 0 || posicaoYAlimento == 0){
                throw new AlimentoForaDoLimiteException();
            }

            String cor = comboBoxCorRobo.getValue().toString().toUpperCase();
            Dificuldade dificuldade = comboBoxDificuldade.getValue();

            controller.receberDados(posicaoXAlimento, posicaoYAlimento, cor, dificuldade, modoDeJogo);

            Stage stage = new Stage();
            stage.setTitle("Modo de jogo: " + modoDeJogo.toString().toLowerCase());
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();

            // pega a janela que o botão está inserido
            Stage currentStage = (Stage) buttonIniciar.getScene().getWindow();
            currentStage.close();

        }catch (IllegalArgumentException e) {
            AlertaUtil.mostrarErro("Entrada inválida!");
        }catch (Exception e){
            AlertaUtil.mostrarErro(e.getMessage());
        }

    }

    private void carregarComboBoxes() {
        cores.add(Cor.VERMELHO);
        cores.add(Cor.MARROM);
        cores.add(Cor.AMARELO);
        cores.add(Cor.AZUL);
        dificuldades.add(Dificuldade.FACILIMO);
        dificuldades.add(Dificuldade.FACIL);
        dificuldades.add(Dificuldade.MEDIO);
        dificuldades.add(Dificuldade.DIFICIL);

        comboBoxCorRobo.setItems(FXCollections.observableArrayList(cores));
        comboBoxDificuldade.setItems(FXCollections.observableArrayList(dificuldades));
    }

    public void receberDados(Modo modo){
        this.modoDeJogo = modo;
    }
}