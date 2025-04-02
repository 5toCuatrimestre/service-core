package jbar.service_core.Sell.Model;

public class SalesTimeChartDTO {
    private String hour;
    private double ventas;

    public SalesTimeChartDTO(String hour, double ventas) {
        this.hour = hour;
        this.ventas = ventas;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public double getVentas() {
        return ventas;
    }

    public void setVentas(double ventas) {
        this.ventas = ventas;
    }
    
}
