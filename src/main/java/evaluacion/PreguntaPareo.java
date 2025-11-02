package evaluacion;



import java.util.List;
import java.util.Map;

public class PreguntaPareo extends Pregunta {
    private List<String> columna1;
    private List<String> columna2;
    private Map<String, String> relaciones; // índice de columna1 → índice de columna2

    public PreguntaPareo(int numero, String descripcion, int puntos,
                         List<String> columna1, List<String> columna2,
                         Map<String, String> relaciones) {
        super(numero, descripcion, puntos);
        this.columna1 = columna1;
        this.columna2 = columna2;
        this.relaciones = relaciones;
    }

    @Override
    public String getTipo() {
        return "Pareo";
    }

    public List<String> getColumna1() { return columna1; }
    public List<String> getColumna2() { return columna2; }
    public Map<String, String> getRelaciones() { return relaciones; }
}
