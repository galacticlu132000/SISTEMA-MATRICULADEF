package gui.evaluacion;

import evaluaciones.Evaluacion;
import evaluaciones.Pregunta;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🔍 DialogoVerEvaluacion                                                   ║
 * ║                                                                            ║
 * ║ Muestra todos los detalles de una evaluación creada por el profesor.      ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class DialogoVerEvaluacion extends JDialog {

    public DialogoVerEvaluacion(JFrame parent, Evaluacion evaluacion) {
        super(parent, "📄 Detalles de Evaluación", true);
        setSize(600, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));

        StringBuilder sb = new StringBuilder();
        sb.append("📘 Evaluación: ").append(evaluacion.getNombreEvaluacion()).append("\n\n");
        sb.append("📝 Instrucciones:\n").append(evaluacion.getInstruccionesGenerales()).append("\n\n");
        sb.append("🎯 Objetivos:\n");
        for (String obj : evaluacion.getObjetivos()) {
            sb.append(" - ").append(obj).append("\n");
        }
        sb.append("\n⏱️ Duración: ").append(evaluacion.getDuracionMinutos()).append(" minutos\n");
        sb.append("🔀 Preguntas aleatorias: ").append(evaluacion.isPreguntasAleatorias() ? "Sí" : "No").append("\n");
        sb.append("🔀 Opciones aleatorias: ").append(evaluacion.isOpcionesAleatorias() ? "Sí" : "No").append("\n\n");

        List<Pregunta> preguntas = evaluacion.getPreguntas();
        for (int i = 0; i < preguntas.size(); i++) {
            Pregunta p = preguntas.get(i);
            sb.append("═══════════════════════════════════════════════\n");
            sb.append("🔢 Pregunta ").append(i + 1).append(" (").append(p.getTipo()).append(")\n");
            sb.append("📄 Descripción: ").append(p.getDescripcion()).append("\n");
            sb.append("🎯 Puntos: ").append(p.getPuntos()).append("\n");
            sb.append(p.representacionDetallada()).append("\n");
        }

        area.setText(sb.toString());
        add(new JScrollPane(area), BorderLayout.CENTER);

        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> dispose());
        add(cerrar, BorderLayout.SOUTH);
    }
}
