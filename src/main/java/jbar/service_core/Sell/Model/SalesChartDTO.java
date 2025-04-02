package jbar.service_core.Sell.Model;

public class SalesChartDTO {
    private String date;
    private double ventasTotales;

    // Constructor
    public SalesChartDTO(String date, double ventasTotales) {
        this.date = date;
        this.ventasTotales = ventasTotales;
    }

    // Getters y setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getVentasTotales() {
        return ventasTotales;
    }

    public void setVentasTotales(double ventasTotales) {
        this.ventasTotales = ventasTotales;
    }
}