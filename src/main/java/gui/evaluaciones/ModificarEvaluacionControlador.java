package gui.evaluaciones;

import evaluacion.*;
import evaluacion.GestorEvaluaciones;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ ✏️ ModificarEvaluacionControlador                                          ║
 * ║                                                                            ║
 * ║ Ventana Swing para editar una evaluación no asignada:                     ║
 * ║ - Precarga de datos                                                       ║
 * ║ - Validación y actualización                                              ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class ModificarEvaluacionControlador extends JFrame {

    private JTextField campoNombre;
    private JTextArea campoInstrucciones;
    private JTextField campoDuracion;
    private JCheckBox chkPreguntasAleatorias;
    private JCheckBox chkOpcionesAleatorias;
    private JTextField campoObjetivo;
    private DefaultListModel<String> modeloObjetivos;
    private JList<String> listaObjetivos;
    private JButton btnAgregarObjetivo;
    private JButton btnGuardar;

    private final Evaluacion evaluacionOriginal;
    private final List<Pregunta> preguntas;

    public ModificarEvaluacionControlador(JFrame padre, Evaluacion evaluacion) {
        this.evaluacionOriginal = evaluacion;
        this.preguntas = new ArrayList<>(evaluacion.getPreguntas());
        setTitle("✏️ Modificar Evaluación");
        setSize(700, 600);
        setLocationRelativeTo(padre);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 250, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titulo = new JLabel("✏️ Modificar Evaluación", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        titulo.setForeground(new Color(160, 120, 60));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(0, 1, 10, 10));
        centro.setBackground(new Color(255, 250, 240));

        campoNombre = new JTextField(evaluacionOriginal.getNombre());
        campoInstrucciones = new JTextArea(evaluacionOriginal.getInstrucciones(), 4, 40);
        campoDuracion = new JTextField(String.valueOf(evaluacionOriginal.getDuracionMinutos()));
        chkPreguntasAleatorias = new JCheckBox("¿Preguntas en orden aleatorio?", evaluacionOriginal.isPreguntasAleatorias());
        chkOpcionesAleatorias = new JCheckBox("¿Opciones en orden aleatorio?", evaluacionOriginal.isOpcionesAleatorias());

        modeloObjetivos = new DefaultListModel<>();
        for (String obj : evaluacionOriginal.getObjetivos()) {
            modeloObjetivos.addElement(obj);
        }

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

        btnGuardar = new JButton("💾 Guardar Cambios");
        btnGuardar.addActionListener(e -> guardarCambios());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.setBackground(new Color(255, 250, 240));
        botones.add(btnGuardar);

        panel.add(botones, BorderLayout.SOUTH);
        add(panel);
    }

    private void guardarCambios() {
        if (evaluacionOriginal.estaAsociadaAGrupo()) {
            mostrarAdvertencia("No se puede modificar una evaluación ya asignada a un grupo.");
            return;
        }

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

        List<String> objetivos = new ArrayList<>();
        for (int i = 0; i < modeloObjetivos.size(); i++) {
            objetivos.add(modeloObjetivos.getElementAt(i));
        }

        evaluacionOriginal.setNombre(nombre);
        evaluacionOriginal.setInstrucciones(instrucciones);
        evaluacionOriginal.setDuracionMinutos(duracion);
        evaluacionOriginal.setPreguntasAleatorias(chkPreguntasAleatorias.isSelected());
        evaluacionOriginal.setOpcionesAleatorias(chkOpcionesAleatorias.isSelected());
        evaluacionOriginal.setObjetivos(objetivos);

        GestorEvaluaciones.getInstancia().modificarEvaluacion(evaluacionOriginal);
        JOptionPane.showMessageDialog(this, "✅ Evaluación modificada exitosamente.");
        dispose();
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}
