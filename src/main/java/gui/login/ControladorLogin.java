package gui.login;
import control.GestorCursos;
import control.GestorGruposCurso;
import control.GestorProfesores;
import evaluacion.GestorEvaluaciones;
import evaluacion.*;
import usuarios.*;
import control.GestorEstudiantes;
import main.Main;
import utilidades.correo.Correo;
import utilidades.correo.GestorCorreos;



import usuarios.Curso;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static seguridad.Encriptador.encriptar;
import static usuarios.Curso.Modalidad.PRESENCIAL;
import static usuarios.Curso.Tipo_Curso.TEORICO;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🔐 ControladorLogin                                                        ║
 * ║                                                                            ║
 * ║ Panel Swing para autenticación de usuarios: administrador o estudiante.   ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class ControladorLogin extends JPanel {

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      COMPONENTES UI                        ║
    // ╚════════════════════════════════════════════════════════════╝
    private JTextField campoIdentificacion;
    private JPasswordField campoContrasena;
    private JComboBox<String> comboTipoUsuario;
    private JLabel mensajeResultado;
    private JButton botonLogin;

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      CONSTANTES Y MODELO                   ║
    // ╚════════════════════════════════════════════════════════════╝
    Administrador administrador=new Administrador("Juan","Ramirez","Cerdas","admin12345","666666666","admin@gmail.com","Desamparados","M4r!p0s@_V1oL3t4#2025");
     private static final GestorEstudiantes gestor = GestorEstudiantes.getInstancia();
     private static final GestorProfesores gestor2= GestorProfesores.getInstancia();

    // ╔════════════════════════════════════════════════════════════╗
    // ║                      CONSTRUCTOR                           ║
    // ╚════════════════════════════════════════════════════════════╝
    public ControladorLogin() {
        aplicarEstiloGlobal();
        inicializarComponentes();
        registrarEvaluacionPareo();
        registrarEvaluacionSeleccionUnica();


    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🎨 ESTILO GLOBAL                         ║
    // ╚════════════════════════════════════════════════════════════╝
    private void aplicarEstiloGlobal() {
        setBackground(new Color(240, 240, 255));
        UIManager.put("Label.font", new Font("Segoe UI Emoji", Font.BOLD, 14));
        UIManager.put("Button.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
        UIManager.put("TextField.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
        UIManager.put("PasswordField.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
        UIManager.put("ComboBox.font", new Font("Segoe UI Emoji", Font.PLAIN, 14));
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║              INICIALIZACIÓN DE COMPONENTES                ║
    // ╚════════════════════════════════════════════════════════════╝
    private void inicializarComponentes() {
        campoIdentificacion = new JTextField(15);
        campoContrasena = new JPasswordField(15);
        comboTipoUsuario = new JComboBox<>(new String[]{"Estudiante", "Administrador", "Profesor"});
        mensajeResultado = new JLabel(" ");
        botonLogin = new JButton("🚪 Iniciar sesión");
        JButton botonRecuperar = new JButton("🔁 ¿Olvidó su contraseña?");
        botonRecuperar.setFocusPainted(false);
        botonRecuperar.setBackground(new Color(255, 230, 230));
        botonRecuperar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 150, 150)),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        botonRecuperar.addActionListener(this::recuperarContrasena);


        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(255, 255, 255));
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 220)),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        panelFormulario.add(new JLabel("🆔 Identificación:"), gbc);
        gbc.gridy++;
        panelFormulario.add(campoIdentificacion, gbc);
        gbc.gridy++;
        panelFormulario.add(new JLabel("🔑 Contraseña:"), gbc);
        gbc.gridy++;
        panelFormulario.add(campoContrasena, gbc);
        gbc.gridy++;
        panelFormulario.add(new JLabel("👤 Tipo de usuario:"), gbc);
        gbc.gridy++;
        panelFormulario.add(comboTipoUsuario, gbc);

        JPanel contenedorCentral = new JPanel(new BorderLayout());
        contenedorCentral.setBackground(new Color(240, 240, 255));
        contenedorCentral.add(panelFormulario, BorderLayout.CENTER);

        JPanel contenedorBoton = new JPanel();
        contenedorBoton.setBackground(new Color(240, 240, 255));
        botonLogin.setFocusPainted(false);
        botonLogin.setBackground(new Color(220, 220, 250));
        botonLogin.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 220)),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        contenedorBoton.add(botonLogin);
        contenedorBoton.add(botonRecuperar);


        mensajeResultado.setHorizontalAlignment(SwingConstants.CENTER);
        mensajeResultado.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        mensajeResultado.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.setLayout(new BorderLayout());
        this.add(mensajeResultado, BorderLayout.NORTH);
        this.add(contenedorCentral, BorderLayout.CENTER);
        this.add(contenedorBoton, BorderLayout.SOUTH);

        botonLogin.addActionListener(this::autenticarUsuario);
    }

    // ╔════════════════════════════════════════════════════════════╗
