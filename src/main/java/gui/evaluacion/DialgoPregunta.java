package gui.evaluacion;
import evaluaciones.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🎨 DialogoAgregarPregunta                                                 ║
 * ║                                                                            ║
 * ║ Diálogo visual para crear preguntas de distintos tipos.                   ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class DialogoAgregarPregunta extends JDialog {

    private JComboBox<String> selectorTipo;
    private JPanel panelCentral;
    private CardLayout layoutTarjetas;

    private JPanel panelUnica, panelMultiple, panelFV, panelPareo, panelSopa;
    private JButton btnConfirmar;

    private Pregunta preguntaCreada;

    public DialogoAgregarPregunta(JFrame parent) {
        super(parent, "➕ Nueva Pregunta", true);
        setSize(600, 500);
        setLocationRelativeTo(parent);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 250, 255));

        selectorTipo = new JComboBox<>(new String[]{
                "Selección Única", "Selección Múltiple", "Falso/Verdadero", "Pareo", "Sopa de Letras"
        });
        selectorTipo.addActionListener(e -> layoutTarjetas.show(panelCentral, (String) selectorTipo.getSelectedItem()));

        JPanel encabezado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        encabezado.setBackground(new Color(100, 149, 237));
        JLabel titulo = new JLabel("🎨 Tipo de pregunta:");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));
        encabezado.add(titulo);
        encabezado.add(selectorTipo);
        add(encabezado, BorderLayout.NORTH);

        layoutTarjetas = new CardLayout();
        panelCentral = new JPanel(layoutTarjetas);
        panelCentral.setBackground(new Color(255, 255, 255));

        panelUnica = crearPanelSeleccionUnica();
        panelMultiple = crearPanelSeleccionMultiple();
        panelFV = crearPanelFalsoVerdadero();
        panelPareo = crearPanelPareo();
        panelSopa = crearPanelSopa();

        panelCentral.add(panelUnica, "Selección Única");
        panelCentral.add(panelMultiple, "Selección Múltiple");
        panelCentral.add(panelFV, "Falso/Verdadero");
        panelCentral.add(panelPareo, "Pareo");
        panelCentral.add(panelSopa, "Sopa de Letras");

        add(panelCentral, BorderLayout.CENTER);

        btnConfirmar = new JButton("✅ Crear Pregunta");
        btnConfirmar.addActionListener(e -> crearPregunta());

        JPanel pie = new JPanel();
        pie.setBackground(new Color(230, 240, 255));
        pie.add(btnConfirmar);
        add(pie, BorderLayout.SOUTH);
    }

    public Pregunta mostrarYObtenerPregunta() {
        setVisible(true);
        return preguntaCreada;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              PANELES POR TIPO DE PREGUNTA                ║
    // ╚════════════════════════════════════════════════════════════╝

    private JPanel crearPanelSeleccionUnica() {
        JPanel panel = new PreguntaBasica("Selección Única");
        panel.add(new CampoOpcionesUnica(panel));
        return panel;
    }

    private JPanel crearPanelSeleccionMultiple() {
        JPanel panel = new PreguntaBasica("Selección Múltiple");
        panel.add(new CampoOpcionesMultiple(panel));
        return panel;
    }

    private JPanel crearPanelFalsoVerdadero() {
        JPanel panel = new PreguntaBasica("Falso/Verdadero");
        JCheckBox chkVerdadero = new JCheckBox("✔ Verdadero es la respuesta correcta");
        panel.add(chkVerdadero);
        panel.putClientProperty("respuestaCorrecta", chkVerdadero);
        return panel;
    }

    private JPanel crearPanelPareo() {
        JPanel panel = new PreguntaBasica("Pareo");
        panel.add(new CampoPareo(panel));
        return panel;
    }

    private JPanel crearPanelSopa() {
        JPanel panel = new PreguntaBasica("Sopa de Letras");
        panel.add(new CampoSopa(panel));
        return panel;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              CREAR PREGUNTA SEGÚN PANEL ACTIVO           ║
    // ╚════════════════════════════════════════════════════════════╝
    private void crearPregunta() {
        String tipo = (String) selectorTipo.getSelectedItem();
        JPanel activo = switch (tipo) {
            case "Selección Única" -> panelUnica;
            case "Selección Múltiple" -> panelMultiple;
            case "Falso/Verdadero" -> panelFV;
            case "Pareo" -> panelPareo;
            case "Sopa de Letras" -> panelSopa;
            default -> null;
        };

        if (activo instanceof PreguntaBasica panel) {
            preguntaCreada = panel.crearPreguntaFinal();
            if (preguntaCreada != null) {
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Verifica que todos los campos estén completos.");
            }
        }
    }
}
