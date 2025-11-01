package gui.evaluacion.seleccionu;



import evaluaciones.Pregunta;
import gui.evaluacion.util.PanelPreguntaBasica;

/**
 * Panel específico para preguntas de selección única.
 */
public class PanelSeleccionUnica extends PanelPreguntaBasica {

    public PanelSeleccionUnica() {
        super("Selección Única");
        add(new CampoOpcionesUnica(this));
    }

    @Override
    public Pregunta crearPreguntaFinal() {
        String descripcion = campoDescripcion.getText().trim();
        String puntosStr = campoPuntos.getText().trim();
        if (descripcion.isEmpty() || puntosStr.isEmpty()) return null;

        try {
            int puntos = Integer.parseInt(puntosStr);
            CampoOpcionesUnica campo = (CampoOpcionesUnica) getClientProperty("opcionesUnica");
            return campo.construirPregunta(1, descripcion, puntos); // El número se asigna luego
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
