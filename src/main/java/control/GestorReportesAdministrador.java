package control;

import usuarios.Curso;
import usuarios.Estudiante;
import usuarios.GrupoCurso;
import usuarios.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


import usuarios.Curso;
import usuarios.Estudiante;
import usuarios.GrupoCurso;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class GestorReportesAdministrador {

    public List<GrupoCurso> filtrarGruposVigentes(List<GrupoCurso> grupos, LocalDate fechaVigencia) {
        return grupos.stream()
                .filter(g -> !g.getFechaFin().isBefore(fechaVigencia))
                .collect(Collectors.toList());
    }

    public List<Estudiante> obtenerEstudiantesMatriculados(Curso curso, GrupoCurso grupo, LocalDate fechaVigencia) {
        if (grupo.getFechaFin().isBefore(fechaVigencia)) return new ArrayList<>();
        return grupo.getEstudiantes().stream()
                .sorted(Comparator.comparing(Estudiante::getPrimerApellido)
                        .thenComparing(Estudiante::getSegundoApellido)
                        .thenComparing(Estudiante::getNombre))
                .collect(Collectors.toList());
    }

    public int contarEstudiantesMatriculados(GrupoCurso grupo, LocalDate fechaVigencia) {
        return grupo.getFechaFin().isBefore(fechaVigencia) ? 0 : grupo.getEstudiantes().size();
    }
}
