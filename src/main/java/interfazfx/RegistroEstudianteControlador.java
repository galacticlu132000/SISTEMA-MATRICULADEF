package interfazfx;

import clases.Estudiante;
import control.GestorEstudiantes;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RegistroEstudianteControlador {

    @FXML private TextField campoNombre, campoApellido1, campoApellido2, campoID, campoTelefono, campoCorreo,
            campoDireccion, campoOrganizacion, campoTemas;
    @FXML private PasswordField campoContrasena;
    @FXML private Button botonRegistrar;

    @FXML private Label errorNombre, errorApellido1, errorApellido2, errorID, errorTelefono, errorCorreo,
            errorDireccion, errorOrganizacion, errorTemas, errorContrasena;

    private final GestorEstudiantes gestor = GestorEstudiantes.getInstancia();

    @FXML
    private void initialize() {
        ChangeListener<String> validador = (obs, oldVal, newVal) -> validarCampos();

        campoNombre.textProperty().addListener(validador);
        campoApellido1.textProperty().addListener(validador);
        campoApellido2.textProperty().addListener(validador);
        campoID.textProperty().addListener(validador);
        campoTelefono.textProperty().addListener(validador);
        campoCorreo.textProperty().addListener(validador);
        campoDireccion.textProperty().addListener(validador);
        campoOrganizacion.textProperty().addListener(validador);
        campoTemas.textProperty().addListener(validador);
        campoContrasena.textProperty().addListener(validador);

        botonRegistrar.setDisable(true);
    }

    private void validarCampos() {
        boolean valido = true;

        String nombre = campoNombre.getText().trim();
        valido &= validarLongitud(nombre, 2, 20, errorNombre, "Debe tener entre 2 y 20 caracteres.");

        String apellido1 = campoApellido1.getText().trim();
        valido &= validarLongitud(apellido1, 2, 20, errorApellido1, "Debe tener entre 2 y 20 caracteres.");

        String apellido2 = campoApellido2.getText().trim();
        valido &= validarLongitud(apellido2, 2, 20, errorApellido2, "Debe tener entre 2 y 20 caracteres.");

        String id = campoID.getText().trim();
        if (id.length() < 9) {
            errorID.setText("Debe tener al menos 9 caracteres.");
            valido = false;
        } else if (gestor.existeID(id)) {
            errorID.setText("Ya existe un usuario con esta identificación.");
            valido = false;
        } else {
            errorID.setText("");
        }

        String telefono = campoTelefono.getText().trim();
        valido &= validarLongitud(telefono, 8, 20, errorTelefono, "Debe tener al menos 8 caracteres.");

        String correo = campoCorreo.getText().trim();
        if (!correo.matches("^[^\\s@]{3,}@[^\\s@]{3,}\\.[^\\s@]+$")) {
            errorCorreo.setText("Formato inválido o partes demasiado cortas.");
            valido = false;
        } else if (gestor.existeCorreo(correo)) {
            errorCorreo.setText("Ya existe un usuario con este correo.");
            valido = false;
        } else {
            errorCorreo.setText("");
        }

        String direccion = campoDireccion.getText().trim();
        valido &= validarLongitud(direccion, 5, 60, errorDireccion, "Debe tener entre 5 y 60 caracteres.");

        String organizacion = campoOrganizacion.getText().trim();
        if (organizacion.length() > 40) {
            errorOrganizacion.setText("Debe tener hasta 40 caracteres.");
            valido = false;
        } else {
            errorOrganizacion.setText("");
        }

        String[] temas = campoTemas.getText().trim().split("\\s*,\\s*");
        boolean temasValidos = Arrays.stream(temas).allMatch(t -> t.length() >= 5 && t.length() <= 30);
        if (!temasValidos) {
            errorTemas.setText("Cada tema debe tener entre 5 y 30 caracteres.");
            valido = false;
        } else {
            errorTemas.setText("");
        }

        String contrasena = campoContrasena.getText().trim();
        valido &= validarLongitud(contrasena, 8, 100, errorContrasena, "Debe tener al menos 8 caracteres.");

        botonRegistrar.setDisable(!valido);
    }

    private boolean validarLongitud(String texto, int min, int max, Label errorLabel, String mensaje) {
        if (texto.length() < min || texto.length() > max) {
            errorLabel.setText(mensaje);
            return false;
        } else {
            errorLabel.setText("");
            return true;
        }
    }

    @FXML
    private void registrarEstudiante() {
        try {
            Estudiante nuevo = new Estudiante(
                    campoNombre.getText().trim(),
                    campoApellido1.getText().trim(),
                    campoApellido2.getText().trim(),
                    campoID.getText().trim(),
                    campoTelefono.getText().trim(),
                    campoCorreo.getText().trim(),
                    campoDireccion.getText().trim(),
                    campoContrasena.getText().trim(),
                    campoOrganizacion.getText().trim(),
                    new ArrayList<>(Arrays.asList(campoTemas.getText().split("\\s*,\\s*")))
            );

            gestor.registrarEstudiante(nuevo);
            mostrarAlerta(Alert.AlertType.INFORMATION, "✅ Registro exitoso", "El estudiante ha sido registrado correctamente.");
            volverAlMenuAdministrador();

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "❌ Error inesperado", "No se pudo registrar el estudiante.");
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void volverAlMenuAdministrador() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfazfx/menus/MenuAdministrador.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) botonRegistrar.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "❌ Error de navegación", "No se pudo cargar el menú del administrador.");
            e.printStackTrace();
        }
    }
}

