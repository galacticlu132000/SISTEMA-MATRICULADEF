package gui.profesor;
import evaluacion.Evaluacion;
import evaluacion.Pregunta;

import javax.swing.*;
import java.awt.*;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🔍 DetallesEvaluacionControlador                                           ║
 * ║                                                                            ║
 * ║ Muestra todos los detalles de una evaluación                              ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class DetallesEvaluacionControlador extends JFrame {

    public DetallesEvaluacionControlador(JFrame padre, Evaluacion evaluacion) {
        setTitle("🔍 Detalles de Evaluación");
        setSize(700, 600);
        setLocationRelativeTo(padre);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        inicializarComponentes(evaluacion);
    }

    private void inicializarComponentes(Evaluacion evaluacion) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 255, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        StringBuilder sb = new StringBuilder();
        sb.append("📌 Nombre: ").append(evaluacion.getNombre()).append("\n");
        sb.append("📜 Instrucciones: ").append(evaluacion.getInstrucciones()).append("\n");
        sb.append("⏱️ Duración: ").append(evaluacion.getDuracionMinutos()).append(" minutos\n");
        sb.append("🎯 Objetivos:\n");
        for (String obj : evaluacion.getObjetivos()) {
            sb.append("   - ").append(obj).append("\n");
        }
        sb.append("🔢 Preguntas:\n");
        for (Pregunta p : evaluacion.getPreguntas()) {
            sb.append("   ").append(p.getNumero()).append(". [").append(p.getTipo()).append("] ")
                    .append(p.getDescripcion()).append(" (").append(p.getPuntos()).append(" pts)\n");
        }

        area.setText(sb.toString());
        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        add(panel);
    }
}

