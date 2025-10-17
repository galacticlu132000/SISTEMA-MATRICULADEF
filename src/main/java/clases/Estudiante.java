package clases;

import java.util.ArrayList;

/**
 * ╔════════════════════════════════════════════════════╗
 * ║        Clase concreta que representa un            ║
 * ║        estudiante registrado en el sistema         ║
 * ╚════════════════════════════════════════════════════╝
 */
public class Estudiante extends Usuario {

    // ╔════════════════════════════════════════════════════╗
    // ║               Atributos específicos                ║
    // ╚════════════════════════════════════════════════════╝

    private String organizacionLaboral;
    private ArrayList<String> temasInteres;

    // ╔════════════════════════════════════════════════════╗
    // ║                    Constructor                     ║
    // ╚════════════════════════════════════════════════════╝

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

        super(nombre, primerApellido, segundoApellido, identificacionPersonal,
                numeroTelefono, correoElectronico, direccionFisica, contrasenaPlano);

        validarDatosEstudiante(organizacionLaboral, temasInteres);

        this.organizacionLaboral = organizacionLaboral;
        this.temasInteres = temasInteres;
    }

    // ╔════════════════════════════════════════════════════╗
    // ║               Validación específica                ║
    // ╚════════════════════════════════════════════════════╝

    private void validarDatosEstudiante(String organizacion, ArrayList<String> temas) {
        if (organizacion == null || organizacion.length() > 40)
            throw new IllegalArgumentException("La organización no puede exceder los 40 caracteres.");

        if (temas == null || temas.isEmpty())
            throw new IllegalArgumentException("Debe especificar al menos un tema de interés.");

        for (String tema : temas) {
            if (tema.length() < 5 || tema.length() > 30)
                throw new IllegalArgumentException("Cada tema de interés debe tener entre 5 y 30 caracteres.");
        }
    }

    // ╔════════════════════════════════════════════════════╗
    // ║                  Getters públicos                  ║
    // ╚════════════════════════════════════════════════════╝

    public String getOrganizacionLaboral() {
        return organizacionLaboral;
    }

    public ArrayList<String> getTemasInteres() {
        return temasInteres;
    }

    // ╔════════════════════════════════════════════════════╗
    // ║            Representación en texto                 ║
    // ╚════════════════════════════════════════════════════╝

    @Override
    public String toString() {
        return super.toString() + " | " + organizacionLaboral + " | Temas: " + String.join(", ", temasInteres);
    }
}
