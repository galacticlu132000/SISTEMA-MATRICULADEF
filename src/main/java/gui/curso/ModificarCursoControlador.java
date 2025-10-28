package gui.curso;
import javax.swing.event.DocumentListener;
import usuarios.Curso;
import control.GestorCursos;

import javax.swing.*;
import java.awt.*;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ ✏️ ModificarCursoControlador                                               ║
 * ║                                                                            ║
 * ║ Ventana Swing para modificar los datos de un curso existente.             ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class ModificarCursoControlador extends JDialog {

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      CAMPOS DE FORMULARIO                  ║
    // ╚════════════════════════════════════════════════════════════╝
    private JTextField txtID, txtNombre, txtDescripcion, txtHoras,
            txtMinEstudiantes, txtMaxEstudiantes, txtMinCalificacion;
    private JComboBox<String> comboModalidad, comboTipoCurso;
    private JButton btnGuardar, btnCancelar;

    private final GestorCursos gestor;
    private final Curso cursoOriginal;

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      CONSTRUCTOR                           ║
    // ╚════════════════════════════════════════════════════════════╝
    public ModificarCursoControlador(Frame parent, Curso curso) {
        super(parent, "✏️ Modificar Curso", true);
        this.gestor = GestorCursos.getInstancia();
        this.cursoOriginal = curso;
        setSize(520, 520);
        setLocationRelativeTo(parent);
        aplicarEstiloGlobal();
        inicializarComponentes();
        cargarDatosCurso();
    }


// ╔════════════════════════════════════════════════════════════╗
// ║                  🎨 ESTILO GLOBAL                         ║
// ╚════════════════════════════════════════════════════════════╝
private void aplicarEstiloGlobal() {
    UIManager.put("Label.font", new Font("Segoe UI Emoji", Font.BOLD, 14));
    UIManager.put("TextField.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
    UIManager.put("Button.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
    UIManager.put("ComboBox.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
}

    // ╔════════════════════════════════════════════════════════════╗
// ║              LISTENER PARA VALIDACIÓN EN TIEMPO REAL       ║
// ╚════════════════════════════════════════════════════════════╝
    private class CampoListener implements javax.swing.event.DocumentListener {
        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            validarCampos();
        }

        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            validarCampos();
        }

        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            validarCampos();
        }
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
    txtDescripcion     = new JTextField();
    txtHoras           = new JTextField();
    txtMinEstudiantes  = new JTextField();
    txtMaxEstudiantes  = new JTextField();
    txtMinCalificacion = new JTextField();

    comboModalidad = new JComboBox<>(new String[]{
            "PRESENCIAL", "VIRTUAL_SINCRONICA", "VIRTUAL_ASINCRONICA", "VIRTUAL_HIBRIDA", "SEMIPRESENCIAL"
    });
    comboTipoCurso = new JComboBox<>(new String[]{
            "TEORICO", "PRACTICO", "TALLER", "SEMINARIO"
    });

    String[] etiquetas = {
            "🆔 Identificación:", "📘 Nombre:", "📝 Descripción:", "⏱️ Horas por día:",
            "👥 Mínimo estudiantes:", "👥 Máximo estudiantes:", "✅ Calificación mínima:",
            "🎓 Modalidad:", "📚 Tipo de curso:"
    };
    Component[] campos = {
            txtID, txtNombre, txtDescripcion, txtHoras,
            txtMinEstudiantes, txtMaxEstudiantes, txtMinCalificacion,
            comboModalidad, comboTipoCurso
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
    for (JTextField campo : new JTextField[]{
            txtNombre, txtDescripcion, txtHoras,
            txtMinEstudiantes, txtMaxEstudiantes, txtMinCalificacion
    }) {
        campo.getDocument().addDocumentListener(new CampoListener());
    }



}
private void cargarDatosCurso() {
    txtID.setText(cursoOriginal.getIdentificacionCurso());
    txtNombre.setText(cursoOriginal.getnombreCurso());
    txtDescripcion.setText(cursoOriginal.getdescripcionCurso());
    txtHoras.setText(String.valueOf(cursoOriginal.gethorasDia()));
    txtMinEstudiantes.setText(String.valueOf(cursoOriginal.getcantidadMinimaE()));
    txtMaxEstudiantes.setText(String.valueOf(cursoOriginal.getcantidadMaximaE()));
    txtMinCalificacion.setText(String.valueOf(cursoOriginal.getcalificacionMinimaE()));
    comboModalidad.setSelectedItem(cursoOriginal.getmodalidad().name());
    comboTipoCurso.setSelectedItem(cursoOriginal.gettipoCurso().name());
}
private boolean validarCampos() {
    boolean nombreValido = txtNombre.getText().length() >= 5 && txtNombre.getText().length() <= 40;
    aplicarEstiloCampo(txtNombre, nombreValido, "Debe tener entre 5 y 40 caracteres");

    boolean descripcionValida = txtDescripcion.getText().length() >= 5 && txtDescripcion.getText().length() <= 400;
    aplicarEstiloCampo(txtDescripcion, descripcionValida, "Debe tener entre 5 y 400 caracteres");

    boolean horasValidas = validarEntero(txtHoras, 1, 8, "Horas por día entre 1 y 8");
    boolean minEstudiantesValidos = validarEntero(txtMinEstudiantes, 1, 5, "Mínimo entre 1 y 5");
    boolean maxEstudiantesValidos = validarEntero(txtMaxEstudiantes,
            Integer.parseInt(txtMinEstudiantes.getText()), 20, "Máximo entre mínimo y 20");
    boolean calificacionValida = validarEntero(txtMinCalificacion, 0, 100, "Calificación entre 0 y 100");

    return nombreValido && descripcionValida && horasValidas &&
            minEstudiantesValidos && maxEstudiantesValidos && calificacionValida;
}

private boolean validarEntero(JTextField campo, int min, int max, String tooltip) {
    try {
        int valor = Integer.parseInt(campo.getText());
        boolean valido = valor >= min && valor <= max;
        aplicarEstiloCampo(campo, valido, tooltip);
        return valido;
    } catch (NumberFormatException e) {
        aplicarEstiloCampo(campo, false, "Debe ser un número válido");
        return false;
    }
}
private void guardarCambios() {
    if (!validarCampos()) {
        mostrarAlerta("❌ Verifica que todos los campos estén correctamente llenos.", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        cursoOriginal.setNombreCurso(txtNombre.getText().trim());
        cursoOriginal.setDescripcionCurso(txtDescripcion.getText().trim());
        cursoOriginal.setHorasDia(Integer.parseInt(txtHoras.getText().trim()));
        cursoOriginal.setCantidadMinimaE(Integer.parseInt(txtMinEstudiantes.getText().trim()));
        cursoOriginal.setCantidadMaximaE(Integer.parseInt(txtMaxEstudiantes.getText().trim()));
        cursoOriginal.setCalificacionMinimaE(Integer.parseInt(txtMinCalificacion.getText().trim()));
        cursoOriginal.setModalidad(Curso.Modalidad.valueOf(comboModalidad.getSelectedItem().toString()));
        cursoOriginal.setTipoCurso(Curso.Tipo_Curso.valueOf(comboTipoCurso.getSelectedItem().toString()));

        boolean exito = gestor.actualizarCursos(cursoOriginal);
        if (exito) {
            mostrarAlerta("✅ Curso modificado correctamente.", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            mostrarAlerta("❌ No se pudo actualizar el curso.", JOptionPane.ERROR_MESSAGE);
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


