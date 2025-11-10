package control;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import usuarios.Curso;
import usuarios.Estudiante;
import usuarios.GrupoCurso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class Generadorpdf {
    private com.itextpdf.text.Document documento;

    public Generadorpdf() {
        documento = new com.itextpdf.text.Document();
        try {
            // Crear carpeta si no existe
            File carpeta = new File("datos/matriculaycalificaciones");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            // Archivo fijo estudiantes.pdf
            File archivo = new File(carpeta, "estudiantes.pdf");

            PdfWriter.getInstance(documento, new FileOutputStream(archivo));
            documento.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void agregarSeccionEstudiantes(Curso curso, GrupoCurso grupo, List<Estudiante> estudiantes) {
        try {
            documento.add(new Paragraph("Curso: " + curso.getNombreCurso()));
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
            documento.add(new Paragraph("Curso: " + curso.getNombreCurso()));
            documento.add(new Paragraph("Grupo: " + grupo.getIdGrupo()));
            documento.add(new Paragraph("Cantidad de estudiantes matriculados: " + cantidadEstudiantes));
            documento.add(new Paragraph("\n"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void generarArchivo() {
        documento.close();
        System.out.println("✅ PDF generado en carpeta datos/matriculaycalificaciones con nombre estudiantes.pdf");
    }
}
