package board;

import exception.MovimentoInvalidoException;
import model.robos.Robo;
import model.enums.Direcao;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
    private int tamanho;
    private List<Robo> robos;
    private int alimentoX, alimentoY;

    public Tabuleiro(int tamanho, int alimentoX, int alimentoY) {
        this.tamanho = tamanho;
        this.robos = new ArrayList<>();
        //validar depois
        setAlimento(alimentoX, alimentoY);
    }

    private Robo getRoboNaPosicao(int x, int y) {
        for (Robo robo : robos) {
            if (robo.getX() == x && robo.getY() == y) {
                return robo;
            }
        }
        return null;
    }

    public void renderizar() {
        for (int y = tamanho - 1; y >= 0; y--) {
            for (int x = 0; x < tamanho; x++) {

                if (getRoboNaPosicao(x, y) != null) {
                    System.out.print(" R ");
                }
                else if (x == alimentoX && y == alimentoY) {
                    System.out.print(" A ");
                }
                else {
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }
    }

    public void moverRobo(Robo robo, Direcao dir) throws MovimentoInvalidoException {

        int oldX = robo.getX();
        int oldY = robo.getY();

        robo.mover(dir);

        if (robo.getX() < 0 || robo.getX() >= tamanho ||
                robo.getY() < 0 || robo.getY() >= tamanho) {

            robo.setX(oldX);
            robo.setY(oldY);

            robo.incrementarInvalidos();
            throw new MovimentoInvalidoException(dir.name());
        }


        if (getRoboNaPosicao(robo.getX(), robo.getY()) != null &&
                getRoboNaPosicao(robo.getX(), robo.getY()) != robo) {

            robo.setX(oldX);
            robo.setY(oldY);

            robo.incrementarInvalidos();
            //Criar Excecao pra colisao
            throw new MovimentoInvalidoException("COLISAO");
        }

        robo.incrementarValidos();
    }

    public void adicionarRobo(Robo robo) {
        if (!robos.isEmpty() && tamanho > 1) {
            robo.setX(robos.size());
        }
        robos.add(robo);
    }

    public void setAlimento(int alimentoX, int alimentoY){
        this.alimentoX = alimentoX;
        this.alimentoY = alimentoY;
    }

    public int getTamanho() {
        return tamanho;
    }

    public List<Robo> getRobos() {
        return robos;
    }

    public int getAlimentoX() {
        return alimentoX;
    }

    public int getAlimentoY() {
        return alimentoY;
    }
}
