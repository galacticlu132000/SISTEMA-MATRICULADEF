package gui.evaluaciones;



import evaluacion.PreguntaSopaLetras;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🧠 PanelSopaLetras                                                         ║
 * ║                                                                            ║
 * ║ Subpanel para crear preguntas tipo sopa de letras                         ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class PanelSopaLetras extends JPanel {

    private final List<JTextField> camposEnunciado = new ArrayList<>();
    private final List<JTextField> camposPalabra = new ArrayList<>();
    private final JPanel panelEnunciados;

    public PanelSopaLetras() {
        setLayout(new BorderLayout());
        setBackground(new Color(250, 255, 240));
        setBorder(BorderFactory.createTitledBorder("🧠 Enunciados y palabras clave"));

        panelEnunciados = new JPanel();
        panelEnunciados.setLayout(new BoxLayout(panelEnunciados, BoxLayout.Y_AXIS));
        panelEnunciados.setBackground(new Color(250, 255, 240));
        panelEnunciados.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < 10; i++) {
            agregarEnunciado(i + 1);
        }

        JScrollPane scroll = new JScrollPane(panelEnunciados);
        scroll.setPreferredSize(new Dimension(600, 300));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        JButton btnAgregar = new JButton("➕ Agregar enunciado");
        btnAgregar.addActionListener(e -> agregarEnunciado(camposEnunciado.size() + 1));

        add(scroll, BorderLayout.CENTER);
        add(btnAgregar, BorderLayout.SOUTH);
    }

    private void agregarEnunciado(int numero) {
        JPanel fila = new JPanel(new BorderLayout(5, 5));
        fila.setBackground(new Color(250, 255, 240));

        JLabel etiqueta = new JLabel("🔤 Enunciado " + numero + ":");
        etiqueta.setPreferredSize(new Dimension(120, 30));

        JTextField campoEnunciado = new JTextField();
        JTextField campoPalabra = new JTextField();

        camposEnunciado.add(campoEnunciado);
        camposPalabra.add(campoPalabra);

        JPanel campos = new JPanel(new GridLayout(1, 2, 5, 5));
        campos.add(campoEnunciado);
        campos.add(campoPalabra);

        fila.add(etiqueta, BorderLayout.WEST);
        fila.add(campos, BorderLayout.CENTER);

        panelEnunciados.add(fila);
        revalidate();
        repaint();
    }

    public PreguntaSopaLetras crearPregunta(int numero, String descripcion, int puntos) {
        List<String> enunciados = new ArrayList<>();
        List<String> palabras = new ArrayList<>();

        for (int i = 0; i < camposEnunciado.size(); i++) {
            String enun = camposEnunciado.get(i).getText().trim();
            String palabra = camposPalabra.get(i).getText().trim().toUpperCase();

            if (!enun.isEmpty() && palabra.length() >= 3 && palabra.length() <= 12) {
                enunciados.add(enun);
                palabras.add(palabra);
            }
        }

        if (enunciados.size() < 10) {
            mostrarAdvertencia("Debe ingresar al menos 10 enunciados con palabras clave válidas.");
            return null;
        }

        return new PreguntaSopaLetras(numero, descripcion, puntos, enunciados, palabras);
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}


