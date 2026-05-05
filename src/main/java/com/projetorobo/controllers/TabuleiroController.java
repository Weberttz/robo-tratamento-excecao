package com.projetorobo.controllers;
import com.projetorobo.model.board.Tabuleiro;
import com.projetorobo.model.enums.CategoriaRobo;
import com.projetorobo.model.enums.Cor;
import com.projetorobo.model.enums.Dificuldade;
import com.projetorobo.model.enums.Modo;
import com.projetorobo.model.robo.Robo;
import com.projetorobo.model.robo.estrategias.*;
import com.projetorobo.service.AnimacoesService;
import com.projetorobo.service.JogoService;
import com.projetorobo.service.ObstaculoService;
import com.projetorobo.util.AlertaUtil;
import com.projetorobo.view.TabuleiroView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class TabuleiroController{
    private final int tamanhoTabuleiro = 10;
    private final int tempoTimeline = 1000;
    private final int tempoDeTrocaDeframe = (int) (0.17 * tempoTimeline);
    private Tabuleiro tabuleiro;
    private Modo modoDeJogo;
    private Robo robo1;
    private Robo robo2;
    private List<Cor> coresRobos = new ArrayList<>();
    private List<CategoriaRobo> estrategias = new ArrayList<>();

    @FXML
    private HBox HBoxEstrategias;

    @FXML
    private HBox HBoxInstrucoes;

    @FXML
    private ImageView imageViewRobo1;
    @FXML
    private ImageView imageViewRobo2;

    @FXML
    private Button buttonMover;

    @FXML
    private ComboBox<Cor> comboBoxCorRobo;

    @FXML
    private ComboBox<CategoriaRobo> comboBoxNovaEstrategiaRobo;

    @FXML
    private AnchorPane containerTabuleiro;

    @FXML
    private TextField textFieldMovimento;

    @FXML
    private ListView<String> listViewHistorico;
    private final ArrayList<String> listaHistorico = new ArrayList<>();

    private TabuleiroView tabuleiroView;
    private JogoService jogoService;
    private AnimacoesService animacoesService = new AnimacoesService(tempoDeTrocaDeframe);
    private final ObstaculoService obstaculoService = new ObstaculoService();

    public void receberDados(int posicaoX, int posicaoY, String cor, Dificuldade dificuldade, Modo modoDeJogo){
        this.tabuleiro = new Tabuleiro(tamanhoTabuleiro, posicaoX, posicaoY);
        this.modoDeJogo = modoDeJogo;
        this.robo1 = new Robo(cor);
        this.tabuleiro.adicionarRobo(robo1);

        this.imageViewRobo2.setVisible(false);

        HBoxInstrucoes.setVisible(true);
        HBoxEstrategias.setVisible(false);

        this.tabuleiroView = new TabuleiroView(imageViewRobo1, imageViewRobo2, containerTabuleiro,
                listViewHistorico, animacoesService);
        this.jogoService = new JogoService(robo1, robo2, tabuleiro, modoDeJogo);

        obstaculoService.calcularObstaculos(tabuleiro, dificuldade, tabuleiroView);
        tabuleiroView.settarRoboNoAnchorPane(robo1, imageViewRobo1);
        tabuleiroView.settarAlimentoNoAnchorPane(tabuleiro);
    }

    public void receberDados(int posicaoX, int posicaoY, String corRobo1, String corRobo2,
                             Dificuldade dificuldade, CategoriaRobo categoriaRobo1, CategoriaRobo categoriaRobo2, Modo modoDeJogo){
        this.buttonMover.setDisable(true);
        this.textFieldMovimento.setText("Os robôs criaram vida!");
        this.textFieldMovimento.setDisable(true);
        this.modoDeJogo = modoDeJogo;

        HBoxInstrucoes.setVisible(false);
        HBoxEstrategias.setVisible(true);

        this.tabuleiro = new Tabuleiro(tamanhoTabuleiro, posicaoX, posicaoY);

        this.robo1 = new Robo(corRobo1);
        this.robo2 = new Robo(corRobo2);
        this.robo2.modificarPosicaoInicial(0, 1);
        
        switch (categoriaRobo1){
            case BURRO -> robo1.setEstrategiaMovimento(new EstrategiaAleatoria());
            case INTELIGENTE -> robo1.setEstrategiaMovimento(new EstrategiaInteligente());
            case MEMORIA -> robo1.setEstrategiaMovimento(new EstrategiaMemoria(robo1));
            case ESTRATEGISTA -> robo1.setEstrategiaMovimento(new EstrategiaEstrategica(robo1, tabuleiro));
        }

        switch (categoriaRobo2){
            case BURRO -> robo2.setEstrategiaMovimento(new EstrategiaAleatoria());
            case INTELIGENTE -> robo2.setEstrategiaMovimento(new EstrategiaInteligente());
            case MEMORIA -> robo2.setEstrategiaMovimento(new EstrategiaMemoria(robo2));
            case ESTRATEGISTA -> robo2.setEstrategiaMovimento(new EstrategiaEstrategica(robo2, tabuleiro));
        }

        coresRobos.add(robo1.getCor());
        coresRobos.add(robo2.getCor());

        estrategias.add(CategoriaRobo.BURRO);
        estrategias.add(CategoriaRobo.INTELIGENTE);
        estrategias.add(CategoriaRobo.ESTRATEGISTA);
        estrategias.add(CategoriaRobo.MEMORIA);

        this.tabuleiroView = new TabuleiroView(imageViewRobo1, imageViewRobo2, containerTabuleiro,
                listViewHistorico, animacoesService);
        this.tabuleiro.adicionarRobo(robo1);
        this.tabuleiro.adicionarRobo(robo2);
        this.jogoService = new JogoService(robo1, robo2, tabuleiro, modoDeJogo);
        obstaculoService.calcularObstaculos(tabuleiro, dificuldade, tabuleiroView);
        tabuleiroView.settarRoboNoAnchorPane(robo1, imageViewRobo1);
        tabuleiroView.settarRoboNoAnchorPane(robo2, imageViewRobo2);
        tabuleiroView.settarAlimentoNoAnchorPane(tabuleiro);

        comboBoxCorRobo.setItems(FXCollections.observableArrayList(coresRobos));
        comboBoxNovaEstrategiaRobo.setItems(FXCollections.observableArrayList(estrategias));

        controlarRobos();
    }

    @FXML
    public void movimentar(ActionEvent event) {
        buttonMover.setDisable(true);  // desabilitar botão após o clique
        jogoService.jogarModoUsuario(tabuleiroView, listaHistorico, textFieldMovimento.getText());
        tabuleiroView.getAnimacoesService().habilitarBotao(buttonMover);
    }

    @FXML
    void trocarEstrategia(ActionEvent event) {
        Cor corRobo = comboBoxCorRobo.getValue();
        CategoriaRobo novaEstrategia = comboBoxNovaEstrategiaRobo.getValue();
        Robo roboAtual = robo1.getCor() == corRobo ? robo1 : robo2;

        if (corRobo == null || novaEstrategia == null) {
            AlertaUtil.mostrarErro("Selecione o robô e a estratégia.");
            return;
        }

        try{

            if(roboAtual.isExplodiu() || roboAtual.getAchouAlimento()){
                throw new RuntimeException("Robô " + corRobo.name().toLowerCase() + " não está mais disponível");
            }
            if(roboAtual.getEstrategiaMovimento() == novaEstrategia.getEstrategia(roboAtual, tabuleiro)){
                throw new RuntimeException("Robô " + corRobo.name().toLowerCase() + " já usa essa estratégia.");
            }

            System.out.println("Antiga estratégia : " + roboAtual.getEstrategiaMovimento());
            roboAtual.setEstrategiaMovimento(novaEstrategia.getEstrategia(roboAtual, tabuleiro));
            System.out.println("Nova estratégia : " + roboAtual.getEstrategiaMovimento());

        }catch (RuntimeException e){
            AlertaUtil.mostrarErro(e.getMessage());
        }
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