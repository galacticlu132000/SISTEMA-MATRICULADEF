package usuarios;

import seguridad.Encriptador;

import java.util.ArrayList;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║                         CLASE CONCRETA: ESTUDIANTE                        ║
 * ║ Representa un estudiante registrado en el sistema.                        ║
 * ║ Hereda de Usuario e incluye organización laboral y temas de interés.     ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class Profesor extends Usuario {

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                      ATRIBUTOS ESPECÍFICOS                         ║
    // ╚════════════════════════════════════════════════════════════════════╝

    private ArrayList<String> titulosObtenidos;
    private ArrayList<String> certificacionesEstudio;

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                          CONSTRUCTOR                              ║
    // ╚════════════════════════════════════════════════════════════════════╝

    public Profesor(String nombre,
                      String primerApellido,
                      String segundoApellido,
                      String identificacionPersonal,
                      String numeroTelefono,
                      String correoElectronico,
                      String direccionFisica,
                      String contrasenaPlano,
                      ArrayList<String>titulosObtenidos,
                      ArrayList<String> certificacionesEstudio) {

        super(nombre, primerApellido, segundoApellido,
                identificacionPersonal, numeroTelefono,
                correoElectronico, direccionFisica, contrasenaPlano);

        validarDatosProfesor(titulosObtenidos, certificacionesEstudio);

        this.titulosObtenidos = titulosObtenidos;
        this.certificacionesEstudio = certificacionesEstudio;
    }

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                    VALIDACIÓN ESPECÍFICA                          ║
    // ╚════════════════════════════════════════════════════════════════════╝

    private void validarDatosProfesor( ArrayList<String> titulosObtenidos, ArrayList<String> certificacionesEstudio) {
        if (titulosObtenidos == null || titulosObtenidos.isEmpty()) {
            throw new IllegalArgumentException("❌ Debe especificar al menos un titulo");}

            for (String titulo : titulosObtenidos) {
                if (titulo.length() < 5 || titulo.length() > 40)
                    throw new IllegalArgumentException("❌ Cada tema de interés debe tener entre 5 y 40 caracteres.");
            }

            if (certificacionesEstudio == null || certificacionesEstudio.isEmpty())
                throw new IllegalArgumentException("❌ Debe especificar al menos una cettificacion.");

            for (String certificacion : certificacionesEstudio) {
                if (certificacion.length() < 5 || certificacion.length() > 40)
                    throw new IllegalArgumentException("❌ Cada tema de interés debe tener entre 5 y 40 caracteres.");
            }
        }


    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                            GETTERS                                ║
    // ╚════════════════════════════════════════════════════════════════════╝

    public ArrayList<String> getTitulos() {
        return titulosObtenidos;
    }

    public ArrayList<String> getCertificaciones() {
        return certificacionesEstudio;
    }

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                            SETTERS                                ║
    // ╚════════════════════════════════════════════════════════════════════╝


    public void setTitulosObtenidos(ArrayList<String> titulos) {
        if (titulos== null || titulos.isEmpty())
            throw new IllegalArgumentException("❌ Debe especificar al menos un tema de interés.");

        for (String titulo: titulos) {
            if (titulo.length() < 5 || titulo.length() > 40)
                throw new IllegalArgumentException("❌ Cada titulo obtenido debe tener entre 5 y 40 caracteres.");
        }

        this.titulosObtenidos = titulos;
    }

    public void setCertificacionesEstudio(ArrayList<String> certificaciones) {
        if (certificaciones== null || certificaciones.isEmpty())
            throw new IllegalArgumentException("❌ Debe especificar al menos un tema de interés.");

        for (String certificacion: certificaciones) {
            if (certificacion.length() < 5 || certificacion.length() > 40)
                throw new IllegalArgumentException("❌ Cada certificacion debe tener entre 5 y 40 caracteres.");
        }

        this.certificacionesEstudio = certificaciones;
    }

    private String contrasenaTemporal;

    public void setContrasenaTemporal(String encriptada) {
        this.contrasenaTemporal = encriptada;
    }

    public boolean esContrasenaTemporalValida(String ingresada) {
        return Encriptador.verificar(ingresada, contrasenaTemporal);
    }


    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                   REPRESENTACIÓN EN TEXTO                         ║
    // ╚════════════════════════════════════════════════════════════════════╝

    @Override
    public String toString() {
        return super.toString() +
                " | Titulos: " + String.join(", ", titulosObtenidos)+
                " | Certificaciones: " + String.join(", ", certificacionesEstudio);
    }
}
