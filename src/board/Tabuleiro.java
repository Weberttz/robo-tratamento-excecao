package board;

import model.Robo;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
    private int tamanho;
    private List<Robo> robos;
    private int alimentoX, alimentoY;

    public Tabuleiro(int tamanho, int alimentoX, int alimentoY) {
        this.tamanho = tamanho;
        this.robos = new ArrayList<>();
        setAlimento(alimentoX, alimentoY);
    }

    public void renderizar() {
        for (int y = tamanho; y >= 0; y--) {
            for (int x = 0; x <= tamanho; x++) {
                if (x == alimentoX && y == alimentoY) {
                    System.out.print(" A ");
                } else {
                    boolean temRobo = false;
                    for (Robo robo : robos) {
                        if (robo.getX() == x && robo.getY() == y) {
                            System.out.print(" R ");
                            temRobo = true;
                            break;
                        }
                    }
                    if (!temRobo) System.out.print(" . ");
                }
            }
            System.out.println();
        }
    }

    public void adicionarRobo(Robo robo){
        robos.add(robo);
    }

    public void setAlimento(int alimentoX, int alimentoY){
        this.alimentoX = alimentoX;
        this.alimentoY = alimentoY;
    }
}
