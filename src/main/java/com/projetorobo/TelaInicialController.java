package com.projetorobo;

import com.projetorobo.model.enums.Cores;
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

import java.io.IOException;

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
        try {
            Stage stage = new Stage();

            TabuleiroPage tabuleiro = new TabuleiroPage();
            tabuleiro.start(stage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregarComboBox() {
        comboBoxCorRobo.setItems(FXCollections.observableArrayList(Cores.values()));
    }
}