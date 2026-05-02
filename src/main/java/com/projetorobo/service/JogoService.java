package com.projetorobo.service;

import com.projetorobo.model.board.Tabuleiro;
import com.projetorobo.model.enums.Modo;
import com.projetorobo.model.robos.Robo;
import javafx.scene.image.ImageView;

public class JogoService {
    private Tabuleiro tabuleiro;

    public boolean verificarFinalizacaoDeJogo(Robo robo1, Robo robo2, Modo modoDeJogo, AnimacoesService animacoesService, ImageView imageViewAlimento){
        boolean robo1Terminou = robo1.getAchouAlimento() || robo1.isExplodiu();
        boolean robo2Terminou = robo2.getAchouAlimento() || robo2.isExplodiu();

        boolean umAchouAlimento = robo1.getAchouAlimento() || robo2.getAchouAlimento();
        boolean robosExplodiram = robo1.isExplodiu() && robo2.isExplodiu();

        if ((umAchouAlimento || robosExplodiram) && modoDeJogo == Modo.COMPETITIVO) {
            animacoesService.finalizarJogo(imageViewAlimento);
            return true;
        }
        else if((robo1Terminou && robo2Terminou) && modoDeJogo == Modo.COOPERATIVO) {
            animacoesService.finalizarJogo(imageViewAlimento);
            return true;
        }
        return false;
    }
}
