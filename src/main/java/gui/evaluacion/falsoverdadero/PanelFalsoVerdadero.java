package gui.evaluacion.falsoverdadero;

import evaluaciones.Pregunta;
import gui.evaluacion.util.PanelPreguntaBasica;

import javax.swing.*;

public class PanelFalsoVerdadero extends PanelPreguntaBasica {

    public PanelFalsoVerdadero() {
        super("Falso/Verdadero");
        JCheckBox chkVerdadero = new JCheckBox("✔ Verdadero es la respuesta correcta");
        add(chkVerdadero);
        putClientProperty("respuestaCorrecta", chkVerdadero);
    }

    @Override
    public Pregunta crearPreguntaFinal() {
        String descripcion = campoDescripcion.getText().trim();
        String puntosStr = campoPuntos.getText().trim();
        if (descripcion.isEmpty() || puntosStr.isEmpty()) return null;

        try {
            int puntos = Integer.parseInt(puntosStr);
            JCheckBox chk = (JCheckBox) getClientProperty("respuestaCorrecta");
            return new evaluaciones.PreguntaFalsoVerdadero(1, descripcion, puntos, chk.isSelected());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
