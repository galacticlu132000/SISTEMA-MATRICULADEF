package control;
import usuarios.GrupoCurso;
import usuarios.Profesor;
import java.util.*;

/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 🎓 GestorProfesores                                                        ║
 * ║                                                                            ║
 * ║ Controlador central para gestionar profesores:                             ║
 * ║ - Registro, consulta, modificación y eliminación                          ║
 * ║ - Implementado como Singleton para acceso global                          ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class GestorProfesores {

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🔒 SINGLETON Y ESTRUCTURA                 ║
    // ╚════════════════════════════════════════════════════════════╝
    public static final GestorProfesores instancia = new GestorProfesores(); // ✅ Singleton
    private List<Profesor> profesores = new ArrayList<>();                    // 🗂️ Lista interna

    private GestorProfesores() {} // ✅ Constructor privado

    public static GestorProfesores getInstancia() { // ✅ Acceso global
        return instancia;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  ➕ REGISTRO DE PROFESOR                   ║
    // ╚════════════════════════════════════════════════════════════╝
    public boolean registrarProfesor(Profesor nuevo) {
        String id = nuevo.getIdentificacionPersonal();
        if (buscarPorID(id) != null) {
            System.out.println("⚠️ Ya existe un profesor con esa identificación.");
            return false;
        }
        profesores.add(nuevo);
        System.out.println("✅ Profesor registrado exitosamente: " + nuevo);
        return true;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🗑️ ELIMINACIÓN DE PROFESOR               ║
    // ╚════════════════════════════════════════════════════════════╝
    public boolean eliminarProfesor(String id) {
        Iterator<Profesor> iter = profesores.iterator();
        while (iter.hasNext()) {
            Profesor p = iter.next();
            if (p.getIdentificacionPersonal().equals(id)) {
                iter.remove();
                System.out.println("🗑️ Profesor eliminado: " + p);
                return true;
            }
        }
        System.out.println("❌ No se encontró el profesor.");
        return false;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  📋 LISTADO DE PROFESORES                 ║
    // ╚════════════════════════════════════════════════════════════╝
    public List<Profesor> listarProfesores() {
        return new ArrayList<>(profesores); // 🧾 Copia defensiva
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🔍 CONSULTA POR IDENTIFICACIÓN           ║
    // ╚════════════════════════════════════════════════════════════╝
    public Profesor consultarProfesor(String id) {
        return buscarPorID(id);
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  ✏️ ACTUALIZACIÓN DE DATOS                ║
    // ╚════════════════════════════════════════════════════════════╝
    public boolean actualizarProfesor(Profesor actualizado) {
        String id = actualizado.getIdentificacionPersonal();
        for (int i = 0; i < profesores.size(); i++) {
            if (profesores.get(i).getIdentificacionPersonal().equals(id)) {
                profesores.set(i, actualizado);
                System.out.println("🔄 Profesor actualizado correctamente.");
                return true;
            }
        }
        System.out.println("❌ No se encontró el profesor con ID: " + id);
        return false;
    }

    // ╔════════════════════════════════════════════════════════════╗
    // ║                  🔎 BÚSQUEDA INTERNA POR ID               ║
    // ╚════════════════════════════════════════════════════════════╝
    private Profesor buscarPorID(String id) {
        for (Profesor p : profesores) {
            if (p.getIdentificacionPersonal().equals(id)) {
                return p;
            }
        }
        return null;
    }



    // ╔════════════════════════════════════════════════════════════╗
    // ║                  ✅ VALIDACIONES ÚNICAS                   ║
    // ╚════════════════════════════════════════════════════════════╝
    public boolean existeID(String id) {
        return profesores.stream().anyMatch(p -> p.getIdentificacionPersonal().equals(id));
    }

    public boolean existeCorreo(String correo) {
        return profesores.stream().anyMatch(p -> p.getCorreoElectronico().equalsIgnoreCase(correo));
    }

}
