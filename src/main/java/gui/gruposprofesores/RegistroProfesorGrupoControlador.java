package gui.gruposprofesores;

import control.GestorGruposCurso;
import control.GestorProfesores;
import usuarios.Profesor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RegistroProfesorGrupoControlador extends JDialog {

    private JComboBox<String> comboProfesores;
    private JComboBox<String> comboGrupos;
    private JButton btnAsociar;
    private JTable tablaAsociaciones;
    private DefaultTableModel modeloTabla;

    private final GestorProfesores gestorProfesores = GestorProfesores.getInstancia();
    private final GestorGruposCurso gestorGrupos = GestorGruposCurso.getInstancia();

    public RegistroProfesorGrupoControlador(JFrame padre) {
        super(padre, "👨‍🏫 Asociar Grupos a Profesores", true);
        setSize(600, 400);
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

        btnAsociar = new JButton("🔗 Asociar");
        btnAsociar.addActionListener(e -> asociarGrupoAProfesor());

        modeloTabla = new DefaultTableModel(new String[]{"Profesor", "Grupo"}, 0);
        tablaAsociaciones = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaAsociaciones);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(btnAsociar, BorderLayout.SOUTH);
    }

    private void asociarGrupoAProfesor() {
        String nombreProfesor = (String) comboProfesores.getSelectedItem();
        String nombreGrupo = (String) comboGrupos.getSelectedItem();

        if (nombreProfesor == null || nombreGrupo == null) {
            JOptionPane.showMessageDialog(this, "⚠️ Selecciona un profesor y un grupo.");
            return;
        }

        Profesor profesor = gestorProfesores.buscarPorNombre(nombreProfesor);
        Grupo grupo = gestorGrupos.buscarPorNombre(nombreGrupo);

        if (profesor == null || grupo == null) {
            JOptionPane.showMessageDialog(this, "❌ No se encontró el profesor o grupo.");
            return;
        }

        boolean exito = gestorProfesores.asociarGrupo(profesor, grupo);
        if (exito) {
            modeloTabla.addRow(new Object[]{nombreProfesor, nombreGrupo});
            JOptionPane.showMessageDialog(this, "✅ Grupo asociado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "⚠️ El grupo ya está asociado a este profesor.");
        }
    }
}