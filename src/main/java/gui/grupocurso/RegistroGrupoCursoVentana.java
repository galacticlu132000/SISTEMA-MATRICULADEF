package gui.grupocurso;

import control.GestorCursos;
import usuarios.Curso;
import usuarios.GrupoCurso;
import control.GestorGruposCurso;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RegistroGrupoCursoVentana extends JDialog {
    private final GestorCursos gestorCursos = GestorCursos.getInstancia();
    private final GestorGruposCurso gestorGruposCurso = GestorGruposCurso.getInstancia();

    private JComboBox<Curso> comboCursos;
    private JTextField campoFechaInicio;
    private JTextField campoFechaFin;
    private JButton btnAgregarGrupo;
    private JTable tablaGrupos;
    private DefaultTableModel modeloTabla;

    public RegistroGrupoCursoVentana(JFrame padre) {
        super(padre, "📅 Registro de grupos a cursos", true);
        setSize(700, 400);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout());

        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        // Panel superior: selección de curso y fechas
        JPanel panelFormulario = new JPanel(new GridLayout(2, 4, 10, 10));
        comboCursos = new JComboBox<>(gestorCursos.listarCursos().toArray(new Curso[0]));
        campoFechaInicio = new JTextField("2025-11-01");
        campoFechaFin = new JTextField("2025-12-15");
        btnAgregarGrupo = new JButton("➕ Agregar grupo");

        panelFormulario.add(new JLabel("Curso:"));
        panelFormulario.add(comboCursos);
        panelFormulario.add(new JLabel("Fecha inicio (YYYY-MM-DD):"));
        panelFormulario.add(campoFechaInicio);
        panelFormulario.add(new JLabel("Fecha fin (YYYY-MM-DD):"));
        panelFormulario.add(campoFechaFin);
        panelFormulario.add(new JLabel(""));
        panelFormulario.add(btnAgregarGrupo);

        add(panelFormulario, BorderLayout.NORTH);

        // Tabla de grupos
        modeloTabla = new DefaultTableModel(new String[]{"Grupo", "Inicio", "Fin"}, 0);
        tablaGrupos = new JTable(modeloTabla);
        add(new JScrollPane(tablaGrupos), BorderLayout.CENTER);

        // Eventos
        comboCursos.addActionListener(e -> actualizarTabla());
        btnAgregarGrupo.addActionListener(e -> registrarGrupo());

        // Cargar tabla inicial
        actualizarTabla();
    }

    private void registrarGrupo() {
        Curso curso = (Curso) comboCursos.getSelectedItem();
        if (curso == null) return;

        try {
            LocalDate inicio = LocalDate.parse(campoFechaInicio.getText().trim());
            LocalDate fin = LocalDate.parse(campoFechaFin.getText().trim());

            int nuevoId = gestorGruposCurso.obtenerSiguienteIdGrupo(curso.getIdentificacionCurso());
            GrupoCurso grupo = new GrupoCurso(nuevoId, inicio, fin);
            gestorGruposCurso.agregarGrupo(curso.getIdentificacionCurso(), grupo);

            JOptionPane.showMessageDialog(this, "✅ Grupo registrado exitosamente.");
            actualizarTabla();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "❌ Formato de fecha inválido. Usa YYYY-MM-DD.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "❌ " + ex.getMessage());
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        Curso curso = (Curso) comboCursos.getSelectedItem();
        if (curso == null) return;

        for (GrupoCurso grupo : gestorGruposCurso.listarGrupos(curso.getIdentificacionCurso())) {
            modeloTabla.addRow(new Object[]{
                    grupo.getIdGrupo(),
                    grupo.getFechaInicio(),
                    grupo.getFechaFin()
            });
        }
    }
}

