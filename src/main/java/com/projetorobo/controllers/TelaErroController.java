package com.projetorobo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class TelaErroController {

    @FXML
    private Label labelMensagem;

    public void receberMensagem(String mensagem) {
        labelMensagem.setText(mensagem);
    }

    @FXML
    void fechar() {
        ((Stage) labelMensagem.getScene().getWindow()).close();
    }
}