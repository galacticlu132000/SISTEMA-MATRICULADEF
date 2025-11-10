package gui.login;

import gui.estudiante.MenuEstudianteControlador;
import gui.profesor.MenuProfesorControlador;
import usuarios.Usuario;

import javax.swing.*;
import java.awt.*;

import static main.Main.abrirMenuEstudiante;
import static main.Main.abrirMenuProfesor;
import static seguridad.Encriptador.encriptar;

public class PanelCambioContrasena extends JFrame {

    public PanelCambioContrasena(Object usuario) {
        setTitle("🔐 Cambio de contraseña");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JLabel label = new JLabel("🛡️ Ingresa tu nueva contraseña:");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));

        JPasswordField campoNueva = new JPasswordField();
        campoNueva.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));

        JButton botonGuardar = new JButton("💾 Guardar nueva contraseña");
        botonGuardar.setBackground(new Color(220, 255, 220));
        botonGuardar.setFocusPainted(false);

        JLabel mensaje = new JLabel(" ");
        mensaje.setHorizontalAlignment(SwingConstants.CENTER);
        mensaje.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));

        botonGuardar.addActionListener(e -> {
            String nueva = new String(campoNueva.getPassword()).trim();
            if (nueva.length() < 6) {
                mensaje.setText("⚠️ La contraseña debe tener al menos 6 caracteres.");
                return;
            }

            String nuevaEncriptada = encriptar(nueva);

            if (usuario instanceof usuarios.Estudiante estudiante) {
                estudiante.setContrasenaEncriptada(nuevaEncriptada);
                mensaje.setText("✅ Contraseña actualizada correctamente.");
                dispose();
                abrirMenuEstudiante(estudiante);
            } else if (usuario instanceof usuarios.Profesor profesor) {
                profesor.setContrasenaEncriptada(nuevaEncriptada);
                mensaje.setText("✅ Contraseña actualizada correctamente.");
                dispose();
                abrirMenuProfesor(profesor);
            } else {
                mensaje.setText("❌ Tipo de usuario no reconocido.");
            }
        });

        add(label);
        add(campoNueva);
        add(botonGuardar);
        add(mensaje);
    }
}
