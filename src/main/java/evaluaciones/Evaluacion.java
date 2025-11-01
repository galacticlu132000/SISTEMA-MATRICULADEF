package evaluaciones;

import usuarios.GrupoCurso;
import usuarios.Profesor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🎓 Evaluacion                                                             ║
 * ║                                                                            ║
 * ║ Representa una evaluación diseñada por un profesor.                       ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class Evaluacion {

    private int idEvaluacion;
    private String nombreEvaluacion;
    private String instruccionesGenerales;
    private List<String> objetivos;
    private int duracionMinutos;
    private boolean preguntasAleatorias;
    private boolean opcionesAleatorias;

    private Profesor profesor;
    private List<Pregunta> preguntas;

    private GrupoCurso grupoAsociado;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  CONSTRUCTOR                              ║
    // ╚════════════════════════════════════════════════════════════╝
    public Evaluacion(String nombreEvaluacion, String instruccionesGenerales, List<String> objetivos,
                      int duracionMinutos, boolean preguntasAleatorias, boolean opcionesAleatorias,
                      Profesor profesor, List<Pregunta> preguntas) {
        this.nombreEvaluacion = nombreEvaluacion;
        this.instruccionesGenerales = instruccionesGenerales;
        this.objetivos = objetivos;
        this.duracionMinutos = duracionMinutos;
        this.preguntasAleatorias = preguntasAleatorias;
        this.opcionesAleatorias = opcionesAleatorias;
        this.profesor = profesor;
        this.preguntas = preguntas;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  MÉTODOS DE ASOCIACIÓN                    ║
    // ╚════════════════════════════════════════════════════════════╝
    public void asociarAGrupo(GrupoCurso grupo, LocalDateTime inicio) {
        this.grupoAsociado = grupo;
        this.fechaInicio = inicio;
        this.fechaFin = inicio.plusMinutes(duracionMinutos);
    }

    public boolean puedeDesasociarse(LocalDateTime ahora) {
        return fechaInicio != null && fechaInicio.isAfter(ahora);
    }

    public void desasociarGrupo() {
        this.grupoAsociado = null;
        this.fechaInicio = null;
        this.fechaFin = null;
    }

    public boolean estaAsociadaAGrupo() {
        return grupoAsociado != null;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  GETTERS Y SETTERS                        ║
    // ╚════════════════════════════════════════════════════════════╝
    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public String getNombreEvaluacion() {
        return nombreEvaluacion;
    }

    public String getInstruccionesGenerales() {
        return instruccionesGenerales;
    }

    public List<String> getObjetivos() {
        return objetivos;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public boolean isPreguntasAleatorias() {
        return preguntasAleatorias;
    }

    public boolean isOpcionesAleatorias() {
        return opcionesAleatorias;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public GrupoCurso getGrupoAsociado() {
        return grupoAsociado;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
}
