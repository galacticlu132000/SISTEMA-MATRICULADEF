package gui.estudiante;
import control.GestorGruposCurso;
import gui.evaluaciones.VentanaEvaluaciones;
import gui.login.ControladorLogin;
import usuarios.Curso;
import usuarios.Estudiante;
import utilidades.correo.GestorCorreos;
import utilidades.correo.RegistroCorreo;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.*;
import java.awt.*;

import static main.Main.abrirLogin;


/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🎓 MenuEstudianteControlador                                               ║
 * ║                                                                            ║
 * ║ Interfaz Swing para gestionar información del estudiante:                  ║
 * ║ - Ver datos personales, correos, cursos y evaluaciones                     ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class MenuEstudianteControlador extends JFrame {

    private JTable tablaCursos;
    private DefaultTableModel modeloTabla;
    // Nuevo botón
    private JButton btnCorreos, btnMatricular, btnVerCursos, btnEvaluaciones, btnDetalles, btnCerrarSesion;

    private final Estudiante estudianteActivo;


    public MenuEstudianteControlador(Estudiante estudiante) {
        this.estudianteActivo = estudiante;
        aplicarEstiloGlobal();
        setTitle("🎓 Panel del Estudiante");
        setSize(850, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
        cargarCursos(estudianteActivo);
    }

    private void aplicarEstiloGlobal() {
        UIManager.put("Table.background", new Color(250, 240, 255));
        UIManager.put("Table.foreground", new Color(60, 40, 80));
        UIManager.put("Table.selectionBackground", new Color(210, 180, 240));
        UIManager.put("Table.selectionForeground", Color.BLACK);
        UIManager.put("Table.gridColor", new Color(200, 180, 220));
        UIManager.put("Panel.background", new Color(240, 230, 250));
        UIManager.put("Button.background", new Color(220, 200, 240));
        UIManager.put("Button.foreground", new Color(60, 40, 80));
        UIManager.put("Button.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
        UIManager.put("Table.font", new Font("Segoe UI Emoji", Font.PLAIN, 13));
        UIManager.put("Label.font", new Font("Segoe UI Emoji", Font.BOLD, 16));
    }

    private void inicializarComponentes() {
        add(crearEncabezado(), BorderLayout.NORTH);

        // 👇 Ajustado a solo dos columnas
        modeloTabla = new DefaultTableModel(new String[]{"ID Curso", "Nombre"}, 0);
        tablaCursos = new JTable(modeloTabla);
        tablaCursos.setRowHeight(25);
        tablaCursos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaCursos.getTableHeader().setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        tablaCursos.getTableHeader().setBackground(new Color(200, 170, 230));
        tablaCursos.getTableHeader().setForeground(new Color(50, 30, 70));

        JScrollPane scroll = new JScrollPane(tablaCursos);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scroll, BorderLayout.CENTER);

        btnCorreos = new JButton("📬 Ver Correos");
        btnMatricular = new JButton("📘 Matricular Curso");
        btnVerCursos = new JButton("📖 Ver Cursos");
        btnEvaluaciones = new JButton("📑 Evaluaciones");
        btnDetalles = new JButton("👩‍🎓 Detalles Estudiante");
        btnCerrarSesion = new JButton("🚪 Cerrar Sesión");


        JPanel botones = new JPanel(new GridLayout(1, 6, 10, 0));
        botones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        botones.setBackground(new Color(240, 230, 250));

        for (JButton btn : new JButton[]{btnCorreos, btnMatricular, btnVerCursos, btnEvaluaciones, btnDetalles, btnCerrarSesion}) {
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(160, 140, 200)),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            botones.add(btn);
        }

        add(botones, BorderLayout.SOUTH);

        btnCorreos.addActionListener(e -> verCorreos());
        btnMatricular.addActionListener(e -> abrirMatricula());
        btnVerCursos.addActionListener(e -> verCursos());
        btnEvaluaciones.addActionListener(e -> abrirEvaluaciones());
        btnDetalles.addActionListener(e -> abrirDetallesEstudiante());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    private JPanel crearEncabezado() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(120, 90, 160));

        JLabel titulo = new JLabel("🎓 Panel del Estudiante", JLabel.LEFT);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));

        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        izquierda.setOpaque(false);
        izquierda.add(titulo);

        header.add(izquierda, BorderLayout.WEST);
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return header;
    }

    private void cargarCursos(Estudiante estudiante) {
        modeloTabla.setRowCount(0);
        List<Curso> cursos = GestorGruposCurso.getInstancia()
                .obtenerCursosMatriculados(estudiante.getIdentificacionPersonal());

        if (cursos != null && !cursos.isEmpty()) {
            for (Curso c : cursos) {
                modeloTabla.addRow(new Object[]{
                        c.getIdentificacionCurso(),
                        c.getNombreCurso()
                });
            }
        } else {
            modeloTabla.addRow(new Object[]{"⚠️", "No hay cursos matriculados"});
        }
    }

    private void verCorreos() {
        RegistroCorreo registro = GestorCorreos.obtenerRegistro(estudianteActivo.getCorreoElectronico());
        java.util.List<String> historial = registro.obtenerHistorial();

        JTextArea area = new JTextArea();
        area.setEditable(false);
        for (String entrada : historial) {
            area.append("═══════════════════════════════════════\n");
            area.append(entrada + "\n");
            area.append("═══════════════════════════════════════\n\n");
        }

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(450, 300));
        JOptionPane.showMessageDialog(this, scroll, "📬 Historial de correos", JOptionPane.PLAIN_MESSAGE);
    }

    private void abrirMatricula() {
        VentanaMatricula ventana = new VentanaMatricula(estudianteActivo);
        ventana.setVisible(true);
        cargarCursos(estudianteActivo); // 👈 refresca después de matricular
    }

    private void verCursos() {
        cargarCursos(estudianteActivo);
    }

    private void abrirEvaluaciones() {
        VentanaEvaluaciones ventana = new VentanaEvaluaciones(estudianteActivo);
        ventana.setVisible(true);
    }

    private void abrirDetallesEstudiante() {
        JOptionPane.showMessageDialog(this,
                "👩‍🎓 " + estudianteActivo.getNombreCompleto() +
                        "\n🆔 " + estudianteActivo.getIdentificacionPersonal() +
                        "\n📧 " + estudianteActivo.getCorreoElectronico(),
                "Detalles del Estudiante",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas cerrar sesión?",
                "Cerrar Sesión",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            dispose(); // Cierra el panel actual
            abrirLogin(); // 👈 vuelve al login
        }
    }

}
