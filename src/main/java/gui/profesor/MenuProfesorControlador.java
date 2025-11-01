package gui.profesor;

import control.GestorEvaluaciones;
import usuarios.Profesor;
import evaluaciones.Evaluacion;
import gui.evaluacion.RegistroEvaluacionControlador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🎓 MenuProfesorPanelControlador                                           ║
 * ║                                                                            ║
 * ║ Panel principal para profesores:                                          ║
 * ║ - Consultar información personal                                          ║
 * ║ - CRUD de evaluaciones                                                    ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class MenuProfesorControlador extends JFrame {

    private JTable tablaEvaluaciones;
    private DefaultTableModel modeloTabla;
    private JButton btnRegistrar, btnModificar, btnEliminar, btnDetalles;
    private JButton btnConsultarInfo;
    private final GestorEvaluaciones gestorEvaluaciones = GestorEvaluaciones.getInstancia();
    private static Profesor profesorActivo;

    public MenuProfesorControlador(Profesor profesor) {
        profesorActivo = profesor;
        aplicarEstiloGlobal();
        setTitle("🎓 Panel del Profesor");
        setSize(900, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
        cargarEvaluaciones();
    }

    private void aplicarEstiloGlobal() {
        UIManager.put("Table.background", new Color(235, 255, 250)); // Verde agua claro
        UIManager.put("Panel.background", new Color(220, 245, 240));
        UIManager.put("Button.background", new Color(200, 230, 220));
        UIManager.put("Button.foreground", Color.DARK_GRAY);
        UIManager.put("Button.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
        UIManager.put("Table.font", new Font("Segoe UI Emoji", Font.PLAIN, 13));
        UIManager.put("Label.font", new Font("Segoe UI Emoji", Font.BOLD, 16));
    }

    private void inicializarComponentes() {
        add(crearEncabezado(), BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Duración", "Objetivos"}, 0);
        tablaEvaluaciones = new JTable(modeloTabla);
        tablaEvaluaciones.setRowHeight(25);
        tablaEvaluaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tablaEvaluaciones);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scroll, BorderLayout.CENTER);

        btnRegistrar = new JButton("➕ Crear Evaluación");
        btnModificar = new JButton("✏️ Modificar");
        btnEliminar = new JButton("🗑️ Eliminar");
        btnDetalles = new JButton("🔍 Ver Detalles");
        btnConsultarInfo = new JButton("👤 Mi Información");

        JPanel botones = new JPanel(new GridLayout(1, 5, 10, 0));
        botones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        botones.setBackground(new Color(220, 245, 240));

        for (JButton btn : new JButton[]{btnRegistrar, btnModificar, btnEliminar, btnDetalles, btnConsultarInfo}) {
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 220, 200)),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            botones.add(btn);
        }

        add(botones, BorderLayout.SOUTH);

        btnRegistrar.addActionListener(e -> abrirRegistro());
        btnModificar.addActionListener(e -> abrirModificacion());
        btnEliminar.addActionListener(e -> eliminarEvaluacion());
        btnDetalles.addActionListener(e -> verDetalles());
        btnConsultarInfo.addActionListener(e -> new MenuProfesorControlador(profesorActivo).setVisible(true));
    }

    private JPanel crearEncabezado() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(100, 200, 180)); // Verde agua profundo

        JLabel titulo = new JLabel("🎓 Panel del Profesor", JLabel.LEFT);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));

        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        izquierda.setOpaque(false);
        izquierda.add(titulo);

        header.add(izquierda, BorderLayout.WEST);
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return header;
    }

    private void cargarEvaluaciones() {
        modeloTabla.setRowCount(0);
        for (Evaluacion e : gestorEvaluaciones.listarEvaluacionesPorProfesor(profesorActivo)) {
            modeloTabla.addRow(new Object[]{
                    e.getIdEvaluacion(),
                    e.getNombreEvaluacion(),
                    e.getDuracionMinutos() + " min",
                    String.join(", ", e.getObjetivos())
            });
        }
    }

    private void abrirRegistro() {
        new RegistroEvaluacionControlador(this, profesorActivo).setVisible(true);
        cargarEvaluaciones();
    }

    private void abrirModificacion() {
        int fila = tablaEvaluaciones.getSelectedRow();
        if (fila != -1) {
            int id = (int) modeloTabla.getValueAt(fila, 0);
            Evaluacion evaluacion = gestorEvaluaciones.consultarEvaluacion(id);
            new ModificarEvaluacionControlador(this, evaluacion).setVisible(true);
            cargarEvaluaciones();
        } else {
            mostrarAdvertencia("⚠️ Selecciona una evaluación para modificar.");
        }
    }

    private void eliminarEvaluacion() {
        int fila = tablaEvaluaciones.getSelectedRow();
        if (fila != -1) {
            int id = (int) modeloTabla.getValueAt(fila, 0);
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Eliminar esta evaluación?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                gestorEvaluaciones.eliminarEvaluacion(id);
                cargarEvaluaciones();
            }
        } else {
            mostrarAdvertencia("⚠️ Selecciona una evaluación para eliminar.");
        }
    }

    private void verDetalles() {
        int fila = tablaEvaluaciones.getSelectedRow();
        if (fila != -1) {
            int id = (int) modeloTabla.getValueAt(fila, 0);
            Evaluacion evaluacion = gestorEvaluaciones.consultarEvaluacion(id);
            new DetallesEvaluacionControlador(this, evaluacion).setVisible(true);
        } else {
            mostrarAdvertencia("⚠️ Selecciona una evaluación para ver detalles.");
        }
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}
