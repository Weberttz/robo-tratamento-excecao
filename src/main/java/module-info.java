module com.projetorobo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.gluonhq.charm.glisten;
    requires java.desktop;
    requires java.sql;
    requires jdk.compiler;
    requires com.almasb.fxgl.io;
    requires com.almasb.fxgl.core;
    //requires com.almasb.fxgl.all;
    opens com.projetorobo to javafx.fxml;
    exports com.projetorobo;
    exports com.projetorobo.controllers;
    opens com.projetorobo.controllers to javafx.fxml;
}