package gui.evaluaciones;

import evaluacion.Evaluacion;
import evaluacion.Pregunta;
import evaluacion.PreguntaSopaLetras;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PrevisualizarEvaluacionControlador extends JFrame {

    private final Evaluacion evaluacion;
    private final List<Pregunta> preguntas;
    private int indiceActual = 0;

    private JLabel lblTemporizador;
    private JButton btnAnterior, btnSiguiente, btnCerrar;

    private JPanel panelCentro;
    private Timer temporizador;
    private int tiempoRestante; // en segundos

    public PrevisualizarEvaluacionControlador(JFrame padre, Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
        this.preguntas = evaluacion.getPreguntas();
        this.tiempoRestante = evaluacion.getDuracionMinutos() * 60;

        setTitle("🧪 Previsualización de Evaluación");
        setSize(700, 500);
        setLocationRelativeTo(padre);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        inicializarComponentes();
        iniciarTemporizador();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 255, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titulo = new JLabel("🧪 Previsualización: " + evaluacion.getNombre(), JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        titulo.setForeground(new Color(50, 120, 100));
        panel.add(titulo, BorderLayout.NORTH);

        panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(new Color(245, 255, 250));
        panel.add(panelCentro, BorderLayout.CENTER);

        lblTemporizador = new JLabel("⏱️ Tiempo restante: ", JLabel.LEFT);
        lblTemporizador.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));

        btnAnterior = new JButton("⬅️ Anterior");
        btnSiguiente = new JButton("➡️ Siguiente");
        btnCerrar = new JButton("❌ Cerrar");

        btnAnterior.addActionListener(e -> mostrarPregunta(indiceActual - 1));
        btnSiguiente.addActionListener(e -> mostrarPregunta(indiceActual + 1));
        btnCerrar.addActionListener(e -> {
            temporizador.stop();
            dispose();
        });

        JPanel inferior = new JPanel(new BorderLayout());
        inferior.setBackground(new Color(245, 255, 250));
        inferior.add(lblTemporizador, BorderLayout.WEST);

        JPanel navegacion = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navegacion.setBackground(new Color(245, 255, 250));
        navegacion.add(btnAnterior);
        navegacion.add(btnSiguiente);
        navegacion.add(btnCerrar);

        inferior.add(navegacion, BorderLayout.EAST);
        panel.add(inferior, BorderLayout.SOUTH);

        add(panel);
        mostrarPregunta(0);
    }

    private void mostrarPregunta(int indice) {
        if (indice < 0 || indice >= preguntas.size()) return;
        this.indiceActual = indice;

        Pregunta p = preguntas.get(indice);
        panelCentro.removeAll();

        if (p instanceof PreguntaSopaLetras) {
            mostrarSopaLetras((PreguntaSopaLetras) p);
        } else {
            JTextArea areaPregunta = new JTextArea();
            areaPregunta.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            areaPregunta.setEditable(false);
            areaPregunta.setLineWrap(true);
            areaPregunta.setWrapStyleWord(true);

            StringBuilder sb = new StringBuilder();
            sb.append("Pregunta ").append(p.getNumero()).append(" (").append(p.getPuntos()).append(" pts)\n\n");
            sb.append(p.getDescripcion()).append("\n\n");
            sb.append("Tipo: ").append(p.getTipo());

            areaPregunta.setText(sb.toString());
            panelCentro.add(new JScrollPane(areaPregunta), BorderLayout.CENTER);
        }

        panelCentro.revalidate();
        panelCentro.repaint();

        btnAnterior.setEnabled(indice > 0);
        btnSiguiente.setEnabled(indice < preguntas.size() - 1);
    }

    private void mostrarSopaLetras(PreguntaSopaLetras pregunta) {
        JPanel panelSopa = new JPanel(new BorderLayout());
        panelSopa.setBackground(new Color(245, 255, 250));

        JTextArea matriz = new JTextArea();
        matriz.setFont(new Font("Monospaced", Font.PLAIN, 16));
        matriz.setEditable(false);

        char[][] letras = pregunta.getMatriz();
        StringBuilder sb = new StringBuilder();
        for (char[] fila : letras) {
            for (char c : fila) {
                sb.append(c).append(' ');
            }
            sb.append('\n');
        }
        matriz.setText(sb.toString());

        JTextArea enunciados = new JTextArea();
        enunciados.setEditable(false);
        enunciados.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        enunciados.setText("🔤 Enunciados:\n");
        for (String e : pregunta.getEnunciados()) {
            enunciados.append("- " + e + "\n");
        }

        panelSopa.add(new JScrollPane(matriz), BorderLayout.CENTER);
        panelSopa.add(new JScrollPane(enunciados), BorderLayout.SOUTH);

        panelCentro.add(panelSopa, BorderLayout.CENTER);
    }

    private void iniciarTemporizador() {
        temporizador = new Timer(1000, e -> {
            tiempoRestante--;
            int minutos = tiempoRestante / 60;
            int segundos = tiempoRestante % 60;
            lblTemporizador.setText(String.format("⏱️ Tiempo restante: %02d:%02d", minutos, segundos));

            if (tiempoRestante <= 0) {
                temporizador.stop();
                JOptionPane.showMessageDialog(this, "⏰ Tiempo agotado. Fin de la previsualización.");
                dispose();
            }
        });
        temporizador.start();
    }
}
