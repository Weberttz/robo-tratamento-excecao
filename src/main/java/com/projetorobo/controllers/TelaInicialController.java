package com.projetorobo.controllers;

import com.projetorobo.model.enums.Modo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TelaInicialController {

    private List<Modo> modosDeJogo = new ArrayList<>();

    @FXML
    private Button buttonJogar;

    @FXML
    private ComboBox<Modo> comboBoxModoJogo;

    @FXML
    public void initialize() {
        carregarComboBox();
    }

    public void carregarComboBox() {
        modosDeJogo.add(Modo.USUARIO);
        modosDeJogo.add(Modo.COMPETITIVO);
        modosDeJogo.add(Modo.COOPERATIVO);

        comboBoxModoJogo.setItems(FXCollections.observableArrayList(modosDeJogo));
    }

    @FXML
    public void comecarJogo(ActionEvent event) {
        if(comboBoxModoJogo.getValue() != null){
           Modo modoEscolhido = comboBoxModoJogo.getValue();
           String caminho = null;
           int tamJanelaX = 338, tamJanelaY = 175;

            switch (modoEscolhido){
                case USUARIO -> {
                    caminho = "/com/projetorobo/telaUsuario.fxml";
                    tamJanelaX = 520;
                    tamJanelaY = 420;
                }
                case COMPETITIVO, COOPERATIVO -> {
                    caminho = "/com/projetorobo/telaRobos.fxml";
                    tamJanelaX = 560;
                    tamJanelaY = 508;
                }
                default -> caminho = "com/projetorobo/telaInicial.fxml"; // alterar dps
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
                Parent root = loader.load();

                if (modoEscolhido == Modo.USUARIO) {
                    TelaUsuarioController controller1 = loader.getController();
                    controller1.receberDados(modoEscolhido);
                } else if (modoEscolhido == Modo.COMPETITIVO || modoEscolhido == Modo.COOPERATIVO){
                    TelaRobosController controller2 = loader.getController();
                    controller2.receberDados(modoEscolhido);
                }

                Stage stage = new Stage();
                stage.setTitle("Modo de Jogo - " + modoEscolhido.name().toLowerCase());
                stage.setResizable(false);
                stage.setScene(new Scene(root, tamJanelaX, tamJanelaY));
                stage.show();

                Stage currentStage = (Stage) buttonJogar.getScene().getWindow();
                currentStage.close();
            }catch (Exception e) {
            System.out.println(e.getMessage());
            }
        }
    }

}
