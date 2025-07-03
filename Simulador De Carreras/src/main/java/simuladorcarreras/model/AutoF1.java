package simuladorcarreras.model;

public class AutoF1 extends Vehiculo {

    private double aerodinamica;

    public AutoF1(int id, String modelo, String fabricante, double velocidadMaxima, double confiabilidad, double condicionNeumaticos, double aerodinamica) {
        super(id, modelo, fabricante, velocidadMaxima, confiabilidad, condicionNeumaticos);
        this.aerodinamica = aerodinamica;
    }

    public AutoF1(String modelo, String fabricante, double velocidadMaxima, double confiabilidad, double aerodinamica) {
        super(modelo, fabricante, velocidadMaxima, confiabilidad);
        this.aerodinamica = aerodinamica;
    }

    @Override
    public double calcularEficiencia() {
        return velocidadMaxima * aerodinamica * condicionNeumaticos * confiabilidad;
    }

    public double getAerodinamica() {
        return aerodinamica;
    }

    public void setAerodinamica(double aerodinamica) {
        this.aerodinamica = aerodinamica;
    }
}
