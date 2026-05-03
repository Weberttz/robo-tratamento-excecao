package com.projetorobo.service;

import com.projetorobo.exception.ColisaoComObstaculoException;
import com.projetorobo.exception.MovimentoInvalidoException;
import com.projetorobo.model.board.Tabuleiro;
import com.projetorobo.model.enums.Direcao;
import com.projetorobo.model.enums.Modo;
import com.projetorobo.model.robos.Robo;
import com.projetorobo.view.TabuleiroView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import javax.swing.text.html.ListView;
import java.sql.Time;
import java.util.ArrayList;

public class JogoService {
    private Robo robo1, robo2;
    private Tabuleiro tabuleiro;
    private int turno = 1;
    private Modo modoDeJogo;

    public JogoService(Robo robo1, Robo robo2, Tabuleiro tabuleiro, Modo modoDeJogo){
        this.robo1 = robo1;
        this.robo2 = robo2;
        this.tabuleiro = tabuleiro;
        this.modoDeJogo = modoDeJogo;
    }
    private void jogarTurno(Robo robo, ImageView imageViewRobo, ArrayList<String> listaHistorico,
                            TabuleiroView tabuleiroView) {
        Direcao dir = robo.escolherDirecao();
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

    public void jogarModoAutomatico(TabuleiroView tabuleiroView, ArrayList<String> listaHistorico, int tempoTimeline){
        Timeline timeline = new Timeline();
        try {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(tempoTimeline), e -> {
                if (turno % 2 == 1 && !robo1.isExplodiu() && !robo1.getAchouAlimento())
                    jogarTurno(robo1, tabuleiroView.getImageViewRobo1(), listaHistorico, tabuleiroView);
                else if (turno % 2 == 0 && !robo2.isExplodiu() && !robo2.getAchouAlimento())
                    jogarTurno(robo2, tabuleiroView.getImageViewRobo2(), listaHistorico, tabuleiroView);
                else if(verificarFinalizacaoDeJogo(robo1, robo2, modoDeJogo, tabuleiroView)){
                    timeline.stop();
                }
                ObservableList<String> obsHistorico = FXCollections.observableArrayList(listaHistorico);
                tabuleiroView.getListaHistorico().setItems(obsHistorico);
                turno++;
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }catch (Exception e){
            System.out.println("Parada Terminal");
        }
    }

    public boolean verificarFinalizacaoDeJogo(Robo robo1, Robo robo2, Modo modoDeJogo, TabuleiroView tabuleiroView){
        boolean robo1Terminou = robo1.getAchouAlimento() || robo1.isExplodiu();
        boolean robo2Terminou = robo2.getAchouAlimento() || robo2.isExplodiu();

        boolean umAchouAlimento = robo1.getAchouAlimento() || robo2.getAchouAlimento();
        boolean robosExplodiram = robo1.isExplodiu() && robo2.isExplodiu();

        if ((umAchouAlimento || robosExplodiram) && modoDeJogo == Modo.COMPETITIVO) {
            tabuleiroView.getAnimacoesService().finalizarJogo(tabuleiroView.getImageViewAlimento());
            return true;
        }
        else if((robo1Terminou && robo2Terminou) && modoDeJogo == Modo.COOPERATIVO) {
            tabuleiroView.getAnimacoesService().finalizarJogo(tabuleiroView.getImageViewAlimento());
            return true;
        }
        return false;
    }

}
