package control;
import usuarios.Curso;
import java.util.*;

public class GestorCursos {
    private static final GestorCursos instancia = new GestorCursos();
    private List<Curso> cursos = new ArrayList<>();

    private GestorCursos() {
    }

    public static GestorCursos getInstancia() { // ✅ Acceso global
        return instancia;
    }


    public boolean registrarCursos(Curso nuevo) {
        String id = nuevo.getIdentificacionCurso();
        if (buscarPorID(id) != null) {
            System.out.println("Ya existe un curso con esa identificación.");
            return false;
        }
        cursos.add(nuevo);
        System.out.println("Curso registrado exitosamente: " + nuevo);
        return true;
    }

    public boolean eliminarCursos(String id) {
        Curso curso = buscarPorID(id);
        if (curso != null) {
            cursos.remove(curso);
            System.out.println("Curso eliminado exitosamente: " + curso);
            return true;

        } else {
            System.out.println("No se encontro el curso con la identificación: " + id);
            return false;
        }
    }


    public boolean actualizarCursos(Curso actualizado) {
        String id = actualizado.getIdentificacionCurso();
        Curso cursoExistente = buscarPorID(id);

        if (cursoExistente != null) {
            try {
                cursoExistente.setNombreCurso(actualizado.getnombreCurso());
                cursoExistente.setDescripcionCurso(actualizado.getdescripcionCurso());
                // Puedes agregar más setters si decides permitir editar otros campos
                System.out.println("Curso actualizado exitosamente: " + cursoExistente);
                return true;
            } catch (IllegalArgumentException ex) {
                System.out.println("Error al actualizar el curso: " + ex.getMessage());
                return false;
            }
        }

        System.out.println("No se encontró un curso con la identificación: " + id);
        return false;
    }



    public Curso buscarPorID(String id) {
        for (Curso e : cursos) {
            if (e.getIdentificacionCurso().equals(id)) {
                return e;
            }
        }
        return null;
    }
    ///////////////ESTE ES EL Q FALTA
    public List<Curso> obtenerCursos() {
        return cursos;
    }
    public void mostrarCursos() {
        if (cursos.isEmpty()) {
            System.out.println("No hay cursos registrados.");
        } else {
            for (Curso c : cursos) {
                System.out.println(c);
            }
        }
    }

}
