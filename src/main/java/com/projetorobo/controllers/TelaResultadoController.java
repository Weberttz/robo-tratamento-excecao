package com.projetorobo.controllers;

import com.projetorobo.model.enums.Modo;
import com.projetorobo.model.robo.Robo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaResultadoController {

    @FXML
    private Button btnSair;

    @FXML
    private Label labelInvalidosRobo1;

    @FXML
    private Label labelInvalidosRobo2;

    @FXML
    private Label labelNomeRobo1;

    @FXML
    private Label labelNomeRobo2;

    @FXML
    private Label labelStatus;

    @FXML
    private Label labelTitulo;

    @FXML
    private Label labelValidosRobo1;

    @FXML
    private Label labelValidosRobo2;

    @FXML
    private Label labelVencedor;

    @FXML
    private VBox VboxRobo2;

    @FXML
    void fecharJogo(ActionEvent event) {
        ((Stage) (btnSair.getScene().getWindow())).close();
    }

    public void receberDados(Robo robo1, Robo robo2, Modo modoDeJogo){
        labelVencedor.setText("Robô " + robo1.getCor().toString().substring(0, 1).toUpperCase() +
                robo1.getCor().toString().substring(1).toLowerCase() + " Venceu!!");

        labelValidosRobo1.setText( String.format("%d", robo1.getMovimentosValidos()));
        labelInvalidosRobo1.setText( String.format("%d", robo1.getMovimentosInvalidos()));
        if(modoDeJogo != Modo.USUARIO) {
            labelValidosRobo2.setText(String.format("%d", robo2.getMovimentosValidos()));
            labelInvalidosRobo2.setText(String.format("%d", robo2.getMovimentosInvalidos()));
        }else{
            VboxRobo2.setVisible(false);
        }
    }

}
