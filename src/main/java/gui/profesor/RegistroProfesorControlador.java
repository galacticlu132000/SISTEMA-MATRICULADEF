package gui.profesor;
import usuarios.Profesor;
import control.GestorProfesores;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 📝 RegistroProfesorControlador                                             ║
 * ║                                                                            ║
 * ║ Ventana Swing para registrar un nuevo profesor con validaciones visuales  ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class RegistroProfesorControlador extends JDialog {

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      CAMPOS DE FORMULARIO                  ║
    // ╚════════════════════════════════════════════════════════════╝
    private JTextField campoNombre, campoApellido1, campoApellido2, campoID, campoTelefono, campoCorreo,
            campoDireccion, campoTitulos, campoCertificaciones;
    private JPasswordField campoContrasena;
    private JButton botonRegistrar;

    private final GestorProfesores gestor = GestorProfesores.getInstancia();

    public RegistroProfesorControlador(Frame parent) {
        super(parent, "📝 Registro de Profesor", true);
        setSize(520, 640);
        setLocationRelativeTo(parent);
        aplicarEstiloGlobal();
        inicializarComponentes();
    }

    private void aplicarEstiloGlobal() {
        UIManager.put("Label.font", new Font("Segoe UI Emoji", Font.BOLD, 14));
        UIManager.put("TextField.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
        UIManager.put("PasswordField.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
        UIManager.put("Button.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(250, 250, 255));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 220)),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        campoNombre = new JTextField();
        campoApellido1 = new JTextField();
        campoApellido2 = new JTextField();
        campoID = new JTextField();
        campoTelefono = new JTextField();
        campoCorreo = new JTextField();
        campoDireccion = new JTextField();
        campoTitulos = new JTextField();
        campoCertificaciones = new JTextField();
        campoContrasena = new JPasswordField();
        botonRegistrar = new JButton("📝 Registrar profesor");

        String[] etiquetas = {
                "👤 Nombre:", "👤 Apellido 1:", "👤 Apellido 2:", "🆔 Identificación:",
                "📞 Teléfono:", "📧 Correo:", "🏠 Dirección:",
                "🎓 Títulos obtenidos:", "📜 Certificaciones:", "🔒 Contraseña:"
        };
        JComponent[] campos = {
                campoNombre, campoApellido1, campoApellido2, campoID,
                campoTelefono, campoCorreo, campoDireccion,
                campoTitulos, campoCertificaciones, campoContrasena
        };

        for (int i = 0; i < etiquetas.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            panel.add(new JLabel(etiquetas[i]), gbc);
            gbc.gridx = 1;
            panel.add(campos[i], gbc);
        }

        botonRegistrar.setEnabled(false);
        botonRegistrar.setFocusPainted(false);
        botonRegistrar.setBackground(new Color(220, 220, 250));
        botonRegistrar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 220)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        JPanel contenedorBoton = new JPanel();
        contenedorBoton.setBackground(new Color(250, 250, 255));
        contenedorBoton.add(botonRegistrar);

        add(panel, BorderLayout.CENTER);
        add(contenedorBoton, BorderLayout.SOUTH);

        botonRegistrar.addActionListener(e -> registrarProfesor());

        JTextField[] camposTexto = {
                campoNombre, campoApellido1, campoApellido2, campoID, campoTelefono,
                campoCorreo, campoDireccion, campoTitulos, campoCertificaciones
        };

        for (JTextField campo : camposTexto) {
            campo.getDocument().addDocumentListener(new CampoListener(campo));
        }
        campoContrasena.getDocument().addDocumentListener(new CampoListener(campoContrasena));
    }

    private void validarCampos() {
        boolean valido =
                validarCampo(campoNombre, 2, 20, "Entre 2 y 20 caracteres") &
                        validarCampo(campoApellido1, 2, 20, "Entre 2 y 20 caracteres") &
                        validarCampo(campoApellido2, 2, 20, "Entre 2 y 20 caracteres") &
                        validarCampoID() &
                        validarCampo(campoTelefono, 8, 20, "Mínimo 8 dígitos") &
                        validarCorreo() &
                        validarCampo(campoDireccion, 5, 60, "Entre 5 y 60 caracteres") &
                        validarLista(campoTitulos, 5, 40, "Cada título debe tener entre 5 y 40 caracteres") &
                        validarLista(campoCertificaciones, 5, 40, "Cada certificación debe tener entre 5 y 40 caracteres") &
                        validarContrasena();

        botonRegistrar.setEnabled(valido);
    }

    private boolean validarCampo(JTextField campo, int min, int max, String tooltip) {
        String texto = campo.getText().trim();
        boolean valido = texto.length() >= min && texto.length() <= max;
        aplicarEstiloCampo(campo, valido, tooltip);
        return valido;
    }

    private boolean validarCampoID() {
        String id = campoID.getText().trim();
        boolean valido = id.length() >= 9 && !gestor.existeID(id);
        aplicarEstiloCampo(campoID, valido, "Debe tener al menos 9 caracteres y no estar registrado");
        return valido;
    }

    private boolean validarCorreo() {
        String correo = campoCorreo.getText().trim();
        boolean valido = correo.matches("^[^\\s@]{3,}@[^\\s@]{3,}\\.[^\\s@]+$") && !gestor.existeCorreo(correo);
        aplicarEstiloCampo(campoCorreo, valido, "Formato inválido o correo ya registrado");
        return valido;
    }

    private boolean validarLista(JTextField campo, int min, int max, String tooltip) {
        String[] items = campo.getText().split("\\s*,\\s*");
        boolean valido = Arrays.stream(items).allMatch(t -> t.length() >= min && t.length() <= max);
        aplicarEstiloCampo(campo, valido, tooltip);
        return valido;
    }

    private boolean validarContrasena() {
        String pass = new String(campoContrasena.getPassword()).trim();
        boolean valido = pass.length() >= 8 &&
                pass.matches(".*[A-Z].*") &&
                pass.matches(".*\\d.*");
        aplicarEstiloCampo(campoContrasena, valido, "Mínimo 8 caracteres, una mayúscula y un número");
        return valido;
    }

    private void aplicarEstiloCampo(JComponent campo, boolean valido, String tooltip) {
        campo.setBorder(BorderFactory.createLineBorder(valido ? new Color(0, 180, 100) : Color.RED));
        campo.setToolTipText(valido ? null : tooltip);
    }

    private void registrarProfesor() {
        try {
            Profesor nuevo = new Profesor(
                    campoNombre.getText().trim(),
                    campoApellido1.getText().trim(),
                    campoApellido2.getText().trim(),
                    campoID.getText().trim(),
                    campoTelefono.getText().trim(),
                    campoCorreo.getText().trim(),
                    campoDireccion.getText().trim(),
                    new String(campoContrasena.getPassword()).trim(),
                    new ArrayList<>(Arrays.asList(campoTitulos.getText().split("\\s*,\\s*"))),
                    new ArrayList<>(Arrays.asList(campoCertificaciones.getText().split("\\s*,\\s*")))
            );

            gestor.registrarProfesor(nuevo);
            mostrarAlerta("✅ Registro exitoso", "El profesor ha sido registrado correctamente.");
            dispose();

        } catch (Exception e) {
            mostrarAlerta("❌ Error inesperado", "No se pudo registrar el profesor.");
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║        LISTENER PARA VALIDACIÓN EN TIEMPO REAL            ║
    // ╚════════════════════════════════════════════════════════════╝
    private class CampoListener implements DocumentListener {
        private final JComponent campo;

        public CampoListener(JComponent campo) {
            this.campo = campo;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            validarCampos();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            validarCampos();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            validarCampos();
        }
    }
}
