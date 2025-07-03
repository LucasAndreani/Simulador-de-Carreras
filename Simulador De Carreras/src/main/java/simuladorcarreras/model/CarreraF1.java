package simuladorcarreras.model;

public class CarreraF1 extends Carrera {

    public CarreraF1(int id, Circuito circuito, int cantidadVueltas) {
        super(id, circuito, cantidadVueltas);
    }

    @Override
    protected void simularVuelta() {
        for (Piloto p : participantes) {
            double desempeno = p.calcularDesempeno();
            double tiempoVuelta = circuito.getLongitudKm() / desempeno * 60;

            p.agregarTiempo(tiempoVuelta);

            double desgaste = 0.04 + Math.random() * 0.03;
            p.reducirCondicionNeumaticos(desgaste);

            if (p.getVehiculo().getCondicionNeumaticos() < p.getUmbralPitStop()) {
                p.hacerPitStop();
            }

            System.out.printf("%s - Condición antes de vuelta: %.2f\n", p.getNombre(), p.getVehiculo().getCondicionNeumaticos());
        }
    }
}
