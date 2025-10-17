package interfazfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

/**
 * ╔════════════════════════════════════════════════════╗
 * ║        Clase principal que lanza la interfaz       ║
 * ╚════════════════════════════════════════════════════╝
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/interfazfx/login.fxml"));
        Scene scene = new Scene(root, 420, 420);
        stage.setScene(scene);
        stage.setTitle("Autenticación de Usuario");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
