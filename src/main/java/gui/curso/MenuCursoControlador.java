package gui.curso;

import usuarios.Curso;

import javax.swing.*;
import java.awt.*;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 📘 MenuCursoControlador                                                    ║
 * ║                                                                            ║
 * ║ Ventana Swing que muestra la información completa del curso activo.       ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class MenuCursoControlador extends JFrame {

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      COMPONENTES UI                        ║
    // ╚════════════════════════════════════════════════════════════╝
    private JLabel tituloCurso;
    private JLabel labelID, labelNombre, labelDescripcion, labelHoras,
            labelMinEstudiantes, labelMaxEstudiantes, labelMinCalificacion,
            labelModalidad, labelTipoCurso;

    private static Curso cursoActivo;

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      CONSTRUCTOR                           ║
    // ╚════════════════════════════════════════════════════════════╝
    public MenuCursoControlador(Curso curso) {
        cursoActivo = curso;
        setTitle("📘 Menú del Curso");
        setSize(550, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        inicializarComponentes();
        cargarDatosCurso();
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              INICIALIZACIÓN DE COMPONENTES                ║
    // ╚════════════════════════════════════════════════════════════╝
    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridLayout(9, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        tituloCurso         = new JLabel();
        labelID             = new JLabel();
        labelNombre         = new JLabel();
        labelDescripcion    = new JLabel();
        labelHoras          = new JLabel();
        labelMinEstudiantes = new JLabel();
        labelMaxEstudiantes = new JLabel();
        labelMinCalificacion = new JLabel();
        labelModalidad      = new JLabel();
        labelTipoCurso      = new JLabel();

        panel.add(new JLabel("📘 Curso:"));              panel.add(tituloCurso);
        panel.add(new JLabel("🆔 Identificación:"));     panel.add(labelID);
        panel.add(new JLabel("📖 Nombre:"));             panel.add(labelNombre);
        panel.add(new JLabel("📝 Descripción:"));        panel.add(labelDescripcion);
        panel.add(new JLabel("⏱️ Horas por día:"));      panel.add(labelHoras);
        panel.add(new JLabel("👥 Mínimo estudiantes:")); panel.add(labelMinEstudiantes);
        panel.add(new JLabel("👥 Máximo estudiantes:")); panel.add(labelMaxEstudiantes);
        panel.add(new JLabel("✅ Calificación mínima:"));panel.add(labelMinCalificacion);
        panel.add(new JLabel("🎓 Modalidad:"));          panel.add(labelModalidad);
        panel.add(new JLabel("📚 Tipo de curso:"));      panel.add(labelTipoCurso);

        add(panel);
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              CARGAR DATOS DEL CURSO                       ║
    // ╚════════════════════════════════════════════════════════════╝
    private void cargarDatosCurso() {
        if (cursoActivo != null) {
            tituloCurso.setText(cursoActivo.getNombreCurso());
            labelID.setText(cursoActivo.getIdentificacionCurso());
            labelNombre.setText(cursoActivo.getNombreCurso());
            labelDescripcion.setText(cursoActivo.getDescripcionCurso());
            labelHoras.setText(String.valueOf(cursoActivo.getHorasDia()));
            labelMinEstudiantes.setText(String.valueOf(cursoActivo.getCantidadMinimaE()));
            labelMaxEstudiantes.setText(String.valueOf(cursoActivo.getCantidadMaximaE()));
            labelMinCalificacion.setText(String.valueOf(cursoActivo.getCalificacionMinimaE()));
            labelModalidad.setText(cursoActivo.getModalidad().toString());
            labelTipoCurso.setText(cursoActivo.getTipoCurso().toString());
        }
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              MÉTODO PARA USO EXTERNO (OPCIONAL)           ║
    // ╚════════════════════════════════════════════════════════════╝
    public static void setCursoActivo(Curso curso) {
        cursoActivo = curso;
    }
}

