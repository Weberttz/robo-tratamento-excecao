package com.projetorobo.service;

import com.projetorobo.controllers.TelaResultadoController;
import com.projetorobo.exception.ColisaoComObstaculoException;
import com.projetorobo.exception.MovimentoInvalidoException;
import com.projetorobo.model.board.Tabuleiro;
import com.projetorobo.model.enums.Direcao;
import com.projetorobo.model.enums.Modo;
import com.projetorobo.model.robo.Robo;
import com.projetorobo.util.AlertaUtil;
import com.projetorobo.view.TabuleiroView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class JogoService {
    private Robo robo1, robo2;
    private Tabuleiro tabuleiro;
    private int turno;
    private Modo modoDeJogo;

    public JogoService(Robo robo1, Robo robo2, Tabuleiro tabuleiro, Modo modoDeJogo){
        this.robo1 = robo1;
        this.robo2 = robo2;
        this.turno = 1;
        this.tabuleiro = tabuleiro;
        this.modoDeJogo = modoDeJogo;
    }
    private void jogarTurno(Robo robo, ImageView imageViewRobo, ArrayList<String> listaHistorico,
                            TabuleiroView tabuleiroView) {
        Direcao dir = robo.getEstrategiaMovimento().escolherDirecao();
        String linha;
        try {
            tabuleiro.moverRobo(robo, dir);
            tabuleiroView.direcionarImageViewRobo(robo, imageViewRobo, dir, true);
            linha = String.format("%s - Robô %s em (%d,%d)", dir.toString().toLowerCase(), robo.getCor().toString().toLowerCase()
                    ,robo.getNewX(), robo.getNewY());
        } catch (MovimentoInvalidoException | ColisaoComObstaculoException e) {
            linha = String.format("%s - Robô %s colidiu", dir.toString().toLowerCase(), robo.getCor().toString().toLowerCase());
            tabuleiroView.direcionarImageViewRobo(robo, imageViewRobo, dir, false);
        }
        listaHistorico.add(linha);

        if(robo.isExplodiu()){
            linha = String.format("Robô %s explodiu!", robo.getCor().toString().toLowerCase());
            listaHistorico.add(linha);
            tabuleiroView.getAnimacoesService().limparColisaoComBomba(tabuleiroView, imageViewRobo);
        }else if(robo.getAchouAlimento()){
            linha = String.format("Robô %s achou alimento!", robo.getCor().toString().toLowerCase());
            listaHistorico.add(linha);
            tabuleiroView.getAnimacoesService().limparColisaoComAlimento(imageViewRobo);
        }
    }

    public void jogarModoUsuario(TabuleiroView tabuleiroView, List<String> listaHistorico, String movimento) {
        Direcao direcao = null;
        try {
            if (movimento.matches("\\d+")) {
                direcao = Direcao.fromInt(Integer.parseInt(movimento));
            } else {
                direcao = Direcao.fromString(movimento);
            }

            tabuleiro.moverRobo(robo1, direcao);
            // boolean serve para animar sprite e só depois mover
            tabuleiroView.direcionarImageViewRobo(robo1, tabuleiroView.getImageViewRobo1(), direcao, true);
            String linha = String.format("%s - Robô em (%d,%d)%n", direcao.toString().toLowerCase(), robo1.getNewX(), robo1.getNewY());
            listaHistorico.add(linha);
        } catch (MovimentoInvalidoException | ColisaoComObstaculoException e) {
            String linha = String.format("%s - Robô  colidiu", direcao.toString().toLowerCase());
            listaHistorico.add(linha);
            tabuleiroView.direcionarImageViewRobo(robo1, tabuleiroView.getImageViewRobo1(), direcao, false);
        } catch (IllegalArgumentException e) {
            AlertaUtil.mostrarErro("Direção desconhecida.\nUse: up, down, left, right  ou  1,2,3,4");
        }

        if (robo1.isExplodiu() || robo1.getAchouAlimento()) {
            tabuleiroView.getAnimacoesService().finalizarJogo(tabuleiroView.getImageViewAlimento());
            tabuleiroView.chamarJanelaResultados(robo1, robo2, modoDeJogo);
        }

        ObservableList<String> obsHistorico = FXCollections.observableArrayList(listaHistorico);
        tabuleiroView.getListaHistorico().setItems(obsHistorico);
    }

    public void jogarModoAutomatico(TabuleiroView tabuleiroView, ArrayList<String> listaHistorico, int tempoTimeline){
        Timeline timeline = new Timeline();
        try {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(tempoTimeline), e -> {

                boolean robo1Acabou = robo1.isExplodiu() || robo1.getAchouAlimento();
                boolean robo2Acabou = robo2.isExplodiu() || robo2.getAchouAlimento();

                if(robo1Acabou || robo2Acabou)
                    turno+=2;

                if(turno % 2 == 1 && !robo1Acabou)
                    jogarTurno(robo1, tabuleiroView.getImageViewRobo1(), listaHistorico, tabuleiroView);
                else if(turno % 2 == 0 && !robo2Acabou)
                    jogarTurno(robo2, tabuleiroView.getImageViewRobo2(), listaHistorico, tabuleiroView);
                else if(verificarFinalizacaoDeJogo(robo1, robo2, modoDeJogo, tabuleiroView))
                    timeline.stop();

                ObservableList<String> obsHistorico = FXCollections.observableArrayList(listaHistorico);
                tabuleiroView.getListaHistorico().setItems(obsHistorico);
                turno++;
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }catch (Exception e){
            AlertaUtil.mostrarErro("Parada terminal.");
        }
    }

    public boolean verificarFinalizacaoDeJogo(Robo robo1, Robo robo2, Modo modoDeJogo, TabuleiroView tabuleiroView){
        boolean robo1Terminou = robo1.getAchouAlimento() || robo1.isExplodiu();
        boolean robo2Terminou = robo2.getAchouAlimento() || robo2.isExplodiu();

        boolean umAchouAlimento = robo1.getAchouAlimento() || robo2.getAchouAlimento();
        boolean robosExplodiram = robo1.isExplodiu() && robo2.isExplodiu();

        if ((umAchouAlimento || robosExplodiram) && modoDeJogo == Modo.COMPETITIVO) {
            tabuleiroView.getAnimacoesService().finalizarJogo(tabuleiroView.getImageViewAlimento());
            tabuleiroView.chamarJanelaResultados(robo1, robo2, modoDeJogo);
            return true;
        }
        else if((robo1Terminou && robo2Terminou) && modoDeJogo == Modo.COOPERATIVO) {
            tabuleiroView.getAnimacoesService().finalizarJogo(tabuleiroView.getImageViewAlimento());
            tabuleiroView.chamarJanelaResultados(robo1, robo2, modoDeJogo);
            return true;
        }
        return false;
    }

}
