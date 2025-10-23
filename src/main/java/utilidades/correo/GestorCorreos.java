package utilidades.correo;



import jakarta.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

public class GestorCorreos {
    private static final Map<String, RegistroCorreo> registros = new HashMap<>();

    public static void enviarYRegistrar(String destinatario, String asunto, String cuerpo) {
        try {
            Correo.enviar(destinatario, asunto, cuerpo);
            obtenerRegistro(destinatario).agregar(asunto, cuerpo);
        } catch (MessagingException e) {
            System.out.println("❌ Error al enviar correo a " + destinatario + ": " + e.getMessage());
        }
    }

    public static RegistroCorreo obtenerRegistro(String correo) {
        return registros.computeIfAbsent(correo, RegistroCorreo::new);
    }
}
