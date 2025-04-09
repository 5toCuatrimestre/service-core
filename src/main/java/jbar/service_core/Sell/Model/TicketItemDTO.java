package jbar.service_core.Sell.Model;

public class TicketItemDTO {
    private String nombre;
    private int cantidad;
    private double precio;
    private double subtotal;

    public TicketItemDTO() {
    }

    public TicketItemDTO(String nombre, int cantidad, double precio, double subtotal) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.subtotal = subtotal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
} 