package gui.admin;
import usuarios.Administrador;

import javax.swing.*;
import java.awt.*;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🛡️ RegistroAdministradorSwing                                              ║
 * ║                                                                            ║
 * ║ Ventana Swing para registrar un nuevo administrador.                       ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class RegistroAdministradorControlador extends JDialog {

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      CAMPOS DE FORMULARIO                  ║
    // ╚════════════════════════════════════════════════════════════╝
    private JTextField campoNombre, campoApellido1, campoApellido2, campoID,
            campoTelefono, campoCorreo, campoDireccion;
    private JPasswordField campoContrasena;
    private JButton botonRegistrar;

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      CONSTRUCTOR                           ║
    // ╚════════════════════════════════════════════════════════════╝
    public RegistroAdministradorControlador(Frame parent) {
        super(parent, "🛡️ Registro de Administrador", true);
        setSize(400, 500);
        setLocationRelativeTo(parent);
        inicializarComponentes();
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              INICIALIZACIÓN DE COMPONENTES                ║
    // ╚════════════════════════════════════════════════════════════╝
    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridLayout(9, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        campoNombre     = new JTextField();
        campoApellido1  = new JTextField();
        campoApellido2  = new JTextField();
        campoID         = new JTextField();
        campoTelefono   = new JTextField();
        campoCorreo     = new JTextField();
        campoDireccion  = new JTextField();
        campoContrasena = new JPasswordField();
        botonRegistrar  = new JButton("Registrar");

        panel.add(new JLabel("👤 Nombre:"));         panel.add(campoNombre);
        panel.add(new JLabel("👤 Apellido 1:"));     panel.add(campoApellido1);
        panel.add(new JLabel("👤 Apellido 2:"));     panel.add(campoApellido2);
        panel.add(new JLabel("🆔 Identificación:")); panel.add(campoID);
        panel.add(new JLabel("📞 Teléfono:"));       panel.add(campoTelefono);
        panel.add(new JLabel("📧 Correo:"));         panel.add(campoCorreo);
        panel.add(new JLabel("🏠 Dirección:"));      panel.add(campoDireccion);
        panel.add(new JLabel("🔒 Contraseña:"));     panel.add(campoContrasena);

        add(panel, BorderLayout.CENTER);
        add(botonRegistrar, BorderLayout.SOUTH);

        botonRegistrar.addActionListener(e -> registrarAdministrador());
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              REGISTRO DEL ADMINISTRADOR                   ║
    // ╚════════════════════════════════════════════════════════════╝
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
                    new String(campoContrasena.getPassword())
            );

            mostrarAlerta("✅ Administrador registrado: " + nuevo.getNombreCompleto());
            dispose(); // cerrar ventana tras registro exitoso

        } catch (Exception e) {
            mostrarAlerta("❌ Error: " + e.getMessage());
        }
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  VISUALIZACIÓN DE MENSAJES                ║
    // ╚════════════════════════════════════════════════════════════╝
    private void mostrarAlerta(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }
}


