package gui.estudiante;
import usuarios.Estudiante;

import javax.swing.*;
import java.awt.*;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 📄 DetallesEstudianteControlador                                           ║
 * ║                                                                            ║
 * ║ Ventana Swing para visualizar los detalles completos de un estudiante.    ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class DetallesEstudianteControlador extends JDialog {

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      ETIQUETAS DE DATOS                    ║
    // ╚════════════════════════════════════════════════════════════╝
    private JLabel lblID, lblNombre, lblCorreo, lblTelefono, lblDireccion,
            lblOrganizacion, lblTemas, lblFecha;
    private JButton btnCerrar;

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      CONSTRUCTOR                           ║
    // ╚════════════════════════════════════════════════════════════╝
    public DetallesEstudianteControlador(Frame parent, Estudiante estudiante) {
        super(parent, "📄 Detalles del Estudiante", true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        inicializarComponentes();
        inicializarConEstudiante(estudiante);
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              INICIALIZACIÓN DE COMPONENTES                ║
    // ╚════════════════════════════════════════════════════════════╝
    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridLayout(9, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        lblID           = new JLabel();
        lblNombre       = new JLabel();
        lblCorreo       = new JLabel();
        lblTelefono     = new JLabel();
        lblDireccion    = new JLabel();
        lblOrganizacion = new JLabel();
        lblTemas        = new JLabel();
        lblFecha        = new JLabel();
        btnCerrar       = new JButton("Cerrar");

        panel.add(new JLabel("🆔 Identificación:")); panel.add(lblID);
        panel.add(new JLabel("👤 Nombre completo:")); panel.add(lblNombre);
        panel.add(new JLabel("📧 Correo electrónico:")); panel.add(lblCorreo);
        panel.add(new JLabel("📞 Teléfono:")); panel.add(lblTelefono);
        panel.add(new JLabel("🏠 Dirección:")); panel.add(lblDireccion);
        panel.add(new JLabel("🏢 Organización:")); panel.add(lblOrganizacion);
        panel.add(new JLabel("📚 Temas de interés:")); panel.add(lblTemas);
        panel.add(new JLabel("📅 Fecha de registro:")); panel.add(lblFecha);

        add(panel, BorderLayout.CENTER);
        add(btnCerrar, BorderLayout.SOUTH);

        btnCerrar.addActionListener(e -> cerrarVentana());
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              CARGAR DATOS DEL ESTUDIANTE                  ║
    // ╚════════════════════════════════════════════════════════════╝
    public void inicializarConEstudiante(Estudiante estudiante) {
        lblID.setText(estudiante.getIdentificacionPersonal());
        lblNombre.setText(estudiante.getNombreCompleto());
        lblCorreo.setText(estudiante.getCorreoElectronico());
        lblTelefono.setText(estudiante.getNumeroTelefono());
        lblDireccion.setText(estudiante.getDireccionFisica());
        lblOrganizacion.setText(estudiante.getOrganizacionLaboral());
        lblTemas.setText(String.join(", ", estudiante.getTemasInteres()));
        lblFecha.setText(estudiante.getFechaRegistro().toString());
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  CERRAR VENTANA MODAL                     ║
    // ╚════════════════════════════════════════════════════════════╝
    private void cerrarVentana() {
        dispose();
    }
}

