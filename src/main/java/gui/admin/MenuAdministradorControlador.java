package gui.admin;

import usuarios.Estudiante;
import usuarios.Profesor;
import usuarios.Curso;
import control.GestorEstudiantes;
import control.GestorProfesores;
import control.GestorCursos;
import gui.estudiante.*;
import gui.profesor.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🧭 MenuAdministradorControlador                                            ║
 * ║                                                                            ║
 * ║ Interfaz Swing para administrar estudiantes y profesores:                 ║
 * ║ - Listar, registrar, modificar, eliminar y ver detalles                   ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class MenuAdministradorControlador extends JFrame {

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      COMPONENTES UI                        ║
    // ╚════════════════════════════════════════════════════════════╝
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JButton btnRegistrar, btnModificar, btnEliminar, btnDetalles;
    private JComboBox<String> selectorVista;
    private final String[] opcionesVista = {"👩‍🎓 Estudiantes", "👨‍🏫 Profesores", "📚 Cursos"};

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      GESTORES                              ║
    // ╚════════════════════════════════════════════════════════════╝
    private final GestorEstudiantes gestorEstudiantes = GestorEstudiantes.getInstancia();
    private final GestorProfesores gestorProfesores = GestorProfesores.getInstancia();
    private final GestorCursos gestorCursos = GestorCursos.getInstancia();

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      CONSTRUCTOR                           ║
    // ╚════════════════════════════════════════════════════════════╝
    public MenuAdministradorControlador() {
        aplicarEstiloGlobal();
        setTitle("📋 Panel de Administración");
        setSize(900, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
        cargarEstudiantes();
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🎨 ESTILO GLOBAL                         ║
    // ╚════════════════════════════════════════════════════════════╝
    private void aplicarEstiloGlobal() {
        UIManager.put("Table.background", new Color(245, 245, 250));
        UIManager.put("Table.foreground", Color.DARK_GRAY);
        UIManager.put("Table.selectionBackground", new Color(173, 216, 230));
        UIManager.put("Table.selectionForeground", Color.BLACK);
        UIManager.put("Table.gridColor", new Color(200, 200, 200));
        UIManager.put("Panel.background", new Color(240, 240, 255));
        UIManager.put("Button.background", new Color(220, 220, 250));
        UIManager.put("Button.foreground", Color.DARK_GRAY);
        UIManager.put("Button.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
        UIManager.put("Table.font", new Font("Segoe UI Emoji", Font.PLAIN, 13));
        UIManager.put("Label.font", new Font("Segoe UI Emoji", Font.BOLD, 16));
        UIManager.put("ComboBox.background", new Color(230, 240, 255));
        UIManager.put("ComboBox.foreground", Color.DARK_GRAY);
        UIManager.put("ComboBox.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              INICIALIZACIÓN DE COMPONENTES                ║
    // ╚════════════════════════════════════════════════════════════╝
    private void inicializarComponentes() {
        add(crearEncabezado(), BorderLayout.NORTH);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Correo"}, 0);
        tablaUsuarios = new JTable(modeloTabla);
        tablaUsuarios.setRowHeight(25);
        tablaUsuarios.setShowHorizontalLines(true);
        tablaUsuarios.setShowVerticalLines(false);
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaUsuarios.getTableHeader().setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        tablaUsuarios.getTableHeader().setBackground(new Color(200, 220, 255));
        tablaUsuarios.getTableHeader().setForeground(Color.DARK_GRAY);

        JScrollPane scroll = new JScrollPane(tablaUsuarios);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scroll, BorderLayout.CENTER);

        btnRegistrar = new JButton("➕ Registrar");
        btnModificar = new JButton("✏️ Modificar");
        btnEliminar = new JButton("🗑️ Eliminar");
        btnDetalles = new JButton("🔍 Ver Detalles");

        JPanel botones = new JPanel(new GridLayout(1, 4, 10, 0));
        botones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        botones.setBackground(new Color(240, 240, 255));

        for (JButton btn : new JButton[]{btnRegistrar, btnModificar, btnEliminar, btnDetalles}) {
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 220)),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            botones.add(btn);
        }

        add(botones, BorderLayout.SOUTH);

        btnRegistrar.addActionListener(e -> abrirRegistro());
        btnModificar.addActionListener(e -> abrirModificacion());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnDetalles.addActionListener(e -> verDetalles());
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  📋 ENCABEZADO DECORATIVO                 ║
    // ╚════════════════════════════════════════════════════════════╝
    private JPanel crearEncabezado() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(100, 149, 237));

        JLabel titulo = new JLabel("📋 Panel de Administración", JLabel.LEFT);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));

        selectorVista = new JComboBox<>(opcionesVista);
        selectorVista.setFocusable(false);
        selectorVista.addActionListener(e -> cambiarVista());

        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        izquierda.setOpaque(false);
        izquierda.add(titulo);

        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        derecha.setOpaque(false);
        derecha.add(new JLabel("👁️ Ver: "));
        derecha.add(selectorVista);

        header.add(izquierda, BorderLayout.WEST);
        header.add(derecha, BorderLayout.EAST);
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return header;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🔄 CAMBIAR VISTA                         ║
    // ╚════════════════════════════════════════════════════════════╝
    private void cambiarVista() {
        String seleccion = (String) selectorVista.getSelectedItem();
        actualizarColoresVista(seleccion);
        if (seleccion.equals("👩‍🎓 Estudiantes")) {
            cargarEstudiantes();
        } else {
            cargarProfesores();
        }
    }

    private void actualizarColoresVista(String vista) {
        Color colorFondo;
        switch (vista) {
            case "👩‍🎓 Estudiantes":
                colorFondo = new Color(220, 90, 100); // Coral oscuro
                break;
            case "👨‍🏫 Profesores":
                colorFondo = new Color(100, 200, 180); // Verde agua más profundo
                break;
            default:
                colorFondo = new Color(240, 240, 255); // Color por defecto
        }

        getContentPane().setBackground(colorFondo);
        for (Component comp : getContentPane().getComponents()) {
            if (comp instanceof JPanel) {
                comp.setBackground(colorFondo);
            }
        }
    }


    // ╔════════════════════════════════════════════════════════════╗
    // ║                  📋 CARGAR ESTUDIANTES                    ║
    // ╚════════════════════════════════════════════════════════════╝
    private void cargarEstudiantes() {
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnIdentifiers(new String[]{"ID", "Nombre", "Correo"});
        for (Estudiante e : gestorEstudiantes.listarEstudiantes()) {
            modeloTabla.addRow(new Object[]{
                    e.getIdentificacionPersonal(),
                    e.getNombreCompleto(),
                    e.getCorreoElectronico()
            });
        }
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  📋 CARGAR PROFESORES                     ║
    // ╚════════════════════════════════════════════════════════════╝
    private void cargarProfesores() {
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnIdentifiers(new String[]{"ID", "Nombre", "Correo"});
        for (Profesor p : gestorProfesores.listarProfesores()) {
            modeloTabla.addRow(new Object[]{
                    p.getIdentificacionPersonal(),
                    p.getNombreCompleto(),
                    p.getCorreoElectronico()
            });
        }
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  ➕ REGISTRAR USUARIO                      ║
    // ╚════════════════════════════════════════════════════════════╝
    private void abrirRegistro() {
        String seleccion = (String) selectorVista.getSelectedItem();
        if (seleccion.equals("👩‍🎓 Estudiantes")) {
            new RegistroEstudianteControlador(this).setVisible(true);
            cargarEstudiantes();
        } else {
            new RegistroProfesorControlador(this).setVisible(true);
            cargarProfesores();
        }
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  ✏️ MODIFICAR USUARIO                     ║
    // ╚════════════════════════════════════════════════════════════╝
    private void abrirModificacion() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila != -1) {
            String id = (String) modeloTabla.getValueAt(fila, 0);
            String seleccion = (String) selectorVista.getSelectedItem();

            if (seleccion.equals("👩‍🎓 Estudiantes")) {
                Estudiante seleccionado = gestorEstudiantes.consultarEstudiante(id);
                new ModificarEstudianteControlador(this, seleccionado).setVisible(true);
                cargarEstudiantes();
            } else {
                Profesor seleccionado = gestorProfesores.consultarProfesor(id);
                new ModificarProfesorControlador(this, seleccionado).setVisible(true);
                cargarProfesores();
            }
        } else {
            mostrarAdvertencia("⚠️ Selecciona un usuario para modificar.");
        }
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🗑️ ELIMINAR USUARIO                      ║
    // ╚════════════════════════════════════════════════════════════╝
    private void eliminarUsuario() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila != -1) {
            String id = (String) modeloTabla.getValueAt(fila, 0);
            String seleccion = (String) selectorVista.getSelectedItem();

            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que deseas eliminar este usuario?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                if (seleccion.equals("👩‍🎓 Estudiantes")) {
                    gestorEstudiantes.eliminarEstudiante(id);
                    cargarEstudiantes();
                } else {
                    gestorProfesores.eliminarProfesor(id);
                    cargarProfesores();
                }
            }
        } else {
            mostrarAdvertencia("⚠️ Selecciona un usuario para eliminar.");
        }
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🔍 VER DETALLES DEL USUARIO              ║
    // ╚════════════════════════════════════════════════════════════╝
    private void verDetalles() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila != -1) {
            String id = (String) modeloTabla.getValueAt(fila, 0);
            String seleccion = (String) selectorVista.getSelectedItem();

            if (seleccion.equals("👩‍🎓 Estudiantes")) {
                Estudiante seleccionado = gestorEstudiantes.consultarEstudiante(id);
                new DetallesEstudianteControlador(this, seleccionado).setVisible(true);
            } else {
                Profesor seleccionado = gestorProfesores.consultarProfesor(id);
                new DetallesProfesorControlador(this, seleccionado).setVisible(true);
            }
        } else {
            mostrarAdvertencia("⚠️ Selecciona un usuario para ver sus detalles.");
        }
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  ⚠️ MENSAJES DE ALERTA                    ║
    // ╚════════════════════════════════════════════════════════════╝
    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

