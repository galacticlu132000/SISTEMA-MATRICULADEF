package control;
import java.util.*;
import usuarios.GrupoCurso;


public class GestorGruposCurso {
    private static final GestorGruposCurso instancia = new GestorGruposCurso();

    // Mapa que asocia ID de curso con su lista de grupos
    private final Map<String, List<GrupoCurso>> gruposPorCurso = new HashMap<>();

    private GestorGruposCurso() {}

    public static GestorGruposCurso getInstancia() {
        return instancia;
    }

    // Agrega un grupo a un curso específico
    public void agregarGrupo(String idCurso, GrupoCurso grupo) {
        gruposPorCurso.computeIfAbsent(idCurso, k -> new ArrayList<>()).add(grupo);
        System.out.println("✅ Grupo agregado al curso " + idCurso + ": " + grupo);
    }



    // Devuelve la lista de grupos de un curso
    public List<GrupoCurso> listarGrupos(String idCurso) {
        return gruposPorCurso.getOrDefault(idCurso, new ArrayList<>());
    }

    // Elimina todos los grupos de un curso (opcional)
    public void eliminarGruposDeCurso(String idCurso) {
        gruposPorCurso.remove(idCurso);
        System.out.println("🗑️ Todos los grupos del curso " + idCurso + " han sido eliminados.");
    }

    // Elimina un grupo específico por ID dentro de un curso
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

    // Devuelve el siguiente número de grupo para un curso
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

}
