package com.projetorobo.controllers;

import com.projetorobo.exception.AlimentoForaDoLimiteException;
import com.projetorobo.exception.CoresDuplicadasException;
import com.projetorobo.model.enums.CategoriaRobo;
import com.projetorobo.model.enums.Cor;
import com.projetorobo.model.enums.Dificuldade;
import com.projetorobo.model.enums.Modo;
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
    private int tamanhoTabuleiro = 10;
    private List<Cor> cores = new ArrayList<>();
    private List<CategoriaRobo> categorias = new ArrayList<>();
    private List<Dificuldade> dificuldades = new ArrayList<>();
    private ObservableList<Cor> obsCores;
    private ObservableList<CategoriaRobo> obsCategotias;
    private ObservableList<Dificuldade> obsDificuldades;

    private Modo modoDeJogo;

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
    private ComboBox<Dificuldade> comboBoxDificuldade;

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

            int posicaoXAlimento = Integer.parseInt(textPosX.getText());
            int posicaoYAlimento = Integer.parseInt(textPosY.getText());

            if(posicaoYAlimento >= tamanhoTabuleiro || posicaoXAlimento >= tamanhoTabuleiro){
                throw new AlimentoForaDoLimiteException();
            }

            if (comboBoxCorRobo1.getValue() == comboBoxCorRobo2.getValue()) {
                throw new CoresDuplicadasException();
            }

            String corRobo1 = comboBoxCorRobo1.getValue().toString();
            String corRobo2 = comboBoxCorRobo2.getValue().toString();
            Dificuldade dificuldade = comboBoxDificuldade.getValue();
            CategoriaRobo categoriaRobo1 = comboBoxInteligenciaRobo1.getValue();
            CategoriaRobo categoriaRobo2 = comboBoxInteligenciaRobo2.getValue();

            TabuleiroController controller = loader.getController();
            controller.receberDados(posicaoXAlimento, posicaoYAlimento, corRobo1, corRobo2,
                    dificuldade, categoriaRobo1, categoriaRobo2, modoDeJogo);

            //Estágio - (Janela)
            Stage stage = new Stage();
            stage.setTitle("Modo de jogo: " + modoDeJogo.toString().toLowerCase());
            stage.setResizable(false);
            //Cena - tem elementos, produz animações
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            //  currentStage - janela atual paegada na janela, onde possui a cena que possui o botão
            Stage currentStage = (Stage) buttonComecar.getScene().getWindow();
            currentStage.close(); // fecha a janela atual

        } catch (IllegalArgumentException e) {
            System.out.println("Entrada inválida!");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void carregarComboboxes(){
        cores.add(Cor.VERMELHO);
        cores.add(Cor.MARROM);
        cores.add(Cor.AMARELO);
        cores.add(Cor.AZUL);

        categorias.add(CategoriaRobo.BURRO);
        categorias.add(CategoriaRobo.INTELIGENTE);

        dificuldades.add(Dificuldade.FACILIMO);
        dificuldades.add(Dificuldade.FACIL);
        dificuldades.add(Dificuldade.MEDIO);
        dificuldades.add(Dificuldade.DIFICIL);

        obsCores = FXCollections.observableArrayList(cores);
        obsCategotias = FXCollections.observableArrayList(categorias);
        obsDificuldades = FXCollections.observableArrayList(dificuldades);

        comboBoxCorRobo1.setItems(obsCores);
        comboBoxCorRobo2.setItems(obsCores);
        comboBoxInteligenciaRobo1.setItems(obsCategotias);
        comboBoxInteligenciaRobo2.setItems(obsCategotias);
        comboBoxDificuldade.setItems(obsDificuldades);
    }

    public void receberDados(Modo modo){
        this.modoDeJogo = modo;
    }
}
