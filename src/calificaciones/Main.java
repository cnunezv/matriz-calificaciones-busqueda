package calificaciones;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

/**
 * Programa principal: menú de consola para buscar calificaciones en la matriz.
 */
public class Main {

    private static final Scanner ENTRADA = new Scanner(System.in);
    private static final BuscadorCalificaciones BUSCADOR = new BuscadorCalificaciones();

    private static MatrizCalificaciones matriz = DatosEjemplo.matrizPrincipal();
    private static String nombreMatriz = "principal";

    public static void main(String[] args) {
        System.out.println("Matriz de Calificaciones - Algoritmos de búsqueda");   // Aqui se coloca el titulo del programa
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            String opcion = leerLinea("Opción: ");
            if (opcion == null) {
                break;   // no hay más entrada
            }
            switch (opcion.trim()) {
                case "1":
                    System.out.println();
                    System.out.println(matriz.comoTabla());
                    break;
                case "2":
                    buscarDesdeTeclado();
                    break;
                case "3":
                    cambiarMatriz();
                    break;
                case "4":
                    demostracion();
                    break;
                case "5":
                    PruebasBuscador.ejecutarTodas();
                    break;
                case "0":
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
        System.out.println("Fin del programa.");
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("=========== MENÚ (matriz " + nombreMatriz + ") ===========");
        System.out.println("1. Ver la matriz de calificaciones");
        System.out.println("2. Buscar una calificación");
        System.out.println("3. Cambiar de matriz (principal / alternativa)");
        System.out.println("4. Demostración automática (existe una vez, varias veces, no existe)");
        System.out.println("5. Ejecutar casos de prueba");
        System.out.println("0. Salir");
    }

    private static void buscarDesdeTeclado() {
        String texto = leerLinea("Calificación a buscar (0.0 - 5.0): ");
        if (texto == null) {
            return;
        }
        double nota;
        try {
            nota = Double.parseDouble(texto.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            System.out.println("Entrada no válida: escriba un número, por ejemplo 4.5");
            return;
        }
        if (!MatrizCalificaciones.esNotaValida(nota)) {
            System.out.println("La nota " + nota + " está fuera del rango 0.0 - 5.0; no puede estar en la matriz.");
            return;
        }
        informeDeBusqueda(matriz, nota);
    }

    private static void cambiarMatriz() {
        if (nombreMatriz.equals("principal")) {
            matriz = DatosEjemplo.matrizAlternativa();
            nombreMatriz = "alternativa";
        } else {
            matriz = DatosEjemplo.matrizPrincipal();
            nombreMatriz = "principal";
        }
        System.out.println("Ahora se usa la matriz " + nombreMatriz + ":");
        System.out.println(matriz.comoTabla());
    }

    private static void demostracion() {
        MatrizCalificaciones principal = DatosEjemplo.matrizPrincipal();
        System.out.println();
        System.out.println(principal.comoTabla());
        System.out.println(">>> Caso 1: nota que aparece UNA sola vez");
        informeDeBusqueda(principal, 2.5);
        System.out.println(">>> Caso 2: nota que aparece VARIAS veces");
        informeDeBusqueda(principal, 4.5);
        System.out.println(">>> Caso 3: nota que NO existe");
        informeDeBusqueda(principal, 1.0);
    }

    /** Ejecuta todas las búsquedas para una nota y muestra los resultados. */
    static void informeDeBusqueda(MatrizCalificaciones m, double nota) {
        String n = String.format(Locale.US, "%.1f", nota);
        System.out.println("------------------------------------------------------------");
        System.out.println("Búsqueda de la calificación " + n);
        if (m.estaVacia()) {
            System.out.println("La matriz está vacía: no hay nada que buscar.");
            return;
        }
        int totalPosiciones = m.getNumeroEstudiantes() * m.getNumeroAsignaturas();

        int[] primera = BUSCADOR.buscarPrimeraAparicion(m, nota);
        int compPrimera = BUSCADOR.getComparaciones();
        if (primera[0] == -1) {
            System.out.println("La calificación " + n + " NO aparece en la matriz.");
            System.out.println("Comparaciones realizadas: " + compPrimera + " de " + totalPosiciones
                    + " posiciones (se recorrió toda la matriz).");
            return;
        }

        int ocurrencias = BUSCADOR.contarOcurrencias(m, nota);
        int[] ultima = BUSCADOR.buscarUltimaAparicion(m, nota);
        int compUltima = BUSCADOR.getComparaciones();
        int[][] coordenadas = BUSCADOR.obtenerCoordenadas(m, nota);
        Coincidencia[] coincidencias = BUSCADOR.buscarCoincidencias(m, nota);

        System.out.println("¿Existe?            : Sí");
        System.out.println("Ocurrencias         : " + ocurrencias);
        System.out.println("Primera aparición   : " + Arrays.toString(primera) + " -> "
                + m.getEstudiante(primera[0]) + " en " + m.getAsignatura(primera[1])
                + "  (" + compPrimera + " comparaciones)");
        System.out.println("Última aparición    : " + Arrays.toString(ultima) + " -> "
                + m.getEstudiante(ultima[0]) + " en " + m.getAsignatura(ultima[1])
                + "  (" + compUltima + " comparaciones, recorrido inverso)");
        System.out.println("Todas las coordenadas: " + Arrays.deepToString(coordenadas));
        System.out.println("Detalle de cada coincidencia:");
        for (int k = 0; k < coincidencias.length; k++) {
            System.out.println("  " + (k + 1) + ". " + coincidencias[k]);
        }
    }

    private static String leerLinea(String mensaje) {
        System.out.print(mensaje);
        if (!ENTRADA.hasNextLine()) {
            return null;
        }
        return ENTRADA.nextLine();
    }
}
