package interfazfx;

import clases.Estudiante;
import control.GestorEstudiantes;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class ModificarEstudianteControlador {

    @FXML private TextField txtID, txtNombre, txtApellido1, txtApellido2, txtCorreo, txtTelefono, txtDireccion, txtOrganizacion, txtTemas;
    @FXML private Button btnGuardar;

    private GestorEstudiantes gestor;
    private Estudiante estudianteOriginal;

    public void setGestor(GestorEstudiantes gestor) {
        this.gestor = gestor;
    }

    public void inicializarConEstudiante(Estudiante estudiante) {
        this.estudianteOriginal = estudiante;
        txtID.setText(estudiante.getIdentificacionPersonal());

        // Separar nombre completo en partes
        String[] partes = estudiante.getNombreCompleto().split(" ");
        txtNombre.setText(partes.length > 0 ? partes[0] : "");
        txtApellido1.setText(partes.length > 1 ? partes[1] : "");
        txtApellido2.setText(partes.length > 2 ? partes[2] : "");

        txtCorreo.setText(estudiante.getCorreoElectronico());
        txtTelefono.setText(estudiante.getTelefono());
        txtDireccion.setText(estudiante.getDireccionFisica());
        txtOrganizacion.setText(estudiante.getOrganizacionLaboral());
        txtTemas.setText(String.join(", ", estudiante.getTemasInteres()));

        configurarValidaciones();
        btnGuardar.setDisable(true);
    }

    private void configurarValidaciones() {
        ChangeListener<String> validador = (obs, oldVal, newVal) -> {
            btnGuardar.setDisable(!todosLosCamposValidos());
        };

        txtNombre.textProperty().addListener(validador);
        txtApellido1.textProperty().addListener(validador);
        txtApellido2.textProperty().addListener(validador);
        txtCorreo.textProperty().addListener(validador);
        txtTelefono.textProperty().addListener(validador);
        txtDireccion.textProperty().addListener(validador);
        txtOrganizacion.textProperty().addListener(validador);
        txtTemas.textProperty().addListener(validador);
    }

    private boolean todosLosCamposValidos() {
        return txtNombre.getText().length() >= 2 &&
                txtApellido1.getText().length() >= 2 &&
                txtApellido2.getText().length() >= 2 &&
                txtCorreo.getText().matches("^[^\\s@]{3,}@[^\\s@]{3,}$") &&
                txtTelefono.getText().length() >= 8 &&
                txtDireccion.getText().length() >= 5 &&
                txtOrganizacion.getText().length() <= 40 &&
                Arrays.stream(txtTemas.getText().split("\\s*,\\s*"))
                        .allMatch(t -> t.length() >= 5 && t.length() <= 30);
    }

    @FXML
    private void guardarCambios() {
        try {
            String nombreCompleto = txtNombre.getText().trim() + " " +
                    txtApellido1.getText().trim() + " " +
                    txtApellido2.getText().trim();
            estudianteOriginal.setNombre(txtNombre.getText().trim());
            estudianteOriginal.setPrimerApellido(txtApellido1.getText().trim());
            estudianteOriginal.setSegundoApellido(txtApellido2.getText().trim());
            estudianteOriginal.setCorreoElectronico(txtCorreo.getText());
            estudianteOriginal.setNumeroTelefono(txtTelefono.getText());
            estudianteOriginal.setDireccionFisica(txtDireccion.getText());
            estudianteOriginal.setOrganizacionLaboral(txtOrganizacion.getText());

            ArrayList<String> listaTemas = new ArrayList<>(Arrays.asList(txtTemas.getText().split("\\s*,\\s*")));
            estudianteOriginal.setTemasInteres(listaTemas);

            boolean exito = gestor.actualizarEstudiante(estudianteOriginal);
            if (exito) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "✅ Cambios guardados", "Los datos fueron actualizados correctamente.");
                cerrarVentana();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el estudiante.");
            }

        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "❌ Error de validación", e.getMessage());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "❌ Error inesperado", "Ocurrió un error al guardar los cambios.");
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

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) txtID.getScene().getWindow();
        stage.close();
    }
}
