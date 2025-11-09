/*
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║                  SISTEMA DE MATRÍCULA Y CALIFICACIONES (TEC)               ║
 * ║ Módulo: usuarios                                                           ║
 * ║ Archivo: Estudiante.java                                                   ║
 * ║ Autoría: Lucía                                                             ║
 * ║ Propósito: Representa un estudiante registrado en el sistema.              ║
 * ║ Dependencias: Usuario.java, seguridad.Encriptador                          ║
 * ║ Versión: 1.0                                                               ║
 * ║ Última actualización: 2025-11-05                                           ║
 * ║ Notas: Incluye validaciones específicas y manejo de contraseña temporal.   ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */

package usuarios;

import seguridad.Encriptador;
import java.util.ArrayList;
import java.util.List;

import usuarios.GrupoCurso;
/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║                         CLASE CONCRETA: ESTUDIANTE                        ║
 * ║ Representa un estudiante registrado en el sistema.                        ║
 * ║ Hereda de {@link Usuario} e incluye organización laboral y temas de interés.║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 *
 * <p><b>Responsabilidades:</b></p>
 * <ul>
 *   <li>Autenticarse en el sistema.</li>
 *   <li>Almacenar información personal y académica.</li>
 *   <li>Validar organización laboral y temas de interés.</li>
 *   <li>Manejar contraseñas temporales para recuperación de acceso.</li>
 * </ul>
 *
 * <p><b>Ejemplo de uso:</b></p>
 * <pre>{@code
 * ArrayList<String> intereses = new ArrayList<>();
 * intereses.add("Programación");
 * intereses.add("Bases de Datos");
 *
 * Estudiante estudiante = new Estudiante(
 *     "Lucía", "Gómez", "Ramírez",
 *     "123456789", "88887777",
 *     "lucia@tec.ac.cr", "San José, Costa Rica",
 *     "ContrasenaSegura123!",
 *     "TEC",
 *     intereses
 * );
 * System.out.println(estudiante);
 * }</pre>
 *
 * @author Lucía y Karla
 * @version 1.0
 * @since 1.0
 */
public class Estudiante extends Usuario {

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                      ATRIBUTOS ESPECÍFICOS                         ║
    // ╚════════════════════════════════════════════════════════════════════╝
    private String organizacionLaboral;
    private ArrayList<String> temasInteres;
    private String contrasenaTemporal;
    private List<GrupoCurso> gruposMatriculados = new ArrayList<>();

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                          CONSTRUCTOR                              ║
    // ╚════════════════════════════════════════════════════════════════════╝

    /**
     * Crea una nueva instancia de {@code Estudiante}.
     *
     * @param nombre Nombre del estudiante (2–20 caracteres).
     * @param primerApellido Primer apellido (2–20 caracteres).
     * @param segundoApellido Segundo apellido (2–20 caracteres).
     * @param identificacionPersonal Identificación única (≥ 9 caracteres).
     * @param numeroTelefono Teléfono (≥ 8 caracteres).
     * @param correoElectronico Correo electrónico válido y único.
     * @param direccionFisica Dirección física (5–60 caracteres).
     * @param contrasenaPlano Contraseña en texto plano, será encriptada.
     * @param organizacionLaboral Organización laboral (≤ 40 caracteres).
     * @param temasInteres Lista de temas de interés (cada uno 5–30 caracteres).
     * @throws IllegalArgumentException Si algún parámetro no cumple las reglas.
     */
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

    /**
     * Valida los datos específicos de un estudiante.
     *
     * @param organizacion Organización laboral (≤ 40 caracteres).
     * @param temas Lista de temas de interés (cada uno 5–30 caracteres).
     * @throws IllegalArgumentException Si los datos no cumplen las reglas.
     */
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

    public String getOrganizacionLaboral() { return organizacionLaboral; }
    public ArrayList<String> getTemasInteres() { return temasInteres; }

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

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                   CONTRASEÑA TEMPORAL                             ║
    // ╚════════════════════════════════════════════════════════════════════╝

    /**
     * Establece una contraseña temporal encriptada.
     *
     * @param encriptada Contraseña temporal encriptada.
     */
    public void setContrasenaTemporal(String encriptada) {
        this.contrasenaTemporal = encriptada;
    }

    /**
     * Verifica si la contraseña temporal ingresada coincide con la almacenada.
     *
     * @param ingresada Contraseña ingresada por el usuario.
     * @return {@code true} si coincide, {@code false} en caso contrario.
     */
    public boolean esContrasenaTemporalValida(String ingresada) {
        return Encriptador.verificar(ingresada, contrasenaTemporal);
    }


    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                   MATRÍCULA EN GRUPOS                             ║
    // ╚════════════════════════════════════════════════════════════════════╝

    /**
     * Matricula al estudiante en un grupo de curso.
     *
     * @param grupo GrupoCurso al que se desea matricular.
     * @return true si la matrícula fue exitosa, false si ya estaba matriculado.
     */

    public boolean matricularEnGrupo(GrupoCurso grupo) {
        if (gruposMatriculados.contains(grupo)) {
            System.out.println("⚠️ Ya estás matriculado en este grupo.");
            return false;
        }
        gruposMatriculados.add(grupo);
        System.out.println("✅ Matrícula registrada en grupo: " + grupo.getNombre());
        return true;
    }

    /**
     * Verifica si el estudiante ya está matriculado en un grupo específico.
     *
     * @param idCurso ID del curso.
     * @param idGrupo Número de grupo.
     * @return true si está matriculado, false si no.
     */
    public boolean estaMatriculadoEn(String idCurso, int idGrupo) {
        for (GrupoCurso g : gruposMatriculados) {
            if (g.getCurso().getIdentificacionCurso().equals(idCurso) && g.getIdGrupo() == idGrupo) {
                return true;
            }
        }
        return false;
    }



// ╔════════════════════════════════════════════════════════════════════╗
    // ║                   REPRESENTACIÓN EN TEXTO                         ║
    // ╚════════════════════════════════════════════════════════════════════╝

    /**
     * Devuelve una representación textual del estudiante.
     *
     * @return Cadena con datos heredados y atributos específicos.
     */
    @Override
    public String toString() {
        return super.toString() +
                " | Organización: " + organizacionLaboral +
                " | Temas: " + String.join(", ", temasInteres);
    }
}