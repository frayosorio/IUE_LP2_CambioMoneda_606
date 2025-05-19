package entidades;

import java.time.LocalDate;

public class CambioMoneda {
    private String moneda;
    private LocalDate fecha;
    private double cambio;

    public CambioMoneda(String moneda, LocalDate fecha, double cambio) {
        this.moneda = moneda;
        this.fecha = fecha;
        this.cambio = cambio;
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
        return cambio;
    }

    public void setCambio(double cambio) {
        this.cambio = cambio;
    }

}
