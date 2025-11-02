package evaluacion;


import java.util.List;

public class PreguntaSeleccionUnica extends Pregunta {
    private List<String> opciones;
    private String indiceCorrecto;

    public PreguntaSeleccionUnica(int numero, String descripcion, int puntos,
                                  List<String> opciones, String indiceCorrecto) {
        super(numero, descripcion, puntos);
        this.opciones = opciones;
        this.indiceCorrecto = indiceCorrecto;
    }

    @Override
    public String getTipo() {
        return "Selección Única";
    }

    public List<String> getOpciones() { return opciones; }
    public String getIndiceCorrecto() { return indiceCorrecto; }
}
