package seguridad;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * Clase utilitaria para encriptar y verificar contraseñas usando SHA-256.
 * Proporciona métodos estáticos para aplicar hashing seguro y comparar credenciales.
 */
public class Encriptador {

    // ╔════════════════════════════════════════════════════╗
    // ║              Encriptación con SHA-256             ║
    // ╚════════════════════════════════════════════════════╝

    /**
     * Encripta una contraseña en texto plano usando el algoritmo SHA-256.
     * El resultado se codifica en Base64 para facilitar su almacenamiento.
     *
     * @param contrasenaPlano Contraseña en texto plano
     * @return Cadena encriptada en Base64
     */
    public static String encriptar(String contrasenaPlano) {
        try {
            MessageDigest algoritmoSHA256 = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = algoritmoSHA256.digest(contrasenaPlano.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (Exception error) {
            throw new RuntimeException("Error al encriptar la contraseña", error);
        }
    }

    // ╔════════════════════════════════════════════════════╗
    // ║             Verificación de contraseña             ║
    // ╚════════════════════════════════════════════════════╝

    /**
     * Verifica si una contraseña ingresada coincide con el hash almacenado.
     *
     * @param contrasenaPlano Contraseña ingresada en texto plano
     * @param hashAlmacenado Hash previamente encriptado y almacenado
     * @return true si coinciden, false en caso contrario
     */
    public static boolean verificar(String contrasenaPlano, String hashAlmacenado) {
        String hashIngresado = encriptar(contrasenaPlano);
        return hashIngresado.equals(hashAlmacenado);
    }
}

