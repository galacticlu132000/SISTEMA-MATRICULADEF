package evaluaciones;

import java.util.List;

public class PreguntaSopaDeLetras extends Pregunta {

    private List<String> palabrasClave;
    private List<String> enunciados;
    private char[][] cuadrícula;

    public PreguntaSopaDeLetras(int numero, String descripcion, int puntos,
                                List<String> palabrasClave, List<String> enunciados, char[][] cuadrícula) {
        super(numero, descripcion, puntos);
        this.palabrasClave = palabrasClave;
        this.enunciados = enunciados;
        this.cuadrícula = cuadrícula;
    }

    @Override
    public String getTipo() {
        return "Sopa de Letras";
    }

    @Override
    public boolean validarRespuesta(Object respuesta) {
        // Validación personalizada según coordenadas o palabras encontradas
        return true; // Se implementa según lógica visual
    }

    public List<String> getPalabrasClave() {
        return palabrasClave;
    }

    public List<String> getEnunciados() {
        return enunciados;
    }

    public char[][] getCuadricula() {
        return cuadrícula;
    }
}
