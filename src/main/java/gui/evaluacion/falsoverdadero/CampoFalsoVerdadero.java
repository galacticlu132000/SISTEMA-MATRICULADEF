package gui.evaluacion.falsoverdadero;
import evaluaciones.PreguntaFalsoVerdadero;

import javax.swing.*;
import java.awt.*;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ ✔️ CampoFalsoVerdadero                                                    ║
 * ║                                                                            ║
 * ║ Panel para definir si la respuesta correcta es verdadero o falso.         ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class CampoFalsoVerdadero extends JPanel {

    private final JRadioButton opcionVerdadero;
    private final JRadioButton opcionFalso;
    private final ButtonGroup grupo;

    public CampoFalsoVerdadero(JPanel contenedor) {
        setLayout(new GridLayout(2, 1));
        setOpaque(false);
        setBorder(BorderFactory.createTitledBorder("⚖️ Respuesta correcta"));

        opcionVerdadero = new JRadioButton("✔ Verdadero");
        opcionFalso = new JRadioButton("❌ Falso");

        grupo = new ButtonGroup();
        grupo.add(opcionVerdadero);
        grupo.add(opcionFalso);

        add(opcionVerdadero);
        add(opcionFalso);

        contenedor.putClientProperty("falsoVerdadero", this);
    }

    public PreguntaFalsoVerdadero construirPregunta(int numero, String descripcion, int puntos) {
        if (!opcionVerdadero.isSelected() && !opcionFalso.isSelected()) return null;
        boolean respuestaCorrecta = opcionVerdadero.isSelected();
        return new PreguntaFalsoVerdadero(numero, descripcion, puntos, respuestaCorrecta);
    }
}

