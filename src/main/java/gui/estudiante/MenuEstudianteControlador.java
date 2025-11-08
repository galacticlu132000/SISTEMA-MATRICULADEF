package gui.estudiante;
import control.GestorGruposCurso;
import usuarios.Estudiante;
import utilidades.correo.GestorCorreos;
import utilidades.correo.RegistroCorreo;
import java.util.List;
import javax.swing.*;
import java.awt.*;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🎓 MenuEstudianteControlador                                               ║
 * ║                                                                            ║
 * ║ Ventana Swing que muestra la información personal y académica del         ║
 * ║ estudiante activo.                                                         ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class MenuEstudianteControlador extends JFrame {

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      COMPONENTES UI                        ║
    // ╚════════════════════════════════════════════════════════════╝
    private JLabel tituloBienvenida;
    private JLabel labelNombre, labelID, labelCorreo, labelTelefono,
            labelDireccion, labelOrganizacion, labelTemas;

    private static Estudiante estudianteActivo;

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      CONSTRUCTOR                           ║
    // ╚════════════════════════════════════════════════════════════╝
    public MenuEstudianteControlador(Estudiante estudiante) {
        estudianteActivo = estudiante;
        setTitle("🎓 Menú del Estudiante");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        inicializarComponentes();
        cargarDatosEstudiante();
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              INICIALIZACIÓN DE COMPONENTES                ║
    // ╚════════════════════════════════════════════════════════════╝
    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        JButton botonVerCorreos = new JButton("📬 Ver correos recibidos");
        botonVerCorreos.setBackground(new Color(220, 235, 255));
        botonVerCorreos.setForeground(new Color(40, 70, 130));
        botonVerCorreos.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        botonVerCorreos.setFocusPainted(false);
        botonVerCorreos.addActionListener(e -> {
            RegistroCorreo registro = GestorCorreos.obtenerRegistro(estudianteActivo.getCorreoElectronico());
            java.util.List<String> historial = registro.obtenerHistorial();

            JTextArea area = new JTextArea();
            area.setEditable(false);
            area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            area.setBackground(new Color(255, 255, 255));
            area.setForeground(new Color(50, 50, 50));

            for (String entrada : historial) {
                area.append("═══════════════════════════════════════\n");
                area.append(entrada + "\n");
                area.append("═══════════════════════════════════════\n\n");
            }

            JScrollPane scroll = new JScrollPane(area);
            scroll.setPreferredSize(new Dimension(450, 300));

            JOptionPane.showMessageDialog(null, scroll, "📬 Historial de correos", JOptionPane.PLAIN_MESSAGE);
        }

        );
        // Botón: Matricular curso
        JButton botonMatricularCurso = new JButton("📘 Matricular Curso");
        botonMatricularCurso.setBackground(new Color(220, 255, 220));
        botonMatricularCurso.setForeground(new Color(30, 100, 60));
        botonMatricularCurso.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        botonMatricularCurso.setFocusPainted(false);
        botonMatricularCurso.addActionListener(e -> {
            VentanaMatricula ventana = new VentanaMatricula(estudianteActivo);
            ventana.setVisible(true);
        });

        // 📖 Botón: Ver cursos matriculados
        JButton botonVerMatriculas = new JButton("📖 Ver cursos matriculados");
        botonVerMatriculas.setBackground(new Color(255, 245, 220));
        botonVerMatriculas.setForeground(new Color(100, 60, 20));
        botonVerMatriculas.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        botonVerMatriculas.setFocusPainted(false);

// 🔹
        botonVerMatriculas.addActionListener(e -> {
            List<String> cursosMatriculados = GestorGruposCurso.getInstancia()
                    .obtenerCursosMatriculados(estudianteActivo.getIdentificacionPersonal());

            JTextArea area = new JTextArea();
            area.setEditable(false);
            area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            area.setBackground(new Color(255, 255, 255));
            area.setForeground(new Color(50, 50, 50));

            if (cursosMatriculados == null || cursosMatriculados.isEmpty()) {
                area.setText("⚠️ No estás matriculado en ningún curso.");
            } else {
                for (String curso : cursosMatriculados) {
                    area.append("📘 " + curso + "\n");
                }
            }

            JScrollPane scroll = new JScrollPane(area);
            scroll.setPreferredSize(new Dimension(450, 250));

            JOptionPane.showMessageDialog(null, scroll, "📖 Cursos matriculados", JOptionPane.PLAIN_MESSAGE);
        });



        tituloBienvenida   = new JLabel();
        labelNombre        = new JLabel();
        labelID            = new JLabel();
        labelCorreo        = new JLabel();
        labelTelefono      = new JLabel();
        labelDireccion     = new JLabel();
        labelOrganizacion  = new JLabel();
        labelTemas         = new JLabel();

        panel.add(new JLabel("👋 Bienvenida:"));       panel.add(tituloBienvenida);
        panel.add(new JLabel("👤 Nombre completo:"));  panel.add(labelNombre);
        panel.add(new JLabel("🆔 Identificación:"));   panel.add(labelID);
        panel.add(new JLabel("📧 Correo electrónico:")); panel.add(labelCorreo);
        panel.add(new JLabel("📞 Teléfono:"));         panel.add(labelTelefono);
        panel.add(new JLabel("🏠 Dirección:"));        panel.add(labelDireccion);
        panel.add(new JLabel("🏢 Organización:"));     panel.add(labelOrganizacion);
        panel.add(new JLabel("📚 Temas de interés:")); panel.add(labelTemas);

        add(panel, BorderLayout.CENTER);
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBoton.setBackground(new Color(240, 240, 255));
        panelBoton.add(botonVerCorreos);
        panelBoton.add(botonMatricularCurso);
        panelBoton.add(botonVerMatriculas);
        add(panelBoton, BorderLayout.SOUTH);


    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              CARGAR DATOS DEL ESTUDIANTE                  ║
    // ╚════════════════════════════════════════════════════════════╝
    private void cargarDatosEstudiante() {
        if (estudianteActivo != null) {
            tituloBienvenida.setText("👩‍🎓 Bienvenida, " + estudianteActivo.getNombre());
            labelNombre.setText(estudianteActivo.getNombreCompleto());
            labelID.setText(estudianteActivo.getIdentificacionPersonal());
            labelCorreo.setText(estudianteActivo.getCorreoElectronico());
            labelTelefono.setText(estudianteActivo.getNumeroTelefono());
            labelDireccion.setText(estudianteActivo.getDireccionFisica());
            labelOrganizacion.setText(estudianteActivo.getOrganizacionLaboral());
            labelTemas.setText(String.join(", ", estudianteActivo.getTemasInteres()));
        }
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              MÉTODO PARA USO EXTERNO (OPCIONAL)           ║
    // ╚════════════════════════════════════════════════════════════╝
    public static void setEstudianteActivo(Estudiante estudiante) {
        estudianteActivo = estudiante;
    }
}
