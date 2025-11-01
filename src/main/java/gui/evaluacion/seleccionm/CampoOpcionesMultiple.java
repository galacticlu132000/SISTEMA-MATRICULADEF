package gui.evaluacion.seleccionm;
import evaluaciones.PreguntaSeleccionMultiple;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ ✅ CampoOpcionesMultiple                                                  ║
 * ║                                                                            ║
 * ║ Panel para ingresar opciones de selección múltiple.                       ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class CampoOpcionesMultiple extends JPanel {

    private final List<JTextField> camposOpciones = new ArrayList<>();
    private final List<JCheckBox> checksCorrectas = new ArrayList<>();

    public CampoOpcionesMultiple(JPanel contenedor) {
        setLayout(new GridLayout(5, 1, 5, 5));
        setOpaque(false);
        setBorder(BorderFactory.createTitledBorder("📌 Opciones de respuesta"));

        for (int i = 0; i < 4; i++) {
            JPanel fila = new JPanel(new BorderLayout());
            fila.setOpaque(false);

            JTextField campo = new JTextField();
            campo.setBorder(BorderFactory.createTitledBorder("Opción " + (i + 1)));
            camposOpciones.add(campo);

            JCheckBox check = new JCheckBox("✅ Correcta");
            checksCorrectas.add(check);

            fila.add(campo, BorderLayout.CENTER);
            fila.add(check, BorderLayout.EAST);
            add(fila);
        }

        contenedor.putClientProperty("opcionesMultiple", this);
    }

    public PreguntaSeleccionMultiple construirPregunta(int numero, String descripcion, int puntos) {
        List<String> opciones = new ArrayList<>();
        List<Integer> correctas = new ArrayList<>();

        for (int i = 0; i < camposOpciones.size(); i++) {
            String texto = camposOpciones.get(i).getText().trim();
            if (texto.length() < 1) return null;
            opciones.add(texto);
            if (checksCorrectas.get(i).isSelected()) correctas.add(i);
        }

        if (correctas.isEmpty()) return null;
        return new PreguntaSeleccionMultiple(numero, descripcion, puntos, opciones, correctas);
    }
}

