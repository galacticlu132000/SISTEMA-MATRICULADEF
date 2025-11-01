package control;

import evaluaciones.Evaluacion;
import usuarios.Profesor;
import usuarios.GrupoCurso;

import java.time.LocalDateTime;
import java.util.*;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🧠 GestorEvaluaciones                                                      ║
 * ║                                                                            ║
 * ║ Controlador lógico para manejar evaluaciones:                             ║
 * ║ - CRUD de evaluaciones                                                    ║
 * ║ - Asociación con grupos y profesores                                      ║
 * ║ - Validaciones de estado y restricciones                                  ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class GestorEvaluaciones {

    private static GestorEvaluaciones instancia;
    private final Map<Integer, Evaluacion> evaluaciones = new HashMap<>();
    private int contadorID = 1;

    private GestorEvaluaciones() {}

    public static GestorEvaluaciones getInstancia() {
        if (instancia == null) {
            instancia = new GestorEvaluaciones();
        }
        return instancia;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  ➕ CREAR EVALUACIÓN                        ║
    // ╚════════════════════════════════════════════════════════════╝
    public int registrarEvaluacion(Evaluacion evaluacion) {
        evaluacion.setIdEvaluacion(contadorID++);
        evaluaciones.put(evaluacion.getIdEvaluacion(), evaluacion);
        return evaluacion.getIdEvaluacion();
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🔍 CONSULTAR EVALUACIÓN                   ║
    // ╚════════════════════════════════════════════════════════════╝
    public Evaluacion consultarEvaluacion(int id) {
        return evaluaciones.get(id);
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  ✏️ MODIFICAR EVALUACIÓN                   ║
    // ╚════════════════════════════════════════════════════════════╝
    public boolean modificarEvaluacion(Evaluacion evaluacionModificada) {
        int id = evaluacionModificada.getIdEvaluacion();
        Evaluacion original = evaluaciones.get(id);
        if (original != null && !original.estaAsociadaAGrupo()) {
            evaluaciones.put(id, evaluacionModificada);
            return true;
        }
        return false;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🗑️ ELIMINAR EVALUACIÓN                    ║
    // ╚════════════════════════════════════════════════════════════╝
    public boolean eliminarEvaluacion(int id) {
        Evaluacion evaluacion = evaluaciones.get(id);
        if (evaluacion != null && !evaluacion.estaAsociadaAGrupo()) {
            evaluaciones.remove(id);
            return true;
        }
        return false;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  📋 LISTAR TODAS                           ║
    // ╚════════════════════════════════════════════════════════════╝
    public List<Evaluacion> listarTodas() {
        return new ArrayList<>(evaluaciones.values());
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  📚 FILTRAR POR PROFESOR                   ║
    // ╚════════════════════════════════════════════════════════════╝
    public List<Evaluacion> listarEvaluacionesPorProfesor(Profesor profesor) {
        List<Evaluacion> resultado = new ArrayList<>();
        for (Evaluacion e : evaluaciones.values()) {
            if (e.getProfesor().equals(profesor)) {
                resultado.add(e);
            }
        }
        return resultado;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🔗 ASOCIAR A GRUPO                        ║
    // ╚════════════════════════════════════════════════════════════╝
    public boolean asociarEvaluacionAGrupo(int idEvaluacion, GrupoCurso grupo, LocalDateTime inicio) {
        Evaluacion evaluacion = evaluaciones.get(idEvaluacion);
        if (evaluacion != null && !evaluacion.estaAsociadaAGrupo()) {
            evaluacion.asociarAGrupo(grupo, inicio);
            return true;
        }
        return false;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🔓 DESASOCIAR DE GRUPO                    ║
    // ╚════════════════════════════════════════════════════════════╝
    public boolean desasociarEvaluacion(int idEvaluacion, LocalDateTime ahora) {
        Evaluacion evaluacion = evaluaciones.get(idEvaluacion);
        if (evaluacion != null && evaluacion.puedeDesasociarse(ahora)) {
            evaluacion.desasociarGrupo();
            return true;
        }
        return false;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🔍 FILTRAR POR GRUPO                      ║
    // ╚════════════════════════════════════════════════════════════╝
    public List<Evaluacion> listarEvaluacionesPorGrupo(GrupoCurso grupo) {
        List<Evaluacion> resultado = new ArrayList<>();
        for (Evaluacion e : evaluaciones.values()) {
            if (e.getGrupoAsociado() != null && e.getGrupoAsociado().equals(grupo)) {
                resultado.add(e);
            }
        }
        return resultado;
    }
}
