package com.projetorobo.controllers;
import com.projetorobo.model.board.Tabuleiro;
import com.projetorobo.exception.ColisaoComObstaculoException;
import com.projetorobo.exception.MovimentoInvalidoException;
import com.projetorobo.model.enums.CategoriaRobo;
import com.projetorobo.model.enums.Dificuldade;
import com.projetorobo.model.enums.Direcao;
import com.projetorobo.model.enums.Modo;
import com.projetorobo.model.robos.Robo;
import com.projetorobo.model.robos.RoboInteligente;
import com.projetorobo.service.AnimacoesService;
import com.projetorobo.service.JogoService;
import com.projetorobo.service.ObstaculoService;
import com.projetorobo.view.TabuleiroView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;

public class TabuleiroController{
    private int turno = 1;
    private final int tamanhoTabuleiro = 10;
    private final int tempoTimeline = 1000;
    private final int tempoDeTrocaDeframe = (int) (0.17 * tempoTimeline);
    private Tabuleiro tabuleiro;
    private Dificuldade dificuldade;
    private Modo modoDeJogo;
    private Robo robo1;
    private Robo robo2;

    private static Timeline timeline;
    
    @FXML
    private ImageView imageViewRobo1;
    @FXML
    private ImageView imageViewRobo2;

    @FXML
    private Button buttonMover;

    @FXML
    private AnchorPane containerTabuleiro;

    @FXML
    private TextField textFieldMovimento;

    @FXML
    private ListView<String> listViewHistorico;
    private ArrayList<String> listaHistorico = new ArrayList<>();
    private ObservableList<String> obsHistorico;

    private TabuleiroView tabuleiroView;
    private AnimacoesService animacoesService;
    private JogoService jogoService;
    private ObstaculoService obstaculoService = new ObstaculoService();

    //Main1
    //Age como se fosse um initialize, configura o tabuleiro com base no que precisamos
    public void receberDados(int posicaoX, int posicaoY, String cor, Dificuldade dificuldade, Modo modoDeJogo){
        this.tabuleiro = new Tabuleiro(tamanhoTabuleiro, posicaoX, posicaoY);
        this.dificuldade = dificuldade;
        this.modoDeJogo = modoDeJogo;
        this.robo1 = new Robo(cor);
        this.tabuleiro.adicionarRobo(robo1);

        this.imageViewRobo2.setVisible(false);

        obstaculoService.calcularObstaculos(tabuleiro, dificuldade, tabuleiroView);
        tabuleiroView.settarRoboNoAnchorPane(robo1, imageViewRobo1);
        tabuleiroView.settarAlimentoNoAnchorPane(tabuleiro);
    }

    //Main2, Main3, Main4
    public void receberDados(int posicaoX, int posicaoY, String corRobo1, String corRobo2,
                             Dificuldade dificuldade, CategoriaRobo categoriaRobo1, CategoriaRobo categoriaRobo2, Modo modoDeJogo){
        this.buttonMover.setDisable(true);
        this.textFieldMovimento.setText("Os robôs criaram vida!");
        this.textFieldMovimento.setDisable(true);
        this.dificuldade = dificuldade;
        this.modoDeJogo = modoDeJogo;

        this.tabuleiro = new Tabuleiro(tamanhoTabuleiro, posicaoX, posicaoY);

        switch (categoriaRobo1){
            case BURRO -> this.robo1 = new Robo(corRobo1);
            case INTELIGENTE -> this.robo1 = new RoboInteligente(corRobo1);
        }

        switch (categoriaRobo2){
            case BURRO -> this.robo2 = new Robo(corRobo2);
            case INTELIGENTE -> this.robo2 = new RoboInteligente(corRobo2);
        }

         tabuleiroView = new TabuleiroView(imageViewRobo1, imageViewRobo2, containerTabuleiro,
                listViewHistorico, tempoDeTrocaDeframe);
         animacoesService =  new AnimacoesService(tempoDeTrocaDeframe);
         jogoService = new JogoService(robo1, robo2, tabuleiro, modoDeJogo);

        this.tabuleiro.adicionarRobo(robo1);
        this.tabuleiro.adicionarRobo(robo2);
        this.robo2.modificarPosicaoInicial(0, 1);
        obstaculoService.calcularObstaculos(tabuleiro, dificuldade, tabuleiroView);
        tabuleiroView.settarRoboNoAnchorPane(robo1, imageViewRobo1);
        tabuleiroView.settarRoboNoAnchorPane(robo2, imageViewRobo2);
        tabuleiroView.settarAlimentoNoAnchorPane(tabuleiro);
        controlarRobos();
    }

    //Main1?????????????????????
    @FXML
    public void movimentar(ActionEvent event) { //Main1
        buttonMover.setDisable(true); // desabilitar botão após o clique
        Direcao direcao = null;
        try {
            if(textFieldMovimento.getText().matches("\\d+")){
                direcao = Direcao.fromInt(Integer.parseInt(textFieldMovimento.getText()));
            }else {
                direcao = Direcao.fromString(textFieldMovimento.getText());
            }

            tabuleiro.moverRobo(robo1, direcao);
            // boolean serve para animar sprite e só depois mover
            tabuleiroView.direcionarImageViewRobo(robo1, imageViewRobo1, direcao, true);
            String linha = String.format("%s - Robô em (%d,%d)%n", direcao.toString().toLowerCase(), robo1.getNewX(), robo1.getNewY());
            listaHistorico.add(linha);
        } catch (MovimentoInvalidoException | ColisaoComObstaculoException e) {
            String linha = String.format("%s - Robô  colidiu", direcao.toString().toLowerCase());
            listaHistorico.add(linha);
            tabuleiroView.direcionarImageViewRobo(robo1, imageViewRobo1, direcao, false);
        } catch (IllegalArgumentException e) {
            System.out.println("Direção desconhecida. Use: up, down, left, right  ou  1,2,3,4");
        }

        if(robo1.isExplodiu() || robo1.getAchouAlimento())
            animacoesService.finalizarJogo(tabuleiroView.getImageViewAlimento());

        //Espera 1.5 segundos e habilita novamente o botão - sem problemas com as animações de movimento
        animacoesService.habilitarBotao(buttonMover);
        obsHistorico = FXCollections.observableArrayList(listaHistorico);
        listViewHistorico.setItems(obsHistorico);
    }

    public void controlarRobos(){
        jogoService.jogarModoAutomatico(tabuleiroView, listaHistorico, tempoTimeline);
    }

    public void chamarCreditos(){
        if(modoDeJogo == Modo.USUARIO) {
            System.out.printf("%n[%s] válidos: %d | inválidos: %d%n",
                    robo1.getCor(), robo1.getMovimentosValidos(), robo1.getMovimentosInvalidos());
        } else{
            System.out.printf("%n[%s] válidos: %d | inválidos: %d%n",
                    robo1.getCor(), robo1.getMovimentosValidos(), robo1.getMovimentosInvalidos());
            System.out.printf("%n[%s] válidos: %d | inválidos: %d%n", robo2.getCor(),
                    robo2.getMovimentosValidos(), robo2.getMovimentosInvalidos());
        }
    }
}

// Dividir a lógica em service, view e controller
// Lógica de jogo em servide - Manipulação do back-end
// Lógica de UI em view, manipulação dos elementos da cena e manipulação das sprites