package gui.evaluaciones;

import evaluacion.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🧩 RegistroPreguntaControlador                                             ║
 * ║                                                                            ║
 * ║ Subpanel para agregar preguntas a una evaluación:                         ║
 * ║ - Selección de tipo                                                       ║
 * ║ - Campos dinámicos según el tipo                                         ║
 * ║ - Validación y retorno al formulario principal                            ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */

public class RegistroPreguntaControlador extends JDialog {

    private JComboBox<String> selectorTipo;
    private JTextArea campoDescripcion;
    private JTextField campoPuntos;
    private JPanel panelCamposDinamicos;
    private JButton btnGuardar;

    private final int numeroPregunta;
    private final List<Pregunta> listaPreguntas;

    public RegistroPreguntaControlador(RegistroEvaluacionControlador padre, int numeroPregunta, List<Pregunta> listaPreguntas) {
        super(padre, "➕ Agregar Pregunta #" + numeroPregunta, true);
        this.numeroPregunta = numeroPregunta;
        this.listaPreguntas = listaPreguntas;
        setSize(750, 700); // ventana más alta
        setLocationRelativeTo(padre);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 255, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titulo = new JLabel("➕ Agregar Pregunta #" + numeroPregunta, JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
        titulo.setForeground(new Color(60, 160, 130));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBackground(new Color(240, 255, 250));

        campoDescripcion = new JTextArea(3, 40);
        campoPuntos = new JTextField();

        selectorTipo = new JComboBox<>(new String[]{
                "Selección Única", "Selección Múltiple", "Verdadero/Falso", "Pareo", "Sopa de Letras"
        });
        selectorTipo.addActionListener(e -> actualizarCamposDinamicos());

        centro.add(new JLabel("📝 Descripción de la pregunta:"));
        centro.add(new JScrollPane(campoDescripcion));
        centro.add(new JLabel("⭐ Puntos que vale:"));
        centro.add(campoPuntos);
        centro.add(new JLabel("📌 Tipo de pregunta:"));
        centro.add(selectorTipo);

        panelCamposDinamicos = new JPanel(new BorderLayout());
        panelCamposDinamicos.setBackground(new Color(240, 255, 250));
        centro.add(panelCamposDinamicos);

        panel.add(centro, BorderLayout.CENTER);

        btnGuardar = new JButton("💾 Guardar Pregunta");
        btnGuardar.addActionListener(e -> guardarPregunta());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.setBackground(new Color(240, 255, 250));
        botones.add(btnGuardar);

        panel.add(botones, BorderLayout.SOUTH);
        add(panel);

        actualizarCamposDinamicos(); // inicial
    }

    private void actualizarCamposDinamicos() {
        panelCamposDinamicos.removeAll();
        String tipo = (String) selectorTipo.getSelectedItem();

        switch (tipo) {
            case "Selección Única":
                panelCamposDinamicos.add(new PanelSeleccionUnica(), BorderLayout.CENTER);
                break;
            case "Selección Múltiple":
                panelCamposDinamicos.add(new PanelSeleccionMultiple(), BorderLayout.CENTER);
                break;
            case "Verdadero/Falso":
                panelCamposDinamicos.add(new PanelVerdaderoFalso(), BorderLayout.CENTER);
                break;
            case "Pareo":
                panelCamposDinamicos.add(new PanelPareo(), BorderLayout.CENTER);
                break;
            case "Sopa de Letras":
                PanelSopaLetras sopa = new PanelSopaLetras();
                JScrollPane scroll = new JScrollPane(sopa);
                scroll.setPreferredSize(new Dimension(700, 400));
                scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
                scroll.getVerticalScrollBar().setUnitIncrement(16);
                panelCamposDinamicos.add(scroll, BorderLayout.CENTER);
                break;
        }

        panelCamposDinamicos.revalidate();
        panelCamposDinamicos.repaint();
    }

    private void guardarPregunta() {
        String descripcion = campoDescripcion.getText().trim();
        String puntosStr = campoPuntos.getText().trim();
        int puntos;

        if (descripcion.length() < 5) {
            mostrarAdvertencia("La descripción debe tener al menos 5 caracteres.");
            return;
        }

        try {
            puntos = Integer.parseInt(puntosStr);
            if (puntos < 1) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            mostrarAdvertencia("Los puntos deben ser un número entero ≥ 1.");
            return;
        }

        String tipo = (String) selectorTipo.getSelectedItem();
        Pregunta nueva = null;

        switch (tipo) {
            case "Selección Única":
                nueva = ((PanelSeleccionUnica) panelCamposDinamicos.getComponent(0)).crearPregunta(numeroPregunta, descripcion, puntos);
                break;
            case "Selección Múltiple":
                nueva = ((PanelSeleccionMultiple) panelCamposDinamicos.getComponent(0)).crearPregunta(numeroPregunta, descripcion, puntos);
                break;
            case "Verdadero/Falso":
                nueva = ((PanelVerdaderoFalso) panelCamposDinamicos.getComponent(0)).crearPregunta(numeroPregunta, descripcion, puntos);
                break;
            case "Pareo":
                nueva = ((PanelPareo) panelCamposDinamicos.getComponent(0)).crearPregunta(numeroPregunta, descripcion, puntos);
                break;
            case "Sopa de Letras":
                JScrollPane scroll = (JScrollPane) panelCamposDinamicos.getComponent(0);
                PanelSopaLetras sopa = (PanelSopaLetras) scroll.getViewport().getView();
                nueva = sopa.crearPregunta(numeroPregunta, descripcion, puntos);
                break;
        }

        if (nueva != null) {
            listaPreguntas.add(nueva);
            JOptionPane.showMessageDialog(this, "✅ Pregunta agregada.");
            dispose();
        }
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}

