package simuladorcarreras.model;

public class Usuario {
    private int id;
    private String nombreUsuario;
    private String contrasenia;

    public Usuario(int id, String nombreUsuario, String contrasenia) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
    }

    public int getId() { return id; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getContrasenia() { return contrasenia; }

    public void setId(int id) { this.id = id; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }
}