// ║              REGISTRO DE DATOS DE PRUEBA                   ║
// ╚════════════════════════════════════════════════════════════╝
// ╔════════════════════════════════════════════════════════════╗
// ║              REGISTRO DE DATOS DE PRUEBA                   ║
// ╚════════════════════════════════════════════════════════════╝

    GestorEstudiantes gestorEstudiantes= GestorEstudiantes.getInstancia();
    GestorProfesores gestorProfesores = GestorProfesores.getInstancia();
    GestorCursos gestorCursos = GestorCursos.getInstancia();
    GestorEvaluaciones gestorEvaluaciones = GestorEvaluaciones.getInstancia();


    // 🧑‍🎓 Estudiante
    Estudiante estudiantePrueba = new Estudiante(
            "Lucía", "González", "Ramírez", "lucia12345",
            "88889999", "lucia@email.com", "San José",
            "ClaveSegura123!", "Universidad de Costa Rica",
            new ArrayList<>(List.of("Java avanzado", "Diseño de interfaces"))
    );
boolean registrado=gestorEstudiantes.registrarEstudiante(estudiantePrueba);

    // 👨‍🏫 Profesor
    Profesor profesorPrueba = new Profesor(
            "Carlos", "Mora", "Soto", "profe12345",
            "89998888", "carlos@tec.ac.cr", "Cartago",
            "ClaveFuerte2025!",
            new ArrayList<>(List.of("Maestría en Educación", "Doctorado en Informática")),
            new ArrayList<>(List.of("Certificación Java", "Certificación en Didáctica"))
    );
