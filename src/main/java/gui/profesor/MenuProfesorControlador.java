package gui.profesor;
import usuarios.Profesor;

import javax.swing.*;
import java.awt.*;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🎓 MenuProfesorControlador                                                 ║
 * ║                                                                            ║
 * ║ Ventana Swing que muestra la información personal del profesor activo.    ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class MenuProfesorControlador extends JFrame {

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      COMPONENTES UI                        ║
    // ╚════════════════════════════════════════════════════════════╝
    private JLabel tituloBienvenida;
    private JLabel labelNombre, labelID, labelCorreo, labelTelefono,
            labelDireccion, labelTitulos, labelCertificaciones;

    private static Profesor profesorActivo;

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      CONSTRUCTOR                           ║
    // ╚════════════════════════════════════════════════════════════╝
    public MenuProfesorControlador(Profesor profesor) {
        profesorActivo = profesor;
        setTitle("🎓 Menú del Profesor");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        inicializarComponentes();
        cargarDatosProfesor();
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              INICIALIZACIÓN DE COMPONENTES                ║
    // ╚════════════════════════════════════════════════════════════╝
    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        tituloBienvenida    = new JLabel();
        labelNombre         = new JLabel();
        labelID             = new JLabel();
        labelCorreo         = new JLabel();
        labelTelefono       = new JLabel();
        labelDireccion      = new JLabel();
        labelTitulos        = new JLabel();
        labelCertificaciones= new JLabel();

        panel.add(new JLabel("👋 Bienvenida:"));         panel.add(tituloBienvenida);
        panel.add(new JLabel("👤 Nombre completo:"));    panel.add(labelNombre);
        panel.add(new JLabel("🆔 Identificación:"));     panel.add(labelID);
        panel.add(new JLabel("📧 Correo electrónico:")); panel.add(labelCorreo);
        panel.add(new JLabel("📞 Teléfono:"));           panel.add(labelTelefono);
        panel.add(new JLabel("🏠 Dirección:"));          panel.add(labelDireccion);
        panel.add(new JLabel("🎓 Títulos obtenidos:"));  panel.add(labelTitulos);
        panel.add(new JLabel("📜 Certificaciones:"));    panel.add(labelCertificaciones);

        add(panel);
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              CARGAR DATOS DEL PROFESOR                    ║
    // ╚════════════════════════════════════════════════════════════╝
    private void cargarDatosProfesor() {
        if (profesorActivo != null) {
            tituloBienvenida.setText("👨‍🏫 Bienvenida, " + profesorActivo.getNombre());
            labelNombre.setText(profesorActivo.getNombreCompleto());
            labelID.setText(profesorActivo.getIdentificacionPersonal());
            labelCorreo.setText(profesorActivo.getCorreoElectronico());
            labelTelefono.setText(profesorActivo.getNumeroTelefono());
            labelDireccion.setText(profesorActivo.getDireccionFisica());
            labelTitulos.setText(String.join(", ", profesorActivo.getTitulos()));
            labelCertificaciones.setText(String.join(", ", profesorActivo.getCertificaciones()));
        }
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              MÉTODO PARA USO EXTERNO (OPCIONAL)           ║
    // ╚════════════════════════════════════════════════════════════╝
    public static void setProfesorActivo(Profesor profesor) {
        profesorActivo = profesor;
    }
}
