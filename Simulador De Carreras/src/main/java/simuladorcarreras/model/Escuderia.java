package simuladorcarreras.model;

public class Escuderia {

    private int id;
    private String nombre;

    public Escuderia() {}

    public Escuderia(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Escuderia(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
