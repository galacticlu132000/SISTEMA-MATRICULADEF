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
public class Estudiante extends Usuario {

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                      ATRIBUTOS ESPECÍFICOS                         ║
    // ╚════════════════════════════════════════════════════════════════════╝

    private String organizacionLaboral;
    private ArrayList<String> temasInteres;

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                          CONSTRUCTOR                              ║
    // ╚════════════════════════════════════════════════════════════════════╝

    public Estudiante(String nombre,
                      String primerApellido,
                      String segundoApellido,
                      String identificacionPersonal,
                      String numeroTelefono,
                      String correoElectronico,
                      String direccionFisica,
                      String contrasenaPlano,
                      String organizacionLaboral,
                      ArrayList<String> temasInteres) {

        super(nombre, primerApellido, segundoApellido,
                identificacionPersonal, numeroTelefono,
                correoElectronico, direccionFisica, contrasenaPlano);

        validarDatosEstudiante(organizacionLaboral, temasInteres);

        this.organizacionLaboral = organizacionLaboral;
        this.temasInteres = temasInteres;
    }

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                    VALIDACIÓN ESPECÍFICA                          ║
    // ╚════════════════════════════════════════════════════════════════════╝

    private void validarDatosEstudiante(String organizacion, ArrayList<String> temas) {
        if (organizacion == null || organizacion.length() > 40)
            throw new IllegalArgumentException("❌ La organización no puede exceder los 40 caracteres.");

        if (temas == null || temas.isEmpty())
            throw new IllegalArgumentException("❌ Debe especificar al menos un tema de interés.");

        for (String tema : temas) {
            if (tema.length() < 5 || tema.length() > 30)
                throw new IllegalArgumentException("❌ Cada tema de interés debe tener entre 5 y 30 caracteres.");
        }
    }

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                            GETTERS                                ║
    // ╚════════════════════════════════════════════════════════════════════╝

    public String getOrganizacionLaboral() {
        return organizacionLaboral;
    }

    public ArrayList<String> getTemasInteres() {
        return temasInteres;
    }

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                            SETTERS                                ║
    // ╚════════════════════════════════════════════════════════════════════╝

    public void setOrganizacionLaboral(String organizacionLaboral) {
        if (organizacionLaboral == null || organizacionLaboral.length() > 40)
            throw new IllegalArgumentException("❌ La organización no puede exceder los 40 caracteres.");
        this.organizacionLaboral = organizacionLaboral;
    }

    public void setTemasInteres(ArrayList<String> temasInteres) {
        if (temasInteres == null || temasInteres.isEmpty())
            throw new IllegalArgumentException("❌ Debe especificar al menos un tema de interés.");

        for (String tema : temasInteres) {
            if (tema.length() < 5 || tema.length() > 30)
                throw new IllegalArgumentException("❌ Cada tema de interés debe tener entre 5 y 30 caracteres.");
        }

        this.temasInteres = temasInteres;
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
                " | Organización: " + organizacionLaboral +
                " | Temas: " + String.join(", ", temasInteres);
    }
}
