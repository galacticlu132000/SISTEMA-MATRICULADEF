package main;

import gui.login.ControladorLogin;
import gui.admin.MenuAdministradorControlador;
import gui.estudiante.MenuEstudianteControlador;
import gui.profesor.MenuProfesorControlador;
import usuarios.Estudiante;
import usuarios.Profesor;

import javax.swing.*;

/**
 * ╔════════════════════════════════════════════════════════════╗
 * ║ 🚀 Main                                                     ║
 * ║                                                            ║
 * ║ Clase principal que lanza la interfaz Swing y permite      ║
 * ║ cambiar entre ventanas según el tipo de usuario.           ║
 * ╚════════════════════════════════════════════════════════════╝
 */
public class Main {

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  MÉTODO PRINCIPAL DE INICIO                ║
    // ╚════════════════════════════════════════════════════════════╝
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::abrirLogin);
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  VENTANA DE LOGIN DE USUARIO               ║
    // ╚════════════════════════════════════════════════════════════╝
    public static void abrirLogin() {
        JFrame loginFrame = new JFrame("🔐 Login de Usuario");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(500, 400);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setContentPane(new ControladorLogin()); // ✅ ControladorLogin ahora es JPanel
        loginFrame.setVisible(true);
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  VENTANA DE ADMINISTRADOR                  ║
    // ╚════════════════════════════════════════════════════════════╝
    public static void abrirMenuAdministrador() {
        JFrame adminFrame = new MenuAdministradorControlador(); // ✅ debe extender JFrame
        adminFrame.setVisible(true);
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  VENTANA DE ESTUDIANTE                     ║
    // ╚════════════════════════════════════════════════════════════╝
    public static void abrirMenuEstudiante(Estudiante estudiante) {
        JFrame estudianteFrame = new MenuEstudianteControlador(estudiante); // ✅ debe extender JFrame
        estudianteFrame.setVisible(true);
    }

    public static void abrirMenuProfesor(Profesor profesor) {
        JFrame profesorFrame = new MenuProfesorControlador(profesor); // ✅ debe extender JFrame
        profesorFrame.setVisible(true);
    }
}
