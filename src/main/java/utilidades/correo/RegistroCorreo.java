package utilidades.correo;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RegistroCorreo {
    private final String correo;
    private final List<String> historial = new ArrayList<>();

    public RegistroCorreo(String correo) {
        this.correo = correo;
    }

    public void agregar(String asunto, String cuerpo) {
        String entrada = "🕒 " + LocalDateTime.now() +
                "\n📝 Asunto: " + asunto +
                "\n✉️  Mensaje:\n" + cuerpo;
        historial.add(entrada);
        System.out.println("📤 Registro de correo enviado a " + correo + ":\n" + entrada + "\n");
    }

    public List<String> obtenerHistorial() {
        return historial;
    }
}
