package calificaciones;

/**
 * Algoritmos de búsqueda sobre una MatrizCalificaciones.
 *
 * Precondición: la matriz NO está ordenada (las notas no siguen ningún orden por
 * filas ni por columnas). Por eso no se puede usar búsqueda binaria ni staircase
 * search: la única opción correcta es la búsqueda lineal (ingenua) en 2D, que
 * recorre la matriz fila por fila con un doble ciclo.
 *
 * Cada método registra cuántas comparaciones hizo, para poder explicar su costo.
 */
public class BuscadorCalificaciones {

    /** Tolerancia para comparar números double (evita errores de redondeo). */
    private static final double EPSILON = 1e-9;

    private int comparaciones;

    /** Número de comparaciones realizadas en la última búsqueda. */
    public int getComparaciones() {
        return comparaciones;
    }

    private boolean esIgual(double a, double b) {
        comparaciones++;
        return Math.abs(a - b) < EPSILON;
    }

    private void validar(MatrizCalificaciones m) {
        if (m == null) {
            throw new IllegalArgumentException("La matriz no puede ser null");
        }
    }

    /**
     * Primera aparición de la nota recorriendo fila por fila (de arriba hacia abajo
     * y de izquierda a derecha). Retorna {fila, columna} o {-1, -1} si no existe.
     *
     * Complejidad: mejor caso O(1), peor caso O(n·m).
     */
    public int[] buscarPrimeraAparicion(MatrizCalificaciones m, double nota) {
        validar(m);
        comparaciones = 0;
        for (int i = 0; i < m.getNumeroEstudiantes(); i++) {
            for (int j = 0; j < m.getNumeroAsignaturas(); j++) {
                if (esIgual(m.getNota(i, j), nota)) {
                    return new int[] {i, j};
                }
            }
        }
        return new int[] {-1, -1};
    }

    /** Indica si la nota aparece al menos una vez. O(n·m) en el peor caso. */
    public boolean existe(MatrizCalificaciones m, double nota) {
        return buscarPrimeraAparicion(m, nota)[0] != -1;
    }

    /**
     * Cuenta cuántas veces aparece la nota. Siempre recorre toda la matriz.
     *
     * Complejidad: O(n·m) en todos los casos.
     */
    public int contarOcurrencias(MatrizCalificaciones m, double nota) {
        validar(m);
        comparaciones = 0;
        int contador = 0;
        for (int i = 0; i < m.getNumeroEstudiantes(); i++) {
            for (int j = 0; j < m.getNumeroAsignaturas(); j++) {
                if (esIgual(m.getNota(i, j), nota)) {
                    contador++;
                }
            }
        }
        return contador;
    }

    /**
     * Todas las coordenadas {fila, columna} donde aparece la nota, en orden fila por fila.
     * Si no aparece, retorna un arreglo vacío (longitud 0).
     *
     * Usa dos recorridos: el primero cuenta (para crear el arreglo del tamaño exacto)
     * y el segundo llena las coordenadas. Complejidad: O(n·m).
     */
    public int[][] obtenerCoordenadas(MatrizCalificaciones m, double nota) {
        int total = contarOcurrencias(m, nota);   // 1er recorrido
        int[][] coordenadas = new int[total][];
        int k = 0;
        for (int i = 0; i < m.getNumeroEstudiantes(); i++) {   // 2do recorrido
            for (int j = 0; j < m.getNumeroAsignaturas(); j++) {
                if (esIgual(m.getNota(i, j), nota)) {
                    coordenadas[k++] = new int[] {i, j};
                }
            }
        }
        return coordenadas;
    }
}
