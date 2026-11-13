# Matriz de calificaciones - Algoritmos de búsqueda

Actividad de la Unidad 2 (Algoritmos de búsqueda) de **Estructuras de Datos** — Ingeniería de Software, Universidad de Cartagena.
**Ejercicio asignado: 14. Matriz de calificaciones.**

Autor: Carlos Andrés Núñez Vargas

## Descripción

Las calificaciones de varios estudiantes en diferentes asignaturas se guardan en una matriz `double[][]`:

- **Filas** = estudiantes
- **Columnas** = asignaturas
- `notas[fila][columna]` = nota del estudiante `fila` en la asignatura `columna` (escala 0.0 – 5.0)

El programa permite:

| Requisito del ejercicio | Método |
| --- | --- |
| Buscar una calificación específica | `existe(...)` / `buscarPrimeraAparicion(...)` |
| Obtener todas las coordenadas donde aparece | `obtenerCoordenadas(...)` |
| Buscar la primera aparición | `buscarPrimeraAparicion(...)` |
| Contar sus ocurrencias | `contarOcurrencias(...)` |
| Determinar en qué estudiante y asignatura aparece cada coincidencia | `buscarCoincidencias(...)` |
| (Adicional) Buscar la última aparición | `buscarUltimaAparicion(...)` |

Cada búsqueda registra el **número de comparaciones** realizadas (`getComparaciones()`).

## Estructura del proyecto

```
src/calificaciones/
├── MatrizCalificaciones.java    Modelo: nombres de estudiantes, asignaturas y matriz de notas (con validaciones)
├── Coincidencia.java            Resultado de una búsqueda: [fila, columna], estudiante, asignatura y nota
├── BuscadorCalificaciones.java  Algoritmos de búsqueda lineal en 2D
├── DatosEjemplo.java            Matriz principal (6x5), matriz alternativa (3x4) y matriz vacía
├── PruebasBuscador.java         19 casos de prueba sin librerías externas
└── Main.java                    Menú de consola
```

## Cómo ejecutar

Requiere Java 8 o superior.

```bash
# Compilar
javac -encoding UTF-8 -d out src/calificaciones/*.java

# Ejecutar el programa (menú)
java -cp out calificaciones.Main

# Ejecutar solo las pruebas
java -cp out calificaciones.PruebasBuscador
```

También se puede abrir la carpeta en NetBeans, IntelliJ o VS Code y ejecutar `Main.java`.

### Menú

```
1. Ver la matriz de calificaciones
2. Buscar una calificación
3. Cambiar de matriz (principal / alternativa)
4. Demostración automática (existe una vez, varias veces, no existe)
5. Ejecutar casos de prueba
0. Salir
```

La nota se puede escribir con punto o con coma (`4.5` o `4,5`). Si se escribe una nota fuera del rango 0.0 – 5.0 o un texto que no es número, el programa lo informa.

## Precondiciones y elección del algoritmo

- La matriz **no está ordenada**: las notas no siguen ningún orden por filas ni por columnas.
- Por eso **no se puede usar búsqueda binaria** (exige datos ordenados) ni **staircase search** (exige filas y columnas crecientes).
- El algoritmo correcto es la **búsqueda lineal (ingenua) en 2D**: un doble ciclo que recorre la matriz fila por fila, de izquierda a derecha.
- "Primera" y "última" aparición se definen según ese recorrido fila por fila.
- Las notas son `double`, así que se comparan con una tolerancia (`Math.abs(a - b) < 1e-9`) en lugar de `==`, para evitar errores de redondeo (por ejemplo, `0.1 + 0.2` no es exactamente `0.3`).

## Complejidad temporal

Sea `n` el número de estudiantes (filas) y `m` el número de asignaturas (columnas).

| Método | Mejor caso | Peor caso | Explicación |
| --- | --- | --- | --- |
| `buscarPrimeraAparicion` | O(1) | O(n·m) | Se detiene en la primera coincidencia; si no existe recorre todo |
| `buscarUltimaAparicion` | O(1) | O(n·m) | Igual, pero recorre desde la última celda hacia atrás |
| `existe` | O(1) | O(n·m) | Usa `buscarPrimeraAparicion` |
| `contarOcurrencias` | O(n·m) | O(n·m) | Siempre debe revisar todas las celdas |
| `obtenerCoordenadas` | O(n·m) | O(n·m) | Dos recorridos (contar y llenar): 2·n·m = O(n·m) |
| `buscarCoincidencias` | O(n·m) | O(n·m) | Coordenadas + O(k) para crear los k objetos, k ≤ n·m |

Ejemplo con la matriz principal (6 × 5 = 30 celdas):

- `4.5` está en `[0, 0]` → **1** comparación (mejor caso).
- `2.5` está en `[3, 0]` → 3·5 + 0 + 1 = **16** comparaciones.
- `1.0` no existe → **30** comparaciones (peor caso, se recorre toda la matriz).

## Ejemplo de salida

```
Búsqueda de la calificación 4.5
¿Existe?            : Sí
Ocurrencias         : 6
Primera aparición   : [0, 0] -> Ana Martínez en Matemáticas  (1 comparaciones)
Última aparición    : [5, 4] -> Julián Castro en Química  (1 comparaciones, recorrido inverso)
Todas las coordenadas: [[0, 0], [1, 1], [2, 0], [2, 4], [4, 2], [5, 4]]
Detalle de cada coincidencia:
  1. [0, 0] -> Ana Martínez en Matemáticas (nota 4.5)
  2. [1, 1] -> Luis Pérez en Física (nota 4.5)
  3. [2, 0] -> María Gómez en Matemáticas (nota 4.5)
  4. [2, 4] -> María Gómez en Química (nota 4.5)
  5. [4, 2] -> Sofía Herrera en Programación (nota 4.5)
  6. [5, 4] -> Julián Castro en Química (nota 4.5)
```
