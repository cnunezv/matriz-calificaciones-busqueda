package calificaciones;

/**
 * Matrices de calificaciones listas para usar en la demostración y en las pruebas.
 * Las notas están en la escala colombiana de 0.0 a 5.0.
 */
public final class DatosEjemplo {

    private DatosEjemplo() {
    }

    /** Ejemplo principal: 6 estudiantes x 5 asignaturas, con notas repetidas a propósito. */
    public static MatrizCalificaciones matrizPrincipal() {
        String[] estudiantes = {
            "Ana Martínez",
            "Luis Pérez",
            "María Gómez",
            "Carlos Ruiz",
            "Sofía Herrera",
            "Julián Castro"
        };
        String[] asignaturas = {"Matemáticas", "Física", "Programación", "Inglés", "Química"};
        double[][] notas = {
            {4.5, 3.8, 5.0, 4.2, 3.5},
            {3.0, 4.5, 3.8, 2.9, 4.0},
            {4.5, 5.0, 4.7, 3.8, 4.5},
            {2.5, 3.0, 3.5, 4.0, 3.0},
            {5.0, 4.2, 4.5, 4.8, 3.9},
            {3.8, 2.8, 4.0, 3.5, 4.5}
        };
        return new MatrizCalificaciones(estudiantes, asignaturas, notas);
    }

    /** Segundo caso, distinto al principal: 3 estudiantes x 4 asignaturas. */
    public static MatrizCalificaciones matrizAlternativa() {
        String[] estudiantes = {"Pedro Díaz", "Laura Torres", "Andrés Mejía"};
        String[] asignaturas = {"Cálculo", "Estadística", "Bases de Datos", "Ética"};
        double[][] notas = {
            {2.0, 3.3, 3.3, 4.9},
            {3.3, 1.5, 4.1, 3.3},
            {4.9, 3.6, 2.0, 1.5}
        };
        return new MatrizCalificaciones(estudiantes, asignaturas, notas);
    }

    /** Caso borde: matriz sin estudiantes ni asignaturas. */
    public static MatrizCalificaciones matrizVacia() {
        return new MatrizCalificaciones(new String[0], new String[0], new double[0][0]);
    }
}
