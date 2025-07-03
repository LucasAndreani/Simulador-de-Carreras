package simuladorcarreras.model;

public abstract class Vehiculo {
    protected int id;
    protected String modelo;
    protected String fabricante;
    protected double velocidadMaxima;
    protected double confiabilidad;
    protected double condicionNeumaticos;

    public Vehiculo(int id, String modelo, String fabricante, double velocidadMaxima, double confiabilidad, double condicionNeumaticos) {
        this.id = id;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.velocidadMaxima = velocidadMaxima;
        this.confiabilidad = confiabilidad;
        this.condicionNeumaticos = condicionNeumaticos;
    }

    public Vehiculo(String modelo, String fabricante, double velocidadMaxima, double confiabilidad) {
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.velocidadMaxima = velocidadMaxima;
        this.confiabilidad = confiabilidad;
        this.condicionNeumaticos = 1.0;
    }

    public void reducirCondicionNeumaticos(double cant) {
        condicionNeumaticos -= cant;
        if (condicionNeumaticos < 0) condicionNeumaticos = 0;
    }

    public void reiniciarCondicion() {
        condicionNeumaticos = 1.0;
    }

    public abstract double calcularEficiencia();

    public int getId() {
        return id;
    }

    public String getModelo() {
        return modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public double getVelocidadMaxima() {
        return velocidadMaxima;
    }

    public double getConfiabilidad() {
        return confiabilidad;
    }

    public double getCondicionNeumaticos() {
        return condicionNeumaticos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public void setVelocidadMaxima(double velocidadMaxima) {
        this.velocidadMaxima = velocidadMaxima;
    }

    public void setConfiabilidad(double confiabilidad) {
        this.confiabilidad = confiabilidad;
    }

    public void setCondicionNeumaticos(double condicionNeumaticos) {
        this.condicionNeumaticos = condicionNeumaticos;
    }
}