boolean registrado2=gestorProfesores.registrarProfesor(profesorPrueba);

    // 📚 Curso
    Curso cursoPrueba = new Curso(
            "TEC001", "Programación OO", "Curso de objetos y clases",
            4, 2, 3, 20, PRESENCIAL, TEORICO
    );
    boolean registrado3 = gestorCursos.registrarCursos(cursoPrueba);

    // 👥 Grupo 1
    GrupoCurso grupoPrueba = new GrupoCurso(1, LocalDate.of(2025, 11, 1), LocalDate.of(2025, 12, 15), cursoPrueba);
    String idCurso = cursoPrueba.getIdentificacionCurso();
    GestorGruposCurso gestorGrupos = GestorGruposCurso.getInstancia();
    boolean grupo = gestorGrupos.agregarGrupo(idCurso, grupoPrueba);
    // 🔗 Asociación grupo–profesor
    boolean grupoProfesor = gestorProfesores.asociarGrupo(profesorPrueba, grupoPrueba);

    // 👥 Grupo 2
    GrupoCurso grupo2 = new GrupoCurso(2, LocalDate.of(2025, 11, 2), LocalDate.of(2025, 12, 16), cursoPrueba);
    boolean grupo2Registrado = gestorGrupos.agregarGrupo(idCurso, grupo2);
    boolean grupo2Profesor = gestorProfesores.asociarGrupo(profesorPrueba, grupo2);

    // 👥 Grupo 3
    GrupoCurso grupo3 = new GrupoCurso(3, LocalDate.of(2025, 11, 3), LocalDate.of(2025, 12, 17), cursoPrueba);
    boolean grupo3Registrado = gestorGrupos.agregarGrupo(idCurso, grupo3);
    boolean grupo3Profesor = gestorProfesores.asociarGrupo(profesorPrueba, grupo3);

    // 📚 Curso adicional
    Curso cursoExtra = new Curso(
            "TEC002", "Estructuras de Datos", "Curso sobre listas, pilas, colas y árboles",
            4, 2, 3, 25, PRESENCIAL, TEORICO
    );
    boolean registradoExtra = gestorCursos.registrarCursos(cursoExtra);


    GrupoCurso grupo4 = new GrupoCurso(1, LocalDate.of(2025, 11, 4), LocalDate.of(2025, 12, 18), cursoExtra);
    String idCursoExtra = cursoExtra.getIdentificacionCurso();
    boolean grupo4Registrado = gestorGrupos.agregarGrupo(idCursoExtra, grupo4);
    boolean grupo4Profesor = gestorProfesores.asociarGrupo(profesorPrueba, grupo4);


    GrupoCurso grupo5 = new GrupoCurso(2, LocalDate.of(2025, 11, 5), LocalDate.of(2025, 12, 19), cursoExtra);
    boolean grupo5Registrado = gestorGrupos.agregarGrupo(idCursoExtra, grupo5);
    boolean grupo5Profesor = gestorProfesores.asociarGrupo(profesorPrueba, grupo5);


    GrupoCurso grupo6 = new GrupoCurso(3, LocalDate.of(2025, 11, 6), LocalDate.of(2025, 12, 20), cursoExtra);
    boolean grupo6Registrado = gestorGrupos.agregarGrupo(idCursoExtra, grupo6);
    boolean grupo6Profesor = gestorProfesores.asociarGrupo(profesorPrueba, grupo6);


    GrupoCurso grupo7 = new GrupoCurso(4, LocalDate.of(2025, 11, 7), LocalDate.of(2025, 12, 21), cursoExtra);
    boolean grupo7Registrado = gestorGrupos.agregarGrupo(idCursoExtra, grupo7);
    boolean grupo7Profesor = gestorProfesores.asociarGrupo(profesorPrueba, grupo7);


    public void registrarEvaluacionSeleccionUnica() {
        List<String> opciones = List.of(
                "Ocultar detalles internos",
                "Reutilización de código",
                "Comportamiento dinámico",
                "Separación entre interfaz y implementación"
        );

        PreguntaSeleccionUnica pregunta = new PreguntaSeleccionUnica(
                1,
                "¿Qué representa el principio de encapsulamiento en POO?",
                5,
                opciones,
                "Ocultar detalles internos"
        );

        List<Pregunta> preguntas = new ArrayList<>();
        preguntas.add(pregunta);

        Evaluacion evaluacionSeleccionUnica = new Evaluacion(
                "Evaluación de POO - Selección Única",
                "Selecciona la opción correcta sobre principios de POO.",
                List.of("Comprender encapsulamiento", "Aplicar principios básicos de diseño"),
                15,
                false,
                false,
                preguntas,
                profesorPrueba // Asegúrate de que esté correctamente inicializado
        );

        // Validar si ya está registrada en el grupo
        boolean yaRegistrada = GestorEvaluaciones.getInstancia()
                .getEvaluacionesPorGrupo(grupoPrueba).stream()
                .anyMatch(e -> e.getNombre().equals("Evaluación de POO - Selección Única"));

        if (!yaRegistrada) {
            GestorEvaluaciones.getInstancia().registrarEvaluacion(evaluacionSeleccionUnica);

            LocalDateTime inicio = LocalDateTime.now();
            LocalDateTime fin = inicio.plusDays(7);

            GestorEvaluaciones.getInstancia().asociarEvaluacionAGrupo(
                    evaluacionSeleccionUnica.getIdEvaluacion(),
                    grupoPrueba,
                    inicio,
                    fin
            );
        }
    }










    // 📝 Evaluación Pareo

    public void registrarEvaluacionPareo() {
        List<String> objetivos = List.of(
                "Relacionar conceptos clave de POO",
                "Aplicar principios de encapsulamiento y herencia"
        );

        Map<String, String> pares = new HashMap<>();
        pares.put("Encapsulamiento", "Ocultar detalles internos");
        pares.put("Herencia", "Reutilización de código");
        pares.put("Polimorfismo", "Comportamiento dinámico");
        pares.put("Abstracción", "Separar interfaz de implementación");

        List<String> izquierda = new ArrayList<>(pares.keySet());
        List<String> derecha = new ArrayList<>(pares.values());

        PreguntaPareo pregunta = new PreguntaPareo(
                1,
                "Relaciona cada concepto de POO con su descripción correspondiente.",
                10,
                izquierda,
                derecha,
                pares
        );
        List<Pregunta> preguntas = new ArrayList<>();
        preguntas.add(pregunta);

        Evaluacion evaluacionPareo = new Evaluacion(
                "Evaluación de POO - Pareo",
                "Relaciona los conceptos clave de POO.",
                objetivos,
                20,
                false,
                false,
                preguntas,
                profesorPrueba
        );

        // ✅ Validar si ya está registrada en ese grupo
        boolean yaRegistrada = GestorEvaluaciones.getInstancia()
                .getEvaluacionesPorGrupo(grupoPrueba).stream()
                .anyMatch(e -> e.getNombre().equals("Evaluación de POO - Pareo"));

        if (!yaRegistrada) {
            GestorEvaluaciones.getInstancia().registrarEvaluacion(evaluacionPareo);

            LocalDateTime inicio = LocalDateTime.of(2025, 11, 8, 8, 0);
            LocalDateTime fin = LocalDateTime.of(2025, 12, 10, 23, 59);

            GestorEvaluaciones.getInstancia().asociarEvaluacionAGrupo(
                    evaluacionPareo.getIdEvaluacion(),
                    grupoPrueba,
                    inicio,
                    fin
            );
        }
    }






    // ╔════════════════════════════════════════════════════════════╗
    // ║                  AUTENTICACIÓN DE USUARIO                 ║
    // ╚════════════════════════════════════════════════════════════╝
    private void autenticarUsuario(ActionEvent evento) {
        try {
            String tipo = (String) comboTipoUsuario.getSelectedItem();
            String id = campoIdentificacion.getText().trim();
            String clave = new String(campoContrasena.getPassword()).trim();
            String mensaje = "";

            if ("Administrador".equals(tipo)) {
                if (id.equals(administrador.getIdentificacionPersonal()) &&
                        encriptar(clave).equals(administrador.getContrasenaEncriptada())) {

                    mensaje = "✅ Bienvenido, administrador.";
                    SwingUtilities.getWindowAncestor(this).dispose();
                    Main.abrirMenuAdministrador();
                } else {
                    mensaje = "❌ Credenciales de administrador incorrectas.";
                }

            } else if (id.equals(estudiantePrueba.getIdentificacionPersonal()) &&
                    encriptar(clave).equals(estudiantePrueba.getContrasenaEncriptada())) {

                Estudiante estudiante = gestor.consultarEstudiante(id);
                if (estudiante != null) {
                    String claveEncriptada = encriptar(clave);

                    if (claveEncriptada.equals(estudiante.getContrasenaEncriptada())) {
                        mostrarMensaje("🔐 Has ingresado con una contraseña temporal.\nDebes establecer una nueva contraseña.");
                        SwingUtilities.getWindowAncestor(this).dispose();
                        abrirPanelCambioContrasena(estudiante);
                        return;
                    }

                    if (estudiante.verificarCredenciales(id, clave).contains("exitosa")) {
                        mensaje = "✅ Bienvenido, " + estudiante.getNombre();
                        SwingUtilities.getWindowAncestor(this).dispose();
                        Main.abrirMenuEstudiante(estudiante);
                    } else {
                        mensaje = "❌ Credenciales de estudiante incorrectas.";
                    }
                }

            } else {
                Profesor profesor = gestor2.consultarProfesor(id);
                if (profesor != null) {
                    String claveEncriptada = encriptar(clave);

                    if (claveEncriptada.equals(profesor.getContrasenaEncriptada())) {
                        mostrarMensaje("🔐 Has ingresado con una contraseña temporal.\nDebes establecer una nueva contraseña.");
                        SwingUtilities.getWindowAncestor(this).dispose();
                        abrirPanelCambioContrasena(profesor);
                        return;
                    }

                    if (profesor.verificarCredenciales(id, clave).contains("exitosa")) {
                        mensaje = "✅ Bienvenido, " + profesor.getNombre();
                        SwingUtilities.getWindowAncestor(this).dispose();
                        Main.abrirMenuProfesor(profesor); // Asegúrate de tener este método
                    } else {
                        mensaje = "❌ Credenciales de profesor incorrectas.";
                    }
                } else {
                    mensaje = "❌ Usuario no encontrado.";
                }
            }

            mostrarMensaje(mensaje);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensaje("❌ Error interno: " + e.getMessage());
        }
    }


    private void recuperarContrasena(ActionEvent e) {
        String id = JOptionPane.showInputDialog(this, "Ingrese su identificación personal:");
        if (id == null || id.trim().isEmpty()) {
            mostrarMensaje("⚠️ Debe ingresar una identificación.");
            return;
        }

        Estudiante estudiante = gestor.consultarEstudiante(id.trim());
        if (estudiante == null) {
            mostrarMensaje("❌ No se encontró ningún usuario con esa identificación.");
            return;
        }

        try {
            String temporal = generarContrasenaTemporal();
            String encriptada = encriptar(temporal);
            estudiante.setContrasenaTemporal(encriptada); // método que debes agregar en Estudiante
            gestor.actualizarEstudiante(estudiante); // guarda el cambio

            GestorCorreos.enviarYRegistrar(
                    estudiante.getCorreoElectronico(),
                    "Recuperación de contraseña",
                    "Hola " + estudiante.getNombre() + ",\n\nTu contraseña temporal es:\n\n" +
                            temporal + "\n\nÚsala una vez para ingresar y establecer una nueva contraseña."
            );


            mostrarMensaje("📩 Se ha enviado una contraseña temporal a tu correo.");
        } catch (Exception ex) {
            ex.printStackTrace();
            mostrarMensaje("❌ Error al generar o enviar la contraseña.");
        }
    }

    private String generarContrasenaTemporal() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#%&!";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int index = (int) (Math.random() * caracteres.length());
            sb.append(caracteres.charAt(index));
        }
        return sb.toString();
    }


    private void abrirPanelCambioContrasena(Usuario usuario) {
        SwingUtilities.getWindowAncestor(this).dispose();
        new PanelCambioContrasena(usuario).setVisible(true);
    }



    // ╔════════════════════════════════════════════════════════════╗
    // ║                  VISUALIZACIÓN DE MENSAJES                ║
    // ╚════════════════════════════════════════════════════════════╝
    private void mostrarMensaje(String mensaje) {
        mensajeResultado.setText(mensaje);
        mensajeResultado.setForeground(mensaje.contains("✅") ? new Color(39, 174, 96) : new Color(231, 76, 60));
    }
}
