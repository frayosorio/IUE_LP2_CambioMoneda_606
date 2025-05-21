package entidades;

import java.time.LocalDate;

public class CambioMoneda {
    private String moneda;
    private LocalDate fecha;
    private double Cambio;

    public CambioMoneda(String moneda, LocalDate fecha, double cambio) {
        this.moneda = moneda;
        this.fecha = fecha;
        Cambio = cambio;
    }

    public CambioMoneda() {
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getCambio() {
        return Cambio;
    }

    public void setCambio(double cambio) {
        Cambio = cambio;
    }

}
