package gui.evaluaciones;


import evaluacion.*;
import usuarios.Profesor;
import evaluacion.GestorEvaluaciones;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ ➕ RegistroEvaluacionControlador                                           ║
 * ║                                                                            ║
 * ║ Ventana Swing para crear una evaluación:                                  ║
 * ║ - Nombre, instrucciones, objetivos, duración, aleatoriedad                ║
 * ║ - Subpanel para agregar preguntas dinámicamente                           ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class RegistroEvaluacionControlador extends JFrame {

    private JTextField campoNombre;
    private JTextArea campoInstrucciones;
    private JTextField campoDuracion;
    private JCheckBox chkPreguntasAleatorias;
    private JCheckBox chkOpcionesAleatorias;
    private JTextField campoObjetivo;
    private DefaultListModel<String> modeloObjetivos;
    private JList<String> listaObjetivos;
    private JButton btnAgregarObjetivo;
    private JButton btnAgregarPregunta;
    private JButton btnGuardar;

    private List<Pregunta> preguntas = new ArrayList<>();
    private final Profesor profesorActual;

    public RegistroEvaluacionControlador(JFrame padre,Profesor profesorActual) {
        this.profesorActual = profesorActual ;
        setTitle("➕ Crear Evaluación");
        setSize(700, 600);
        setLocationRelativeTo(padre);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 250, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titulo = new JLabel("➕ Crear Nueva Evaluación", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        titulo.setForeground(new Color(60, 160, 130));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(0, 1, 10, 10));
        centro.setBackground(new Color(230, 250, 240));

        campoNombre = new JTextField();
        campoInstrucciones = new JTextArea(4, 40);
        campoDuracion = new JTextField();
        chkPreguntasAleatorias = new JCheckBox("¿Preguntas en orden aleatorio?");
        chkOpcionesAleatorias = new JCheckBox("¿Opciones en orden aleatorio?");

        modeloObjetivos = new DefaultListModel<>();
        listaObjetivos = new JList<>(modeloObjetivos);
        campoObjetivo = new JTextField();
        btnAgregarObjetivo = new JButton("➕ Agregar Objetivo");
        btnAgregarObjetivo.addActionListener(e -> {
            String texto = campoObjetivo.getText().trim();
            if (texto.length() >= 10 && texto.length() <= 40) {
                modeloObjetivos.addElement(texto);
                campoObjetivo.setText("");
            } else {
                mostrarAdvertencia("El objetivo debe tener entre 10 y 40 caracteres.");
            }
        });

        centro.add(new JLabel("📌 Nombre de la evaluación:"));
        centro.add(campoNombre);
        centro.add(new JLabel("📜 Instrucciones generales:"));
        centro.add(new JScrollPane(campoInstrucciones));
        centro.add(new JLabel("⏱️ Duración (minutos):"));
        centro.add(campoDuracion);
        centro.add(chkPreguntasAleatorias);
        centro.add(chkOpcionesAleatorias);
        centro.add(new JLabel("🎯 Objetivos de la evaluación:"));
        centro.add(campoObjetivo);
        centro.add(btnAgregarObjetivo);
        centro.add(new JScrollPane(listaObjetivos));

        panel.add(centro, BorderLayout.CENTER);

        btnAgregarPregunta = new JButton("➕ Agregar Pregunta");
        btnAgregarPregunta.addActionListener(e -> abrirPanelPregunta());

        btnGuardar = new JButton("💾 Guardar Evaluación");
        btnGuardar.addActionListener(e -> guardarEvaluacion());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.setBackground(new Color(230, 250, 240));
        botones.add(btnAgregarPregunta);
        botones.add(btnGuardar);

        panel.add(botones, BorderLayout.SOUTH);
        add(panel);
    }

    private void abrirPanelPregunta() {
        new RegistroPreguntaControlador(this, preguntas.size() + 1, preguntas).setVisible(true);
    }

    private void guardarEvaluacion() {
        String nombre = campoNombre.getText().trim();
        String instrucciones = campoInstrucciones.getText().trim();
        String duracionStr = campoDuracion.getText().trim();

        if (nombre.length() < 5 || nombre.length() > 20) {
            mostrarAdvertencia("El nombre debe tener entre 5 y 20 caracteres.");
            return;
        }
        if (instrucciones.length() < 5 || instrucciones.length() > 400) {
            mostrarAdvertencia("Las instrucciones deben tener entre 5 y 400 caracteres.");
            return;
        }
        int duracion;
        try {
            duracion = Integer.parseInt(duracionStr);
            if (duracion < 1) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            mostrarAdvertencia("Duración inválida. Debe ser un número entero ≥ 1.");
            return;
        }
        if (modeloObjetivos.isEmpty()) {
            mostrarAdvertencia("Debe agregar al menos un objetivo.");
            return;
        }
        if (preguntas.isEmpty()) {
            mostrarAdvertencia("Debe agregar al menos una pregunta.");
            return;
        }

        List<String> objetivos = new ArrayList<>();
        for (int i = 0; i < modeloObjetivos.size(); i++) {
            objetivos.add(modeloObjetivos.getElementAt(i));
        }

        Evaluacion evaluacion = new Evaluacion(
                nombre, instrucciones, objetivos, duracion,
                chkPreguntasAleatorias.isSelected(),
                chkOpcionesAleatorias.isSelected(),
                preguntas, profesorActual
        );

        GestorEvaluaciones.getInstancia().registrarEvaluacion(evaluacion);
        JOptionPane.showMessageDialog(this, "✅ Evaluación registrada exitosamente.");
        dispose();
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}
