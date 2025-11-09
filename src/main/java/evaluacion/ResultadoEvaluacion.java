package evaluacion;
import usuarios.Estudiante;
import java.time.LocalDateTime;
import java.util.Map;
import java.io.Serializable;



public class ResultadoEvaluacion  implements Serializable{
    private Estudiante estudiante;
    private Evaluacion evaluacion;
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private Map<Integer, RespuestaPregunta> respuestas; // clave: número de pregunta
    private int puntosObtenidos;

    public ResultadoEvaluacion(Estudiante estudiante, Evaluacion evaluacion,
                               LocalDateTime inicio, LocalDateTime fin,
                               Map<Integer, RespuestaPregunta> respuestas,
                               int puntosObtenidos) {
        this.estudiante = estudiante;
        this.evaluacion = evaluacion;
        this.inicio = inicio;
        this.fin = fin;
        this.respuestas = respuestas;
        this.puntosObtenidos = puntosObtenidos;
    }

    public Estudiante getEstudiante() { return estudiante; }
    public Evaluacion getEvaluacion() { return evaluacion; }
    public LocalDateTime getInicio() { return inicio; }
    public LocalDateTime getFin() { return fin; }
    public Map<Integer, RespuestaPregunta> getRespuestas() { return respuestas; }
    public int getPuntosObtenidos() { return puntosObtenidos; }

    public int getPuntajeTotal() {
        return evaluacion.getPreguntas().stream().mapToInt(Pregunta::getPuntos).sum();
    }

    public int getCalificacionBase100() {
        int total = getPuntajeTotal();
        return total == 0 ? 0 : (int) ((puntosObtenidos * 100.0) / total);
    }
}
