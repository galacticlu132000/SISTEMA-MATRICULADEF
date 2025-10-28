package gui.login;
import control.GestorProfesores;

import usuarios.Estudiante;
import usuarios.Administrador;
import usuarios.Profesor;
import control.GestorEstudiantes;
import main.Main;
import usuarios.Usuario;
import utilidades.correo.Correo;
import utilidades.correo.GestorCorreos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import static seguridad.Encriptador.encriptar;

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
    // ║              REGISTRO DE ESTUDIANTE DE PRUEBA             ║
    // ╚════════════════════════════════════════════════════════════╝



    Estudiante estudiantePrueba = new Estudiante(
                        "Lucía", "González", "Ramírez", "lucia12345",
                        "88889999", "lucia@email.com", "San José",
                        "ClaveSegura123!", "Universidad de Costa Rica",
                        new ArrayList<>(List.of("Java avanzado", "Diseño de interfaces"))
                );
   boolean r=gestor.registrarEstudiante(estudiantePrueba);

    Profesor profesorPrueba = new Profesor(
            "Carlos", "Mora", "Soto", "profe12345",
            "89998888", "carlos@tec.ac.cr", "Cartago",
            "ClaveFuerte2025!",
            new ArrayList<>(List.of("Maestría en Educación", "Doctorado en Informática")),
            new ArrayList<>(List.of("Certificación Java", "Certificación en Didáctica"))
    );
 boolean r2=gestor2.registrarProfesor(profesorPrueba);



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

                    if (claveEncriptada.equals(estudiante.getContrasenaTemporal())) {
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

                    if (claveEncriptada.equals(profesor.getContrasenaTemporal())) {
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
