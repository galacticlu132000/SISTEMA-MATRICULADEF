/*
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║                  SISTEMA DE MATRÍCULA Y CALIFICACIONES (TEC)               ║
 * ║ Módulo: usuarios                                                           ║
 * ║ Archivo: Curso.java                                                        ║
 * ║ Autoría: Lucía y Karla                                                     ║
 * ║ Propósito: Representa un curso con sus atributos, validaciones y grupos.   ║
 * ║ Dependencias: GrupoCurso.java                                              ║
 * ║ Versión: 1.0                                                               ║
 * ║ Última actualización: 2025-11-05                                           ║
 * ║ Notas: Incluye validaciones estrictas según especificación del proyecto.   ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */

package usuarios;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║                               CLASE: CURSO                                 ║
 * ║ Representa un curso académico con identificación, nombre, descripción,     ║
 * ║ modalidad, tipo, cantidad de estudiantes y grupos asociados.               ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 *
 * <p><b>Responsabilidades:</b></p>
 * <ul>
 *   <li>Validar atributos del curso según reglas establecidas.</li>
 *   <li>Gestionar grupos asociados al curso.</li>
 *   <li>Proveer representación textual clara.</li>
 * </ul>
 *
 * <p><b>Ejemplo de uso:</b></p>
 * <pre>{@code
 * Curso curso = new Curso(
 *     "INF001",
 *     "Programación Orientada a Objetos",
 *     "Curso introductorio sobre POO en Java",
 *     4, 3, 20, 70,
 *     Curso.Modalidad.PRESENCIAL,
 *     Curso.Tipo_Curso.TEORICO
 * );
 * curso.agregarGrupo(new GrupoCurso(1));
 * System.out.println(curso);
 * }</pre>
 *
 * @author Lucía y Karla
 * @version 1.0
 * @since 1.0
 */
public class Curso implements Serializable {

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                          ATRIBUTOS                                 ║
    // ╚════════════════════════════════════════════════════════════════════╝
    private String identificacionCurso;
    private String nombreCurso;
    private String descripcionCurso;
    private int horasDia;
    private int cantidadMinimaE;
    private int cantidadMaximaE;
    private int calificacionMinimaE;
    private Modalidad modalidad;
    private Tipo_Curso tipo_Curso;
    private List<GrupoCurso> grupos = new ArrayList<>();

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                          ENUMERACIONES                             ║
    // ╚════════════════════════════════════════════════════════════════════╝
    public enum Modalidad {
        PRESENCIAL,
        VIRTUAL_SINCRONICA,
        VIRTUAL_ASINCRONICA,
        VIRTUAL_HIBRIDA,
        SEMIPRESENCIAL
    }

    public enum Tipo_Curso {
        TEORICO,
        PRACTICO,
        TALLER,
        SEMINARIO
    }

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                          CONSTRUCTOR                              ║
    // ╚════════════════════════════════════════════════════════════════════╝

    /**
     * Crea una nueva instancia de {@code Curso} validando todos sus atributos.
     *
     * @param identificacionCurso Identificación única (exactamente 6 caracteres).
     * @param nombreCurso Nombre del curso (5–40 caracteres).
     * @param descripcionCurso Descripción (5–400 caracteres).
     * @param horasDia Cantidad de horas por día (1–8).
     * @param cantidadMinimaE Cantidad mínima de estudiantes (1–5).
     * @param cantidadMaximaE Cantidad máxima de estudiantes (≥ mínima y ≤ 20).
     * @param calificacionMinimaE Calificación mínima para aprobar (0–100).
     * @param modalidad Modalidad del curso.
     * @param tipo_Curso Tipo de curso.
     * @throws IllegalArgumentException Si algún parámetro no cumple las reglas.
     */
    public Curso(String identificacionCurso,
                 String nombreCurso,
                 String descripcionCurso,
                 int horasDia,
                 int cantidadMinimaE,
                 int cantidadMaximaE,
                 int calificacionMinimaE,
                 Modalidad modalidad,
                 Tipo_Curso tipo_Curso) {

        if (identificacionCurso == null || identificacionCurso.length() != 6) {
            throw new IllegalArgumentException("La identificación del curso debe tener exactamente 6 caracteres.");
        }
        if (nombreCurso == null || nombreCurso.length() < 5 || nombreCurso.length() > 40) {
            throw new IllegalArgumentException("El nombre del curso debe tener entre 5 y 40 caracteres.");
        }
        if (descripcionCurso == null || descripcionCurso.length() < 5 || descripcionCurso.length() > 400) {
            throw new IllegalArgumentException("La descripción del curso debe tener entre 5 y 400 caracteres.");
        }
        if (calificacionMinimaE < 0 || calificacionMinimaE > 100) {
            throw new IllegalArgumentException("La calificación mínima debe estar entre 0 y 100.");
        }
        if (cantidadMinimaE < 1 || cantidadMinimaE > 5) {
            throw new IllegalArgumentException("La cantidad mínima de estudiantes debe estar entre 1 y 5.");
        }
        if (cantidadMaximaE < cantidadMinimaE || cantidadMaximaE > 20) {
            throw new IllegalArgumentException("La cantidad máxima debe estar entre la mínima y 20.");
        }
        if (horasDia < 1 || horasDia > 8) {
            throw new IllegalArgumentException("Las horas por día deben estar entre 1 y 8.");
        }

        this.identificacionCurso = identificacionCurso;
        this.nombreCurso = nombreCurso;
        this.descripcionCurso = descripcionCurso;
        this.horasDia = horasDia;
        this.cantidadMinimaE = cantidadMinimaE;
        this.cantidadMaximaE = cantidadMaximaE;
        this.calificacionMinimaE = calificacionMinimaE;
        this.modalidad = modalidad;
        this.tipo_Curso = tipo_Curso;
    }

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                          GETTERS & SETTERS                         ║
    // ╚════════════════════════════════════════════════════════════════════╝

