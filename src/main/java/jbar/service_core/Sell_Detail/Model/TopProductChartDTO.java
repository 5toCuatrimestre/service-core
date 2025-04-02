package jbar.service_core.Sell_Detail.Model;

public class TopProductChartDTO {
    private String name;
    private int ventas;

    public TopProductChartDTO(String name, int ventas) {
        this.name = name;
        this.ventas = ventas;
    }

    public String getName() {
        return name;
    }

    public int getVentas() {
        return ventas;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVentas(int ventas) {
        this.ventas = ventas;
    }
}
