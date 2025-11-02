package gui.profesor;

import evaluacion.Evaluacion;
import evaluacion.ResultadoEvaluacion;
import evaluacion.GestorEvaluaciones;
import control.GestorResultados;
import usuarios.Profesor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 📄 ReportesProfesorControlador                                             ║
 * ║                                                                            ║
 * ║ Muestra evaluaciones realizadas y detalle por estudiante                  ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class ReportesProfesorControlador extends JFrame {

    private JComboBox<Evaluacion> comboEvaluaciones;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;

    private final GestorResultados gestorResultados = GestorResultados.getInstancia();
    private final GestorEvaluaciones gestorEvaluaciones = GestorEvaluaciones.getInstancia();

    public ReportesProfesorControlador(JFrame padre, Profesor profesor) {
        setTitle("📄 Reportes de Evaluaciones Realizadas");
        setSize(800, 500);
        setLocationRelativeTo(padre);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        inicializarComponentes(profesor);
    }

    private void inicializarComponentes(Profesor profesor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 255, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        comboEvaluaciones = new JComboBox<>();
        for (Evaluacion e : gestorEvaluaciones.listarEvaluacionesPorProfesor(profesor)) {
            if (e.estaAsociadaAGrupo()) {
                comboEvaluaciones.addItem(e);
            }
        }
        comboEvaluaciones.addActionListener(e -> cargarResultados());

        JPanel selector = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selector.setBackground(new Color(255, 255, 240));
        selector.add(new JLabel("📌 Evaluación realizada:"));
        selector.add(comboEvaluaciones);
        panel.add(selector, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"Estudiante", "Puntaje", "Calificación"}, 0);
        tablaResultados = new JTable(modeloTabla);
        tablaResultados.setRowHeight(25);
        tablaResultados.getTableHeader().setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        tablaResultados.getTableHeader().setBackground(new Color(220, 220, 180));

        JScrollPane scroll = new JScrollPane(tablaResultados);
        panel.add(scroll, BorderLayout.CENTER);

        add(panel);
        cargarResultados();
    }

    private void cargarResultados() {
        modeloTabla.setRowCount(0);
        Evaluacion seleccionada = (Evaluacion) comboEvaluaciones.getSelectedItem();
        if (seleccionada == null) return;

        List<ResultadoEvaluacion> resultados = gestorResultados.listarPorEvaluacion(seleccionada);
        for (ResultadoEvaluacion r : resultados) {
            modeloTabla.addRow(new Object[]{
                    r.getEstudiante().getNombreCompleto(),
                    r.getPuntosObtenidos() + " / " + r.getPuntajeTotal(),
                    r.getCalificacionBase100() + " / 100"
            });
        }
    }
}

