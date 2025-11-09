/*
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║                  SISTEMA DE MATRÍCULA Y CALIFICACIONES (TEC)               ║
 * ║ Módulo: usuarios                                                           ║
 * ║ Archivo: Profesor.java                                                     ║
 * ║ Autoría: Lucía y Karla                                                     ║
 * ║ Propósito: Representa un profesor registrado en el sistema.                ║
 * ║ Dependencias: Usuario.java, GrupoCurso.java, seguridad.Encriptador         ║
 * ║ Versión: 1.0                                                               ║
 * ║ Última actualización: 2025-11-05                                           ║
 * ║ Notas: Incluye validaciones específicas y asociación de grupos.            ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */

package usuarios;

import seguridad.Encriptador;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║                         CLASE CONCRETA: PROFESOR                          ║
 * ║ Representa un profesor registrado en el sistema.                          ║
 * ║ Hereda de {@link Usuario} e incluye títulos, certificaciones y grupos.    ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 *
 * <p><b>Responsabilidades:</b></p>
 * <ul>
 *   <li>Autenticarse en el sistema.</li>
 *   <li>Almacenar títulos y certificaciones.</li>
 *   <li>Asociar grupos de cursos que imparte.</li>
 *   <li>Manejar contraseñas temporales para recuperación de acceso.</li>
 * </ul>
 *
 * <p><b>Ejemplo de uso:</b></p>
 * <pre>{@code
 * ArrayList<String> titulos = new ArrayList<>();
 * titulos.add("Licenciatura en Computación");
 *
 * ArrayList<String> certificaciones = new ArrayList<>();
 * certificaciones.add("Certificación Java SE");
 *
 * Profesor profesor = new Profesor(
 *     "Carlos", "Méndez", "Soto",
 *     "987654321", "88889999",
 *     "carlos@tec.ac.cr", "Cartago, Costa Rica",
 *     "ContrasenaSegura123!",
 *     titulos,
 *     certificaciones
 * );
 *
 * GrupoCurso grupo = new GrupoCurso(1, LocalDate.now(), LocalDate.now().plusMonths(4), curso);
 * profesor.agregarGrupo(grupo);
 * System.out.println(profesor);
 * }</pre>
 *
 * @author Lucía y Karla
 * @version 1.0
 * @since 1.0
 */
public class Profesor extends Usuario {

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                      ATRIBUTOS ESPECÍFICOS                         ║
    // ╚════════════════════════════════════════════════════════════════════╝
    private ArrayList<String> titulosObtenidos;
    private ArrayList<String> certificacionesEstudio;
    private String contrasenaTemporal;
    private ArrayList<GrupoCurso> gruposAsignados = new ArrayList<>();

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                          CONSTRUCTOR                              ║
    // ╚════════════════════════════════════════════════════════════════════╝

