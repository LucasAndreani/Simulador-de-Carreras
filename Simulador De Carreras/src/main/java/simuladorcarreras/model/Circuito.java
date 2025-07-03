package simuladorcarreras.model;

public class Circuito {

    private int id;
    private String nombre;
    private String ubicacion;
    private double longitudKm;
    private double dificultad;
    private TipoTerreno terreno;
    private TipoCircuito tipoCircuito;

    public Circuito(int id, String nombre, String ubicacion, double longitudKm, double dificultad, TipoTerreno terreno, TipoCircuito tipoCircuito) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.longitudKm = longitudKm;
        this.dificultad = dificultad;
        this.terreno = terreno;
        this.tipoCircuito = tipoCircuito;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getUbicacion() { return ubicacion; }
    public double getLongitudKm() { return longitudKm; }
    public double getDificultad() { return dificultad; }
    public TipoTerreno getTerreno() { return terreno; }
    public TipoCircuito getTipoCircuito() { return tipoCircuito; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public void setLongitudKm(double longitudKm) { this.longitudKm = longitudKm; }
    public void setDificultad(double dificultad) { this.dificultad = dificultad; }
    public void setTerreno(TipoTerreno terreno) { this.terreno = terreno; }
    public void setTipoCircuito(TipoCircuito tipoCircuito) { this.tipoCircuito = tipoCircuito; }
}
