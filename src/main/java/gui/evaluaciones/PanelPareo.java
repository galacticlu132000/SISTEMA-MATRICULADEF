package gui.evaluaciones;
import evaluacion.PreguntaPareo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🔗 PanelPareo                                                              ║
 * ║                                                                            ║
 * ║ Subpanel para crear preguntas de pareo con dos columnas                   ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class PanelPareo extends JPanel {

    private final List<JTextField> camposEnunciados = new ArrayList<>();
    private final List<JTextField> camposRespuestas = new ArrayList<>();
    private final DefaultListModel<String> modeloDistractores = new DefaultListModel<>();
    private final JList<String> listaDistractores;
    private final JTextField campoDistractor;
    private final JPanel panelPares;

    public PanelPareo() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 255));
        setBorder(BorderFactory.createTitledBorder("🔗 Enunciados y respuestas"));

        panelPares = new JPanel(new GridLayout(0, 1, 5, 5));
        panelPares.setBackground(new Color(240, 245, 255));

        agregarPar();
        agregarPar();

        JButton btnAgregarPar = new JButton("➕ Agregar par");
        btnAgregarPar.addActionListener(e -> agregarPar());

        JPanel columnaDistractores = new JPanel(new BorderLayout());
        columnaDistractores.setBackground(new Color(240, 245, 255));
        campoDistractor = new JTextField();
        JButton btnAgregarDistractor = new JButton("➕ Agregar distractor");
        btnAgregarDistractor.addActionListener(e -> {
            String texto = campoDistractor.getText().trim();
            if (texto.length() >= 3 && texto.length() <= 40) {
                modeloDistractores.addElement(texto);
                campoDistractor.setText("");
            } else {
                mostrarAdvertencia("El distractor debe tener entre 3 y 40 caracteres.");
            }
        });

        listaDistractores = new JList<>(modeloDistractores);
        columnaDistractores.add(new JLabel("🎭 Distractores:"), BorderLayout.NORTH);
        columnaDistractores.add(campoDistractor, BorderLayout.CENTER);
        columnaDistractores.add(btnAgregarDistractor, BorderLayout.SOUTH);

        JPanel centro = new JPanel(new GridLayout(1, 2, 10, 10));
        centro.setBackground(new Color(240, 245, 255));
        centro.add(panelPares);
        centro.add(columnaDistractores);

        add(centro, BorderLayout.CENTER);
        add(btnAgregarPar, BorderLayout.SOUTH);
    }

    private void agregarPar() {
        JPanel fila = new JPanel(new GridLayout(1, 2, 5, 5));
        fila.setBackground(new Color(240, 245, 255));

        JTextField campoEnunciado = new JTextField();
        JTextField campoRespuesta = new JTextField();

        camposEnunciados.add(campoEnunciado);
        camposRespuestas.add(campoRespuesta);

        fila.add(campoEnunciado);
        fila.add(campoRespuesta);
        panelPares.add(fila);
        revalidate();
        repaint();
    }

    public PreguntaPareo crearPregunta(int numero, String descripcion, int puntos) {
        List<String> enunciados = new ArrayList<>();
        List<String> respuestas = new ArrayList<>();

        for (int i = 0; i < camposEnunciados.size(); i++) {
            String enun = camposEnunciados.get(i).getText().trim();
            String resp = camposRespuestas.get(i).getText().trim();
            if (!enun.isEmpty() && !resp.isEmpty()) {
                enunciados.add(enun);
                respuestas.add(resp);
            }
        }

        if (enunciados.size() < 2) {
            mostrarAdvertencia("Debe ingresar al menos 2 pares enunciado–respuesta.");
            return null;
        }

        for (int i = 0; i < modeloDistractores.size(); i++) {
            respuestas.add(modeloDistractores.getElementAt(i));
        }
        Map<String,String> relaciones = new HashMap<>();
        for (int i = 0; i < enunciados.size(); i++) {
            relaciones.put(enunciados.get(i), camposRespuestas.get(i).getText().trim());
        }

        return new PreguntaPareo(numero, descripcion, puntos, enunciados, respuestas,relaciones);
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}

