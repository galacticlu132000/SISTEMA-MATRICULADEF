package usuarios;

import seguridad.Encriptador;
import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Clase abstracta que representa un usuario del sistema (estudiante o profesor).
 * Incluye validación de datos, encriptación de contraseña y lógica de autenticación.
 */
public abstract class Usuario {

    // ╔════════════════════════════════════════════════════╗
    // ║                  Atributos privados                ║
    // ╚════════════════════════════════════════════════════╝

    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String identificacionPersonal;
    private String numeroTelefono;
    private String correoElectronico;
    private String direccionFisica;
    private LocalDate fechaRegistro;
    private String contrasenaEncriptada;
    private boolean requiereCambioContrasena;

    // ╔════════════════════════════════════════════════════╗
    // ║                    Constructor                     ║
    // ╚════════════════════════════════════════════════════╝

    public Usuario(String nombre,
                   String primerApellido,
                   String segundoApellido,
                   String identificacionPersonal,
                   String numeroTelefono,
                   String correoElectronico,
                   String direccionFisica,
                   String contrasenaPlano) {

        validarDatos(nombre, primerApellido, segundoApellido, identificacionPersonal,
                numeroTelefono, correoElectronico, direccionFisica, contrasenaPlano);

        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.identificacionPersonal = identificacionPersonal;
        this.numeroTelefono = numeroTelefono;
        this.correoElectronico = correoElectronico;
        this.direccionFisica = direccionFisica;
        this.fechaRegistro = LocalDate.now();
        this.contrasenaEncriptada = Encriptador.encriptar(contrasenaPlano);
        this.requiereCambioContrasena = false;
    }

    // ╔════════════════════════════════════════════════════╗
    // ║               Validación de atributos              ║
    // ╚════════════════════════════════════════════════════╝

    private void validarDatos(String nombre,
                              String apellido1,
                              String apellido2,
                              String id,
                              String telefono,
                              String correo,
                              String direccion,
                              String contrasena) {

        if (nombre == null || nombre.length() < 2 || nombre.length() > 20)
            throw new IllegalArgumentException("❌ El nombre debe tener entre 2 y 20 caracteres.");

        if (apellido1 == null || apellido1.length() < 2 || apellido1.length() > 20)
            throw new IllegalArgumentException("❌ El primer apellido debe tener entre 2 y 20 caracteres.");

        if (apellido2 == null || apellido2.length() < 2 || apellido2.length() > 20)
            throw new IllegalArgumentException("❌ El segundo apellido debe tener entre 2 y 20 caracteres.");

        if (id == null || id.length() < 9)
            throw new IllegalArgumentException("❌ La identificación debe tener al menos 9 caracteres.");

        if (telefono == null || telefono.length() < 8)
            throw new IllegalArgumentException("❌ El número de teléfono debe tener al menos 8 caracteres.");

        if (correo == null || !Pattern.matches("^[^\\s@]{3,}@[^\\s@]{3,}$", correo))
            throw new IllegalArgumentException("❌ El correo electrónico no tiene un formato válido.");

        if (direccion == null || direccion.length() < 5 || direccion.length() > 60)
            throw new IllegalArgumentException("❌ La dirección debe tener entre 5 y 60 caracteres.");

        if (!esContrasenaSegura(contrasena))
            throw new IllegalArgumentException("❌ La contraseña no cumple con los requisitos de seguridad.");
    }

    private boolean esContrasenaSegura(String contrasena) {
        return contrasena != null &&
                contrasena.length() >= 8 &&
                contrasena.matches(".*[A-Z].*") &&
                contrasena.matches(".*[a-z].*") &&
                contrasena.matches(".*\\d.*") &&
                contrasena.matches(".*[!@#$%^&*()_+=<>?].*");
    }

    // ╔════════════════════════════════════════════════════╗
    // ║           Seguridad y autenticación                ║
    // ╚════════════════════════════════════════════════════╝

    public boolean autenticar(String idIngresado, String contrasenaPlano) {
        return this.identificacionPersonal.equals(idIngresado) &&
                Encriptador.verificar(contrasenaPlano, this.contrasenaEncriptada);
    }

    public String verificarCredenciales(String idIngresado, String contrasenaPlano) {
        if (!this.identificacionPersonal.equals(idIngresado)) {
            return "⚠️ Identificación no registrada.";
        }
        if (!Encriptador.verificar(contrasenaPlano, this.contrasenaEncriptada)) {
            return "❌ Contraseña incorrecta.";
        }
        return "✅ Autenticación exitosa.";
    }

    public void actualizarContrasena(String nuevaContrasenaPlano) {
        if (!esContrasenaSegura(nuevaContrasenaPlano))
            throw new IllegalArgumentException("❌ La nueva contraseña no es segura.");
        this.contrasenaEncriptada = Encriptador.encriptar(nuevaContrasenaPlano);
        this.requiereCambioContrasena = false;
    }

    public void establecerContrasenaTemporal(String contrasenaTemporalPlano) {
        this.contrasenaEncriptada = Encriptador.encriptar(contrasenaTemporalPlano);
        this.requiereCambioContrasena = true;
    }

    public boolean isRequiereCambioContrasena() {
        return requiereCambioContrasena;
    }

    // ╔════════════════════════════════════════════════════╗
    // ║                  Getters públicos                  ║
    // ╚════════════════════════════════════════════════════╝

    public String getNombre() {
        return nombre;
    }

    public String getContrasenaEncriptada() {
        return contrasenaEncriptada;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public String getIdentificacionPersonal() {
        return identificacionPersonal;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getDireccionFisica() {
        return direccionFisica;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public String getNombreCompleto() {
        return nombre + " " + primerApellido + " " + segundoApellido;
    }

    private String contrasenaTemporal;

    public String getContrasenaTemporal() {
        return contrasenaTemporal;
    }

    public void setContrasenaTemporal(String contrasenaTemporal) {
        this.contrasenaTemporal = contrasenaTemporal;
    }


    // ╔════════════════════════════════════════════════════╗
    // ║                  Setters públicos                  ║
    // ╚════════════════════════════════════════════════════╝

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setDireccionFisica(String direccionFisica) {
        this.direccionFisica = direccionFisica;
    }

    public void setContrasenaEncriptada(String contrasenaEncriptada) {
        this.contrasenaEncriptada = contrasenaEncriptada;
    }

    // ╔════════════════════════════════════════════════════╗
    // ║            Representación en texto                 ║
    // ╚════════════════════════════════════════════════════╝

    @Override
    public String toString() {
        return getNombreCompleto() + " [" + identificacionPersonal + "]";
    }
}
