package simuladorcarreras.model;

import java.util.*;

public abstract class Carrera {
    protected int id;
    protected Circuito circuito;
    protected List<Piloto> participantes = new ArrayList<>();
    protected Map<Piloto, ResultadoPiloto> resultados = new HashMap<>();
    protected int cantidadVueltas;
    protected int vueltasSimuladas = 0;

    public Carrera(int id, Circuito circuito, int cantidadVueltas) {
        this.id = id;
        this.circuito = circuito;
        this.cantidadVueltas = cantidadVueltas;
    }

    public void agregarPiloto(Piloto p) {
        participantes.add(p);
    }

    public void quitarPiloto(Piloto p) {
        participantes.remove(p);
    }

    public int getCantidadVueltas() {
        return cantidadVueltas;
    }

    public int getVueltasSimuladas() {
        return vueltasSimuladas;
    }

    public boolean simularSiguienteVuelta() {
        if (vueltasSimuladas < cantidadVueltas) {
            simularVuelta();
            vueltasSimuladas++;
            actualizarResultadosParciales();
            return true;
        }
        return false;
    }

    public void simularCarrera() {
        while (simularSiguienteVuelta()) {}
    }

    private void actualizarResultadosParciales() {
        List<Piloto> orden = new ArrayList<>(participantes);
        orden.sort(Comparator.comparingDouble(Piloto::getTiempoTotal));
        double tiempoLider = orden.get(0).getTiempoTotal();

        resultados.clear();

        for (int i = 0; i < orden.size(); i++) {
            Piloto p = orden.get(i);
            ResultadoPiloto res = new ResultadoPiloto(
                    p,
                    p.getTiempoTotal(),
                    i + 1,
                    p.getTiempoTotal() - tiempoLider,
                    p.getCantidadPitStop()
            );
            resultados.put(p, res);
        }
    }

    public Map<Piloto, ResultadoPiloto> getResultados() {
        return resultados;
    }

    public List<Piloto> getParticipantes() {
        return participantes;
    }

    protected abstract void simularVuelta();
}
