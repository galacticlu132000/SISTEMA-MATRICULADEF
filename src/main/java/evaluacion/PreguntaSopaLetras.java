package evaluacion;




import evaluacion.GeneradorSopaLetras;
import java.util.List;

public class PreguntaSopaLetras extends Pregunta {
    private List<String> enunciados;
    private List<String> palabrasClave;
    private char[][] matrizLetras;

    public PreguntaSopaLetras(int numero, String descripcion, int puntos,
                              List<String> enunciados, List<String> palabrasClave) {
        super(numero, descripcion, puntos);

        if (enunciados.size() < 10 || palabrasClave.size() < 10) {
            throw new IllegalArgumentException("Debe haber al menos 10 enunciados y 10 palabras clave.");
        }

        if (enunciados.size() != palabrasClave.size()) {
            throw new IllegalArgumentException("La cantidad de enunciados y palabras clave debe coincidir.");
        }

        this.enunciados = enunciados;
        this.palabrasClave = palabrasClave;
        this.matrizLetras = GeneradorSopaLetras.generarMatriz(palabrasClave);
    }

    @Override
    public String getTipo() {
        return "Sopa de Letras";
    }

    public List<String> getEnunciados() { return enunciados; }
    public List<String> getPalabrasClave() { return palabrasClave; }
    public char[][] getMatrizLetras() { return matrizLetras; }

    public char[][] getMatriz() {
        return matrizLetras;
    }
}
