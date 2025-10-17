package interfazfx;

import clases.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import java.util.List;

/**
 * ╔════════════════════════════════════════════════════╗
 * ║   Controlador para la pantalla de inicio de sesión ║
 * ║   Valida credenciales contra un usuario de prueba  ║
 * ╚════════════════════════════════════════════════════╝
 */
public class ControladorLogin {

    @FXML private TextField campoIdentificacion;
    @FXML private PasswordField campoContrasena;
    @FXML private Label mensajeResultado;

    private final Usuario usuarioPrueba = new Usuario(
            "Lucía",
            "González",
            "Ramírez",
            "lucia123456",
            "88889999",
            "lucia@email.com",
            "San José, Costa Rica",
            "ClaveSegura123!"
    ) {};



    @FXML
    public void autenticarUsuario() {
        String id = campoIdentificacion.getText().trim();
        String clave = campoContrasena.getText().trim();

        // Obtener mensaje específico desde Usuario
        String mensaje = usuarioPrueba.verificarCredenciales(id, clave);
        mensajeResultado.setText(mensaje);

        // Estilo según el tipo de mensaje
        if (mensaje.contains("exitosa")) {
            mensajeResultado.setStyle("-fx-text-fill: #27ae60;"); // verde
        } else if (mensaje.contains("Contraseña")) {
            mensajeResultado.setStyle("-fx-text-fill: #e74c3c;"); // rojo
        } else {
            mensajeResultado.setStyle("-fx-text-fill: #e67e22;"); // naranja
        }

        // Animación de aparición
        FadeTransition animacion = new FadeTransition(Duration.millis(500), mensajeResultado);
        animacion.setFromValue(0);
        animacion.setToValue(1);
        animacion.play();
    }}
