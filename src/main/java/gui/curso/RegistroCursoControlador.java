package gui.curso;

import usuarios.Curso;
import control.GestorCursos;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 📝 RegistroCursoControlador                                                ║
 * ║                                                                            ║
 * ║ Ventana Swing para registrar un nuevo curso con validaciones visuales.    ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class RegistroCursoControlador extends JDialog {

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      CAMPOS DE FORMULARIO                  ║
    // ╚════════════════════════════════════════════════════════════╝
    private JTextField campoID, campoNombre, campoDescripcion, campoHoras,
            campoMinEstudiantes, campoMaxEstudiantes, campoMinCalificacion;
    private JComboBox<String> comboModalidad, comboTipoCurso;
    private JButton botonRegistrar;

    private final GestorCursos gestor = GestorCursos.getInstancia();

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      CONSTRUCTOR                           ║
    // ╚════════════════════════════════════════════════════════════╝
    public RegistroCursoControlador(Frame parent) {
        super(parent, "📝 Registro de Curso", true);
        setSize(520, 600);
        setLocationRelativeTo(parent);
        aplicarEstiloGlobal();
        inicializarComponentes();
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🎨 ESTILO GLOBAL                         ║
    // ╚════════════════════════════════════════════════════════════╝
    private void aplicarEstiloGlobal() {
        UIManager.put("Label.font", new Font("Segoe UI Emoji", Font.BOLD, 14));
        UIManager.put("TextField.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
        UIManager.put("ComboBox.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
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

        campoID              = new JTextField();
        campoNombre          = new JTextField();
        campoDescripcion     = new JTextField();
        campoHoras           = new JTextField();
        campoMinEstudiantes  = new JTextField();
        campoMaxEstudiantes  = new JTextField();
        campoMinCalificacion = new JTextField();

        comboModalidad = new JComboBox<>(new String[]{
                "PRESENCIAL", "VIRTUAL_SINCRONICA", "VIRTUAL_ASINCRONICA", "VIRTUAL_HIBRIDA", "SEMIPRESENCIAL"
        });
        comboTipoCurso = new JComboBox<>(new String[]{
                "TEORICO", "PRACTICO", "TALLER", "SEMINARIO"
        });

        botonRegistrar = new JButton("📝 Registrar curso");

        String[] etiquetas = {
                "🆔 Identificación:", "📘 Nombre:", "📝 Descripción:", "⏱️ Horas por día:",
                "👥 Mínimo estudiantes:", "👥 Máximo estudiantes:", "✅ Calificación mínima:",
                "🎓 Modalidad:", "📚 Tipo de curso:"
        };
        JComponent[] campos = {
                campoID, campoNombre, campoDescripcion, campoHoras,
                campoMinEstudiantes, campoMaxEstudiantes, campoMinCalificacion,
                comboModalidad, comboTipoCurso
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

        botonRegistrar.addActionListener(e -> registrarCurso());

        JTextField[] camposTexto = {
                campoID, campoNombre, campoDescripcion, campoHoras,
                campoMinEstudiantes, campoMaxEstudiantes, campoMinCalificacion
        };

        for (JTextField campo : camposTexto) {
            campo.getDocument().addDocumentListener(new CampoListener(campo));
        }
        comboModalidad = new JComboBox<>(modalidadMap.keySet().toArray(new String[0]));
        comboTipoCurso = new JComboBox<>(tipoCursoMap.keySet().toArray(new String[0]));
    }
    // ╔════════════════════════════════════════════════════════════╗
    // ║                  VALIDACIÓN DE CAMPOS                     ║
    // ╚════════════════════════════════════════════════════════════╝
    private void validarCampos() {
        boolean valido =
                validarCampo(campoID, 5, 20, "Entre 5 y 20 caracteres") &
                        validarCampo(campoNombre, 5, 40, "Entre 5 y 40 caracteres") &
                        validarCampo(campoDescripcion, 5, 400, "Entre 5 y 400 caracteres") &
                        validarEntero(campoHoras, 1, 8, "Horas por día entre 1 y 8") &
                        validarEntero(campoMinEstudiantes, 1, 5, "Mínimo entre 1 y 5") &
                        validarEnteroDependiente(campoMaxEstudiantes, campoMinEstudiantes, 6, 20, "Máximo entre mínimo y 20") &
                        validarEntero(campoMinCalificacion, 0, 100, "Calificación entre 0 y 100");

        botonRegistrar.setEnabled(valido);
    }

    private boolean validarCampo(JTextField campo, int min, int max, String tooltip) {
        String texto = campo.getText().trim();
        boolean valido = texto.length() >= min && texto.length() <= max;
        aplicarEstiloCampo(campo, valido, tooltip);
        return valido;
    }

    private boolean validarEntero(JTextField campo, int min, int max, String tooltip) {
        try {
            int valor = Integer.parseInt(campo.getText().trim());
            boolean valido = valor >= min && valor <= max;
            aplicarEstiloCampo(campo, valido, tooltip);
            return valido;
        } catch (NumberFormatException e) {
            aplicarEstiloCampo(campo, false, "Debe ser un número válido");
            return false;
        }
    }

    private boolean validarEnteroDependiente(JTextField campoMax, JTextField campoMin, int min, int max, String tooltip) {
        try {
            int valorMin = Integer.parseInt(campoMin.getText().trim());
            int valorMax = Integer.parseInt(campoMax.getText().trim());
            boolean valido = valorMax >= valorMin && valorMax <= max;
            aplicarEstiloCampo(campoMax, valido, tooltip);
            return valido;
        } catch (NumberFormatException e) {
            aplicarEstiloCampo(campoMax, false, "Debe ser un número válido");
            return false;
        }
    }
    // ╔════════════════════════════════════════════════════════════╗
// ║              ESTILO Y REGISTRO DE CURSO                    ║
// ╚════════════════════════════════════════════════════════════╝
    private void aplicarEstiloCampo(JComponent campo, boolean valido, String tooltip) {
        campo.setBorder(BorderFactory.createLineBorder(valido ? new Color(0, 180, 100) : Color.RED));
        campo.setToolTipText(valido ? null : tooltip);
    }

    private final java.util.Map<String, Curso.Modalidad> modalidadMap = java.util.Map.of(
            "Presencial", Curso.Modalidad.PRESENCIAL,
            "Virtual Sincrónica", Curso.Modalidad.VIRTUAL_SINCRONICA,
            "Virtual Asincrónica", Curso.Modalidad.VIRTUAL_ASINCRONICA,
            "Virtual Híbrida", Curso.Modalidad.VIRTUAL_HIBRIDA,
            "Semipresencial", Curso.Modalidad.SEMIPRESENCIAL
    );

    private final java.util.Map<String, Curso.Tipo_Curso> tipoCursoMap = java.util.Map.of(
            "Teórico", Curso.Tipo_Curso.TEORICO,
            "Práctico", Curso.Tipo_Curso.PRACTICO,
            "Taller", Curso.Tipo_Curso.TALLER,
            "Seminario", Curso.Tipo_Curso.SEMINARIO
    );


    private void registrarCurso() {
        try {
            Curso nuevo = new Curso(
                    campoID.getText().trim(),
                    campoNombre.getText().trim(),
                    campoDescripcion.getText().trim(),
                    Integer.parseInt(campoHoras.getText().trim()),
                    Integer.parseInt(campoMinEstudiantes.getText().trim()),
                    Integer.parseInt(campoMaxEstudiantes.getText().trim()),
                    Integer.parseInt(campoMinCalificacion.getText().trim()),
                    modalidadMap.get(comboModalidad.getSelectedItem().toString()).ordinal(),
                    tipoCursoMap.get(comboTipoCurso.getSelectedItem().toString()).ordinal()
            );

            gestor.registrarCursos(nuevo);
            mostrarAlerta("✅ Registro exitoso", "El curso ha sido registrado correctamente.");
            dispose();

        } catch (Exception e) {
            mostrarAlerta("❌ Error inesperado", "No se pudo registrar el curso.");
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

