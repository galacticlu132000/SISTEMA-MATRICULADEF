/*
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║                  SISTEMA DE MATRÍCULA Y CALIFICACIONES (TEC)               ║
 * ║ Módulo: usuarios                                                           ║
 * ║ Archivo: GrupoCurso.java                                                   ║
 * ║ Autoría: Lucía y Karla                                                     ║
 * ║ Propósito: Representa un grupo asociado a un curso con fechas y estudiantes║
 * ║ Dependencias: Curso.java, Estudiante.java                                  ║
 * ║ Versión: 1.0                                                               ║
 * ║ Última actualización: 2025-11-05                                           ║
 * ║ Notas: Valida coherencia de fechas y capacidad de estudiantes.             ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */

package usuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║                               CLASE: GRUPOCURSO                            ║
 * ║ Representa un grupo dentro de un curso, con fechas de inicio/fin y         ║
 * ║ estudiantes matriculados.                                                  ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 *
 * <p><b>Responsabilidades:</b></p>
 * <ul>
 *   <li>Validar coherencia de fechas y número de grupo.</li>
 *   <li>Asociar estudiantes al grupo respetando capacidad máxima del curso.</li>
 *   <li>Proveer representación textual clara.</li>
 * </ul>
 *
 * <p><b>Ejemplo de uso:</b></p>
 * <pre>{@code
 * Curso curso = new Curso(...);
 * GrupoCurso grupo = new GrupoCurso(1, LocalDate.now(), LocalDate.now().plusMonths(3), curso);
 * grupo.agregarEstudiante(new Estudiante(...));
 * System.out.println(grupo);
 * }</pre>
 *
 * @author Lucía y Karla
 * @version 1.0
 * @since 1.0
 */
public class GrupoCurso {

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                          ATRIBUTOS                                 ║
    // ╚════════════════════════════════════════════════════════════════════╝
    private int idGrupo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Curso curso; // Composición: referencia al curso
    private List<Estudiante> estudiantes;


    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                          CONSTRUCTOR                              ║
    // ╚════════════════════════════════════════════════════════════════════╝

    /**
     * Crea una nueva instancia de {@code GrupoCurso}.
     *
     * @param idGrupo Identificación del grupo (≥ 1).
     * @param fechaInicio Fecha de inicio del grupo.
     * @param fechaFin Fecha de finalización (≥ fechaInicio).
     * @param curso Curso asociado al grupo (no nulo).
     * @throws IllegalArgumentException Si los parámetros no cumplen las reglas.
     */
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

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                          GETTERS                                   ║
    // ╚════════════════════════════════════════════════════════════════════╝

    public int getIdGrupo() { return idGrupo; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public Curso getCurso() { return curso; }

    /**
     * Devuelve el nombre del grupo junto con el curso.
     *
     * @return Nombre descriptivo del grupo.
     */
    public String getNombre() {
        return "Grupo " + idGrupo + " – " + curso.getNombreCurso();
    }

    public List<Estudiante> getEstudiantes() { return estudiantes; }

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                          GESTIÓN DE ESTUDIANTES                    ║
    // ╚════════════════════════════════════════════════════════════════════╝

    /**
     * Agrega un estudiante al grupo si hay capacidad disponible.
     *
     * @param estudiante Estudiante a matricular.
     * @throws IllegalStateException Si el grupo ya alcanzó la capacidad máxima.
     */
    public void agregarEstudiante(Estudiante estudiante) {
        if (estudiantes.contains(estudiante)) {
            System.out.println("⚠️ El estudiante ya está en este grupo.");
            return;
        }

        if (estudiantes.size() >= curso.getCantidadMaximaE()) {
            throw new IllegalStateException("❌ El grupo ya alcanzó la capacidad máxima de estudiantes.");
        }

        estudiantes.add(estudiante);

        // 🔁 Matrícula bidireccional: también actualiza al estudiante
        if (!estudiante.estaMatriculadoEn(curso.getIdentificacionCurso(), idGrupo)) {
            estudiante.matricularEnGrupo(this);
        }

        System.out.println("✅ Estudiante agregado al grupo: " + estudiante.getNombre());
    }

    public boolean eliminarEstudiante(Estudiante estudiante) {
        return estudiantes.remove(estudiante);
    }
    public boolean contieneEstudiante(String idEstudiante) {
        return estudiantes.stream()
                .anyMatch(e -> e.getIdentificacionPersonal().equals(idEstudiante));
    }
    /**
     * Devuelve una lista con los IDs de los estudiantes matriculados.
     *
     * @return Lista de identificaciones personales.
     */
    public List<String> getEstudiantesMatriculados() {
        List<String> ids = new ArrayList<>();
        for (Estudiante estudiante : estudiantes) {
            ids.add(estudiante.getIdentificacionPersonal());
        }
        return ids;
    }




    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                          REPRESENTACIÓN                            ║
    // ╚════════════════════════════════════════════════════════════════════╝

    /**
     * Devuelve una representación textual del grupo.
     *
     * @return Cadena con nombre del grupo y fechas.
     */
    @Override
    public String toString() {
        return getNombre() + " (" + fechaInicio + " → " + fechaFin + ")";
    }
}
