package control;
import evaluacion.ResultadoEvaluacion;
import usuarios.Estudiante;
import evaluacion.Evaluacion;

import java.util.*;

public class GestorResultados {
    private static GestorResultados instancia;
    private final List<ResultadoEvaluacion> resultados = new ArrayList<>();

    private GestorResultados() {}

    public static GestorResultados getInstancia() {
        if (instancia == null) {
            instancia = new GestorResultados();
        }
        return instancia;
    }

    public void registrarResultado(ResultadoEvaluacion resultado) {
        resultados.add(resultado);
    }

    public List<ResultadoEvaluacion> listarPorEvaluacion(Evaluacion evaluacion) {
        List<ResultadoEvaluacion> lista = new ArrayList<>();
        for (ResultadoEvaluacion r : resultados) {
            if (r.getEvaluacion().equals(evaluacion)) {
                lista.add(r);
            }
        }
        return lista;
    }

    public List<ResultadoEvaluacion> listarPorEstudiante(Estudiante estudiante) {
        List<ResultadoEvaluacion> lista = new ArrayList<>();
        for (ResultadoEvaluacion r : resultados) {
            if (r.getEstudiante().equals(estudiante)) {
                lista.add(r);
            }
        }
        return lista;
    }
}
