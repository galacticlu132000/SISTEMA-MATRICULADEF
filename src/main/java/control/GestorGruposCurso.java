package control;

import java.util.*;
import usuarios.GrupoCurso;
import usuarios.Estudiante;

public class GestorGruposCurso {
    private static final GestorGruposCurso instancia = new GestorGruposCurso();
    private final Map<String, List<GrupoCurso>> gruposPorCurso = new HashMap<>();

    private GestorGruposCurso() {}

    public static GestorGruposCurso getInstancia() {
        return instancia;
    }

    public boolean agregarGrupo(String idCurso, GrupoCurso grupo) {
        gruposPorCurso.computeIfAbsent(idCurso, k -> new ArrayList<>()).add(grupo);
        System.out.println("✅ Grupo agregado al curso " + idCurso + ": " + grupo);
        return false;
    }
    public List<GrupoCurso> getGruposPorCurso(String idCurso) {
        return gruposPorCurso.getOrDefault(idCurso, new ArrayList<>());
    }


    public List<GrupoCurso> listarGrupos(String idCurso) {
        return gruposPorCurso.getOrDefault(idCurso, new ArrayList<>());
    }

    public void eliminarGruposDeCurso(String idCurso) {
        gruposPorCurso.remove(idCurso);
        System.out.println("🗑️ Todos los grupos del curso " + idCurso + " han sido eliminados.");
    }

    public boolean eliminarGrupo(String idCurso, int idGrupo) {
        List<GrupoCurso> grupos = gruposPorCurso.get(idCurso);
        if (grupos != null) {
            Iterator<GrupoCurso> iter = grupos.iterator();
            while (iter.hasNext()) {
                GrupoCurso g = iter.next();
                if (g.getIdGrupo() == idGrupo) {
                    iter.remove();
                    System.out.println("🗑️ Grupo " + idGrupo + " eliminado del curso " + idCurso);
                    return true;
                }
            }
        }
        System.out.println("❌ No se encontró el grupo " + idGrupo + " en el curso " + idCurso);
        return false;
    }

    public int obtenerSiguienteIdGrupo(String idCurso) {
        List<GrupoCurso> grupos = gruposPorCurso.get(idCurso);
        return (grupos == null) ? 1 : grupos.size() + 1;
    }

    public String[] getNombresGrupos() {
        List<String> nombres = new ArrayList<>();
        for (List<GrupoCurso> lista : gruposPorCurso.values()) {
            for (GrupoCurso grupo : lista) {
                nombres.add(grupo.getNombre());
            }
        }
        return nombres.toArray(new String[0]);
    }

    public GrupoCurso buscarPorNombre(String nombre) {
        for (List<GrupoCurso> lista : gruposPorCurso.values()) {
            for (GrupoCurso grupo : lista) {
                if (grupo.getNombre().equalsIgnoreCase(nombre)) {
                    return grupo;
                }
            }
        }
        return null;
    }





    public boolean matricularEstudiante(String idCurso, int idGrupo, String idEstudiante) {
        // Buscar el grupo
        List<GrupoCurso> grupos = gruposPorCurso.get(idCurso);
        if (grupos == null) {
            System.out.println("❌ No hay grupos registrados para el curso " + idCurso);
            return false;
        }

        GrupoCurso grupo = null;
        for (GrupoCurso g : grupos) {
            if (g.getIdGrupo() == idGrupo) {
                grupo = g;
                break;
            }
        }

        if (grupo == null) {
            System.out.println("❌ No se encontró el grupo " + idGrupo + " en el curso " + idCurso);
            return false;
        }

        // Buscar el estudiante
        Estudiante estudiante = GestorEstudiantes.getInstancia().consultarEstudiante(idEstudiante);
        if (estudiante == null) {
            System.out.println("❌ Estudiante no encontrado con ID: " + idEstudiante);
            return false;
        }

        // Validar si ya está matriculado
        if (estudiante.estaMatriculadoEn(idCurso, idGrupo)) {
            System.out.println("⚠️ El estudiante ya está matriculado en este grupo.");
            return false;
        }

        // Agregar al grupo y al estudiante
        grupo.agregarEstudiante(estudiante); // esto también actualiza al estudiante
        return true;
    }

    public List<String> obtenerCursosMatriculados(String idEstudiante) {
        List<String> cursos = new ArrayList<>();

        for (Map.Entry<String, List<GrupoCurso>> entry : gruposPorCurso.entrySet()) {
            for (GrupoCurso grupo : entry.getValue()) {
                if (grupo.getEstudiantesMatriculados().contains(idEstudiante)) {
                    String nombreCurso = grupo.getCurso().getIdentificacionCurso() + " - " + grupo.getCurso().getNombreCurso();
                    if (!cursos.contains(nombreCurso)) {
                        cursos.add(nombreCurso);
                    }
                }
            }
        }

        return cursos;
    }






    public void imprimirGrupos(String idCurso) {
        List<GrupoCurso> grupos = listarGrupos(idCurso);
        System.out.println("📋 Grupos del curso " + idCurso + ":");
        for (GrupoCurso g : grupos) {
            System.out.println(" - " + g.getIdGrupo() + ": " + g.getNombre());
        }
    }
}
