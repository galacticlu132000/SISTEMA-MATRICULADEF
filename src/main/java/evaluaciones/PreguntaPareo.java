package evaluaciones;

import java.util.List;

public class PreguntaPareo extends Pregunta {

    private List<String> columna1;
    private List<String> columna2;
    private List<Integer> indicesRelacionados;

    public PreguntaPareo(int numero, String descripcion, int puntos,
                         List<String> columna1, List<String> columna2, List<Integer> indicesRelacionados) {
        super(numero, descripcion, puntos);
        this.columna1 = columna1;
        this.columna2 = columna2;
        this.indicesRelacionados = indicesRelacionados;
    }

    @Override
    public String getTipo() {
        return "Pareo";
    }

    @Override
    public boolean validarRespuesta(Object respuesta) {
        return respuesta instanceof List && respuesta.equals(indicesRelacionados);
    }

    public List<String> getColumna1() {
        return columna1;
    }

    public List<String> getColumna2() {
        return columna2;
    }

    public List<Integer> getIndicesRelacionados() {
        return indicesRelacionados;
    }
}
