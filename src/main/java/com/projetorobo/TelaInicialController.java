package com.projetorobo;

import com.projetorobo.model.enums.Cor;
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

public class TelaInicialController {

    @FXML
    private Button buttonIniciar;

    @FXML
    private ComboBox<Cor> comboBoxCorRobo;
    private List<Cor> cores = new ArrayList<>();

    @FXML
    private TextField textPosX;

    @FXML
    private TextField textPosY;

    @FXML
    public void initialize() {
        carregarComboBox();
    }

    @FXML
    public void clicouNoBotaoIniciar(ActionEvent event) {

        if(textPosX.getText() != null && textPosY.getText() != null && comboBoxCorRobo.getValue() != null ){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetorobo/tabuleiro.fxml"));
                Parent root = loader.load();

                TabuleiroController controller = loader.getController();

                int x = Integer.parseInt(textPosX.getText());
                int y = Integer.parseInt(textPosY.getText());
                String cor = comboBoxCorRobo.getValue().toString().toLowerCase();

                controller.receberDados(x, y, cor);

                Stage stage = new Stage();
                stage.setTitle("Projeto!");
                stage.setResizable(false);
                stage.setScene(new Scene(root, 634, 577));
                stage.show();

                Stage currentStage = (Stage) buttonIniciar.getScene().getWindow();
                currentStage.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void carregarComboBox() {
        Cor vermelha = Cor.VERMELHO;
        Cor azul = Cor.AZUL;
        Cor marrom = Cor.MARROM;
        Cor amarelo = Cor.AMARELO;

        cores.add(vermelha);
        cores.add(azul);
        cores.add(marrom);
        cores.add(amarelo);

        comboBoxCorRobo.setItems(FXCollections.observableArrayList(cores));
    }

    public TextField getTextPosX() {return textPosX;}
    public TextField getTextPosY() {return textPosY;}
    public ComboBox getComboBoxCorRobo() { return comboBoxCorRobo;}
}