package gui.evaluacion.pareo;

import evaluaciones.Pregunta;
import gui.evaluacion.util.PanelPreguntaBasica;

public class PanelPareo extends PanelPreguntaBasica {

    public PanelPareo() {
        super("Pareo");
        add(new CampoPareo(this));
    }

    @Override
    public Pregunta crearPreguntaFinal() {
        String descripcion = campoDescripcion.getText().trim();
        String puntosStr = campoPuntos.getText().trim();
        if (descripcion.isEmpty() || puntosStr.isEmpty()) return null;

        try {
            int puntos = Integer.parseInt(puntosStr);
            CampoPareo campo = (CampoPareo) getClientProperty("pareo");
            return campo.construirPregunta(1, descripcion, puntos);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
