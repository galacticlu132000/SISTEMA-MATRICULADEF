package gui.evaluacion.util;

import evaluaciones.Pregunta;

import javax.swing.*;
import java.awt.*;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🌟 PanelPreguntaBasica                                                    ║
 * ║                                                                            ║
 * ║ Panel base con campos comunes para cualquier tipo de pregunta.            ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public abstract class PanelPreguntaBasica extends JPanel {

    protected JTextField campoDescripcion;
    protected JTextField campoPuntos;
    protected String tipo;

    public PanelPreguntaBasica(String tipo) {
        this.tipo = tipo;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(255, 255, 255));
        setBorder(BorderFactory.createTitledBorder("🧩 " + tipo));

        JLabel lblDescripcion = new JLabel("📝 Descripción de la pregunta:");
        campoDescripcion = new JTextField();

        JLabel lblPuntos = new JLabel("⭐ Puntos que vale:");
        campoPuntos = new JTextField();

        add(lblDescripcion);
        add(campoDescripcion);
        add(Box.createVerticalStrut(10));
        add(lblPuntos);
        add(campoPuntos);
        add(Box.createVerticalStrut(15));
    }

    /**
     * Método que cada subpanel debe implementar para construir la pregunta final.
     */
    public abstract Pregunta crearPreguntaFinal();
}
