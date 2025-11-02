package evaluacion;

import java.io.Serializable;

public abstract class Pregunta implements Serializable {
    protected int numero;
    protected String descripcion;
    protected int puntos;

    public Pregunta(int numero, String descripcion, int puntos) {
        this.numero = numero;
        this.descripcion = descripcion;
        this.puntos = puntos;
    }

    public int getNumero() { return numero; }
    public String getDescripcion() { return descripcion; }
    public int getPuntos() { return puntos; }

    public abstract String getTipo();
}
