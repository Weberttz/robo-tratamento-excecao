module com.projetorobo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.gluonhq.charm.glisten;
    requires java.desktop;
    requires java.sql;
    //requires com.almasb.fxgl.all;
    opens com.projetorobo to javafx.fxml;
    exports com.projetorobo;
}