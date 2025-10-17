package interfazfx;

import clases.Estudiante;
import control.GestorEstudiantes;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.Arrays;
import java.util.ArrayList;

public class RegistroEstudianteControlador {

    @FXML private TextField campoNombre, campoApellido1, campoApellido2, campoID, campoTelefono, campoCorreo, campoDireccion, campoOrganizacion, campoTemas;
    @FXML private PasswordField campoContrasena;

    private static GestorEstudiantes gestor = new GestorEstudiantes(); // Puedes hacerlo compartido

    @FXML
    private void registrarEstudiante() {
        try {
            ArrayList<String> temas = new ArrayList<>(Arrays.asList(campoTemas.getText().split(",")));

            Estudiante nuevo = new Estudiante(
                    campoNombre.getText(),
                    campoApellido1.getText(),
                    campoApellido2.getText(),
                    campoID.getText(),
                    campoTelefono.getText(),
                    campoCorreo.getText(),
                    campoDireccion.getText(),
                    campoContrasena.getText(),
                    campoOrganizacion.getText(),
                    temas
            );

            if (gestor.registrarEstudiante(nuevo)) {
                mostrarAlerta("✅ Estudiante registrado.");
            } else {
                mostrarAlerta("⚠️ Ya existe un estudiante con ese ID.");
            }

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
