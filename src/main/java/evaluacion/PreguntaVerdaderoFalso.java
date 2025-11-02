package evaluacion;



public class PreguntaVerdaderoFalso extends Pregunta {
    private boolean respuestaCorrecta;

    public PreguntaVerdaderoFalso(int numero, String descripcion, int puntos, boolean respuestaCorrecta) {
        super(numero, descripcion, puntos);
        this.respuestaCorrecta = respuestaCorrecta;
    }

    @Override
    public String getTipo() {
        return "Verdadero/Falso";
    }

    public boolean isRespuestaCorrecta() { return respuestaCorrecta; }
}
