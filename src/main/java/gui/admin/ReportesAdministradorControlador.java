package gui.admin;

import control.GestorCursos;
import control.GestorGruposCurso;
import control.GestorReportesAdministrador;
import control.Generadorpdf;
import usuarios.Curso;
import usuarios.Estudiante;
import usuarios.GrupoCurso;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.jdatepicker.impl.*;

public class ReportesAdministradorControlador extends JFrame {

    private JComboBox<String> modoImpresionCombo;
    private JComboBox<String> tipoReporteCombo;
    private JDatePickerImpl fechaPicker;
    private JComboBox<Curso> cursoCombo;
    private JComboBox<GrupoCurso> grupoCombo;
    private JButton generarBtn;

    public ReportesAdministradorControlador() {
        setTitle("📄 Reportes del Administrador");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formulario = new JPanel(new GridLayout(6, 2, 10, 10));
        formulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        modoImpresionCombo = new JComboBox<>(new String[]{
                "Todos los cursos y grupos vigentes",
                "Solo un curso",
                "Solo un grupo"
        });

        tipoReporteCombo = new JComboBox<>(new String[]{
                "Lista de estudiantes", "Estadística de matrícula"
        });

        fechaPicker = crearSelectorFecha();
        cursoCombo = new JComboBox<>(GestorCursos.getInstancia().obtenerCursos().toArray(new Curso[0]));
        grupoCombo = new JComboBox<>();
        generarBtn = new JButton("📄 Generar PDF");

        formulario.add(new JLabel("Modo de impresión:"));
        formulario.add(modoImpresionCombo);
        formulario.add(new JLabel("Tipo de reporte:"));
        formulario.add(tipoReporteCombo);
        formulario.add(new JLabel("Fecha de vigencia:"));
        formulario.add(fechaPicker);
        formulario.add(new JLabel("Curso:"));
        formulario.add(cursoCombo);
        formulario.add(new JLabel("Grupo:"));
        formulario.add(grupoCombo);

        add(formulario, BorderLayout.CENTER);

        JPanel pie = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pie.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 20));
        pie.add(generarBtn);
        add(pie, BorderLayout.SOUTH);

        modoImpresionCombo.addActionListener(e -> actualizarVisibilidadCampos());
        cursoCombo.addActionListener(e -> actualizarGrupos());
        generarBtn.addActionListener(e -> generarReporte());

        actualizarGrupos();
        actualizarVisibilidadCampos();
        setVisible(true);
    }

    private JDatePickerImpl crearSelectorFecha() {
        UtilDateModel model = new UtilDateModel();
        model.setSelected(true);
        Properties p = new Properties();
        p.put("text.today", "Hoy");
        p.put("text.month", "Mes");
        p.put("text.year", "Año");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }

    private static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final String pattern = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);

        @Override
        public Object stringToValue(String text) throws java.text.ParseException {
            return dateFormatter.parse(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value instanceof Calendar) {
                return dateFormatter.format(((Calendar) value).getTime());
            }
            return "";
        }
    }

    private void actualizarGrupos() {
        grupoCombo.removeAllItems();
        Curso curso = (Curso) cursoCombo.getSelectedItem();
        if (curso != null) {
            List<GrupoCurso> grupos = GestorGruposCurso.getInstancia()
                    .listarGrupos(curso.getIdentificacionCurso());
            for (GrupoCurso g : grupos) {
                grupoCombo.addItem(g);
            }
        }
    }

    private void actualizarVisibilidadCampos() {
        String modo = (String) modoImpresionCombo.getSelectedItem();
        boolean mostrarCurso = !modo.equals("Todos los cursos y grupos vigentes");
        boolean mostrarGrupo = modo.equals("Solo un grupo");

        cursoCombo.setVisible(mostrarCurso);
        grupoCombo.setVisible(mostrarGrupo);

        for (Component comp : ((JPanel) getContentPane().getComponent(0)).getComponents()) {
            if (comp instanceof JLabel label) {
                if (label.getText().equals("Curso:")) {
                    label.setVisible(mostrarCurso);
                } else if (label.getText().equals("Grupo:")) {
                    label.setVisible(mostrarGrupo);
                }
            }
        }

        if (mostrarCurso) {
            actualizarGrupos();
        }
    }

    private void generarReporte() {
        java.util.Date selectedDate = (java.util.Date) fechaPicker.getModel().getValue();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "⚠️ Seleccione una fecha de vigencia.");
            return;
        }
        LocalDate fecha = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        String tipo = (String) tipoReporteCombo.getSelectedItem();
        String modo = (String) modoImpresionCombo.getSelectedItem();

        GestorReportesAdministrador gestor = new GestorReportesAdministrador();
        Generadorpdf pdf = new Generadorpdf();

        int gruposProcesados = 0;

        if (modo.equals("Todos los cursos y grupos vigentes")) {
            for (Curso curso : GestorCursos.getInstancia().obtenerCursos()) {
                List<GrupoCurso> grupos = GestorGruposCurso.getInstancia()
                        .listarGrupos(curso.getIdentificacionCurso());
                for (GrupoCurso grupo : grupos) {
                    if (!grupo.getFechaFin().isBefore(fecha)) {
                        agregarSeccion(pdf, tipo, gestor, curso, grupo, fecha);
                        gruposProcesados++;
                    }
                }
            }
        } else if (modo.equals("Solo un curso")) {
            Curso curso = (Curso) cursoCombo.getSelectedItem();
            if (curso == null) {
                JOptionPane.showMessageDialog(this, "⚠️ Seleccione un curso.");
                return;
            }
            List<GrupoCurso> grupos = GestorGruposCurso.getInstancia()
                    .listarGrupos(curso.getIdentificacionCurso());
            for (GrupoCurso grupo : grupos) {
                if (!grupo.getFechaFin().isBefore(fecha)) {
                    agregarSeccion(pdf, tipo, gestor, curso, grupo, fecha);
                    gruposProcesados++;
                }
            }
        } else if (modo.equals("Solo un grupo")) {
            Curso curso = (Curso) cursoCombo.getSelectedItem();
            GrupoCurso grupo = (GrupoCurso) grupoCombo.getSelectedItem();
            if (curso == null || grupo == null) {
                JOptionPane.showMessageDialog(this, "⚠️ Seleccione un curso y un grupo.");
                return;
            }
            if (!grupo.getFechaFin().isBefore(fecha)) {
                agregarSeccion(pdf, tipo, gestor, curso, grupo, fecha);
                gruposProcesados++;
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ El grupo seleccionado no está vigente en esa fecha.");
                return;
            }
        }

        if (gruposProcesados == 0) {
            JOptionPane.showMessageDialog(this, "⚠️ No hay grupos vigentes en la fecha seleccionada.");
            return;
        }

        pdf.generarArchivo("ReporteEstudiantesMatriculados.pdf");
        JOptionPane.showMessageDialog(this, "✅ Reporte generado exitosamente.");
    }

    private void agregarSeccion(Generadorpdf pdf, String tipo, GestorReportesAdministrador gestor,
                                Curso curso, GrupoCurso grupo, LocalDate fecha) {
        if (tipo.equals("Lista de estudiantes")) {
            List<Estudiante> estudiantes = gestor.obtenerEstudiantesMatriculados(curso, grupo, fecha);
            pdf.agregarSeccionEstudiantes(curso, grupo, estudiantes);
        } else if (tipo.equals("Estadística de matrícula")) {
            int cantidad = gestor.contarEstudiantesMatriculados(grupo, fecha);
            pdf.agregarSeccionEstadistica(curso, grupo, cantidad);
        }
    }
}
