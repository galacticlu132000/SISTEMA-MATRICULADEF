package evaluaciones;

import java.util.List;

public class PreguntaSeleccionMultiple extends Pregunta {

    private List<String> opciones;
    private List<Integer> indicesCorrectos;

    public PreguntaSeleccionMultiple(int numero, String descripcion, int puntos,
                                     List<String> opciones, List<Integer> indicesCorrectos) {
        super(numero, descripcion, puntos);
        this.opciones = opciones;
        this.indicesCorrectos = indicesCorrectos;
    }

    @Override
    public String getTipo() {
        return "Selección Múltiple";
    }

    @Override
    public boolean validarRespuesta(Object respuesta) {
        if (!(respuesta instanceof List)) return false;
        List<Integer> seleccion = (List<Integer>) respuesta;
        return seleccion.containsAll(indicesCorrectos) && indicesCorrectos.containsAll(seleccion);
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public List<Integer> getIndicesCorrectos() {
        return indicesCorrectos;
    }
}
