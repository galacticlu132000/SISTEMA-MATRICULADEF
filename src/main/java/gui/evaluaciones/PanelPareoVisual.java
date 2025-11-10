package gui.evaluaciones;

import evaluacion.PreguntaPareo;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PanelPareoVisual extends JPanel {

    private Map<String, JComboBox<String>> emparejamientos = new LinkedHashMap<>();

    public PanelPareoVisual(PreguntaPareo pregunta) {
        setLayout(new GridLayout(0, 2, 10, 10));
        setBackground(new Color(245, 250, 255));
        setBorder(BorderFactory.createTitledBorder("🔗 Relaciona los elementos"));

        List<String> enunciados = pregunta.getColumna1();
        List<String> opciones = new ArrayList<>(pregunta.getColumna2());
        Collections.shuffle(opciones); // Mezclar opciones

        for (String enunciado : enunciados) {
            JLabel label = new JLabel(enunciado);
            JComboBox<String> combo = new JComboBox<>(opciones.toArray(new String[0]));
            emparejamientos.put(enunciado, combo);

            add(label);
            add(combo);
        }
    }

    public Map<String, String> obtenerRespuestas() {
        Map<String, String> respuestas = new LinkedHashMap<>();
        for (Map.Entry<String, JComboBox<String>> entry : emparejamientos.entrySet()) {
            respuestas.put(entry.getKey(), (String) entry.getValue().getSelectedItem());
        }
        return respuestas;
    }
}

