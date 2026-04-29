package com.projetorobo;

import com.projetorobo.model.enums.CategoriaRobo;
import com.projetorobo.model.enums.Cor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TelaRobosController {

    private List<Cor> cores = new ArrayList<>();
    private List<CategoriaRobo> categorias = new ArrayList<>();
    private ObservableList<Cor> obsCores;
    private ObservableList<CategoriaRobo> obsCategotias;

    @FXML
    private ImageView Robo;

    @FXML
    private Button buttonComecar;

    @FXML
    private ComboBox<Cor> comboBoxCorRobo1;

    @FXML
    private ComboBox<Cor> comboBoxCorRobo2;

    @FXML
    private ComboBox<CategoriaRobo> comboBoxInteligenciaRobo1;

    @FXML
    private ComboBox<CategoriaRobo> comboBoxInteligenciaRobo2;

    @FXML
    private TextField textPosX;

    @FXML
    private TextField textPosY;

    @FXML
    void initialize(){
        carregarComboboxes();
    }

    @FXML
    void comecarJogo(ActionEvent event) {
        try{
            //Carregador - o que carrega
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetorobo/tabuleiro.fxml"));
            Parent root = loader.load();

            //Estágio - (Janela)
            Stage stage = new Stage();
            stage.setTitle("Projeto");
            stage.setResizable(false);
            //Cena - tem elementos, produz animações
            Scene scene = new Scene(root, 634, 577);
            stage.setScene(scene);
            stage.show();

            //  currentStage - janela atual paegada na janela, onde possui a cena que possui o botão
            Stage currentStage = (Stage) buttonComecar.getScene().getWindow();
            currentStage.close(); // fecha a janela atual

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void carregarComboboxes(){
        cores.add(Cor.AMARELO);
        cores.add(Cor.VERMELHO);
        cores.add(Cor.AZUL);
        cores.add(Cor.MARROM);

        categorias.add(CategoriaRobo.INTELIGENTE);
        categorias.add(CategoriaRobo.BURRO);

        obsCores = FXCollections.observableArrayList(cores);
        obsCategotias = FXCollections.observableArrayList(categorias);

        comboBoxCorRobo1.setItems(obsCores);
        comboBoxCorRobo2.setItems(obsCores);
        comboBoxInteligenciaRobo1.setItems(obsCategotias);
        comboBoxInteligenciaRobo2.setItems(obsCategotias);
    }

}
