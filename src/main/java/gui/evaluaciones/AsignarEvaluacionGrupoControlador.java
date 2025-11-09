package gui.evaluaciones;


import evaluacion.Evaluacion;
import usuarios.GrupoCurso;
import evaluacion.GestorEvaluaciones;
import control.GestorGruposCurso;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 📎 AsignarEvaluacionGrupoControlador                                      ║
 * ║                                                                            ║
 * ║ Permite vincular una evaluación a un grupo con fecha/hora de inicio       ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class AsignarEvaluacionGrupoControlador extends JFrame {

    private JComboBox<GrupoCurso> comboGrupos;
    private JTextField campoFecha;
    private JTextField campoHora;
    private JButton btnAsignar;

    private final Evaluacion evaluacion;

    public AsignarEvaluacionGrupoControlador(JFrame padre, Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
        setTitle("📎 Asignar Evaluación a Grupo");
        setSize(500, 300);
        setLocationRelativeTo(padre);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(250, 245, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titulo = new JLabel("📎 Asignar Evaluación", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        titulo.setForeground(new Color(100, 60, 160));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(0, 1, 10, 10));
        centro.setBackground(new Color(250, 245, 255));

        comboGrupos = new JComboBox<>();
        int idCurso = Integer.parseInt(evaluacion.getCurso().getIdentificacionCurso());
        List<GrupoCurso> grupos = GestorGruposCurso.getInstancia().listarGrupos(String.valueOf(idCurso));
        for (GrupoCurso g : grupos) {
            comboGrupos.addItem(g);
        }

        campoFecha = new JTextField("2025-11-02"); // formato ISO
        campoHora = new JTextField("08:00");       // formato HH:mm

        centro.add(new JLabel("👥 Selecciona el grupo:"));
        centro.add(comboGrupos);
        centro.add(new JLabel("📅 Fecha de inicio (YYYY-MM-DD):"));
        centro.add(campoFecha);
        centro.add(new JLabel("⏰ Hora de inicio (HH:mm):"));
        centro.add(campoHora);

        panel.add(centro, BorderLayout.CENTER);

        btnAsignar = new JButton("✅ Confirmar Asignación");
        btnAsignar.addActionListener(e -> asignarEvaluacion());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.setBackground(new Color(250, 245, 255));
        botones.add(btnAsignar);

        panel.add(botones, BorderLayout.SOUTH);
        add(panel);
    }

    private void asignarEvaluacion() {
        if (evaluacion.estaAsociadaAGrupo()) {
            mostrarAdvertencia("Esta evaluación ya está asignada a un grupo.");
            return;
        }

        GrupoCurso grupo = (GrupoCurso) comboGrupos.getSelectedItem();
        String fechaStr = campoFecha.getText().trim();
        String horaStr = campoHora.getText().trim();

        try {
            LocalDateTime inicio = LocalDateTime.parse(fechaStr + "T" + horaStr);
            LocalDateTime fin = inicio.plusMinutes(evaluacion.getDuracionMinutos()); // ✅ calcular fin

            boolean ok = GestorEvaluaciones.getInstancia().asociarEvaluacionAGrupo(
                    evaluacion.getIdEvaluacion(),
                    grupo,
                    inicio,
                    fin
            );

            if (ok) {
                JOptionPane.showMessageDialog(this, "✅ Evaluación asignada correctamente.");
                dispose();
            } else {
                mostrarAdvertencia("No se pudo asignar la evaluación.");
            }
        } catch (Exception e) {
            mostrarAdvertencia("Formato de fecha/hora inválido. Usa YYYY-MM-DD y HH:mm.");
        }
    }


    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}
