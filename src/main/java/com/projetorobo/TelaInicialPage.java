package com.projetorobo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaInicialPage extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TelaInicialPage.class.getResource("/com/projetorobo/telaInicial.fxml"));
        stage.setResizable(false);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Five Nights At Freddy!");
        stage.setScene(scene);
        stage.show();
    }
}