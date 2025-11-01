package gui.evaluacion.seleccionm;
import evaluaciones.Pregunta;
import gui.evaluacion.util.PanelPreguntaBasica;

public class PanelSeleccionMultiple extends PanelPreguntaBasica {

    public PanelSeleccionMultiple() {
        super("Selección Múltiple");
        add(new CampoOpcionesMultiple(this));
    }

    @Override
    public Pregunta crearPreguntaFinal() {
        String descripcion = campoDescripcion.getText().trim();
        String puntosStr = campoPuntos.getText().trim();
        if (descripcion.isEmpty() || puntosStr.isEmpty()) return null;

        try {
            int puntos = Integer.parseInt(puntosStr);
            CampoOpcionesMultiple campo = (CampoOpcionesMultiple) getClientProperty("opcionesMultiple");
            return campo.construirPregunta(1, descripcion, puntos);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
