package com.projetorobo;

import com.projetorobo.model.enums.Cores;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class TelaInicialController {

    @FXML
    private Button buttonIniciar;

    @FXML
    private ComboBox<Cores> comboBoxCorRobo;

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
        System.out.println("Você clicou no botão");
        System.out.println("Pos X: " + textPosX.getText());
        System.out.println("Pos Y: " + textPosY.getText());
        System.out.println("Cor: " + comboBoxCorRobo.getValue());
    }

    private void carregarComboBox() {
        comboBoxCorRobo.setItems(FXCollections.observableArrayList(Cores.values()));
    }
}