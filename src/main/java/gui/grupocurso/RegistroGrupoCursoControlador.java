package gui.grupocurso;

import control.GestorCursos;
import control.GestorGruposCurso;
import usuarios.Curso;
import usuarios.GrupoCurso;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class RegistroGrupoCursoControlador extends JDialog {
    private final GestorCursos gestorCursos = GestorCursos.getInstancia();
    private final GestorGruposCurso gestorGruposCurso = GestorGruposCurso.getInstancia();

    private JComboBox<Curso> comboCursos;
    private JDateChooser campoFechaInicio;
    private JDateChooser campoFechaFin;
    private JButton btnAgregarGrupo;
    private JButton btnEliminarGrupo;
    private JTable tablaGrupos;
    private DefaultTableModel modeloTabla;

    public RegistroGrupoCursoControlador(JFrame padre) {
        super(padre, "📅 Registro de grupos a cursos", true);
        setSize(700, 450);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout());

        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        JPanel panelFormulario = new JPanel(new GridLayout(3, 4, 10, 10));
        comboCursos = new JComboBox<>(gestorCursos.listarCursos().toArray(new Curso[0]));

        campoFechaInicio = new JDateChooser();
        campoFechaInicio.setDateFormatString("yyyy-MM-dd");
        campoFechaInicio.setDate(new Date());

        campoFechaFin = new JDateChooser();
        campoFechaFin.setDateFormatString("yyyy-MM-dd");
        campoFechaFin.setDate(new Date());

        btnAgregarGrupo = new JButton("➕ Agregar grupo");
        btnEliminarGrupo = new JButton("🗑️ Eliminar grupo seleccionado");

        panelFormulario.add(new JLabel("Curso:"));
        panelFormulario.add(comboCursos);
        panelFormulario.add(new JLabel("Fecha inicio:"));
        panelFormulario.add(campoFechaInicio);
        panelFormulario.add(new JLabel("Fecha fin:"));
        panelFormulario.add(campoFechaFin);
        panelFormulario.add(new JLabel(""));
        panelFormulario.add(btnAgregarGrupo);
        panelFormulario.add(new JLabel(""));
        panelFormulario.add(new JLabel(""));
        panelFormulario.add(new JLabel(""));
        panelFormulario.add(btnEliminarGrupo);

        add(panelFormulario, BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"Grupo", "Curso", "Inicio", "Fin"}, 0);
        tablaGrupos = new JTable(modeloTabla);
        add(new JScrollPane(tablaGrupos), BorderLayout.CENTER);

        comboCursos.addActionListener(e -> actualizarTabla());
        btnAgregarGrupo.addActionListener(e -> registrarGrupo());
        btnEliminarGrupo.addActionListener(e -> eliminarGrupoSeleccionado());

        actualizarTabla();
    }

    private void registrarGrupo() {
        Curso curso = (Curso) comboCursos.getSelectedItem();
        if (curso == null) {
            JOptionPane.showMessageDialog(this, "⚠️ Selecciona un curso.");
            return;
        }

        Date fechaInicio = campoFechaInicio.getDate();
        Date fechaFin = campoFechaFin.getDate();

        if (fechaInicio == null || fechaFin == null) {
            JOptionPane.showMessageDialog(this, "⚠️ Selecciona ambas fechas.");
            return;
        }

        LocalDate inicio = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fin = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (fin.isBefore(inicio)) {
            JOptionPane.showMessageDialog(this, "❌ La fecha de finalización no puede ser anterior a la de inicio.");
            return;
        }

        int nuevoId = gestorGruposCurso.obtenerSiguienteIdGrupo(curso.getIdentificacionCurso());
        GrupoCurso grupo = new GrupoCurso(nuevoId, inicio, fin, curso);
        gestorGruposCurso.agregarGrupo(curso.getIdentificacionCurso(), grupo);

        JOptionPane.showMessageDialog(this, "✅ Grupo registrado exitosamente.");
        actualizarTabla();
    }

    private void eliminarGrupoSeleccionado() {
        int fila = tablaGrupos.getSelectedRow();
        if (fila != -1) {
            Curso curso = (Curso) comboCursos.getSelectedItem();
            int idGrupo = (int) modeloTabla.getValueAt(fila, 0);

            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Estás segura de eliminar el grupo " + idGrupo + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean eliminado = gestorGruposCurso.eliminarGrupo(curso.getIdentificacionCurso(), idGrupo);
                if (eliminado) {
                    JOptionPane.showMessageDialog(this, "🗑️ Grupo eliminado.");
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ No se pudo eliminar el grupo.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "⚠️ Selecciona un grupo en la tabla para eliminar.");
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        Curso curso = (Curso) comboCursos.getSelectedItem();
        if (curso == null) return;

        for (GrupoCurso grupo : gestorGruposCurso.listarGrupos(curso.getIdentificacionCurso())) {
            modeloTabla.addRow(new Object[]{
                    grupo.getIdGrupo(),
                    grupo.getCurso().getNombreCurso(),
                    grupo.getFechaInicio(),
                    grupo.getFechaFin()
            });
        }
    }
}
