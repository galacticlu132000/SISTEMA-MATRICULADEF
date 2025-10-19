package interfazfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

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
        cambiarEscena("/interfazfx/registrousuarios/login.fxml", 500, 400);
    }

    public static void cambiarEscena(String rutaFXML, int ancho, int alto) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(rutaFXML));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root, ancho, alto));
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("❌ No se pudo cargar la escena: " + rutaFXML);
            e.printStackTrace();
        }
    }

    public static void cambiarEscenaAdministrador(String rutaFXML, int ancho, int alto) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(rutaFXML));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root, ancho, alto));
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("❌ No se pudo cargar la escena de administrador: " + rutaFXML);
            e.printStackTrace();
        }
    }
}
