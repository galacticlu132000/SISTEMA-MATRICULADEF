package utilidades.correo;
import gui.login.ControladorLogin;


import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;


/**
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ 📧 Correo                                                                 ║
 * ║                                                                            ║
 * ║ Clase utilitaria para enviar correos electrónicos desde la aplicación.    ║
 * ║ Utiliza JavaMail API y SMTP para enviar mensajes decorativos y seguros.   ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 */
public class Correo {

    // ╔════════════════════════════════════════════════════════════╗
    // ║                 CONFIGURACIÓN DEL SERVIDOR SMTP            ║
    // ╚════════════════════════════════════════════════════════════╝
    private static final String REMITENTE = "admin@gmail.com"; // Cambiar por tu cuenta real
    private static final String CLAVE_REMITENTE = "M4r!p0s@_V1oL3t4#2025";  // Nunca dejar en texto plano
    private static final String HOST = "sandbox.smtp.mailtrap.io";
    private static final int PUERTO = 587;

    /**
     * ╔════════════════════════════════════════════════════════════╗
     * ║                 ENVÍO DE CORREO ELECTRÓNICO                ║
     * ╚════════════════════════════════════════════════════════════╝
     * @param destinatario correo del receptor
     * @param asunto asunto del mensaje
     * @param cuerpo cuerpo del mensaje
     */
    public static void enviar(String destinatario, String asunto, String cuerpo) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", String.valueOf(PUERTO));

        Session sesion = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMITENTE, CLAVE_REMITENTE);
            }
        });

        Message mensaje = new MimeMessage(sesion);
        mensaje.setFrom(new InternetAddress(REMITENTE));
        mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);

        Transport.send(mensaje);
        System.out.println("📨 Correo enviado exitosamente a " + destinatario);
    }
}

