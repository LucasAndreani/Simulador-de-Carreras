package simuladorcarreras.model;

public class ResultadoPiloto {
    private Piloto piloto;
    private double tiempoTotal;
    private int posicionFinal;
    private double diferenciaConLider;
    private int cantidadPitStop;

    public ResultadoPiloto(Piloto piloto, double tiempoTotal, int posicionFinal, double diferenciaConLider, int cantidadPitStop) {
        this.piloto = piloto;
        this.tiempoTotal = tiempoTotal;
        this.posicionFinal = posicionFinal;
        this.diferenciaConLider = diferenciaConLider;
        this.cantidadPitStop = cantidadPitStop;
    }

    public Piloto getPiloto() {
        return piloto;
    }

    public double getTiempoTotal() {
        return tiempoTotal;
    }

    public int getPosicionFinal() {
        return posicionFinal;
    }

    public double getDiferenciaConLider() {
        return diferenciaConLider;
    }

    public int getCantidadPitStop() {
        return cantidadPitStop;
    }

    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;
    }

    public void setTiempoTotal(double tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }

    public void setPosicionFinal(int posicionFinal) {
        this.posicionFinal = posicionFinal;
    }

    public void setDiferenciaConLider(double diferenciaConLider) {
        this.diferenciaConLider = diferenciaConLider;
    }

    public void setCantidadPitStop(int cantidadPitStop) {
        this.cantidadPitStop = cantidadPitStop;
    }

    @Override
    public String toString() {
        return "ResultadoPiloto{" +
                "piloto=" + piloto.getNombre() +
                ", tiempoTotal=" + tiempoTotal +
                ", posicionFinal=" + posicionFinal +
                ", diferenciaConLider=" + diferenciaConLider +
                ", cantidadPitStop=" + cantidadPitStop +
                '}';
    }
}
