package interfazfx;

import clases.Estudiante;
import control.GestorEstudiantes;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class ControladorLogin {

    @FXML private TextField campoIdentificacion;
    @FXML private PasswordField campoContrasena;
    @FXML private ComboBox<String> comboTipoUsuario;
    @FXML private Label mensajeResultado;

    private static final String ID_ADMIN = "admin";
    private static final String PASS_ADMIN = "Admin123!";
    private final GestorEstudiantes gestor = GestorEstudiantes.getInstancia();

    @FXML
    private void initialize() {
        comboTipoUsuario.getItems().addAll("Administrador", "Estudiante");
        comboTipoUsuario.setValue("Estudiante");

        // Registrar estudiante de prueba si no existe
        if (!gestor.existeID("lucia12345")) {
            try {
                Estudiante estudiantePrueba = new Estudiante(
                        "Lucía", "González", "Ramírez", "lucia12345",
                        "88889999", "lucia@email.com", "San José",
                        "ClaveSegura123!", "Universidad de Costa Rica",
                        new ArrayList<>(List.of("Java avanzado", "Diseño de interfaces"))
                );
                gestor.registrarEstudiante(estudiantePrueba);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void autenticarUsuario() {
        try {
            String tipo = comboTipoUsuario.getValue();
            String id = campoIdentificacion.getText().trim();
            String clave = campoContrasena.getText().trim();
            String mensaje;

            if (tipo.equals("Administrador")) {
                if (id.equals(ID_ADMIN) && clave.equals(PASS_ADMIN)) {
                    mensaje = "✅ Bienvenido, administrador.";
                    MainApp.cambiarEscenaAdministrador("/interfazfx/menus/MenuAdministrador.fxml", 800, 600);
                } else {
                    mensaje = "❌ Credenciales de administrador incorrectas.";
                }
            } else {
                Estudiante estudiante = gestor.consultarEstudiante(id);
                if (estudiante != null && estudiante.verificarCredenciales(id, clave).contains("exitosa")) {
                    MenuEstudianteControlador.setEstudianteActivo(estudiante);
                    mensaje = "✅ Bienvenido, " + estudiante.getNombre();
                    MainApp.cambiarEscena("/interfazfx/menus/MenuEstudiante.fxml", 800, 600);
                } else {
                    mensaje = "❌ Credenciales de estudiante incorrectas.";
                }
            }

            mostrarMensaje(mensaje);
        } catch (Exception e) {
            e.printStackTrace();
            mensajeResultado.setText("❌ Error interno: " + e.getMessage());
        }
    }

    private void mostrarMensaje(String mensaje) {
        mensajeResultado.setText(mensaje);
        mensajeResultado.setStyle(mensaje.contains("✅") ? "-fx-text-fill: #27ae60;" : "-fx-text-fill: #e74c3c;");
        FadeTransition animacion = new FadeTransition(Duration.millis(500), mensajeResultado);
        animacion.setFromValue(0);
        animacion.setToValue(1);
        animacion.play();
    }
}
