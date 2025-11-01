package evaluaciones;
import java.util.List;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🌟 Pregunta (abstracta)                                                    ║
 * ║                                                                            ║
 * ║ Clase base para todos los tipos de preguntas.                             ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public abstract class Pregunta {

    protected int numero;
    protected String descripcion;
    protected int puntos;

    public Pregunta(int numero, String descripcion, int puntos) {
        this.numero = numero;
        this.descripcion = descripcion;
        this.puntos = puntos;
    }

    public int getNumero() {
        return numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPuntos() {
        return puntos;
    }

    public abstract String getTipo();

    public abstract boolean validarRespuesta(Object respuesta);

    // ✅ Nuevo método para mostrar detalles específicos
    public abstract String representacionDetallada();
}
