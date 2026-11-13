package com.mycompany.tiendaecommerceweb;

public class DetallePedido {
    private int idDetalle;
    private int cantidad;
    private double precioUnitario;
    private int idPedido;
    private int idProducto;

    public DetallePedido() {
    }

    public DetallePedido(int cantidad, double precioUnitario, int idPedido, int idProducto) {
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.idPedido = idPedido;
        this.idProducto = idProducto;
    }

    public DetallePedido(int idDetalle, int cantidad, double precioUnitario, int idPedido, int idProducto) {
        this.idDetalle = idDetalle;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.idPedido = idPedido;
        this.idProducto = idProducto;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
}