package interfazfx;

import clases.Estudiante;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * ╔════════════════════════════════════════════════════╗
 * ║   Controlador del menú del estudiante              ║
 * ║   Muestra su información personal y académica      ║
 * ╚════════════════════════════════════════════════════╝
 */
public class MenuEstudianteControlador {

    @FXML private Label tituloBienvenida;
    @FXML private Label labelNombre, labelID, labelCorreo, labelTelefono, labelDireccion, labelOrganizacion, labelTemas;

    private static Estudiante estudianteActivo;

    public static void setEstudianteActivo(Estudiante estudiante) {
        estudianteActivo = estudiante;
    }

    @FXML
    private void initialize() {
        if (estudianteActivo != null) {
            tituloBienvenida.setText("👩‍🎓 Bienvenida, " + estudianteActivo.getNombre());
            labelNombre.setText(estudianteActivo.getNombreCompleto());
            labelID.setText(estudianteActivo.getIdentificacionPersonal());
            labelCorreo.setText(estudianteActivo.getCorreoElectronico());
            labelTelefono.setText(estudianteActivo.getTelefono());
            labelDireccion.setText(estudianteActivo.getDireccionFisica());
            labelOrganizacion.setText(estudianteActivo.getOrganizacionLaboral());
            labelTemas.setText(String.join(", ", estudianteActivo.getTemasInteres()));
        }
    }
}
