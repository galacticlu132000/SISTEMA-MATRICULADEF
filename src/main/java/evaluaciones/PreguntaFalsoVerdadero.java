package evaluaciones;

public class PreguntaFalsoVerdadero extends Pregunta {

    private boolean respuestaCorrecta;

    public PreguntaFalsoVerdadero(int numero, String descripcion, int puntos, boolean respuestaCorrecta) {
        super(numero, descripcion, puntos);
        this.respuestaCorrecta = respuestaCorrecta;
    }

    @Override
    public String getTipo() {
        return "Falso/Verdadero";
    }

    @Override
    public boolean validarRespuesta(Object respuesta) {
        return respuesta instanceof Boolean && (boolean) respuesta == respuestaCorrecta;
    }

    public boolean getRespuestaCorrecta() {
        return respuestaCorrecta;
    }
}
