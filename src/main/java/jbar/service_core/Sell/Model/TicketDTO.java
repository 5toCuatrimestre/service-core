package jbar.service_core.Sell.Model;

import java.util.List;

public class TicketDTO {
    private String ticketId;
    private String fecha;
    private String hora;
    private String mesero;
    private String mesa;
    private List<TicketItemDTO> items;
    private double subtotal;
    private double total;

    public TicketDTO() {
    }

    public TicketDTO(String ticketId, String fecha, String hora, String mesero, String mesa, List<TicketItemDTO> items, double subtotal, double total) {
        this.ticketId = ticketId;
        this.fecha = fecha;
        this.hora = hora;
        this.mesero = mesero;
        this.mesa = mesa;
        this.items = items;
        this.subtotal = subtotal;
        this.total = total;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMesero() {
        return mesero;
    }

    public void setMesero(String mesero) {
        this.mesero = mesero;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public List<TicketItemDTO> getItems() {
        return items;
    }

    public void setItems(List<TicketItemDTO> items) {
        this.items = items;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
} 