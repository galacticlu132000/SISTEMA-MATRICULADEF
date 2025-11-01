package gui.evaluacion;


import evaluaciones.PreguntaSeleccionUnica;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🎯 CampoOpcionesUnica                                                     ║
 * ║                                                                            ║
 * ║ Panel para ingresar opciones de selección única.                          ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class CampoOpcionesUnica extends JPanel {

    private final List<JTextField> camposOpciones = new ArrayList<>();
    private final JComboBox<String> selectorCorrecta;

    public CampoOpcionesUnica(JPanel contenedor) {
        setLayout(new GridLayout(6, 1, 5, 5));
        setOpaque(false);
        setBorder(BorderFactory.createTitledBorder("📌 Opciones de respuesta"));

        for (int i = 0; i < 4; i++) {
            JTextField campo = new JTextField();
            campo.setBorder(BorderFactory.createTitledBorder("Opción " + (i + 1)));
            camposOpciones.add(campo);
            add(campo);
        }

        selectorCorrecta = new JComboBox<>(new String[]{"Opción 1", "Opción 2", "Opción 3", "Opción 4"});
        selectorCorrecta.setBorder(BorderFactory.createTitledBorder("✅ Opción correcta"));
        add(selectorCorrecta);

        contenedor.putClientProperty("opcionesUnica", this);
    }

    public PreguntaSeleccionUnica construirPregunta(int numero, String descripcion, int puntos) {
        List<String> opciones = new ArrayList<>();
        for (JTextField campo : camposOpciones) {
            String texto = campo.getText().trim();
            if (texto.length() < 1) return null;
            opciones.add(texto);
        }
        int correcta = selectorCorrecta.getSelectedIndex();
        return new PreguntaSeleccionUnica(numero, descripcion, puntos, opciones, correcta);
    }
}
