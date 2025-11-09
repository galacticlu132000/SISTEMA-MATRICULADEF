package gui.estudiante;

import evaluacion.Evaluacion;
import evaluacion.Pregunta;
import evaluacion.PreguntaPareo;
import evaluacion.PreguntaSeleccionUnica;
import usuarios.Estudiante;
import gui.estudiante.PanelPareoVisual;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class VentanaEvaluacionActiva extends JFrame {

    private Evaluacion evaluacion;
    private LocalDateTime inicio;
    private Timer temporizador;
    private int tiempoRestante;
    private JLabel labelTemporizador;
    private JPanel panelPregunta;
    private int preguntaActual = 0;
    private List<Pregunta> preguntas;
    private Map<Integer, Object> respuestasEstudiante = new HashMap<>();
    private Estudiante estudiante;

    public VentanaEvaluacionActiva(Evaluacion evaluacion, Estudiante estudiante) {
        this.evaluacion = evaluacion;
        this.estudiante = estudiante;
        this.preguntas = new ArrayList<>(evaluacion.getPreguntas());
        Collections.shuffle(this.preguntas);
        this.inicio = LocalDateTime.now();
        this.tiempoRestante = evaluacion.getDuracionMinutos() * 60;

        setTitle("📝 Evaluación: " + evaluacion.getNombre());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarUI();
        iniciarTemporizador();
    }

    private void inicializarUI() {
        setLayout(new BorderLayout());

        labelTemporizador = new JLabel("⏳ Tiempo restante: " + tiempoRestante + "s");
        labelTemporizador.setFont(new Font("Segoe UI", Font.BOLD, 16));
        labelTemporizador.setHorizontalAlignment(SwingConstants.CENTER);
        add(labelTemporizador, BorderLayout.NORTH);

        panelPregunta = new JPanel();
        panelPregunta.setLayout(new BorderLayout());
        add(panelPregunta, BorderLayout.CENTER);

        mostrarPreguntaActual();

        JButton btnAnterior = new JButton("⏮ Anterior");
        btnAnterior.addActionListener(e -> {
            guardarRespuestaActual();
            if (preguntaActual > 0) {
                preguntaActual--;
                mostrarPreguntaActual();
            }
        });

        JButton btnSiguiente = new JButton("⏭ Siguiente");
        btnSiguiente.addActionListener(e -> {
            guardarRespuestaActual();
            if (preguntaActual < preguntas.size() - 1) {
                preguntaActual++;
                mostrarPreguntaActual();
            }
        });

        JButton btnFinalizar = new JButton("✅ Finalizar evaluación");
        btnFinalizar.addActionListener(e -> {
            guardarRespuestaActual();
            if (hayRespuestasIncompletas()) {
                JOptionPane.showMessageDialog(this,
                        "⚠️ Debes responder todas las preguntas antes de finalizar.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            finalizarEvaluacion();
        });


        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.add(btnAnterior);
        panelBotones.add(btnSiguiente);
        panelBotones.add(btnFinalizar);

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void iniciarTemporizador() {
        temporizador = new Timer(1000, e -> {
            tiempoRestante--;
            labelTemporizador.setText("⏳ Tiempo restante: " + tiempoRestante + "s");
            if (tiempoRestante <= 0) {
                temporizador.stop();
                finalizarEvaluacion();
            }
        });
        temporizador.start();
    }

    private void mostrarPreguntaActual() {
        panelPregunta.removeAll();
        Pregunta pregunta = preguntas.get(preguntaActual);

        JLabel label = new JLabel("Pregunta " + (preguntaActual + 1) + ": " + pregunta.getDescripcion());
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panelPregunta.add(label, BorderLayout.NORTH);

        String tipo = pregunta.getTipo().toLowerCase().trim();

        switch (tipo) {
            case "pareo":
                panelPregunta.add(new PanelPareoVisual((PreguntaPareo) pregunta), BorderLayout.CENTER);
                break;
            case "seleccion_unica":
                panelPregunta.add(new PanelSeleccionUnica((PreguntaSeleccionUnica) pregunta), BorderLayout.CENTER);
                break;
            case "verdadero_falso":
              //  panelPregunta.add(new PanelVerdaderoFalso((PreguntaVerdaderoFalso) pregunta), BorderLayout.CENTER);
                break;
            case "seleccion_multiple":
            //    panelPregunta.add(new PanelSeleccionMultiple((PreguntaSeleccionMultiple) pregunta), BorderLayout.CENTER);
                break;
            case "sopa_letras":
          //      panelPregunta.add(new PanelSopaLetras((PreguntaSopaLetras) pregunta), BorderLayout.CENTER);
                break;
            default:
                panelPregunta.add(new JLabel("❌ Tipo de pregunta no soportado: " + tipo), BorderLayout.CENTER);
        }


        panelPregunta.revalidate();
        panelPregunta.repaint();
    }
    private void guardarRespuestaActual() {
        Pregunta pregunta = preguntas.get(preguntaActual);
        String tipo = pregunta.getTipo().toLowerCase().trim(); // Normaliza el tipo
        Component[] componentes = panelPregunta.getComponents();

        for (Component comp : componentes) {
            //Pareo
            if (tipo.equals("pareo") && comp instanceof PanelPareoVisual) {
                PanelPareoVisual panel = (PanelPareoVisual) comp;
                respuestasEstudiante.put(pregunta.getNumero(), panel.obtenerRespuestas());
            }
            //Seleccion Unica
            if (tipo.equals("seleccion_unica") && comp instanceof gui.estudiante.PanelSeleccionUnica) {
                gui.estudiante.PanelSeleccionUnica panel = (gui.estudiante.PanelSeleccionUnica) comp;
                respuestasEstudiante.put(pregunta.getNumero(), panel.obtenerRespuesta());
            }

            // Aquí puedes agregar otros tipos:
            // if (tipo.equals("seleccion_unica") && comp instanceof PanelSeleccionUnica) { ... }
            // if (tipo.equals("verdadero_falso") && comp instanceof PanelVerdaderoFalso) { ... }
        }
    }

    private boolean hayRespuestasIncompletas() {
        for (Pregunta p : preguntas) {
            if (!respuestasEstudiante.containsKey(p.getNumero())) {
                return true;
            }
        }
        return false;
    }



    private void finalizarEvaluacion() {
        LocalDateTime fin = LocalDateTime.now();
        temporizador.stop();

        StringBuilder resumen = new StringBuilder("✅ Evaluación finalizada.\n\n");
        resumen.append("🕒 Inicio: ").append(inicio).append("\n");
        resumen.append("🕒 Fin: ").append(fin).append("\n\n");

        for (Pregunta p : preguntas) {
            Object respuesta = respuestasEstudiante.get(p.getNumero());
            resumen.append("🔹 Pregunta ").append(p.getNumero()).append(": ").append(p.getDescripcion()).append("\n");
            resumen.append("   Respuesta: ").append(respuesta != null ? respuesta.toString() : "Sin respuesta").append("\n\n");
        }

        JOptionPane.showMessageDialog(this, resumen.toString(), "Resumen de respuestas", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

}

