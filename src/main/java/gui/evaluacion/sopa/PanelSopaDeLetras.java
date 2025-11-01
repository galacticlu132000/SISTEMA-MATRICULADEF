package gui.evaluacion.sopa;

import evaluaciones.Pregunta;
import gui.evaluacion.util.PanelPreguntaBasica;

public class PanelSopaDeLetras extends PanelPreguntaBasica {

    public PanelSopaDeLetras() {
        super("Sopa de Letras");
        add(new CampoSopaDeLetras(this));
    }

    @Override
    public Pregunta crearPreguntaFinal() {
        String descripcion = campoDescripcion.getText().trim();
        String puntosStr = campoPuntos.getText().trim();
        if (descripcion.isEmpty() || puntosStr.isEmpty()) return null;

        try {
            int puntos = Integer.parseInt(puntosStr);
            CampoSopaDeLetras campo = (CampoSopaDeLetras) getClientProperty("sopa");
            return campo.construirPregunta(1, descripcion, puntos);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}