    public String getIdentificacionCurso() { return identificacionCurso; }
    public void setIdentificacionCurso(String identificacionCurso) { this.identificacionCurso = identificacionCurso; }

    public String getNombreCurso() { return nombreCurso; }
    public void setNombreCurso(String nombreCurso) { this.nombreCurso = nombreCurso; }

    public String getDescripcionCurso() { return descripcionCurso; }
    public void setDescripcionCurso(String descripcionCurso) { this.descripcionCurso = descripcionCurso; }

    public int getHorasDia() { return horasDia; }
    public void setHorasDia(int horasDia) { this.horasDia = horasDia; }

    public int getCantidadMinimaE() { return cantidadMinimaE; }
    public void setCantidadMinimaE(int cantidadMinimaE) { this.cantidadMinimaE = cantidadMinimaE; }

    public int getCantidadMaximaE() { return cantidadMaximaE; }
    public void setCantidadMaximaE(int cantidadMaximaE) { this.cantidadMaximaE = cantidadMaximaE; }

    public int getCalificacionMinimaE() { return calificacionMinimaE; }
    public void setCalificacionMinimaE(int calificacionMinimaE) { this.calificacionMinimaE = calificacionMinimaE; }

    public Modalidad getModalidad() { return modalidad; }
    public void setModalidad(Modalidad modalidad) { this.modalidad = modalidad; }

    public Tipo_Curso getTipoCurso() { return tipo_Curso; }
    public void setTipoCurso(Tipo_Curso tipoCurso) { this.tipo_Curso = tipoCurso; }

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                          GESTIÓN DE GRUPOS                         ║
    // ╚════════════════════════════════════════════════════════════════════╝

    /**
     * Devuelve la lista de grupos asociados al curso.
     *
     * @return Lista de {@code GrupoCurso}.
     */
    public List<GrupoCurso> getGrupos() { return grupos; }

    /**
     * Agrega un nuevo grupo al curso.
     *
     * @param grupo Grupo a agregar.
     */
    public void agregarGrupo(GrupoCurso grupo) { grupos.add(grupo); }

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                          REPRESENTACIÓN                            ║
    // ╚════════════════════════════════════════════════════════════════════╝

    /**
     * Devuelve una representación textual del curso con todos sus atributos.
     *
     * @return Cadena con nombre, identificación, descripción, horas, cantidades,
     *         calificación mínima, modalidad y tipo.
     */
    @Override
    public String toString() {
        return "Curso: " + nombreCurso +
                " | Identificación: " + identificacionCurso +
                " | Descripción: " + descripcionCurso +
                " | Horas por día: " + horasDia +
                " | Mínimo estudiantes: " + cantidadMinimaE +
                " | Máximo estudiantes: " + cantidadMaximaE +
                " | Calificación mínima: " + calificacionMinimaE +
                " | Modalidad: " + modalidad +
                " | Tipo: " + tipo_Curso;
    }
}
