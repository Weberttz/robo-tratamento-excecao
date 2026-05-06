package com.projetorobo.util;

import com.projetorobo.controllers.TelaErroController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AlertaUtil {

    public static void mostrarErro(String mensagem) {
        try {
            FXMLLoader loader = new FXMLLoader(AlertaUtil.class.getResource("/com/projetorobo/telaErro.fxml"));
            Parent root = loader.load();
            TelaErroController controller = loader.getController();
            controller.receberMensagem(mensagem);
            Stage stage = new Stage();
            stage.setTitle("Erro");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}