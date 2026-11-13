package calificaciones;

import java.util.Locale;

/**
 * Representa una coincidencia encontrada en la matriz: la posición [fila, columna]
 * y a qué estudiante y asignatura corresponde esa posición.
 */
public class Coincidencia {

    private final int fila;
    private final int columna;
    private final String estudiante;
    private final String asignatura;
    private final double nota;

    public Coincidencia(int fila, int columna, String estudiante, String asignatura, double nota) {
        this.fila = fila;
        this.columna = columna;
        this.estudiante = estudiante;
        this.asignatura = asignatura;
        this.nota = nota;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public double getNota() {
        return nota;
    }

    /** Coordenadas en formato [fila, columna]. */
    public int[] getCoordenadas() {
        return new int[] {fila, columna};
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "[%d, %d] -> %s en %s (nota %.1f)",
                fila, columna, estudiante, asignatura, nota);
    }
}
