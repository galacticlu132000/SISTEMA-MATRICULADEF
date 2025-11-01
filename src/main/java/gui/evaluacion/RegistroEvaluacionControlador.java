package gui.evaluacion;
import evaluaciones.Evaluacion;
import evaluaciones.Pregunta;
import usuarios.Profesor;
import control.GestorEvaluaciones;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🎓 RegistroEvaluacionControlador                                          ║
 * ║                                                                            ║
 * ║ Ventana Swing para crear una evaluación con preguntas personalizadas.     ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class RegistroEvaluacionControlador extends JFrame {

    private JTextField campoNombre;
    private JTextArea campoInstrucciones;
    private JTextField campoDuracion;
    private DefaultListModel<String> modeloObjetivos;
    private JList<String> listaObjetivos;
    private JTextField campoNuevoObjetivo;
    private JCheckBox chkPreguntasAleatorias, chkOpcionesAleatorias;
    private JButton btnAgregarObjetivo, btnAgregarPregunta, btnGuardar;

    private DefaultListModel<String> modeloPreguntas;
    private JList<String> listaPreguntas;
    private List<Pregunta> preguntas = new ArrayList<>();

    private Profesor profesorActivo;

    public RegistroEvaluacionControlador(JFrame ventanaAnterior, Profesor profesor) {
        this.profesorActivo = profesor;
        setTitle("📝 Crear Evaluación");
        setSize(700, 600);
        setLocationRelativeTo(ventanaAnterior);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panel.setBackground(new Color(230, 250, 240));

        // ╔════════════════════════════════════════════════════════════╗
        // ║                  CAMPOS GENERALES                         ║
        // ╚════════════════════════════════════════════════════════════╝
        JPanel datos = new JPanel(new GridLayout(6, 2, 10, 10));
        datos.setOpaque(false);

        campoNombre = new JTextField();
        campoInstrucciones = new JTextArea(3, 20);
        campoInstrucciones.setLineWrap(true);
        campoInstrucciones.setWrapStyleWord(true);
        campoDuracion = new JTextField();

        chkPreguntasAleatorias = new JCheckBox("Preguntas en orden aleatorio");
        chkOpcionesAleatorias = new JCheckBox("Opciones en orden aleatorio");

        datos.add(new JLabel("📌 Nombre:")); datos.add(campoNombre);
        datos.add(new JLabel("📋 Instrucciones:")); datos.add(new JScrollPane(campoInstrucciones));
        datos.add(new JLabel("⏱️ Duración (min):")); datos.add(campoDuracion);
        datos.add(new JLabel("🔀 Aleatoriedad preguntas:")); datos.add(chkPreguntasAleatorias);
        datos.add(new JLabel("🔀 Aleatoriedad opciones:")); datos.add(chkOpcionesAleatorias);

        panel.add(datos, BorderLayout.NORTH);

        // ╔════════════════════════════════════════════════════════════╗
        // ║                  OBJETIVOS                                ║
        // ╚════════════════════════════════════════════════════════════╝
        modeloObjetivos = new DefaultListModel<>();
        listaObjetivos = new JList<>(modeloObjetivos);
        campoNuevoObjetivo = new JTextField();
        btnAgregarObjetivo = new JButton("➕ Agregar Objetivo");

        btnAgregarObjetivo.addActionListener(e -> {
            String texto = campoNuevoObjetivo.getText().trim();
            if (texto.length() >= 10 && texto.length() <= 40) {
                modeloObjetivos.addElement(texto);
                campoNuevoObjetivo.setText("");
            } else {
                mostrarAdvertencia("El objetivo debe tener entre 10 y 40 caracteres.");
            }
        });

        JPanel objetivosPanel = new JPanel(new BorderLayout());
        objetivosPanel.setOpaque(false);
        objetivosPanel.setBorder(BorderFactory.createTitledBorder("🎯 Objetivos de la evaluación"));
        objetivosPanel.add(new JScrollPane(listaObjetivos), BorderLayout.CENTER);

        JPanel agregarObjetivo = new JPanel(new BorderLayout());
        agregarObjetivo.setOpaque(false);
        agregarObjetivo.add(campoNuevoObjetivo, BorderLayout.CENTER);
        agregarObjetivo.add(btnAgregarObjetivo, BorderLayout.EAST);
        objetivosPanel.add(agregarObjetivo, BorderLayout.SOUTH);

        panel.add(objetivosPanel, BorderLayout.CENTER);

        // ╔════════════════════════════════════════════════════════════╗
        // ║                  PREGUNTAS                                ║
        // ╚════════════════════════════════════════════════════════════╝
        modeloPreguntas = new DefaultListModel<>();
        listaPreguntas = new JList<>(modeloPreguntas);
        btnAgregarPregunta = new JButton("➕ Agregar Pregunta");

        btnAgregarPregunta.addActionListener(e -> {
            DialogoAgregarPregunta dialogo = new DialogoAgregarPregunta(this);
            Pregunta nueva = dialogo.mostrarYObtenerPregunta();
            if (nueva != null) {
                preguntas.add(nueva);
                modeloPreguntas.addElement("[" + nueva.getTipo() + "] " + nueva.getDescripcion());
            }
        });

        JPanel preguntasPanel = new JPanel(new BorderLayout());
        preguntasPanel.setOpaque(false);
        preguntasPanel.setBorder(BorderFactory.createTitledBorder("📚 Preguntas agregadas"));
        preguntasPanel.add(new JScrollPane(listaPreguntas), BorderLayout.CENTER);
        preguntasPanel.add(btnAgregarPregunta, BorderLayout.SOUTH);

        panel.add(preguntasPanel, BorderLayout.SOUTH);

        // ╔════════════════════════════════════════════════════════════╗
        // ║                  BOTÓN GUARDAR                            ║
        // ╚════════════════════════════════════════════════════════════╝
        btnGuardar = new JButton("✅ Guardar Evaluación");
        btnGuardar.addActionListener(e -> guardarEvaluacion());

        JPanel pie = new JPanel();
        pie.setOpaque(false);
        pie.add(btnGuardar);

        add(panel, BorderLayout.CENTER);
        add(pie, BorderLayout.SOUTH);
    }

    private void guardarEvaluacion() {
        String nombre = campoNombre.getText().trim();
        String instrucciones = campoInstrucciones.getText().trim();
        String duracionStr = campoDuracion.getText().trim();

        if (nombre.length() < 5 || nombre.length() > 20) {
            mostrarAdvertencia("El nombre debe tener entre 5 y 20 caracteres.");
            return;
        }
        if (instrucciones.length() < 5 || instrucciones.length() > 400) {
            mostrarAdvertencia("Las instrucciones deben tener entre 5 y 400 caracteres.");
            return;
        }
        int duracion;
        try {
            duracion = Integer.parseInt(duracionStr);
            if (duracion < 1) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            mostrarAdvertencia("Duración inválida. Debe ser un número entero mayor o igual a 1.");
            return;
        }
        if (modeloObjetivos.isEmpty()) {
            mostrarAdvertencia("Debe agregar al menos un objetivo.");
            return;
        }
        if (preguntas.isEmpty()) {
            mostrarAdvertencia("Debe agregar al menos una pregunta.");
            return;
        }

        List<String> objetivos = new ArrayList<>();
        for (int i = 0; i < modeloObjetivos.size(); i++) {
            objetivos.add(modeloObjetivos.get(i));
        }

        Evaluacion evaluacion = new Evaluacion(
                nombre,
                instrucciones,
                objetivos,
                duracion,
                chkPreguntasAleatorias.isSelected(),
                chkOpcionesAleatorias.isSelected(),
                profesorActivo,
                preguntas
        );

        GestorEvaluaciones.getInstancia().registrarEvaluacion(evaluacion);
        JOptionPane.showMessageDialog(this, "✅ Evaluación registrada exitosamente.");
        dispose();
    }

    private void mostrarAdvertencia(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
}
