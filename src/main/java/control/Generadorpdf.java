package control;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import usuarios.Curso;
import usuarios.Estudiante;
import usuarios.GrupoCurso;

import java.io.FileOutputStream;
import java.util.List;

public class Generadorpdf {
    private com.itextpdf.text.Document documento;

    public Generadorpdf() {
        documento = new com.itextpdf.text.Document();
        try {
            PdfWriter.getInstance(documento, new FileOutputStream("ReporteEstudiantesMatriculados.pdf"));
            documento.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregarSeccionEstudiantes(Curso curso, GrupoCurso grupo, List<Estudiante> estudiantes) {
        try {
            documento.add(new Paragraph("Curso: " + curso.getnombreCurso()));
            documento.add(new Paragraph("Grupo: " + grupo.getIdGrupo()));
            documento.add(new Paragraph("Estudiantes matriculados:"));

            PdfPTable tabla = new PdfPTable(3);
            tabla.addCell("Apellido 1");
            tabla.addCell("Apellido 2");
            tabla.addCell("Nombre");

            for (Estudiante e : estudiantes) {
                tabla.addCell(e.getPrimerApellido());
                tabla.addCell(e.getSegundoApellido());
                tabla.addCell(e.getNombre());
            }

            documento.add(tabla);
            documento.add(new Paragraph("\n"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void agregarSeccionEstadistica(Curso curso, GrupoCurso grupo, int cantidadEstudiantes) {
        try {
            documento.add(new Paragraph("Curso: " + curso.getnombreCurso()));
            documento.add(new Paragraph("Grupo: " + grupo.getIdGrupo()));
            documento.add(new Paragraph("Cantidad de estudiantes matriculados: " + cantidadEstudiantes));
            documento.add(new Paragraph("\n"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void generarArchivo(String nombreArchivo) {
        documento.close();
        System.out.println("PDF generado: " + nombreArchivo);
    }
}
