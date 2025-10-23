package gui.profesor;
import usuarios.Profesor;

import javax.swing.*;
import java.awt.*;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 📄 DetallesProfesorControlador                                             ║
 * ║                                                                            ║
 * ║ Ventana Swing para visualizar los detalles completos de un profesor.      ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class DetallesProfesorControlador extends JDialog {

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      ETIQUETAS DE DATOS                    ║
    // ╚════════════════════════════════════════════════════════════╝
    private JLabel lblID, lblNombre, lblCorreo, lblTelefono, lblDireccion,
            lblTitulos, lblCertificaciones, lblFecha;
    private JButton btnCerrar;

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      CONSTRUCTOR                           ║
    // ╚════════════════════════════════════════════════════════════╝
    public DetallesProfesorControlador(Frame parent, Profesor profesor) {
        super(parent, "📄 Detalles del Profesor", true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        inicializarComponentes();
        inicializarConProfesor(profesor);
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              INICIALIZACIÓN DE COMPONENTES                ║
    // ╚════════════════════════════════════════════════════════════╝
    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        lblID             = new JLabel();
        lblNombre         = new JLabel();
        lblCorreo         = new JLabel();
        lblTelefono       = new JLabel();
        lblDireccion      = new JLabel();
        lblTitulos        = new JLabel();
        lblCertificaciones= new JLabel();
        lblFecha          = new JLabel();
        btnCerrar         = new JButton("Cerrar");

        panel.add(new JLabel("🆔 Identificación:")); panel.add(lblID);
        panel.add(new JLabel("👤 Nombre completo:")); panel.add(lblNombre);
        panel.add(new JLabel("📧 Correo electrónico:")); panel.add(lblCorreo);
        panel.add(new JLabel("📞 Teléfono:")); panel.add(lblTelefono);
        panel.add(new JLabel("🏠 Dirección:")); panel.add(lblDireccion);
        panel.add(new JLabel("🎓 Títulos obtenidos:")); panel.add(lblTitulos);
        panel.add(new JLabel("📜 Certificaciones:")); panel.add(lblCertificaciones);
        panel.add(new JLabel("📅 Fecha de registro:")); panel.add(lblFecha);

        add(panel, BorderLayout.CENTER);
        add(btnCerrar, BorderLayout.SOUTH);

        btnCerrar.addActionListener(e -> cerrarVentana());
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              CARGAR DATOS DEL PROFESOR                    ║
    // ╚════════════════════════════════════════════════════════════╝
    public void inicializarConProfesor(Profesor profesor) {
        lblID.setText(profesor.getIdentificacionPersonal());
        lblNombre.setText(profesor.getNombreCompleto());
        lblCorreo.setText(profesor.getCorreoElectronico());
        lblTelefono.setText(profesor.getNumeroTelefono());
        lblDireccion.setText(profesor.getDireccionFisica());
        lblTitulos.setText(String.join(", ", profesor.getTitulos()));
        lblCertificaciones.setText(String.join(", ", profesor.getCertificaciones()));
        lblFecha.setText(profesor.getFechaRegistro().toString());
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  CERRAR VENTANA MODAL                     ║
    // ╚════════════════════════════════════════════════════════════╝
    private void cerrarVentana() {
        dispose();
    }
}
