package evaluacion;
import java.util.List;

public class RespuestaPregunta {
    private int numeroPregunta;
    private List<String> opcionesSeleccionadas;
    private int puntosObtenidos;

    public RespuestaPregunta(int numeroPregunta, List<String> opcionesSeleccionadas, int puntosObtenidos) {
        this.numeroPregunta = numeroPregunta;
        this.opcionesSeleccionadas = opcionesSeleccionadas;
        this.puntosObtenidos = puntosObtenidos;
    }

    public int getNumeroPregunta() { return numeroPregunta; }
    public List<String> getOpcionesSeleccionadas() { return opcionesSeleccionadas; }
    public int getPuntosObtenidos() { return puntosObtenidos; }
}
