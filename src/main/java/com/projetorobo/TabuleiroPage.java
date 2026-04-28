package com.projetorobo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TabuleiroPage extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TabuleiroPage.class.getResource("/com/projetorobo/tabuleiro.fxml"));
        stage.setResizable(false);
        Scene scene = new Scene(fxmlLoader.load(), 756, 563);
        stage.setTitle("Projeto!");
        stage.setScene(scene);
        stage.show();
    }
}