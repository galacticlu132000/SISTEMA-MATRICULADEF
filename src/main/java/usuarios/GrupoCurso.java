package usuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GrupoCurso {
    private int idGrupo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Curso curso; // Composición: referencia al curso
    private List<Estudiante> estudiantes;


    public GrupoCurso(int idGrupo, LocalDate fechaInicio, LocalDate fechaFin, Curso curso) {
        if (idGrupo < 1) {
            throw new IllegalArgumentException("La identificación del grupo debe ser >= 1.");
        }
        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de finalización no puede ser anterior a la de inicio.");
        }
        if (curso == null) {
            throw new IllegalArgumentException("El curso no puede ser nulo.");
        }

        this.idGrupo = idGrupo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.curso = curso;
        this.estudiantes = new ArrayList<>();
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public Curso getCurso() {
        return curso;
    }

    public String getNombre() {
        return "Grupo " + idGrupo + " – " + curso.getnombreCurso();
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }


    @Override
    public String toString() {
        return getNombre() + " (" + fechaInicio + " → " + fechaFin + ")";
    }
}
