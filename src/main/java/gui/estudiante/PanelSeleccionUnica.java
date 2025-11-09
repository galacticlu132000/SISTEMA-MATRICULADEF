package gui.estudiante;

import evaluacion.PreguntaSeleccionUnica;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

public class PanelSeleccionUnica extends JPanel {

    private ButtonGroup grupoOpciones = new ButtonGroup();

    public PanelSeleccionUnica(PreguntaSeleccionUnica pregunta) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for (String opcion : pregunta.getOpciones()) {
            JRadioButton radio = new JRadioButton(opcion);
            grupoOpciones.add(radio);
            add(radio);
        }
    }

    public String obtenerRespuesta() {
        Enumeration<AbstractButton> botones = grupoOpciones.getElements();
        while (botones.hasMoreElements()) {
            AbstractButton boton = botones.nextElement();
            if (boton.isSelected()) {
                return boton.getText();
            }
        }
        return null;
    }
}
