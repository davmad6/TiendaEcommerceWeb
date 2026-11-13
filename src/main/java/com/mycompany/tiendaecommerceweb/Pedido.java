package com.mycompany.tiendaecommerceweb;

import java.time.LocalDateTime;

public class Pedido {
    private int idPedido;
    private LocalDateTime fecha;
    private double total;
    private String estado;
    private int idUsuario;

    public Pedido() {
    }

    public Pedido(LocalDateTime fecha, double total, String estado, int idUsuario) {
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.idUsuario = idUsuario;
    }

    public Pedido(int idPedido, LocalDateTime fecha, double total, String estado, int idUsuario) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.idUsuario = idUsuario;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}