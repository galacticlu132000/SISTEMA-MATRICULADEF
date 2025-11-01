package gui.evaluacion.sopa;

import java.util.*;

public class GeneradorSopaDeLetras {

    private static final Random random = new Random();
    private static final char[] letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static char[][] generarCuadricula(List<String> palabras, int tamaño) {
        char[][] matriz = new char[tamaño][tamaño];
        for (int i = 0; i < tamaño; i++) {
            Arrays.fill(matriz[i], '_');
        }

        for (String palabra : palabras) {
            boolean colocada = false;
            for (int intento = 0; intento < 100 && !colocada; intento++) {
                int fila = random.nextInt(tamaño);
                int col = random.nextInt(tamaño);
                Direccion dir = Direccion.random();

                if (puedeInsertar(matriz, palabra, fila, col, dir)) {
                    insertar(matriz, palabra, fila, col, dir);
                    colocada = true;
                }
            }
        }

        rellenarAleatorio(matriz);
        return matriz;
    }

    private static boolean puedeInsertar(char[][] matriz, String palabra, int fila, int col, Direccion dir) {
        int dx = dir.dx, dy = dir.dy;
        int x = fila, y = col;

        for (char c : palabra.toCharArray()) {
            if (x < 0 || x >= matriz.length || y < 0 || y >= matriz[0].length) return false;
            if (matriz[x][y] != '_' && matriz[x][y] != c) return false;
            x += dx;
            y += dy;
        }
        return true;
    }

    private static void insertar(char[][] matriz, String palabra, int fila, int col, Direccion dir) {
        int dx = dir.dx, dy = dir.dy;
        int x = fila, y = col;

        for (char c : palabra.toCharArray()) {
            matriz[x][y] = c;
            x += dx;
            y += dy;
        }
    }

    private static void rellenarAleatorio(char[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] == '_') {
                    matriz[i][j] = letras[random.nextInt(letras.length)];
                }
            }
        }
    }

    public enum Direccion {
        DERECHA(0, 1), IZQUIERDA(0, -1),
        ABAJO(1, 0), ARRIBA(-1, 0),
        DIAG_ABAJO_DER(1, 1), DIAG_ABAJO_IZQ(1, -1),
        DIAG_ARRIBA_DER(-1, 1), DIAG_ARRIBA_IZQ(-1, -1);

        public final int dx, dy;

        Direccion(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public static Direccion random() {
            Direccion[] dirs = values();
            return dirs[random.nextInt(dirs.length)];
        }
    }
}

