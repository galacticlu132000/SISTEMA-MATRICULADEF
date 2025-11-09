package evaluacion;
import java.util.*;
import java.io.Serializable;

public class GeneradorSopaLetras implements Serializable{

    private static final Random random = new Random();
    private static final int[] DX = {0, 0, 1, -1, 1, -1, 1, -1}; // 8 direcciones
    private static final int[] DY = {1, -1, 0, 0, 1, -1, -1, 1};

    public static char[][] generarMatriz(List<String> palabras) {
        if (palabras.size() < 10) {
            throw new IllegalArgumentException("Debe haber al menos 10 palabras para la sopa de letras.");
        }

        int maxLongitud = palabras.stream().mapToInt(String::length).max().orElse(8);
        int tamano = Math.max(10, maxLongitud); // mínimo 10x10
        char[][] matriz = new char[tamano][tamano];

        // Inicializar con espacios
        for (char[] fila : matriz) {
            Arrays.fill(fila, ' ');
        }

        for (String palabra : palabras) {
            boolean colocada = false;
            int intentos = 0;

            while (!colocada && intentos < 100) {
                int direccion = random.nextInt(8);
                int fila = random.nextInt(tamano);
                int col = random.nextInt(tamano);

                if (puedeColocar(matriz, palabra, fila, col, direccion)) {
                    colocarPalabra(matriz, palabra, fila, col, direccion);
                    colocada = true;
                }
                intentos++;
            }

            if (!colocada) {
                System.out.println("⚠️ No se pudo colocar la palabra: " + palabra);
            }
        }

        // Rellenar espacios vacíos
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                if (matriz[i][j] == ' ') {
                    matriz[i][j] = (char) ('A' + random.nextInt(26));
                }
            }
        }

        return matriz;
    }

    private static boolean puedeColocar(char[][] matriz, String palabra, int fila, int col, int dir) {
        int dx = DX[dir];
        int dy = DY[dir];
        int tamano = matriz.length;

        for (int i = 0; i < palabra.length(); i++) {
            int x = fila + i * dx;
            int y = col + i * dy;

            if (x < 0 || x >= tamano || y < 0 || y >= tamano) return false;
            if (matriz[x][y] != ' ' && matriz[x][y] != palabra.charAt(i)) return false;
        }
        return true;
    }

    private static void colocarPalabra(char[][] matriz, String palabra, int fila, int col, int dir) {
        int dx = DX[dir];
        int dy = DY[dir];

        for (int i = 0; i < palabra.length(); i++) {
            int x = fila + i * dx;
            int y = col + i * dy;
            matriz[x][y] = palabra.charAt(i);
        }
    }
}
