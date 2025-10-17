module com.karlalucia.sistemamatricula {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    opens interfazfx to javafx.graphics, javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.karlalucia.sistemamatricula to javafx.fxml;
    exports com.karlalucia.sistemamatricula;
}