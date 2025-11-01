package gui.evaluacion.pareo;
import evaluaciones.PreguntaPareo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🔗 CampoPareo                                                              ║
 * ║                                                                            ║
 * ║ Panel para ingresar columnas de pareo y sus relaciones.                   ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class CampoPareo extends JPanel {

    private final List<JTextField> columna1Campos = new ArrayList<>();
    private final List<JTextField> columna2Campos = new ArrayList<>();
    private final List<JComboBox<String>> relaciones = new ArrayList<>();

    public CampoPareo(JPanel contenedor) {
        setLayout(new GridLayout(5, 1, 5, 5));
        setOpaque(false);
        setBorder(BorderFactory.createTitledBorder("🔗 Columnas para pareo"));

        for (int i = 0; i < 3; i++) {
            JPanel fila = new JPanel(new GridLayout(1, 3, 5, 5));
            fila.setOpaque(false);

            JTextField campo1 = new JTextField();
            campo1.setBorder(BorderFactory.createTitledBorder("Columna 1 - Enunciado " + (i + 1)));
            columna1Campos.add(campo1);

            JTextField campo2 = new JTextField();
            campo2.setBorder(BorderFactory.createTitledBorder("Columna 2 - Respuesta " + (i + 1)));
            columna2Campos.add(campo2);

            JComboBox<String> comboRelacion = new JComboBox<>(new String[]{"1", "2", "3"});
            comboRelacion.setBorder(BorderFactory.createTitledBorder("🔁 Relación con columna 2"));
            relaciones.add(comboRelacion);

            fila.add(campo1);
            fila.add(campo2);
            fila.add(comboRelacion);
            add(fila);
        }

        contenedor.putClientProperty("pareo", this);
    }

    public PreguntaPareo construirPregunta(int numero, String descripcion, int puntos) {
        List<String> col1 = new ArrayList<>();
        List<String> col2 = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        for (int i = 0; i < columna1Campos.size(); i++) {
            String texto1 = columna1Campos.get(i).getText().trim();
            String texto2 = columna2Campos.get(i).getText().trim();
            if (texto1.isEmpty() || texto2.isEmpty()) return null;

            col1.add(texto1);
            col2.add(texto2);
            indices.add(relaciones.get(i).getSelectedIndex());
        }

        return new PreguntaPareo(numero, descripcion, puntos, col1, col2, indices);
    }
}
