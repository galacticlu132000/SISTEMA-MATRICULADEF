package evaluacion;
import java.util.List;

public class PreguntaSeleccionMultiple extends Pregunta {
    private List<String> opciones;
    private List<String> indicesCorrectos;

    public PreguntaSeleccionMultiple(int numero, String descripcion, int puntos,
                                     List<String> opciones, List<String> indicesCorrectos) {
        super(numero, descripcion, puntos);
        this.opciones = opciones;
        this.indicesCorrectos = indicesCorrectos;
    }

    @Override
    public String getTipo() {
        return "Selección Múltiple";
    }

    public List<String> getOpciones() { return opciones; }
    public List<String> getIndicesCorrectos() { return indicesCorrectos; }
}
