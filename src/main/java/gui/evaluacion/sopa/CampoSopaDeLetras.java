package gui.evaluacion.sopa;
import evaluaciones.PreguntaSopaDeLetras;
import gui.evaluacion.sopa.GeneradorSopaDeLetras;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🧠 CampoSopa                                                               ║
 * ║                                                                            ║
 * ║ Panel para definir enunciados y palabras clave de una sopa de letras.     ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class CampoSopaDeLetras extends JPanel {

    private final List<JTextField> camposEnunciado = new ArrayList<>();
    private final List<JTextField> camposPalabra = new ArrayList<>();

    public CampoSopaDeLetras(JPanel contenedor) {
        setLayout(new GridLayout(5, 1, 5, 5));
        setOpaque(false);
        setBorder(BorderFactory.createTitledBorder("🧩 Enunciados y palabras clave"));

        for (int i = 0; i < 3; i++) {
            JPanel fila = new JPanel(new GridLayout(1, 2, 5, 5));
            fila.setOpaque(false);

            JTextField enunciado = new JTextField();
            enunciado.setBorder(BorderFactory.createTitledBorder("🔍 Enunciado " + (i + 1)));
            camposEnunciado.add(enunciado);

            JTextField palabra = new JTextField();
            palabra.setBorder(BorderFactory.createTitledBorder("🔑 Palabra clave " + (i + 1)));
            camposPalabra.add(palabra);

            fila.add(enunciado);
            fila.add(palabra);
            add(fila);
        }

        contenedor.putClientProperty("sopa", this);
    }

    public PreguntaSopaDeLetras construirPregunta(int numero, String descripcion, int puntos) {
        List<String> enunciados = new ArrayList<>();
        List<String> palabras = new ArrayList<>();

        for (int i = 0; i < camposEnunciado.size(); i++) {
            String enunciado = camposEnunciado.get(i).getText().trim();
            String palabra = camposPalabra.get(i).getText().trim();
            if (enunciado.isEmpty() || palabra.isEmpty()) return null;

            enunciados.add(enunciado);
            palabras.add(palabra);
        }

        int tamaño = calcularTamañoCuadricula(palabras);
        char[][] cuadrícula = generarCuadriculaFinal(palabras);

        return new PreguntaSopaDeLetras(numero, descripcion, puntos, palabras, enunciados, cuadrícula);
    }

    private int calcularTamañoCuadricula(List<String> palabras) {
        int max = 8;
        for (String palabra : palabras) {
            if (palabra.length() > max) max = palabra.length();
        }
        return Math.max(max, 8);
    }

    private char[][] generarCuadriculaFinal(List<String> palabras) {
        int tamaño = calcularTamañoCuadricula(palabras);
        return GeneradorSopaDeLetras.generarCuadricula(palabras, tamaño);
    }

}
