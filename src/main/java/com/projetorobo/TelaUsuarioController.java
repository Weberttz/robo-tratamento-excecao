package com.projetorobo;

import com.almasb.fxgl.core.fsm.StateMachine;
import com.projetorobo.exception.AlimentoForaDoLimiteException;
import com.projetorobo.model.enums.Cor;
import com.projetorobo.model.enums.Dificuldade;
import com.projetorobo.model.enums.Modo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    public void initialize() {
        carregarComboBoxes();
    }

    @FXML
    public void comecarJogo(ActionEvent event) {
        if(textPosX.getText() != null && textPosY.getText() != null && comboBoxCorRobo.getValue() != null ){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetorobo/tabuleiro.fxml"));
                Parent root = loader.load();

                TabuleiroController controller = loader.getController();

                int x = Integer.parseInt(textPosX.getText());
                int y = Integer.parseInt(textPosY.getText());

                if(x >= tamanhoTabuleiro || y >= tamanhoTabuleiro){
                    throw new AlimentoForaDoLimiteException();
                }

                String cor = comboBoxCorRobo.getValue().toString().toUpperCase();
                Dificuldade dificuldade = comboBoxDificuldade.getValue();

                controller.receberDados(x, y, cor, dificuldade, modoDeJogo);

                Stage stage = new Stage();
                stage.setTitle("Projeto!");
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.show();

                // pega a janela que o botão está inserido
                Stage currentStage = (Stage) buttonIniciar.getScene().getWindow();
                currentStage.close();

            }catch (IllegalArgumentException e) {
                System.out.println("Entrada inválida!");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void carregarComboBoxes() {
        cores.add(Cor.VERMELHO);
        cores.add(Cor.MARROM);
        cores.add(Cor.AMARELO);
        cores.add(Cor.AZUL);
        dificuldades.add(Dificuldade.FASSILIMO);
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