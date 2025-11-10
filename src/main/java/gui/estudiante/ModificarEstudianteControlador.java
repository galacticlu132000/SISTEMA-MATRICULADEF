package gui.estudiante;

import usuarios.Estudiante;
import control.GestorEstudiantes;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ ✏️ ModificarEstudianteControlador                                          ║
 * ║                                                                            ║
 * ║ Ventana Swing para modificar los datos de un estudiante existente.        ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class ModificarEstudianteControlador extends JDialog {

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      CAMPOS DE FORMULARIO                  ║
    // ╚════════════════════════════════════════════════════════════╝
    private JTextField txtID, txtNombre, txtApellido1, txtApellido2, txtCorreo,
            txtTelefono, txtDireccion, txtOrganizacion, txtTemas;
    private JButton btnGuardar, btnCancelar;

    private final GestorEstudiantes gestor;
    private final Estudiante estudianteOriginal;

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      CONSTRUCTOR                           ║
    // ╚════════════════════════════════════════════════════════════╝
    public ModificarEstudianteControlador(Frame parent, Estudiante estudiante) {
        super(parent, "✏️ Modificar Estudiante", true);
        this.gestor = GestorEstudiantes.getInstancia();
        this.estudianteOriginal = estudiante;
        setSize(520, 560);
        setLocationRelativeTo(parent);
        aplicarEstiloGlobal();
        inicializarComponentes();
        cargarDatosEstudiante();
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

        txtID           = new JTextField(); txtID.setEnabled(false);
        txtNombre       = new JTextField();
        txtApellido1    = new JTextField();
        txtApellido2    = new JTextField();
        txtCorreo       = new JTextField();
        txtTelefono     = new JTextField();
        txtDireccion    = new JTextField();
        txtOrganizacion = new JTextField();
        txtTemas        = new JTextField();

        String[] etiquetas = {
                "🆔 Identificación:", "👤 Nombre:", "👤 Apellido 1:", "👤 Apellido 2:",
                "📧 Correo:", "📞 Teléfono:", "🏠 Dirección:", "🏢 Organización:", "📚 Temas de interés:"
        };
        JTextField[] campos = {
                txtID, txtNombre, txtApellido1, txtApellido2,
                txtCorreo, txtTelefono, txtDireccion, txtOrganizacion, txtTemas
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
                txtTelefono, txtDireccion, txtOrganizacion, txtTemas
        }) {
            campo.getDocument().addDocumentListener(new CampoListener());
        }
    }


    // ╔════════════════════════════════════════════════════════════╗
    // ║              CARGAR DATOS DEL ESTUDIANTE                  ║
    // ╚════════════════════════════════════════════════════════════╝
    private void cargarDatosEstudiante() {
        txtID.setText(estudianteOriginal.getIdentificacionPersonal());
        txtNombre.setText(estudianteOriginal.getNombre());
        txtApellido1.setText(estudianteOriginal.getPrimerApellido());
        txtApellido2.setText(estudianteOriginal.getSegundoApellido());
        txtCorreo.setText(estudianteOriginal.getCorreoElectronico());
        txtTelefono.setText(estudianteOriginal.getNumeroTelefono());
        txtDireccion.setText(estudianteOriginal.getDireccionFisica());
        txtOrganizacion.setText(estudianteOriginal.getOrganizacionLaboral());
        txtTemas.setText(String.join(", ", estudianteOriginal.getTemasInteres()));
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

        boolean organizacionValida =txtOrganizacion.getText().length() >=1&&txtOrganizacion.getText().length() <=40;
        aplicarEstiloCampo(txtOrganizacion, organizacionValida, "Máximo 40 caracteres");

        boolean temasValidos = Arrays.stream(txtTemas.getText().split("\\s*,\\s*"))
                .allMatch(t -> t.length() >= 5 && t.length() <= 30);
        aplicarEstiloCampo(txtTemas, temasValidos, "Cada tema debe tener entre 5 y 30 caracteres");

        return nombreValido && apellido1Valido && apellido2Valido &&
                correoValido && telefonoValido && direccionValida &&
                organizacionValida && temasValidos;
    }

    private class CampoListener implements javax.swing.event.DocumentListener {
        public void insertUpdate(javax.swing.event.DocumentEvent e) { validarCampos(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e) { validarCampos(); }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { validarCampos(); }
    }



    // ╔════════════════════════════════════════════════════════════╗
    // ║                  GUARDAR CAMBIOS                          ║
    // ╚════════════════════════════════════════════════════════════╝
    private void guardarCambios() {
        if (!validarCampos()) {
            mostrarAlerta("❌ Verifica que todos los campos estén correctamente llenos.", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            estudianteOriginal.setNombre(txtNombre.getText().trim());
            estudianteOriginal.setPrimerApellido(txtApellido1.getText().trim());
            estudianteOriginal.setSegundoApellido(txtApellido2.getText().trim());
            estudianteOriginal.setCorreoElectronico(txtCorreo.getText().trim());
            estudianteOriginal.setNumeroTelefono(txtTelefono.getText().trim());
            estudianteOriginal.setDireccionFisica(txtDireccion.getText().trim());
            estudianteOriginal.setOrganizacionLaboral(txtOrganizacion.getText().trim());

            ArrayList<String> temas = new ArrayList<>(Arrays.asList(txtTemas.getText().split("\\s*,\\s*")));
            estudianteOriginal.setTemasInteres(temas);

            boolean exito = gestor.actualizarEstudiante(estudianteOriginal);
            if (exito) {
                mostrarAlerta("✅ Cambios guardados correctamente.", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                mostrarAlerta("❌ No se pudo actualizar el estudiante.", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            mostrarAlerta("❌ Error inesperado al guardar los cambios.", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  VISUALIZACIÓN DE MENSAJES                ║
    // ╚════════════════════════════════════════════════════════════╝
    private void mostrarAlerta(String mensaje, int tipo) { JOptionPane.showMessageDialog(this, mensaje, "Resultado", tipo); } private void aplicarEstiloCampo(JComponent campo, boolean valido, String tooltip) { campo.setBorder(BorderFactory.createLineBorder(valido ? new Color(0, 180, 100) : Color.RED)); campo.setToolTipText(valido ? null : tooltip); } }