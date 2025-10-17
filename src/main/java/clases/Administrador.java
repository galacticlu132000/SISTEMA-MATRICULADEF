// ╔════════════════════════════════════════════════════╗
// ║     Clase concreta que representa al administrador  ║
// ║     del sistema, con acceso a funciones de gestión  ║
// ╚════════════════════════════════════════════════════╝

package clases;

public class Administrador extends Usuario {

    // ╔════════════════════════════════════════════════════╗
    // ║                    Constructor                     ║
    // ╚════════════════════════════════════════════════════╝
    public Administrador(String nombre,
                         String primerApellido,
                         String segundoApellido,
                         String identificacionPersonal,
                         String numeroTelefono,
                         String correoElectronico,
                         String direccionFisica,
                         String contrasenaPlano) {
        super(nombre, primerApellido, segundoApellido, identificacionPersonal,
                numeroTelefono, correoElectronico, direccionFisica, contrasenaPlano);
    }

    // ╔════════════════════════════════════════════════════╗
    // ║            Representación en texto                 ║
    // ╚════════════════════════════════════════════════════╝
    @Override
    public String toString() {
        return "Administrador: " + getNombreCompleto() + " [" + getIdentificacionPersonal() + "]";
    }
}
