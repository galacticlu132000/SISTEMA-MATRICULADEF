package gui.evaluaciones;
import evaluacion.PreguntaVerdaderoFalso;

import javax.swing.*;
import java.awt.*;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ ⚖️ PanelVerdaderoFalso                                                     ║
 * ║                                                                            ║
 * ║ Subpanel para crear preguntas de verdadero/falso                          ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class PanelVerdaderoFalso extends JPanel {

    private final JRadioButton opcionVerdadero;
    private final JRadioButton opcionFalso;
    private final ButtonGroup grupo;

    public PanelVerdaderoFalso() {
        setLayout(new GridLayout(2, 1, 10, 10));
        setBackground(new Color(250, 240, 255));
        setBorder(BorderFactory.createTitledBorder("⚖️ Selecciona la respuesta correcta"));

        grupo = new ButtonGroup();
        opcionVerdadero = new JRadioButton("Verdadero");
        opcionFalso = new JRadioButton("Falso");

        grupo.add(opcionVerdadero);
        grupo.add(opcionFalso);

        add(opcionVerdadero);
        add(opcionFalso);
    }

    public PreguntaVerdaderoFalso crearPregunta(int numero, String descripcion, int puntos) {
        if (!opcionVerdadero.isSelected() && !opcionFalso.isSelected()) {
            mostrarAdvertencia("Debe seleccionar Verdadero o Falso como respuesta correcta.");
            return null;
        }

        boolean respuestaCorrecta = opcionVerdadero.isSelected();
        return new PreguntaVerdaderoFalso(numero, descripcion, puntos, respuestaCorrecta);
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}