    /**
     * Crea una nueva instancia de {@code Profesor}.
     *
     * @param nombre Nombre del profesor (2–20 caracteres).
     * @param primerApellido Primer apellido (2–20 caracteres).
     * @param segundoApellido Segundo apellido (2–20 caracteres).
     * @param identificacionPersonal Identificación única (≥ 9 caracteres).
     * @param numeroTelefono Teléfono (≥ 8 caracteres).
     * @param correoElectronico Correo electrónico válido y único.
     * @param direccionFisica Dirección física (5–60 caracteres).
     * @param contrasenaPlano Contraseña en texto plano, será encriptada.
     * @param titulosObtenidos Lista de títulos obtenidos (cada uno 5–40 caracteres).
     * @param certificacionesEstudio Lista de certificaciones (cada una 5–40 caracteres).
     * @throws IllegalArgumentException Si algún parámetro no cumple las reglas.
     */
    public Profesor(String nombre,
                    String primerApellido,
                    String segundoApellido,
                    String identificacionPersonal,
                    String numeroTelefono,
                    String correoElectronico,
                    String direccionFisica,
                    String contrasenaPlano,
                    ArrayList<String> titulosObtenidos,
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

    /**
     * Valida los títulos y certificaciones de un profesor.
     *
     * @param titulosObtenidos Lista de títulos (cada uno 5–40 caracteres).
     * @param certificacionesEstudio Lista de certificaciones (cada una 5–40 caracteres).
     * @throws IllegalArgumentException Si los datos no cumplen las reglas.
     */
    private void validarDatosProfesor(ArrayList<String> titulosObtenidos, ArrayList<String> certificacionesEstudio) {
        if (titulosObtenidos == null || titulosObtenidos.isEmpty()) {
            throw new IllegalArgumentException("❌ Debe especificar al menos un título.");
        }
        for (String titulo : titulosObtenidos) {
            if (titulo.length() < 5 || titulo.length() > 40)
                throw new IllegalArgumentException("❌ Cada título debe tener entre 5 y 40 caracteres.");
        }

        if (certificacionesEstudio == null || certificacionesEstudio.isEmpty()) {
            throw new IllegalArgumentException("❌ Debe especificar al menos una certificación.");
        }
        for (String certificacion : certificacionesEstudio) {
            if (certificacion.length() < 5 || certificacion.length() > 40)
                throw new IllegalArgumentException("❌ Cada certificación debe tener entre 5 y 40 caracteres.");
        }
    }

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                            GETTERS                                ║
    // ╚════════════════════════════════════════════════════════════════════╝
    public ArrayList<String> getTitulos() { return titulosObtenidos; }
    public ArrayList<String> getCertificaciones() { return certificacionesEstudio; }
    public ArrayList<GrupoCurso> getGruposAsignados() { return new ArrayList<>(gruposAsignados); }

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                            SETTERS                                ║
    // ╚════════════════════════════════════════════════════════════════════╝
    public void setTitulosObtenidos(ArrayList<String> titulos) {
        validarDatosProfesor(titulos, certificacionesEstudio);
        this.titulosObtenidos = titulos;
    }
    public void setCertificacionesEstudio(ArrayList<String> certificaciones) {
        if (certificaciones == null || certificaciones.isEmpty())
            throw new IllegalArgumentException("❌ Debe especificar al menos una certificación.");

        for (String certificacion : certificaciones) {
            if (certificacion.length() < 5 || certificacion.length() > 40)
                throw new IllegalArgumentException("❌ Cada certificación debe tener entre 5 y 40 caracteres.");
        }

        this.certificacionesEstudio = certificaciones;
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
    // ║                 ASOCIACIÓN DE GRUPOS A PROFESOR                    ║
    // ╚════════════════════════════════════════════════════════════════════╝

    /**

     */

    /**
     * Asigna un grupo al profesor si no estaba previamente asociados
     * @param grupo Grupo a asociar.
     * @return {@code true} si se agregó, {@code false} si ya estaba.
     */
    public boolean agregarGrupo(GrupoCurso grupo) {
        if (grupo == null) return false;
        if (!gruposAsignados.contains(grupo)) {
            gruposAsignados.add(grupo);
            return true;
        }
        return false;
    }

    /**
     * Elimina un grupo previamente asignado al profesor.
     *
     * @param grupo Grupo a eliminar.
     * @return {@code true} si se eliminó, {@code false} si no estaba.
     */
    public boolean eliminarGrupo(GrupoCurso grupo) {
        return gruposAsignados.remove(grupo);
    }

    // ╔════════════════════════════════════════════════════════════════════╗
    // ║                   REPRESENTACIÓN EN TEXTO                         ║
    // ╚════════════════════════════════════════════════════════════════════╝

    /**
     * Devuelve una representación textual del profesor.
     *
     * @return Cadena con datos heredados, títulos y certificaciones.
     */
    @Override
    public String toString() {
        return super.toString() +
                " | Títulos: " + String.join(", ", titulosObtenidos) +
                " | Certificaciones: " + String.join(", ", certificacionesEstudio);
    }
}

