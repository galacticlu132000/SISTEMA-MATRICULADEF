package evaluaciones;

import java.util.List;

public class PreguntaSeleccionUnica extends Pregunta {

    private List<String> opciones;
    private int indiceCorrecto;

    public PreguntaSeleccionUnica(int numero, String descripcion, int puntos,
                                  List<String> opciones, int indiceCorrecto) {
        super(numero, descripcion, puntos);
        this.opciones = opciones;
        this.indiceCorrecto = indiceCorrecto;
    }

    @Override
    public String getTipo() {
        return "Selección Única";
    }

    @Override
    public boolean validarRespuesta(Object respuesta) {
        return respuesta instanceof Integer && (int) respuesta == indiceCorrecto;
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public int getIndiceCorrecto() {
        return indiceCorrecto;
    }
}
