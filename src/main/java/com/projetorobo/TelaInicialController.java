package com.projetorobo;

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

    @FXML
    private Button buttonJogar;

    @FXML
    private ComboBox<Modo> comboBoxModoJogo;
    private List<Modo> modosDeJogo = new ArrayList<>();

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

            switch (modoEscolhido){
                case USUARIO -> caminho = "/com/projetorobo/telaUsuario.fxml";
                case COMPETITIVO -> caminho = "/com/projetorobo/telaCompetitivo.fxml";
                case COOPERATIVO -> caminho = "/com/projetorobo/telaCooperativo.fxml";
                default -> caminho = "com/projetorobo/telaInicial.fxml"; // alterar dps
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Modo de Jogo - " + modoEscolhido.name().toLowerCase());
                stage.setResizable(false);
                stage.setScene(new Scene(root, 517, 402));
                stage.show();

                Stage currentStage = (Stage) buttonJogar.getScene().getWindow();
                currentStage.close();
            }catch (Exception e) {
            System.out.println(e.getMessage());
        }

        }
    }

}
