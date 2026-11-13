package calificaciones;

import java.util.Arrays;

/**
 * Casos de prueba del buscador, sin librerías externas.
 * Cada prueba imprime [OK] o [FALLA]; al final se muestra el resumen.
 *
 * Se puede ejecutar sola:  java -cp out calificaciones.PruebasBuscador
 * o desde la opción 5 del menú principal.
 */
public class PruebasBuscador {

    private static int aprobadas;
    private static int fallidas;

    public static void main(String[] args) {
        boolean todoBien = ejecutarTodas();
        System.exit(todoBien ? 0 : 1);
    }

    /** Ejecuta todas las pruebas y retorna true si todas pasaron. */
    public static boolean ejecutarTodas() {
        aprobadas = 0;
        fallidas = 0;
        BuscadorCalificaciones b = new BuscadorCalificaciones();
        MatrizCalificaciones p = DatosEjemplo.matrizPrincipal();
        MatrizCalificaciones alt = DatosEjemplo.matrizAlternativa();
        MatrizCalificaciones vacia = DatosEjemplo.matrizVacia();

        System.out.println("=============== PRUEBAS DEL BUSCADOR ===============");

        // 1. La nota existe una sola vez
        verificar("2.5 aparece una vez en [3, 0]",
                Arrays.equals(b.buscarPrimeraAparicion(p, 2.5), new int[] {3, 0})
                        && b.contarOcurrencias(p, 2.5) == 1);

        // 2. La nota aparece varias veces: primera y última siguen el recorrido fila por fila
        verificar("4.5: primera aparición en [0, 0]",
                Arrays.equals(b.buscarPrimeraAparicion(p, 4.5), new int[] {0, 0}));
        verificar("4.5: última aparición en [5, 4]",
                Arrays.equals(b.buscarUltimaAparicion(p, 4.5), new int[] {5, 4}));
        verificar("4.5 aparece 6 veces",
                b.contarOcurrencias(p, 4.5) == 6);
        verificar("4.5: todas las coordenadas en orden fila por fila",
                Arrays.deepEquals(b.obtenerCoordenadas(p, 4.5), new int[][] {
                    {0, 0}, {1, 1}, {2, 0}, {2, 4}, {4, 2}, {5, 4}}));

        // 3. La nota no existe
        verificar("1.0 no existe -> {-1, -1}",
                Arrays.equals(b.buscarPrimeraAparicion(p, 1.0), new int[] {-1, -1}));
        verificar("1.0 no existe -> recorre las 30 posiciones",
                b.getComparaciones() == 30);
        verificar("1.0 no existe -> 0 ocurrencias y arreglo vacío",
                b.contarOcurrencias(p, 1.0) == 0 && b.obtenerCoordenadas(p, 1.0).length == 0);
        verificar("existe(1.0) es false y existe(5.0) es true",
                !b.existe(p, 1.0) && b.existe(p, 5.0));

        // 4. Estudiante y asignatura de cada coincidencia
        Coincidencia[] c = b.buscarCoincidencias(p, 5.0);
        verificar("5.0: Ana en Programación y María en Física y Sofía en Matemáticas",
                c.length == 3
                        && c[0].getEstudiante().equals("Ana Martínez") && c[0].getAsignatura().equals("Programación")
                        && c[1].getEstudiante().equals("María Gómez") && c[1].getAsignatura().equals("Física")
                        && c[2].getEstudiante().equals("Sofía Herrera") && c[2].getAsignatura().equals("Matemáticas"));

        // 5. Conteo de comparaciones en la primera aparición
        b.buscarPrimeraAparicion(p, 4.5);
        verificar("4.5 en la primera celda -> 1 comparación (mejor caso)",
                b.getComparaciones() == 1);
        b.buscarPrimeraAparicion(p, 2.5);
        verificar("2.5 en [3, 0] -> 3*5 + 0 + 1 = 16 comparaciones",
                b.getComparaciones() == 16);

        // 6. Segundo caso, diferente al principal
        verificar("Matriz alternativa: 3.3 aparece 4 veces",
                b.contarOcurrencias(alt, 3.3) == 4);
        verificar("Matriz alternativa: 1.5 primera [1, 1] y última [2, 3]",
                Arrays.equals(b.buscarPrimeraAparicion(alt, 1.5), new int[] {1, 1})
                        && Arrays.equals(b.buscarUltimaAparicion(alt, 1.5), new int[] {2, 3}));

        // 7. Comparación de decimales con tolerancia (0.1 + 0.2 no es exactamente 0.3 en double)
        MatrizCalificaciones dec = new MatrizCalificaciones(
                new String[] {"Test"}, new String[] {"A"}, new double[][] {{0.3}});
        verificar("0.1 + 0.2 se encuentra como 0.3",
                b.existe(dec, 0.1 + 0.2));

        // 8. Casos borde
        verificar("Matriz vacía -> {-1, -1} y 0 ocurrencias",
                Arrays.equals(b.buscarPrimeraAparicion(vacia, 4.0), new int[] {-1, -1})
                        && b.contarOcurrencias(vacia, 4.0) == 0
                        && b.obtenerCoordenadas(vacia, 4.0).length == 0);
        verificar("Matriz null -> lanza IllegalArgumentException",
                lanzaExcepcion(() -> b.buscarPrimeraAparicion(null, 4.0)));
        verificar("Nota fuera de rango (6.0) al crear la matriz -> excepción",
                lanzaExcepcion(() -> new MatrizCalificaciones(
                        new String[] {"X"}, new String[] {"A"}, new double[][] {{6.0}})));
        verificar("Fila con notas incompletas -> excepción",
                lanzaExcepcion(() -> new MatrizCalificaciones(
                        new String[] {"X"}, new String[] {"A", "B"}, new double[][] {{3.0}})));

        System.out.println("----------------------------------------------------");
        System.out.println("Resultado: " + aprobadas + " pruebas OK, " + fallidas + " fallidas.");
        return fallidas == 0;
    }

    private static void verificar(String descripcion, boolean condicion) {
        if (condicion) {
            aprobadas++;
            System.out.println("[OK]    " + descripcion);
        } else {
            fallidas++;
            System.out.println("[FALLA] " + descripcion);
        }
    }

    private static boolean lanzaExcepcion(Runnable accion) {
        try {
            accion.run();
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }
}
