package gui.gruposprofesores;

import control.GestorGruposCurso;
import control.GestorProfesores;
import usuarios.GrupoCurso;
import usuarios.Profesor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RegistroProfesorGrupoControlador extends JDialog {

    private JComboBox<String> comboProfesores;
    private JComboBox<String> comboGrupos;
    private JButton btnAsociar;
    private JButton btnVolver;
    private JTable tablaAsociaciones;
    private DefaultTableModel modeloTabla;

    private final GestorProfesores gestorProfesores = GestorProfesores.getInstancia();
    private final GestorGruposCurso gestorGrupos = GestorGruposCurso.getInstancia();

    public RegistroProfesorGrupoControlador(JFrame padre) {
        super(padre, "👨‍🏫 Asociar Grupos a Profesores", true);
        setSize(700, 450);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout());
        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        JPanel panelSuperior = new JPanel(new GridLayout(2, 2, 10, 10));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        comboProfesores = new JComboBox<>(gestorProfesores.getNombresProfesores());
        comboGrupos = new JComboBox<>(gestorGrupos.getNombresGrupos());

        panelSuperior.add(new JLabel("👨‍🏫 Profesor:"));
        panelSuperior.add(comboProfesores);
        panelSuperior.add(new JLabel("👥 Grupo:"));
        panelSuperior.add(comboGrupos);

        add(panelSuperior, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"Profesor", "Grupo", "Curso"}, 0);
        tablaAsociaciones = new JTable(modeloTabla);
        tablaAsociaciones.setRowHeight(24);
        tablaAsociaciones.setFillsViewportHeight(true);
        tablaAsociaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tablaAsociaciones), BorderLayout.CENTER);

        btnAsociar = new JButton("🔗 Asociar");
        btnAsociar.setEnabled(false); // desactivado inicialmente

        btnVolver = new JButton("↩️ Volver");
        btnVolver.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelBotones.add(btnAsociar);
        panelBotones.add(btnVolver);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        comboProfesores.addActionListener(e -> validarSeleccion());
        comboGrupos.addActionListener(e -> validarSeleccion());
        btnAsociar.addActionListener(e -> asociarGrupoAProfesor());
    }

    private void validarSeleccion() {
        boolean profesorSeleccionado = comboProfesores.getSelectedItem() != null;
        boolean grupoSeleccionado = comboGrupos.getSelectedItem() != null;
        btnAsociar.setEnabled(profesorSeleccionado && grupoSeleccionado);
    }

    private void asociarGrupoAProfesor() {
        String nombreProfesor = (String) comboProfesores.getSelectedItem();
        String nombreGrupo = (String) comboGrupos.getSelectedItem();

        if (nombreProfesor == null || nombreGrupo == null) {
            JOptionPane.showMessageDialog(this, "⚠️ Selecciona un profesor y un grupo.");
            return;
        }

        Profesor profesor = gestorProfesores.buscarPorNombre(nombreProfesor);
        GrupoCurso grupo = gestorGrupos.buscarPorNombre(nombreGrupo);

        if (profesor == null || grupo == null) {
            JOptionPane.showMessageDialog(this, "❌ No se encontró el profesor o grupo.");
            return;
        }

        // Verificar si ya está en la tabla
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            if (modeloTabla.getValueAt(i, 0).equals(nombreProfesor) &&
                    modeloTabla.getValueAt(i, 1).equals(grupo.getNombre())) {
                JOptionPane.showMessageDialog(this, "⚠️ Esta asociación ya está registrada.");
                return;
            }
        }

        boolean exito = gestorProfesores.asociarGrupo(profesor, grupo);
        if (exito) {
            modeloTabla.addRow(new Object[]{
                    nombreProfesor,
                    grupo.getNombre(),
                    grupo.getCurso().getnombreCurso()
            });
            JOptionPane.showMessageDialog(this, "✅ Grupo asociado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "⚠️ El grupo ya está asociado a este profesor.");
        }
    }
}
