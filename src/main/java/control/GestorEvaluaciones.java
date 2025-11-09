package control;

import evaluacion.Evaluacion;
import usuarios.GrupoCurso;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorEvaluaciones {

    private static GestorEvaluaciones instancia;
    private List<Evaluacion> listaEvaluaciones;

    private GestorEvaluaciones() {
        listaEvaluaciones = new ArrayList<>();
    }

    public static GestorEvaluaciones getInstancia() {
        if (instancia == null) {
            instancia = new GestorEvaluaciones();
        }
        return instancia;
    }

    public void registrarEvaluacion(Evaluacion evaluacion) {
        listaEvaluaciones.add(evaluacion);
    }


    public List<Evaluacion> obtenerEvaluacionesPorCurso(String idCurso) {
        return listaEvaluaciones.stream()
                .filter(e -> e.getCurso() != null && e.getCurso().getIdentificacionCurso().equals(idCurso))
                .collect(Collectors.toList());
    }


    public List<Evaluacion> getTodasLasEvaluaciones() {
        return listaEvaluaciones;
    }
    public boolean asociarEvaluacionAGrupo(int idEvaluacion, GrupoCurso grupo, LocalDateTime inicio, LocalDateTime fin) {
        for (Evaluacion e : listaEvaluaciones) {
            if (e.getIdEvaluacion() == idEvaluacion) {
                e.asociarAGrupo(grupo, inicio, fin);
                return true;
            }
        }
        return false;
    }

}

