package gui.estudiante;

import control.GestorCursos;
import control.GestorGruposCurso;
import usuarios.Curso;
import usuarios.Estudiante;
import usuarios.GrupoCurso;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Ventana para que el estudiante seleccione curso y grupo para matricularse.
 */
public class VentanaMatricula extends JFrame {

    private Estudiante estudiante;

    public VentanaMatricula(Estudiante estudiante) {
        this.estudiante = estudiante;
        setTitle("📘 Matrícula de Curso");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        inicializarUI();
    }

    private void inicializarUI() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JComboBox<String> comboCursos = new JComboBox<>();
        JComboBox<Integer> comboGrupos = new JComboBox<>();

        // 🔹 Llenar cursos desde GestorCursos
        for (Curso curso : GestorCursos.getInstancia().obtenerCursos()) {
            comboCursos.addItem(curso.getIdentificacionCurso() + " - " + curso.getNombreCurso());
        }

        // 🔹 Actualizar grupos según curso seleccionado
        comboCursos.addActionListener(e -> {
            comboGrupos.removeAllItems();
            String cursoSeleccionado = (String) comboCursos.getSelectedItem();
            if (cursoSeleccionado != null) {
                String idCurso = cursoSeleccionado.split(" ")[0];
                List<GrupoCurso> grupos = GestorGruposCurso.getInstancia().getGruposPorCurso(idCurso);
                for (GrupoCurso grupo : grupos) {
                    comboGrupos.addItem(grupo.getIdGrupo());
                }
            }
        });

        // 🔹 Botón para confirmar matrícula
        JButton botonMatricular = new JButton("✅ Confirmar Matrícula");
        botonMatricular.addActionListener(e -> {
            String cursoSeleccionado = (String) comboCursos.getSelectedItem();
            Integer grupoSeleccionado = (Integer) comboGrupos.getSelectedItem();

            if (cursoSeleccionado == null || grupoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "⚠️ Debes seleccionar curso y grupo");
                return;
            }

            String idCurso = cursoSeleccionado.split(" ")[0];
            int idGrupo = grupoSeleccionado;

            boolean exito = GestorGruposCurso.getInstancia()
                    .matricularEstudiante(idCurso, idGrupo, estudiante.getIdentificacionPersonal());

            if (exito) {
                JOptionPane.showMessageDialog(this, "✅ Matrícula exitosa");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ No se pudo realizar la matrícula");
            }
        });

        panel.add(new JLabel("📘 Curso:"));
        panel.add(comboCursos);
        panel.add(new JLabel("👥 Grupo:"));
        panel.add(comboGrupos);
        panel.add(new JLabel());
        panel.add(botonMatricular);

        add(panel);
    }
}

