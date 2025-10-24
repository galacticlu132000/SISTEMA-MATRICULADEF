package clases;
import java.util.ArrayList;

public class Curso {
    private String identificacionCurso;
    private String nombreCurso;
    private String descripcionCurso;
    private int horasDia;
    private int cantidadMinimaE;
    private int cantidadMaximaE;
    private int calificacionMinimaE;
    private Modalidad modalidad;
    private Tipo_Curso tipo_Curso;


    public enum Modalidad{
        PRESENCIAL,
        VIRTUAL_SINCRONICA,
        VIRTUAL_ASINCRONICA,
        VIRTUAL_HIBRIDA,
        SEMIPRESENCIAL

    }

    public enum Tipo_Curso{
        TEORICO,
        PRACTICO,
        TALLER,
        SEMINARIO
    }

    public Curso(String identificacionCurso,
                 String nombreCurso,
                 String descripcionCurso,
                 int horasDia,
                 int cantidadMinimaE,
                 int cantidadMaximaE,
                 int calificacionMinimaE,
                 int modalidad,
                 int tipo_Curso) {

        if (identificacionCurso == null || identificacionCurso.length() != 6) {
            throw new IllegalArgumentException("La identificación del curso debe tener exactamente 6 caracteres.");
        }
        if (nombreCurso == null || nombreCurso.length() < 5 || nombreCurso.length() > 40) {
            throw new IllegalArgumentException("El nombre del curso debe tener entre 5 y 40 caracteres.");
        }
        if (descripcionCurso== null || descripcionCurso.length()<5|| descripcionCurso.length()>400){
            throw new IllegalArgumentException("La descripción del curso debe tener entre 5 a 400 caracteres.");

        }
        if (calificacionMinimaE < 0 || calificacionMinimaE > 100) {
            throw new IllegalArgumentException("La calificación mínima debe estar entre 0 y 100.");
        }

        if (cantidadMinimaE < 1 || cantidadMinimaE>5){
            throw new IllegalArgumentException("La cantidad mínima de estudiantes es de 1 a 5.");
        }
        if (cantidadMaximaE < cantidadMinimaE || cantidadMaximaE > 20) {
            throw new IllegalArgumentException("La cantidad máxima de estudiantes debe estar entre la cantidad mínima y 20.");
        }
        if (horasDia < 1 || horasDia > 8) {
            throw new IllegalArgumentException("Las horas por día deben estar entre 1 y 8.");
        }
        if (modalidad < 1 || modalidad > 5) {
            throw new IllegalArgumentException("Modalidad inválida. Usa un número entre 1 y 5.");
        } else {
            switch (modalidad) {
                case 1: this.modalidad = Modalidad.PRESENCIAL; break;
                case 2: this.modalidad = Modalidad.VIRTUAL_SINCRONICA; break;
                case 3: this.modalidad = Modalidad.VIRTUAL_ASINCRONICA; break;
                case 4: this.modalidad = Modalidad.VIRTUAL_HIBRIDA; break;
                case 5: this.modalidad = Modalidad.SEMIPRESENCIAL; break;
            }
        }
        if (tipo_Curso < 1 || tipo_Curso > 4) {
            throw new IllegalArgumentException("Tipo de curso inválido. Debe ser un número entre 1 y 4.");
        } else {
            switch (tipo_Curso) {
                case 1: this.tipo_Curso = Tipo_Curso.TEORICO; break;
                case 2: this.tipo_Curso = Tipo_Curso.PRACTICO; break;
                case 3: this.tipo_Curso = Tipo_Curso.TALLER; break;
                case 4: this.tipo_Curso = Tipo_Curso.SEMINARIO; break;
            }
        }
        this.identificacionCurso = identificacionCurso;
        this.nombreCurso = nombreCurso;
        this.descripcionCurso = descripcionCurso;
        this.horasDia = horasDia;
        this.cantidadMinimaE = cantidadMinimaE;
        this.cantidadMaximaE = cantidadMaximaE;
        this.calificacionMinimaE = calificacionMinimaE;





    }
    public String getIdentificacionCurso() {
        return identificacionCurso;
    }
    @Override
    public String toString() {
        return "Curso: " + nombreCurso +
                " | Identificación: " + identificacionCurso +
                " | Descripción: " + descripcionCurso +
                " | Cantidad de horas por día: " + horasDia +
                " | Cantidad mínima de estudiantes: " + cantidadMinimaE +
                " | Cantidad máxima de estudiantes: " + cantidadMaximaE +
                " | Calificación mínima para aprobar: " + calificacionMinimaE +
                " | Modalidad: " + modalidad +
                " | Tipo: " + tipo_Curso;
    }

}



