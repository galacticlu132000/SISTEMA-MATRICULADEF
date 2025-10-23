package usuarios;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║                         CLASE CONCRETA: ADMINISTRADOR                     ║
 * ║ Representa al administrador del sistema con acceso a funciones de gestión.║
 * ║ Hereda de Usuario y puede autenticarse, gestionar usuarios y generar      ║
 * ║ reportes.                                                                 ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class Administrador extends Usuario {

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                          CONSTRUCTOR                              ║
    // ╚════════════════════════════════════════════════════════════════════╝

    public Administrador(String nombre,
                         String primerApellido,
                         String segundoApellido,
                         String identificacionPersonal,
                         String numeroTelefono,
                         String correoElectronico,
                         String direccionFisica,
                         String contrasenaPlano) {

        super(nombre, primerApellido, segundoApellido,
                identificacionPersonal, numeroTelefono,
                correoElectronico, direccionFisica, contrasenaPlano);
    }

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                   REPRESENTACIÓN EN TEXTO                         ║
    // ╚════════════════════════════════════════════════════════════════════╝

    @Override
    public String toString() {
        return "Administrador: " + getNombreCompleto() +
                " [" + getIdentificacionPersonal() + "]";
    }
}
