package gui.admin;

import gui.grupocurso.RegistroGrupoCursoControlador;
import gui.gruposprofesores.RegistroProfesorGrupoControlador;
import usuarios.Estudiante;
import usuarios.Profesor;
import usuarios.Curso;
import control.GestorEstudiantes;
import control.GestorProfesores;
import control.GestorGruposCurso;
import control.GestorCursos;
import gui.estudiante.*;
import gui.profesor.*;
import gui.curso.*;
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
    private JButton btnGruposCurso;
    private JButton btnAsociarGrupoProfesor;
    private JButton btnReportes; // 📄 Botón para reportes




    // ╔════════════════════════════════════════════════════════════╗
    // ║                      GESTORES                              ║
    // ╚════════════════════════════════════════════════════════════╝
    private final GestorEstudiantes gestorEstudiantes = GestorEstudiantes.getInstancia();
    private final GestorProfesores gestorProfesores = GestorProfesores.getInstancia();
    private final GestorCursos gestorCursos = GestorCursos.getInstancia();
    private final GestorGruposCurso gestorGruposCurso = GestorGruposCurso.getInstancia();

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
        // Encabezado superior con título y selector de vista
        add(crearEncabezado(), BorderLayout.NORTH);

        // Tabla principal para mostrar usuarios
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

        // Botones principales
        btnRegistrar = new JButton("➕ Registrar");
        btnModificar = new JButton("✏️ Modificar");
        btnEliminar = new JButton("🗑️ Eliminar");
        btnDetalles = new JButton("🔍 Ver Detalles");

        // Botón adicional para grupos por curso (solo visible en vista de cursos)
        btnGruposCurso = new JButton("📅 Grupos por Curso");
        btnGruposCurso.setFocusPainted(false);
        btnGruposCurso.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 220)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        btnGruposCurso.addActionListener(e -> abrirVentanaGruposCurso());
        btnGruposCurso.setVisible(false); // Oculto por defecto

        btnAsociarGrupoProfesor = new JButton("👥 Asociar Grupo a Profesor");
        btnAsociarGrupoProfesor.setFocusPainted(false);
        btnAsociarGrupoProfesor.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 220)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        btnAsociarGrupoProfesor.addActionListener(e -> abrirVentanaAsociacion());
        btnAsociarGrupoProfesor.setVisible(false); // Oculto por defecto

        btnReportes = new JButton("📄 Reportes");
        btnReportes.setFocusPainted(false);
        btnReportes.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 220)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        btnReportes.addActionListener(e -> abrirVentanaReportes());



        // Panel inferior con botones
        JPanel botones = new JPanel(new GridLayout(1, 5, 10, 0)); // ahora con 5 columnas
        botones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        botones.setBackground(new Color(240, 240, 255));

        for (JButton btn : new JButton[]{btnRegistrar, btnModificar, btnEliminar, btnDetalles, btnGruposCurso,btnAsociarGrupoProfesor,btnReportes}) {
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 220)),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            botones.add(btn);
        }

        add(botones, BorderLayout.SOUTH);

        // Eventos de los botones
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
        if (seleccion == null) return;

        actualizarColoresVista(seleccion);

        boolean esVistaEstudiantes = "👩‍🎓 Estudiantes".equals(seleccion);
        boolean esVistaProfesores = "👨‍🏫 Profesores".equals(seleccion);
        boolean esVistaCursos = "📚 Cursos".equals(seleccion);

        // Mostrar u ocultar botones específicos
        btnGruposCurso.setVisible(esVistaCursos);
        btnAsociarGrupoProfesor.setVisible(esVistaCursos);

        // Cargar datos según vista
        if (esVistaEstudiantes) {
            cargarEstudiantes();
        } else if (esVistaProfesores) {
            cargarProfesores();
        } else if (esVistaCursos) {
            cargarCursos();
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
            case "\uD83D\uDCDA Cursos":
                colorFondo = new Color(90, 60, 150); // Morado oscuro
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

    private void abrirVentanaAsociacion() {
        new RegistroProfesorGrupoControlador(this);
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
// ║                     📚 CARGAR CURSOS                       ║
// ╚════════════════════════════════════════════════════════════╝
    private void cargarCursos() {
        modeloTabla.setRowCount(0);
        modeloTabla.setColumnIdentifiers(new String[]{
                "ID", "Nombre", "Descripción", "Horas/Día", "Min Estudiantes", "Max Estudiantes",
                "Calificación Mínima", "Modalidad", "Tipo de Curso"
        });

        for (Curso c : gestorCursos.listarCursos()) {
            modeloTabla.addRow(new Object[]{
                    c.getIdentificacionCurso(),
                    c.getnombreCurso(),
                    c.getdescripcionCurso(),
                    c.gethorasDia(),
                    c.getcantidadMinimaE(),
                    c.getcantidadMaximaE(),
                    c.getcalificacionMinimaE(),
                    c.getmodalidad().toString().replace("_", " "), // para legibilidad
                    c.gettipoCurso().toString()
            });
        }
    }


    // ╔════════════════════════════════════════════════════════════╗
    // ║                  ➕ REGISTRAR USUARIO                      ║
    // ╚════════════════════════════════════════════════════════════╝
    private void abrirRegistro() {
        String seleccion = (String) selectorVista.getSelectedItem();
        switch (seleccion) {
            case "👩‍🎓 Estudiantes":
                new RegistroEstudianteControlador(this).setVisible(true);
                cargarEstudiantes();
                break;
            case "👨‍🏫 Profesores":
                new RegistroProfesorControlador(this).setVisible(true);
                cargarProfesores();
                break;
            case "📚 Cursos":
                new RegistroCursoControlador(this).setVisible(true);
                cargarCursos();
                break;
            default:
                JOptionPane.showMessageDialog(this, "Vista no reconocida: " + seleccion);
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

            switch (seleccion) {
                case "👩‍🎓 Estudiantes":
                    Estudiante estudiante = gestorEstudiantes.consultarEstudiante(id);
                    new ModificarEstudianteControlador(this, estudiante).setVisible(true);
                    cargarEstudiantes();
                    break;
                case "👨‍🏫 Profesores":
                    Profesor profesor = gestorProfesores.consultarProfesor(id);
                    new ModificarProfesorControlador(this, profesor).setVisible(true);
                    cargarProfesores();
                    break;
                case "📚 Cursos":
                    Curso curso = gestorCursos.consultarCurso(id);
                    new ModificarCursoControlador(this, curso).setVisible(true);
                    cargarCursos();
                    break;
                default:
                    mostrarAdvertencia("⚠️ Vista no reconocida: " + seleccion);
            }
        } else {
            mostrarAdvertencia("⚠️ Selecciona un usuario para modificar.");
        }
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🗑️ ELIMINAR USUARIO                       ║
    // ╚════════════════════════════════════════════════════════════╝


    private void eliminarUsuario() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila != -1) {
            String id = (String) modeloTabla.getValueAt(fila, 0);
            String seleccion = (String) selectorVista.getSelectedItem();

            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que deseas eliminar este elemento?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                switch (seleccion) {
                    case "👩‍🎓 Estudiantes":
                        gestorEstudiantes.eliminarEstudiante(id);
                        cargarEstudiantes();
                        break;
                    case "👨‍🏫 Profesores":
                        gestorProfesores.eliminarProfesor(id);
                        cargarProfesores();
                        break;
                    case "📚 Cursos":
                        gestorCursos.eliminarCurso(id);
                        cargarCursos();
                        break;
                    default:
                        mostrarAdvertencia("⚠️ Vista no reconocida: " + seleccion);
                }
            }
        } else {
            mostrarAdvertencia("⚠️ Selecciona un elemento para eliminar.");
        }
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🔍 VER DETALLES DEL USUARIO                      ║
    // ╚


    private void verDetalles() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila != -1) {
            String id = (String) modeloTabla.getValueAt(fila, 0);
            String seleccion = (String) selectorVista.getSelectedItem();

            switch (seleccion) {
                case "👩‍🎓 Estudiantes":
                    Estudiante estudiante = gestorEstudiantes.consultarEstudiante(id);
                    new DetallesEstudianteControlador(this, estudiante).setVisible(true);
                    break;
                case "👨‍🏫 Profesores":
                    Profesor profesor = gestorProfesores.consultarProfesor(id);
                    new DetallesProfesorControlador(this, profesor).setVisible(true);
                    break;
                case "📚 Cursos":
                    Curso curso = gestorCursos.consultarCurso(id);
                    new DetallesCursoControlador(this, curso).setVisible(true);
                    break;
                default:
                    mostrarAdvertencia("⚠️ Vista no reconocida: " + seleccion);
            }
        } else {
            mostrarAdvertencia("⚠️ Selecciona un elemento para ver sus detalles.");
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

    // ╔════════════════════════════════════════════════════════════╗
// ║         📅 ABRIR VENTANA DE GRUPOS POR CURSO              ║
// ╚════════════════════════════════════════════════════════════╝
    private void abrirVentanaGruposCurso() {
        new RegistroGrupoCursoControlador(this);
    }

    private void abrirVentanaReportes() {
        new ReportesAdministradorControlador();
    }

}





