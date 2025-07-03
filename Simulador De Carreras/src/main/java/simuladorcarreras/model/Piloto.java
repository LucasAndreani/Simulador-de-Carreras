package simuladorcarreras.model;

public class Piloto {
    private int id;
    private String nombre;
    private String nacionalidad;
    private int nivelAtaque;
    private boolean esReal;
    private Integer usuarioId;
    private Integer escuderiaId;
    private double umbralPitStop;


    private Vehiculo vehiculo;
    private double tiempoTotal;
    private int cantidadPitStop;

    public Piloto() {
        this.tiempoTotal = 0;
        this.cantidadPitStop = 0;
    }

    public Piloto(int id, String nombre, String nacionalidad, int nivelAtaque, boolean esReal, Integer usuarioId) {
        this(id, nombre, nacionalidad, nivelAtaque, esReal, usuarioId, null);
    }

    public Piloto(int id, String nombre, String nacionalidad, int nivelAtaque, boolean esReal, Integer usuarioId, Integer escuderiaId) {
        this.id = id;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.nivelAtaque = nivelAtaque;
        this.esReal = esReal;
        this.usuarioId = usuarioId;
        this.escuderiaId = escuderiaId;
        this.tiempoTotal = 0;
        this.cantidadPitStop = 0;
        this.umbralPitStop = 0.4 + Math.random() * 0.1;
    }

    public Piloto(String nombre, String nacionalidad, int nivelAtaque, boolean esReal, Integer usuarioId) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.nivelAtaque = nivelAtaque;
        this.esReal = esReal;
        this.usuarioId = usuarioId;
        this.tiempoTotal = 0;
        this.cantidadPitStop = 0;
    }

    public double calcularDesempeno() {
        double agresividad = 1 + (nivelAtaque - 5) * 0.02;
        double randomFactor = 0.95 + Math.random() * 0.1;
        return vehiculo != null ? vehiculo.calcularEficiencia() * agresividad * randomFactor : 0;
    }

    public void reducirCondicionNeumaticos(double cant) {
        if (vehiculo != null) {
            vehiculo.reducirCondicionNeumaticos(cant);
        }
    }

    public void hacerPitStop() {
        if (vehiculo != null) {
            vehiculo.reiniciarCondicion();
        }
        cantidadPitStop++;
        tiempoTotal += 20;
        System.out.printf("%s entra a boxes\n", this.getNombre());
    }

    public void agregarTiempo(double tiempo) {
        this.tiempoTotal += tiempo;
    }

    public void reiniciarEstadisticas() {
        this.tiempoTotal = 0;
        this.cantidadPitStop = 0;
        if (vehiculo != null) {
            vehiculo.reiniciarCondicion();
        }
    }

    public double getTiempoTotal() {
        return tiempoTotal;
    }

    public int getCantidadPitStop() {
        return cantidadPitStop;
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

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getNivelAtaque() {
        return nivelAtaque;
    }

    public void setNivelAtaque(int nivelAtaque) {
        this.nivelAtaque = nivelAtaque;
    }

    public boolean isEsReal() {
        return esReal;
    }

    public void setEsReal(boolean esReal) {
        this.esReal = esReal;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getEscuderiaId() {
        return escuderiaId;
    }

    public void setEscuderiaId(Integer escuderiaId) {
        this.escuderiaId = escuderiaId;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public double getUmbralPitStop() {
        return umbralPitStop;
    }

    @Override
    public String toString() {
        return nombre + " (" + nacionalidad + ") - Ataque: " + nivelAtaque;
    }
}
