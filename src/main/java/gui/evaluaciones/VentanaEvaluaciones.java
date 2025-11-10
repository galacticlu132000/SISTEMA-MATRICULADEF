package gui.evaluaciones;

import usuarios.Curso;
import usuarios.Estudiante;
import evaluacion.Evaluacion;
import control.GestorGruposCurso;

import usuarios.GrupoCurso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import evaluacion.GestorEvaluaciones;
public class VentanaEvaluaciones extends JFrame {

    private Estudiante estudiante;

    public VentanaEvaluaciones(Estudiante estudianteActivo) {
        this.estudiante = estudianteActivo;

        setTitle("📑 Evaluaciones asignadas");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarUI();
    }

    private void inicializarUI() {
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("📑 Evaluaciones asignadas para " + estudiante.getNombreCompleto());
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        // 🔹 Tabla de evaluaciones
        String[] columnas = {"Curso / Grupo", "ID Evaluación", "Nombre", "Inicio", "Fin"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // 🔹 Botón para iniciar evaluación
        JButton botonIniciar = new JButton("🟢 Iniciar evaluación seleccionada");
        botonIniciar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "⚠️ Selecciona una evaluación para iniciar.", "Sin selección", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String idEvaluacionStr = (String) modelo.getValueAt(fila, 1);
            int idEvaluacion = Integer.parseInt(idEvaluacionStr);

            Evaluacion eval = GestorEvaluaciones.getInstancia().consultarEvaluacion(idEvaluacion);
            if (eval != null) {
                JOptionPane.showMessageDialog(this, "🟢 Iniciando evaluación: " + eval.getNombre());

                // 🔹 Abre la ventana de resolución
                VentanaEvaluacionActiva ventana = new VentanaEvaluacionActiva(eval, estudiante);
                ventana.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "❌ No se encontró la evaluación con ID: " + idEvaluacion, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.add(botonIniciar);
        add(panelBoton, BorderLayout.SOUTH);

        cargarEvaluaciones(modelo);
    }

    private void cargarEvaluaciones(DefaultTableModel modelo) {
        List<Curso> cursos = GestorGruposCurso.getInstancia()
                .obtenerCursosMatriculados(estudiante.getIdentificacionPersonal());
        System.out.println("📋 Evaluaciones registradas en el sistema:");
        for (Evaluacion eval : GestorEvaluaciones.getInstancia().listarEvaluaciones()) {
            System.out.println("📝 " + eval.getNombre());
            System.out.println("📘 Curso: " + (eval.getCurso() != null ? eval.getCurso().getIdentificacionCurso() : "null"));
            System.out.println("👥 Grupo: " + (eval.getGrupoAsociado() != null ? eval.getGrupoAsociado().getNombre() : "null"));
            System.out.println("⏰ Inicio: " + eval.getFechaInicio());
            System.out.println("⏰ Fin: " + eval.getFechaFin());
            System.out.println("───────────────");
        }

        LocalDateTime ahora = LocalDateTime.now();

        for (Curso curso : cursos) {
            List<Evaluacion> evaluaciones = GestorEvaluaciones.getInstancia()
                    .listarEvaluaciones().stream()
                    .filter(e -> e.getCurso() != null &&
                            e.getCurso().getIdentificacionCurso().equals(curso.getIdentificacionCurso()))
                    .toList();

            for (Evaluacion eval : evaluaciones) {
                GrupoCurso grupo = eval.getGrupoAsociado();

                boolean enGrupo = grupo.contieneEstudiante(estudiante.getIdentificacionPersonal());
                boolean activa = !eval.getFechaInicio().isAfter(ahora) &&
                        !eval.getFechaFin().isBefore(ahora);

                // 🔍 Depuración en consola
                System.out.println("📝 Evaluación: " + eval.getNombre());
                System.out.println("📘 Curso: " + curso.getIdentificacionCurso());
                System.out.println("👥 Grupo: " + grupo.getNombre());
                System.out.println("✅ Estudiante en grupo: " + enGrupo);
                System.out.println("⏰ Evaluación activa: " + activa);

                if (enGrupo && activa) {
                    modelo.addRow(new Object[]{
                            curso.getNombreCurso(),
                            String.valueOf(eval.getIdEvaluacion()),
                            eval.getNombre(),
                            eval.getFechaInicioTexto() + " " + eval.getHoraInicioTexto(),
                            eval.getFechaFinTexto() + " " + eval.getHoraFinTexto()
                    });
                }
            }
        }
        modelo.fireTableDataChanged();
    }}



