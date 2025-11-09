package evaluacion;



import evaluacion.Evaluacion;
import usuarios.GrupoCurso;
import usuarios.Profesor;

import java.time.LocalDateTime;
import java.util.*;
import java.io.*;

public class GestorEvaluaciones implements Serializable {
    private static GestorEvaluaciones instancia;
    private final List<Evaluacion> evaluaciones = new ArrayList<>();
    private final String rutaArchivo = "datos/matri culaycalificaciones/evaluaciones.dat";
    private final Map<GrupoCurso, List<Evaluacion>> mapaEvaluacionesPorGrupo = new HashMap<>();


    private GestorEvaluaciones() {
        cargarEvaluaciones();
    }

    public static GestorEvaluaciones getInstancia() {
        if (instancia == null) {
            instancia = new GestorEvaluaciones();
        }
        return instancia;
    }

    public void agregarEvaluacion(Evaluacion e) {
        evaluaciones.add(e);
        guardarEvaluaciones();
    }

    public void modificarEvaluacion(Evaluacion actualizada) {
        for (int i = 0; i < evaluaciones.size(); i++) {
            if (evaluaciones.get(i).getIdEvaluacion() == actualizada.getIdEvaluacion()) {
                evaluaciones.set(i, actualizada);
                break;
            }
        }
        guardarEvaluaciones();
    }

    public void eliminarEvaluacion(int id) {
        evaluaciones.removeIf(e -> e.getIdEvaluacion() == id);
        guardarEvaluaciones();
    }

    public Evaluacion consultarEvaluacion(int id) {
        return evaluaciones.stream()
                .filter(e -> e.getIdEvaluacion() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Evaluacion> listarEvaluaciones() {
        return new ArrayList<>(evaluaciones);
    }

    private void guardarEvaluaciones() {
        try {
            File archivo = new File(rutaArchivo);
            File carpeta = archivo.getParentFile(); // obtiene la carpeta "datos/matriculaycalificaciones"

            if (!carpeta.exists()) {
                carpeta.mkdirs(); // ✅ crea la carpeta si no existe
            }

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivo))) {
                out.writeObject(evaluaciones);
            }

        } catch (IOException e) {
            System.out.println("Error al guardar evaluaciones: " + e.getMessage());
        }
    }

    private void cargarEvaluaciones() {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            List<Evaluacion> cargadas = (List<Evaluacion>) in.readObject();
            evaluaciones.clear();
            evaluaciones.addAll(cargadas);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar evaluaciones: " + e.getMessage());
        }
    }

    public Evaluacion[] listarEvaluacionesPorProfesor(Profesor profesor) {
        List<Evaluacion> resultado = new ArrayList<>();
        for (Evaluacion e : evaluaciones) {
            if (e.getProfesor() != null &&
                    e.getProfesor().getIdentificacionPersonal().equals(profesor.getIdentificacionPersonal())) {
                resultado.add(e);
            }
        }
        return resultado.toArray(new Evaluacion[0]);
    }



    public boolean asociarEvaluacionAGrupo(int idEvaluacion, GrupoCurso grupo, LocalDateTime inicio, LocalDateTime fin) {
        for (Evaluacion e : evaluaciones) {
            if (e.getIdEvaluacion() == idEvaluacion) {
                e.asociarAGrupo(grupo, inicio, fin);

                // ✅ Asociar en el mapa
                mapaEvaluacionesPorGrupo.computeIfAbsent(grupo, k -> new ArrayList<>()).add(e);

                guardarEvaluaciones();
                return true;
            }
        }
        return false;
    }
    public List<Evaluacion> getEvaluacionesPorGrupo(GrupoCurso grupo) {
        return mapaEvaluacionesPorGrupo.getOrDefault(grupo, new ArrayList<>());
    }




    public void registrarEvaluacion(Evaluacion evaluacion) {
        agregarEvaluacion(evaluacion);
    }



}
