package calificaciones;
import java.util.Locale;

/**
 * Modelo de datos: una matriz de calificaciones de estudiantes en diferentes asignaturas.
 * Filas  = estudiantes
 * Columnas = asignaturas
 * notas[filas][columnas]= calificacion del estudiante "fila" en la asignatura "columna".
 */
public class MatrizCalificaciones {

    public static final double NOTA_MINIMA = 0.0;
    public static final double NOTA_MAXIMA = 5.0;

    private final String[] estudiantes;
    private final String[] asignaturas;
    private final double[][] notas;

    public MatrizCalificaciones(String[] estudiantes, String[] asignaturas, double[][] notas) {
            if (estudiantes == null || asignaturas == null || notas == null) {
                throw new IllegalArgumentException("Estudiantes, asignatura y notas no pueden ser null");
            }
            for (int i=0; i< notas.length; i++){
                if (notas[i] == null || notas[i].length != asignaturas.length) {
                    throw new IllegalArgumentException( 
                        "La fila "+i+" debe tener una nota por cada asignatura ("+asignaturas.length+")");
                }
                for (int j = 0; j < notas[i].length; j++){
                    if (!esNotaValida(notas[i][j])){
                        throw new IllegalArgumentException(
                            "Nota fuera de rango en [" + i + ", " + j + "]:" + notas[i][j]);
                    }
                }
            }
            this.estudiantes = estudiantes;
            this.asignaturas = asignaturas;
            this.notas = notas;
    }
    public static boolean esNotaValida(double nota) {
        return nota >= NOTA_MINIMA && nota <= NOTA_MAXIMA;
    }

    /*Metodos Get y setter */

    public int getNumeroEstudiantes(){
        return notas.length;
    }

    public int getNumeroAsignaturas(){
        return asignaturas.length;
    }

public double getNota(int fila, int columna){
        return notas[fila][columna];
    }
public String getEstudiante(int fila) {
return estudiantes[fila];
}
public String getAsignatura(int columna) {
return asignaturas[columna];
}
public boolean estaVacia() {
return notas.length == 0 || asignaturas.length == 0;
}
/** Devuelve la matriz como texto en forma de tabla, con índices de fila
y columna. */
public String comoTabla() {
if (estaVacia()) {
return "(matriz vacía)";
}
StringBuilder sb = new StringBuilder();
sb.append(String.format("%-24s", "Estudiante \\ Asignatura"));
for (int j = 0; j < asignaturas.length; j++) {
sb.append(String.format("%14s", "[" + j + "] " +
recortar(asignaturas[j], 9)));
}
sb.append('\n');
for (int i = 0; i < notas.length; i++) {
sb.append(String.format("%-24s", "[" + i + "] " +
recortar(estudiantes[i], 17)));
for (int j = 0; j < notas[i].length; j++) {
sb.append(String.format(Locale.US, "%14.1f", notas[i][j]));
}
sb.append('\n');
}
return sb.toString();
}
private static String recortar(String texto, int max) {
return texto.length() <= max ? texto : texto.substring(0, max);
}
    
}
