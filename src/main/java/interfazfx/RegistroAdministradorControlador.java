package interfazfx;

import clases.Administrador;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegistroAdministradorControlador {

    @FXML private TextField campoNombre, campoApellido1, campoApellido2, campoID, campoTelefono, campoCorreo, campoDireccion;
    @FXML private PasswordField campoContrasena;

    @FXML
    private void registrarAdministrador() {
        try {
            Administrador nuevo = new Administrador(
                    campoNombre.getText(),
                    campoApellido1.getText(),
                    campoApellido2.getText(),
                    campoID.getText(),
                    campoTelefono.getText(),
                    campoCorreo.getText(),
                    campoDireccion.getText(),
                    campoContrasena.getText()
            );

            mostrarAlerta("✅ Administrador registrado: " + nuevo.getNombreCompleto());

        } catch (Exception e) {
            mostrarAlerta("❌ Error: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(mensaje);
        alert.show();
    }
}
