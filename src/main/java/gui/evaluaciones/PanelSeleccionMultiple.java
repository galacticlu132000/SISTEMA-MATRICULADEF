package gui.evaluaciones;



import evaluacion.PreguntaSeleccionMultiple;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ ✅ PanelSeleccionMultiple                                                  ║
 * ║                                                                            ║
 * ║ Subpanel para crear preguntas de selección múltiple                       ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class PanelSeleccionMultiple extends JPanel {

    private final List<JTextField> camposOpciones = new ArrayList<>();
    private final List<JCheckBox> casillasCorrectas = new ArrayList<>();
    private final JPanel panelOpciones;

    public PanelSeleccionMultiple() {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 250, 240));
        setBorder(BorderFactory.createTitledBorder("✅ Opciones de respuesta"));

        panelOpciones = new JPanel(new GridLayout(0, 1, 5, 5));
        panelOpciones.setBackground(new Color(255, 250, 240));

        agregarOpcion();
        agregarOpcion();

        JButton btnAgregar = new JButton("➕ Agregar opción");
        btnAgregar.addActionListener(e -> agregarOpcion());

        add(panelOpciones, BorderLayout.CENTER);
        add(btnAgregar, BorderLayout.SOUTH);
    }

    private void agregarOpcion() {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(new Color(255, 250, 240));

        JTextField campo = new JTextField();
        JCheckBox casilla = new JCheckBox("Correcta");

        camposOpciones.add(campo);
        casillasCorrectas.add(casilla);

        fila.add(campo, BorderLayout.CENTER);
        fila.add(casilla, BorderLayout.EAST);
        panelOpciones.add(fila);
        revalidate();
        repaint();
    }

    public PreguntaSeleccionMultiple crearPregunta(int numero, String descripcion, int puntos) {
        List<String> opciones = new ArrayList<>();
        List<String> correctas = new ArrayList<>();

        for (int i = 0; i < camposOpciones.size(); i++) {
            String texto = camposOpciones.get(i).getText().trim();
            if (!texto.isEmpty()) {
                opciones.add(texto);
                if (casillasCorrectas.get(i).isSelected()) {
                    correctas.add(texto);
                }
            }
        }

        if (opciones.size() < 2) {
            mostrarAdvertencia("Debe ingresar al menos 2 opciones.");
            return null;
        }
        if (correctas.isEmpty()) {
            mostrarAdvertencia("Debe marcar al menos una opción como correcta.");
            return null;
        }

        return new PreguntaSeleccionMultiple(numero, descripcion, puntos, opciones, correctas);
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}
