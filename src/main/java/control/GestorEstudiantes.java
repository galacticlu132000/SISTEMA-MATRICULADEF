package control;

import usuarios.Estudiante;
import java.util.*;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🎓 GestorEstudiantes                                                       ║
 * ║                                                                            ║
 * ║ Controlador central para gestionar estudiantes:                            ║
 * ║ - Registro, consulta, modificación y eliminación                          ║
 * ║ - Implementado como Singleton para acceso global                          ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class GestorEstudiantes {

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🔒 SINGLETON Y ESTRUCTURA                 ║
    // ╚════════════════════════════════════════════════════════════╝
    public static final GestorEstudiantes instancia = new GestorEstudiantes(); // ✅ Singleton
    private List<Estudiante> estudiantes = new ArrayList<>();                   // 🗂️ Lista interna

    private GestorEstudiantes() {} // ✅ Constructor privado

    public static GestorEstudiantes getInstancia() { // ✅ Acceso global
        return instancia;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  ➕ REGISTRO DE ESTUDIANTE                 ║
    // ╚════════════════════════════════════════════════════════════╝
    public boolean registrarEstudiante(Estudiante nuevo) {
        String id = nuevo.getIdentificacionPersonal();
        if (buscarPorID(id) != null) {
            System.out.println("⚠️ Ya existe un estudiante con esa identificación.");
            return false;
        }
        estudiantes.add(nuevo);
        System.out.println("✅ Estudiante registrado exitosamente: " + nuevo);
        return true;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🗑️ ELIMINACIÓN DE ESTUDIANTE             ║
    // ╚════════════════════════════════════════════════════════════╝
    public boolean eliminarEstudiante(String id) {
        Iterator<Estudiante> iter = estudiantes.iterator();
        while (iter.hasNext()) {
            Estudiante e = iter.next();
            if (e.getIdentificacionPersonal().equals(id)) {
                iter.remove();
                System.out.println("🗑️ Estudiante eliminado: " + e);
                return true;
            }
        }
        System.out.println("❌ No se encontró el estudiante.");
        return false;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  📋 LISTADO DE ESTUDIANTES                ║
    // ╚════════════════════════════════════════════════════════════╝
    public List<Estudiante> listarEstudiantes() {
        return new ArrayList<>(estudiantes); // 🧾 Copia defensiva
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🔍 CONSULTA POR IDENTIFICACIÓN           ║
    // ╚════════════════════════════════════════════════════════════╝
    public Estudiante consultarEstudiante(String id) {
        return buscarPorID(id);
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  ✏️ ACTUALIZACIÓN DE DATOS                ║
    // ╚════════════════════════════════════════════════════════════╝
    public boolean actualizarEstudiante(Estudiante actualizado) {
        String id = actualizado.getIdentificacionPersonal();
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).getIdentificacionPersonal().equals(id)) {
                estudiantes.set(i, actualizado);
                System.out.println("🔄 Estudiante actualizado correctamente.");
                return true;
            }
        }
        System.out.println("❌ No se encontró el estudiante con ID: " + id);
        return false;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🔎 BÚSQUEDA INTERNA POR ID               ║
    // ╚════════════════════════════════════════════════════════════╝
    private Estudiante buscarPorID(String id) {
        for (Estudiante e : estudiantes) {
            if (e.getIdentificacionPersonal().equals(id)) {
                return e;
            }
        }
        return null;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  ✅ VALIDACIONES ÚNICAS                   ║
    // ╚════════════════════════════════════════════════════════════╝
    public boolean existeID(String id) {
        return estudiantes.stream().anyMatch(e -> e.getIdentificacionPersonal().equals(id));
    }

    public boolean existeCorreo(String correo) {
        return estudiantes.stream().anyMatch(e -> e.getCorreoElectronico().equalsIgnoreCase(correo));
    }


}
