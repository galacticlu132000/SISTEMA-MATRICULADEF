package gui.profesor;
import usuarios.Profesor;
import control.GestorProfesores;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

    /**
     * ╔════════════════════════════════════════════════════════════════════════════╗
     * ║ ✏️ ModificarProfesorControlador                                            ║
     * ║                                                                            ║
     * ║ Ventana Swing para modificar los datos de un profesor existente.          ║
     * ╚════════════════════════════════════════════════════════════════════════════╝
     */
    public class ModificarProfesorControlador extends JDialog {

        // ╔════════════════════════════════════════════════════════════╗
        // ║                      CAMPOS DE FORMULARIO                  ║
        // ╚════════════════════════════════════════════════════════════╝
        private JTextField txtID, txtNombre, txtApellido1, txtApellido2, txtCorreo,
                txtTelefono, txtDireccion, txtTitulos, txtCertificaciones;
        private JButton btnGuardar, btnCancelar;

        private final GestorProfesores gestor;
        private final Profesor profesorOriginal;

        // ╔════════════════════════════════════════════════════════════╗
        // ║                      CONSTRUCTOR                           ║
        // ╚════════════════════════════════════════════════════════════╝
        public ModificarProfesorControlador(Frame parent, Profesor profesor) {
            super(parent, "✏️ Modificar Profesor", true);
            this.gestor = GestorProfesores.getInstancia();
            this.profesorOriginal = profesor;
            setSize(520, 540);
            setLocationRelativeTo(parent);
            aplicarEstiloGlobal();
            inicializarComponentes();
            cargarDatosProfesor();
        }

        // ╔════════════════════════════════════════════════════════════╗
        // ║                  🎨 ESTILO GLOBAL                         ║
        // ╚════════════════════════════════════════════════════════════╝
        private void aplicarEstiloGlobal() {
            UIManager.put("Label.font", new Font("Segoe UI Emoji", Font.BOLD, 14));
            UIManager.put("TextField.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
            UIManager.put("Button.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
        }

        // ╔════════════════════════════════════════════════════════════╗
        // ║              INICIALIZACIÓN DE COMPONENTES                ║
        // ╚════════════════════════════════════════════════════════════╝
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

            txtID              = new JTextField(); txtID.setEnabled(false);
            txtNombre          = new JTextField();
            txtApellido1       = new JTextField();
            txtApellido2       = new JTextField();
            txtCorreo          = new JTextField();
            txtTelefono        = new JTextField();
            txtDireccion       = new JTextField();
            txtTitulos         = new JTextField();
            txtCertificaciones = new JTextField();

            String[] etiquetas = {
                    "🆔 Identificación:", "👤 Nombre:", "👤 Apellido 1:", "👤 Apellido 2:",
                    "📧 Correo:", "📞 Teléfono:", "🏠 Dirección:",
                    "🎓 Títulos obtenidos:", "📜 Certificaciones:"
            };
            JTextField[] campos = {
                    txtID, txtNombre, txtApellido1, txtApellido2,
                    txtCorreo, txtTelefono, txtDireccion,
                    txtTitulos, txtCertificaciones
            };

            for (int i = 0; i < etiquetas.length; i++) {
                gbc.gridx = 0;
                gbc.gridy = i;
                panel.add(new JLabel(etiquetas[i]), gbc);
                gbc.gridx = 1;
                panel.add(campos[i], gbc);
            }

            btnGuardar = new JButton("💾 Guardar cambios");
            btnCancelar = new JButton("❌ Cancelar");

            btnGuardar.setFocusPainted(false);
            btnGuardar.setBackground(new Color(220, 220, 250));
            btnGuardar.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 220)),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
            ));

            btnCancelar.setFocusPainted(false);
            btnCancelar.setBackground(new Color(240, 240, 240));
            btnCancelar.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
            ));

            JPanel botones = new JPanel(new FlowLayout());
            botones.setBackground(new Color(250, 250, 255));
            botones.add(btnGuardar);
            botones.add(btnCancelar);

            add(panel, BorderLayout.CENTER);
            add(botones, BorderLayout.SOUTH);

            btnGuardar.addActionListener(e -> guardarCambios());
            btnCancelar.addActionListener(e -> dispose());

            // 🎯 Validación en tiempo real
            for (JTextField campo : new JTextField[]{
                    txtNombre, txtApellido1, txtApellido2, txtCorreo,
                    txtTelefono, txtDireccion, txtTitulos, txtCertificaciones
            }) {
                campo.getDocument().addDocumentListener(new CampoListener());
            }
        }

        // ╔════════════════════════════════════════════════════════════╗
        // ║              CARGAR DATOS DEL PROFESOR                    ║
        // ╚════════════════════════════════════════════════════════════╝
        private void cargarDatosProfesor() {
            txtID.setText(profesorOriginal.getIdentificacionPersonal());
            txtNombre.setText(profesorOriginal.getNombre());
            txtApellido1.setText(profesorOriginal.getPrimerApellido());
            txtApellido2.setText(profesorOriginal.getSegundoApellido());
            txtCorreo.setText(profesorOriginal.getCorreoElectronico());
            txtTelefono.setText(profesorOriginal.getNumeroTelefono());
            txtDireccion.setText(profesorOriginal.getDireccionFisica());
            txtTitulos.setText(String.join(", ", profesorOriginal.getTitulos()));
            txtCertificaciones.setText(String.join(", ", profesorOriginal.getCertificaciones()));
        }

        // ╔════════════════════════════════════════════════════════════╗
        // ║                  VALIDACIÓN DE CAMPOS                     ║
        // ╚════════════════════════════════════════════════════════════╝
        private boolean validarCampos() {
            boolean nombreValido = txtNombre.getText().length() >= 2;
            aplicarEstiloCampo(txtNombre, nombreValido, "Debe tener al menos 2 caracteres");

            boolean apellido1Valido = txtApellido1.getText().length() >= 2;
            aplicarEstiloCampo(txtApellido1, apellido1Valido, "Debe tener al menos 2 caracteres");

            boolean apellido2Valido = txtApellido2.getText().length() >= 2;
            aplicarEstiloCampo(txtApellido2, apellido2Valido, "Debe tener al menos 2 caracteres");

            boolean correoValido = txtCorreo.getText().matches("^[^\\s@]{3,}@[^\\s@]{3,}$");
            aplicarEstiloCampo(txtCorreo, correoValido, "Correo inválido");

            boolean telefonoValido = txtTelefono.getText().length() >= 8;
            aplicarEstiloCampo(txtTelefono, telefonoValido, "Debe tener al menos 8 dígitos");

            boolean direccionValida = txtDireccion.getText().length() >= 5;
            aplicarEstiloCampo(txtDireccion, direccionValida, "Debe tener al menos 5 caracteres");

            boolean titulosValidos = Arrays.stream(txtTitulos.getText().split("\\s*,\\s*"))
                    .allMatch(t -> t.length() >= 5 && t.length() <= 40);
            aplicarEstiloCampo(txtTitulos, titulosValidos, "Cada título debe tener entre 5 y 40 caracteres");

            boolean certificacionesValidas = Arrays.stream(txtCertificaciones.getText().split("\\s*,\\s*"))
                    .allMatch(c -> c.length() >= 5 && c.length() <= 40);
            aplicarEstiloCampo(txtCertificaciones, certificacionesValidas, "Cada certificación debe tener entre 5 y 40 caracteres");

            return nombreValido && apellido1Valido && apellido2Valido &&
                    correoValido && telefonoValido && direccionValida &&
                    titulosValidos && certificacionesValidas;
        }

        private void aplicarEstiloCampo(JTextField campo, boolean valido, String tooltip) {
            campo.setBackground(valido ? Color.WHITE : new Color(255, 230, 230));
            campo.setToolTipText(valido ? null : tooltip);
        }

        private class CampoListener implements javax.swing.event.DocumentListener {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { validarCampos(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { validarCampos(); }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { validarCampos(); }
        }

        // ╔════════════════════════════════════════════════════════════╗
        // ║                  💾 GUARDAR CAMBIOS                       ║
        // ╚════════════════════════════════════════════════════════════╝
        private void guardarCambios() {
            if (!validarCampos()) {
                JOptionPane.showMessageDialog(this,
                        "⚠️ Corrige los campos marcados antes de guardar.",
                        "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                profesorOriginal.setNombre(txtNombre.getText().trim());
                profesorOriginal.setPrimerApellido(txtApellido1.getText().trim());
                profesorOriginal.setSegundoApellido(txtApellido2.getText().trim());
                profesorOriginal.setCorreoElectronico(txtCorreo.getText().trim());
                profesorOriginal.setNumeroTelefono(txtTelefono.getText().trim());
                profesorOriginal.setDireccionFisica(txtDireccion.getText().trim());

                profesorOriginal.setTitulosObtenidos(new java.util.ArrayList<>(
                        Arrays.asList(txtTitulos.getText().split("\\s*,\\s*"))));

                profesorOriginal.setCertificacionesEstudio(new java.util.ArrayList<>(
                        Arrays.asList(txtCertificaciones.getText().split("\\s*,\\s*"))));

                gestor.actualizarProfesor(profesorOriginal);

                JOptionPane.showMessageDialog(this,
                        "✅ Cambios guardados correctamente.",
                        "Confirmación", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Error al guardar los cambios: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }


        // ╔════════════════════════════════════════════════════════════╗
        // ║                  VISUALIZACIÓN DE MENSAJES                ║
        // ╚════════════════════════════════════════════════════════════╝
        private void mostrarAlerta(String mensaje, int tipo) {
            JOptionPane.showMessageDialog(this, mensaje, "Resultado", tipo);
        }

        private void aplicarEstiloCampo(JComponent campo, boolean valido, String tooltip) {
            campo.setBorder(BorderFactory.createLineBorder(valido ? new Color(0, 180, 100) : Color.RED));
            campo.setToolTipText(valido ? null : tooltip);
        }

    }




