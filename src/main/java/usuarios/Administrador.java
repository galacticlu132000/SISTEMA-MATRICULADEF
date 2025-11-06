/*
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║                  SISTEMA DE MATRÍCULA Y CALIFICACIONES (TEC)               ║
 * ║ Módulo: usuarios                                                           ║
 * ║ Archivo: Administrador.java                                                ║
 * ║ Autoría: Lucía y Karla                                                             ║
 * ║ Propósito: Representa al administrador del sistema con funciones de gestión║
 * ║ Dependencias: Usuario.java                                                 ║
 * ║ Versión: 1.0                                                               ║
 * ║ Última actualización: 2025-11-05                                           ║
 * ║ Notas: El administrador puede autenticarse, gestionar usuarios y generar   ║
 * ║        reportes.                                                           ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */

package usuarios;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║                         CLASE CONCRETA: ADMINISTRADOR                     ║
 * ║ Representa al administrador del sistema con acceso a funciones de gestión.║
 * ║ Hereda de {@link Usuario} y puede autenticarse, gestionar usuarios y       ║
 * ║ generar reportes.                                                          ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 *
 * <p><b>Responsabilidades:</b></p>
 * <ul>
 *   <li>Autenticación en el sistema.</li>
 *   <li>CRUD de estudiantes y profesores.</li>
 *   <li>Registro de cursos y grupos.</li>
 *   <li>Generación de reportes y estadísticas.</li>
 * </ul>
 *
 * <p><b>Ejemplo de uso:</b></p>
 * <pre>{@code
 * Administrador admin = new Administrador(
 *     "Lucía", "Gómez", "Ramírez",
 *     "123456789", "88887777",
 *     "lucia@tec.ac.cr", "San José, Costa Rica",
 *     "ContrasenaSegura123!"
 * );
 * System.out.println(admin);
 * }</pre>
 *
 * @author Lucía y Karla
 * @version 1.0
 * @since 1.0
 */
public class Administrador extends Usuario {

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                          CONSTRUCTOR                              ║
    // ╚════════════════════════════════════════════════════════════════════╝

    /**
     * Crea una nueva instancia de {@code Administrador}.
     *
     * @param nombre Nombre del administrador (2–20 caracteres).
     * @param primerApellido Primer apellido (2–20 caracteres).
     * @param segundoApellido Segundo apellido (2–20 caracteres).
     * @param identificacionPersonal Identificación única (≥ 9 caracteres).
     * @param numeroTelefono Teléfono (≥ 8 caracteres).
     * @param correoElectronico Correo electrónico válido y único.
     * @param direccionFisica Dirección física (5–60 caracteres).
     * @param contrasenaPlano Contraseña en texto plano, será encriptada.
     * @throws IllegalArgumentException Si algún parámetro no cumple las reglas de validación.
     */
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

    /**
     * Devuelve una representación textual del administrador.
     *
     * @return Cadena con el nombre completo y la identificación personal.
     */
    @Override
    public String toString() {
        return "Administrador: " + getNombreCompleto() +
                " [" + getIdentificacionPersonal() + "]";
    }
}
