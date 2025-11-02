package gui.evaluaciones;

import evaluacion.PreguntaSeleccionUnica;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🎯 PanelSeleccionUnica                                                     ║
 * ║                                                                            ║
 * ║ Subpanel para crear preguntas de selección única                          ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class PanelSeleccionUnica extends JPanel {

    private final List<JTextField> camposOpciones = new ArrayList<>();
    private final ButtonGroup grupoBotones = new ButtonGroup();
    private final List<JRadioButton> botonesCorrectos = new ArrayList<>();
    private final JPanel panelOpciones;

    public PanelSeleccionUnica() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 255, 250));
        setBorder(BorderFactory.createTitledBorder("🎯 Opciones de respuesta"));

        panelOpciones = new JPanel(new GridLayout(0, 1, 5, 5));
        panelOpciones.setBackground(new Color(240, 255, 250));

        agregarOpcion(); // mínimo 2
        agregarOpcion();

        JButton btnAgregar = new JButton("➕ Agregar opción");
        btnAgregar.addActionListener(e -> agregarOpcion());

        add(panelOpciones, BorderLayout.CENTER);
        add(btnAgregar, BorderLayout.SOUTH);
    }

    private void agregarOpcion() {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(new Color(240, 255, 250));

        JTextField campo = new JTextField();
        JRadioButton boton = new JRadioButton("Correcta");
        grupoBotones.add(boton);

        camposOpciones.add(campo);
        botonesCorrectos.add(boton);

        fila.add(campo, BorderLayout.CENTER);
        fila.add(boton, BorderLayout.EAST);
        panelOpciones.add(fila);
        revalidate();
        repaint();
    }

    public PreguntaSeleccionUnica crearPregunta(int numero, String descripcion, int puntos) {
        List<String> opciones = new ArrayList<>();
        String correcta = null;

        for (int i = 0; i < camposOpciones.size(); i++) {
            String texto = camposOpciones.get(i).getText().trim();
            if (!texto.isEmpty()) {
                opciones.add(texto);
                if (botonesCorrectos.get(i).isSelected()) {
                    correcta = texto;
                }
            }
        }

        if (opciones.size() < 2) {
            mostrarAdvertencia("Debe ingresar al menos 2 opciones.");
            return null;
        }
        if (correcta == null) {
            mostrarAdvertencia("Debe marcar una opción como correcta.");
            return null;
        }

        return new PreguntaSeleccionUnica(numero, descripcion, puntos, opciones, correcta);
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}

