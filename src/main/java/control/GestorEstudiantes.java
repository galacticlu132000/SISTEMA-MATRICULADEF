package control;

// ╔════════════════════════════════════════════════════╗
// ║        Clase controladora para gestión CRUD         ║
// ║        de estudiantes registrados en el sistema     ║
// ╚════════════════════════════════════════════════════╝
import clases.Estudiante;

import java.util.*;

/**
 * ╔════════════════════════════════════════════════════╗
 * ║     Controlador que permite registrar, consultar,   ║
 * ║     modificar y eliminar estudiantes del sistema    ║
 * ╚════════════════════════════════════════════════════╝
 */
public class GestorEstudiantes {

    // ╔════════════════════════════════════════════════════╗
    // ║                 Atributos privados                 ║
    // ╚════════════════════════════════════════════════════╝
    private Map<String, Estudiante> estudiantes = new HashMap<>();

    // ╔════════════════════════════════════════════════════╗
    // ║              Registro de nuevo estudiante          ║
    // ╚════════════════════════════════════════════════════╝
    public boolean registrarEstudiante(Estudiante nuevo) {
        String id = nuevo.getIdentificacionPersonal();
        if (estudiantes.containsKey(id)) {
            System.out.println("⚠️ Ya existe un estudiante con esa identificación.");
            return false;
        }
        estudiantes.put(id, nuevo);
        System.out.println("✅ Estudiante registrado exitosamente: " + nuevo);
        return true;
    }

    // ╔════════════════════════════════════════════════════╗
    // ║              Consulta detallada por ID             ║
    // ╚════════════════════════════════════════════════════╝
    public void mostrarInformacionEstudiante(String id) {
        Estudiante estudiante = estudiantes.get(id);
        if (estudiante == null) {
            System.out.println("❌ No se encontró el estudiante con ID: " + id);
            return;
        }

        System.out.println("📋 Información del estudiante:");
        System.out.println("Nombre completo: " + estudiante.getNombreCompleto());
        System.out.println("Identificación: " + estudiante.getIdentificacionPersonal());
        System.out.println("Teléfono: " + estudiante.getTelefono());
        System.out.println("Correo: " + estudiante.getCorreoElectronico());
        System.out.println("Dirección: " + estudiante.getDireccionFisica());
        System.out.println("Organización: " + estudiante.getOrganizacionLaboral());
        System.out.println("Temas de interés: " + String.join(", ", estudiante.getTemasInteres()));
        System.out.println("Fecha de registro: " + estudiante.getFechaRegistro());
    }

    // ╔════════════════════════════════════════════════════╗
    // ║              Modificación completa de datos        ║
    // ╚════════════════════════════════════════════════════╝
    public boolean modificarEstudianteCompleto(String id,
                                               String nuevoNombre,
                                               String nuevoApellido1,
                                               String nuevoApellido2,
                                               String nuevoTelefono,
                                               String nuevoCorreo,
                                               String nuevaDireccion,
                                               String nuevaOrganizacion,
                                               ArrayList<String> nuevosTemas,
                                               String nuevaContrasenaPlano) {
        Estudiante actual = estudiantes.get(id);
        if (actual == null) {
            System.out.println("❌ No se encontró el estudiante con ID: " + id);
            return false;
        }

        try {
            Estudiante actualizado = new Estudiante(
                    nuevoNombre,
                    nuevoApellido1,
                    nuevoApellido2,
                    id,
                    nuevoTelefono,
                    nuevoCorreo,
                    nuevaDireccion,
                    nuevaContrasenaPlano,
                    nuevaOrganizacion,
                    nuevosTemas
            );

            estudiantes.put(id, actualizado);
            System.out.println("🔄 Estudiante actualizado exitosamente.");
            return true;

        } catch (Exception e) {
            System.out.println("❌ Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    // ╔════════════════════════════════════════════════════╗
    // ║              Eliminación de estudiante             ║
    // ╚════════════════════════════════════════════════════╝
    public boolean eliminarEstudiante(String id) {
        if (!estudiantes.containsKey(id)) {
            System.out.println("❌ No se encontró el estudiante.");
            return false;
        }
        Estudiante eliminado = estudiantes.remove(id);
        System.out.println("🗑️ Estudiante eliminado: " + eliminado);
        return true;
    }

    // ╔════════════════════════════════════════════════════╗
    // ║              Listado de todos los estudiantes      ║
    // ╚════════════════════════════════════════════════════╝
    public List<Estudiante> listarEstudiantes() {
        return new ArrayList<>(estudiantes.values());
    }

// ╔════════════════════════════════════════════════════╗
// ║        Consulta directa del estudiante por ID      ║
// ╚════════════════════════════════════════════════════╝
public Estudiante consultarEstudiante(String id) {
    return estudiantes.get(id);
}}
