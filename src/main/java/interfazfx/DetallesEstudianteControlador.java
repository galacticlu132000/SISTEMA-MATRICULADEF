package interfazfx;
import clases.Estudiante;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DetallesEstudianteControlador {

    @FXML private Label lblID, lblNombre, lblCorreo, lblTelefono, lblDireccion, lblOrganizacion, lblTemas, lblFecha;

    public void inicializarConEstudiante(Estudiante estudiante) {
        lblID.setText(estudiante.getIdentificacionPersonal());
        lblNombre.setText(estudiante.getNombreCompleto());
        lblCorreo.setText(estudiante.getCorreoElectronico());
        lblTelefono.setText(estudiante.getTelefono());
        lblDireccion.setText(estudiante.getDireccionFisica());
        lblOrganizacion.setText(estudiante.getOrganizacionLaboral());
        lblTemas.setText(String.join(", ", estudiante.getTemasInteres()));
        lblFecha.setText(estudiante.getFechaRegistro().toString());
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) lblID.getScene().getWindow();
        stage.close();
    }
}
