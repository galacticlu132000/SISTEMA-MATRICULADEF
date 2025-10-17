package interfazfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import control.GestorEstudiantes;

/**
 * ╔════════════════════════════════════════════════════╗
 * ║        Clase principal que lanza la interfaz       ║
 * ║        y permite cambiar entre escenas             ║
 * ╚════════════════════════════════════════════════════╝
 */
public class MainApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        cambiarEscena("login.fxml", 500, 400);
    }

    public static void cambiarEscena(String fxml, int ancho, int alto) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/interfazfx/" + fxml));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root, ancho, alto));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cambiarEscenaAdministrador(String fxml, int ancho, int alto, GestorEstudiantes gestor) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/interfazfx/" + fxml));
            Parent root = loader.load();
            MenuAdministradorControlador controlador = loader.getController();
            controlador.setGestor(gestor);
            primaryStage.setScene(new Scene(root, ancho, alto));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
