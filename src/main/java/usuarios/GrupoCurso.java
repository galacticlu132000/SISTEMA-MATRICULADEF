package usuarios;
import java.time.LocalDate;
public class GrupoCurso {
    private int idGrupo; // empieza en 1
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public GrupoCurso(int idGrupo, LocalDate fechaInicio, LocalDate fechaFin) {
        if (idGrupo < 1) {
            throw new IllegalArgumentException("La identificación del grupo debe ser >= 1.");
        }
        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de finalización no puede ser anterior a la de inicio.");
        }
        this.idGrupo = idGrupo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
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

    @Override
    public String toString() {
        return "Grupo " + idGrupo + " (" + fechaInicio + " → " + fechaFin + ")";
    }

}
