package evaluacion;




import usuarios.Curso;
import usuarios.GrupoCurso;
import usuarios.Profesor;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Evaluacion implements Serializable {
    private int idEvaluacion;
    private String nombre;
    private String instrucciones;
    private List<String> objetivos;
    private int duracionMinutos;
    private boolean preguntasAleatorias;
    private boolean opcionesAleatorias;
    private List<Pregunta> preguntas;
    private LocalDateTime fechaFin;

    private Profesor profesor;
    private GrupoCurso grupoAsociado;
    private LocalDateTime fechaInicio;
    private Curso curso;
    private static final long serialVersionUID = 1L;
    private Map<GrupoCurso, LocalDateTime> fechasInicio = new HashMap<>();
    private Map<GrupoCurso, LocalDateTime> fechasFin = new HashMap<>();


    public Evaluacion(String nombre, String instrucciones, List<String> objetivos,
                      int duracionMinutos, boolean preguntasAleatorias,
                      boolean opcionesAleatorias, List<Pregunta> preguntas,
                      Profesor profesor) {
        this.nombre = nombre;
        this.instrucciones = instrucciones;
        this.objetivos = objetivos;
        this.duracionMinutos = duracionMinutos;
        this.preguntasAleatorias = preguntasAleatorias;
        this.opcionesAleatorias = opcionesAleatorias;
        this.preguntas = preguntas;
        this.profesor = profesor;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🔧 Métodos requeridos                     ║
    // ╚════════════════════════════════════════════════════════════╝
    public void setIdEvaluacion(int id) { this.idEvaluacion = id; }
    public int getIdEvaluacion() { return idEvaluacion; }

    public Profesor getProfesor() { return profesor; }

    public Curso getCurso() {
        return curso;
    }


    public boolean estaAsociadaAGrupo() {
        return grupoAsociado != null;
    }

    public void asociarAGrupo(GrupoCurso grupo, LocalDateTime inicio, LocalDateTime fin) {
        this.grupoAsociado = grupo;
        this.fechaInicio = inicio;
        this.fechaFin = fin;
    }

    public void desasociarGrupo() {
        this.grupoAsociado = null;
        this.fechaInicio = null;
    }


    public boolean puedeDesasociarse(LocalDateTime ahora) {
        return fechaInicio != null && fechaInicio.isAfter(ahora);
    }

    public GrupoCurso getGrupoAsociado() {
        return grupoAsociado;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  Otros getters opcionales                 ║
    // ╚════════════════════════════════════════════════════════════╝
    public String getNombre() { return nombre; }
    public String getInstrucciones() { return instrucciones; }
    public List<String> getObjetivos() { return objetivos; }
    public int getDuracionMinutos() { return duracionMinutos; }
    public boolean isPreguntasAleatorias() { return preguntasAleatorias; }
    public boolean isOpcionesAleatorias() { return opcionesAleatorias; }
    public List<Pregunta> getPreguntas() { return preguntas; }

    public void setNombre(String nombre) {
        this.nombre=nombre;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones=instrucciones;
    }

    public void setDuracionMinutos(int duracion) {
        this.duracionMinutos=duracion;
    }

    public void setPreguntasAleatorias(boolean selected) {
        this.preguntasAleatorias=selected;
    }

    public void setOpcionesAleatorias(boolean selected) {
        this.opcionesAleatorias=selected;
    }

    public void setObjetivos(List<String> objetivos) {
        this.objetivos=objetivos;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    public LocalDateTime getInicio(GrupoCurso grupo) {
        return fechasInicio.get(grupo);
    }

    public LocalDateTime getFin(GrupoCurso grupo) {
        return fechasFin.get(grupo);
    }


    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public String getFechaInicioTexto() {
        return fechaInicio.toLocalDate().toString();
    }

    public String getHoraInicioTexto() {
        return fechaInicio.toLocalTime().toString();
    }

    public String getFechaFinTexto() {
        return fechaFin.toLocalDate().toString();
    }

    public String getHoraFinTexto() {
        return fechaFin.toLocalTime().toString();
    }

}
