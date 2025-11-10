package gui.profesor;
import evaluacion.GestorEvaluaciones;
import evaluacion.Evaluacion;
import gui.evaluaciones.*;
import usuarios.Profesor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static main.Main.abrirLogin;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🧑‍🏫 MenuProfesorControlador                                               ║
 * ║                                                                            ║
 * ║ Interfaz Swing para gestionar evaluaciones:                               ║
 * ║ - Crear, modificar, eliminar, ver detalles, asignar y reportar            ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class MenuProfesorControlador extends JFrame {

    private JTable tablaEvaluaciones;
    private DefaultTableModel modeloTabla;
    private JButton btnRegistrar, btnModificar, btnEliminar, btnDetalles,btnDetallesProfesor;
    private JButton btnPrevisualizar, btnAsignarGrupo, btnReportes,btnCerrarSesion;
    private final Profesor profesorActual;


    private final GestorEvaluaciones gestorEvaluaciones = GestorEvaluaciones.getInstancia();

    public MenuProfesorControlador(Profesor profesor) {
        this.profesorActual = profesor;
        aplicarEstiloGlobal();
        setTitle("🧑‍🏫 Panel del Profesor");
        setSize(900, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
        cargarEvaluaciones(profesorActual);
    }

    private void aplicarEstiloGlobal() {
        UIManager.put("Table.background", new Color(240, 255, 250));
        UIManager.put("Table.foreground", Color.DARK_GRAY);
        UIManager.put("Table.selectionBackground", new Color(180, 230, 200));
        UIManager.put("Table.selectionForeground", Color.BLACK);
        UIManager.put("Table.gridColor", new Color(200, 200, 200));
        UIManager.put("Panel.background", new Color(230, 250, 240));
        UIManager.put("Button.background", new Color(200, 240, 220));
        UIManager.put("Button.foreground", Color.DARK_GRAY);
        UIManager.put("Button.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
        UIManager.put("Table.font", new Font("Segoe UI Emoji", Font.PLAIN, 13));
        UIManager.put("Label.font", new Font("Segoe UI Emoji", Font.BOLD, 16));
    }

    private void inicializarComponentes() {
        add(crearEncabezado(), BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Duración", "Preguntas"}, 0);
        tablaEvaluaciones = new JTable(modeloTabla);
        tablaEvaluaciones.setRowHeight(25);
        tablaEvaluaciones.setShowHorizontalLines(true);
        tablaEvaluaciones.setShowVerticalLines(false);
        tablaEvaluaciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaEvaluaciones.getTableHeader().setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        tablaEvaluaciones.getTableHeader().setBackground(new Color(180, 220, 200));
        tablaEvaluaciones.getTableHeader().setForeground(Color.DARK_GRAY);

        JScrollPane scroll = new JScrollPane(tablaEvaluaciones);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scroll, BorderLayout.CENTER);

        btnRegistrar = new JButton("➕ Crear Evaluación");
        btnModificar = new JButton("✏️ Modificar");
        btnEliminar = new JButton("🗑️ Eliminar");
        btnDetalles = new JButton("🔍 Ver Detalles");
        btnPrevisualizar = new JButton("🧪 Previsualizar");
        btnAsignarGrupo = new JButton("📎 Asignar a Grupo");
        btnReportes = new JButton("📄 Reportes");
        btnDetallesProfesor = new JButton("👨‍🏫 Detalles del Profesor");
        btnCerrarSesion = new JButton("🚪 Cerrar Sesión");


        JPanel botones = new JPanel(new GridLayout(1, 8, 10, 0));
        botones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        botones.setBackground(new Color(230, 250, 240));

        for (JButton btn : new JButton[]{btnRegistrar, btnModificar, btnEliminar, btnDetalles, btnPrevisualizar, btnAsignarGrupo, btnReportes, btnDetallesProfesor, btnCerrarSesion}) {
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(160, 200, 180)),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            botones.add(btn);
        }

        add(botones, BorderLayout.SOUTH);

// Listeners
        btnRegistrar.addActionListener(e -> abrirRegistro());
        btnModificar.addActionListener(e -> abrirModificacion());
        btnEliminar.addActionListener(e -> eliminarEvaluacion());
        btnDetalles.addActionListener(e -> verDetalles());
        btnPrevisualizar.addActionListener(e -> previsualizarEvaluacion());
        btnAsignarGrupo.addActionListener(e -> asignarEvaluacionAGrupo());
        btnReportes.addActionListener(e -> abrirReportes());
        btnDetallesProfesor.addActionListener(e -> abrirDetallesProfesor());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    private JPanel crearEncabezado() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(60, 160, 130));

        JLabel titulo = new JLabel("🧑‍🏫 Panel del Profesor", JLabel.LEFT);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));

        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        izquierda.setOpaque(false);
        izquierda.add(titulo);

        header.add(izquierda, BorderLayout.WEST);
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return header;
    }

    private void cargarEvaluaciones(Profesor profesor) {
        modeloTabla.setRowCount(0);
        for (Evaluacion e : gestorEvaluaciones.listarEvaluacionesPorProfesor(profesor)) {
            modeloTabla.addRow(new Object[]{
                    e.getIdEvaluacion(),
                    e.getNombre(),
                    e.getDuracionMinutos() + " min",
                    e.getPreguntas().size()
            });
        }
    }

    private void abrirRegistro() {
        RegistroEvaluacionControlador dialog = new RegistroEvaluacionControlador(this, profesorActual);
        dialog.setVisible(true); // espera hasta que se cierre
        cargarEvaluaciones(profesorActual); // 👈 ahora sí refresca después de guardar
    }



    private void abrirModificacion() {
        int fila = tablaEvaluaciones.getSelectedRow();
        if (fila != -1) {
            int id = (int) modeloTabla.getValueAt(fila, 0);
            Evaluacion evaluacion = gestorEvaluaciones.consultarEvaluacion(id);
            new ModificarEvaluacionControlador(this, evaluacion).setVisible(true);
            cargarEvaluaciones(profesorActual);
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
                cargarEvaluaciones(profesorActual);
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

    private void previsualizarEvaluacion() {
        int fila = tablaEvaluaciones.getSelectedRow();
        if (fila != -1) {
            int id = (int) modeloTabla.getValueAt(fila, 0);
            Evaluacion evaluacion = gestorEvaluaciones.consultarEvaluacion(id);
            new PrevisualizarEvaluacionControlador(this, evaluacion).setVisible(true);
        } else {
            mostrarAdvertencia("⚠️ Selecciona una evaluación para previsualizar.");
        }
    }

    private void asignarEvaluacionAGrupo() {
        int fila = tablaEvaluaciones.getSelectedRow();
        if (fila != -1) {
            int id = (int) modeloTabla.getValueAt(fila, 0);
            Evaluacion evaluacion = gestorEvaluaciones.consultarEvaluacion(id);
            new AsignarEvaluacionGrupoControlador(this, evaluacion).setVisible(true);
        } else {
            mostrarAdvertencia("⚠️ Selecciona una evaluación para asignar.");
        }
    }

    private void abrirReportes() {
        new ReportesProfesorControlador(this,profesorActual);
    }

    private void abrirDetallesProfesor() {
        DetallesProfesorControlador dialog = new DetallesProfesorControlador(this, profesorActual);
        dialog.setVisible(true); // 👈 esto abre la ventana modal
    }


    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
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
