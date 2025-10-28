package gui.curso;

import usuarios.Curso;

import javax.swing.*;
import java.awt.*;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 📄 DetallesCursoControlador                                                ║
 * ║                                                                            ║
 * ║ Ventana Swing para visualizar los detalles completos de un curso.         ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class DetallesCursoControlador extends JDialog {

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      ETIQUETAS DE DATOS                    ║
    // ╚════════════════════════════════════════════════════════════╝
    private JLabel lblID, lblNombre, lblDescripcion, lblHoras, lblMinEstudiantes,
            lblMaxEstudiantes, lblMinCalificacion, lblModalidad, lblTipoCurso;
    private JButton btnCerrar;

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      CONSTRUCTOR                           ║
    // ╚════════════════════════════════════════════════════════════╝
    public DetallesCursoControlador(Frame parent, Curso curso) {
        super(parent, "📄 Detalles del Curso", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        inicializarComponentes();
        inicializarConCurso(curso);
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              INICIALIZACIÓN DE COMPONENTES                ║
    // ╚════════════════════════════════════════════════════════════╝
    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridLayout(9, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        lblID              = new JLabel();
        lblNombre          = new JLabel();
        lblDescripcion     = new JLabel();
        lblHoras           = new JLabel();
        lblMinEstudiantes  = new JLabel();
        lblMaxEstudiantes  = new JLabel();
        lblMinCalificacion = new JLabel();
        lblModalidad       = new JLabel();
        lblTipoCurso       = new JLabel();
        btnCerrar          = new JButton("Cerrar");

        panel.add(new JLabel("🆔 Identificación:")); panel.add(lblID);
        panel.add(new JLabel("📘 Nombre del curso:")); panel.add(lblNombre);
        panel.add(new JLabel("📝 Descripción:")); panel.add(lblDescripcion);
        panel.add(new JLabel("⏱️ Horas por día:")); panel.add(lblHoras);
        panel.add(new JLabel("👥 Mínimo estudiantes:")); panel.add(lblMinEstudiantes);
        panel.add(new JLabel("👥 Máximo estudiantes:")); panel.add(lblMaxEstudiantes);
        panel.add(new JLabel("✅ Calificación mínima:")); panel.add(lblMinCalificacion);
        panel.add(new JLabel("🎓 Modalidad:")); panel.add(lblModalidad);
        panel.add(new JLabel("📚 Tipo de curso:")); panel.add(lblTipoCurso);

        add(panel, BorderLayout.CENTER);
        add(btnCerrar, BorderLayout.SOUTH);

        btnCerrar.addActionListener(e -> cerrarVentana());
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              CARGAR DATOS DEL CURSO                       ║
    // ╚════════════════════════════════════════════════════════════╝
    public void inicializarConCurso(Curso curso) {
        lblID.setText(curso.getIdentificacionCurso());
        lblNombre.setText(curso.getnombreCurso());
        lblDescripcion.setText(curso.getdescripcionCurso());
        lblHoras.setText(String.valueOf(curso.gethorasDia()));
        lblMinEstudiantes.setText(String.valueOf(curso.getcantidadMinimaE()));
        lblMaxEstudiantes.setText(String.valueOf(curso.getcantidadMaximaE()));
        lblMinCalificacion.setText(String.valueOf(curso.getcalificacionMinimaE()));
        lblModalidad.setText(curso.getmodalidad().toString());
        lblTipoCurso.setText(curso.gettipoCurso().toString());
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  CERRAR VENTANA MODAL                     ║
    // ╚════════════════════════════════════════════════════════════╝
    private void cerrarVentana() {
        dispose();
    }
}

