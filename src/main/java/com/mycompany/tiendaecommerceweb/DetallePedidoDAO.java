package com.mycompany.tiendaecommerceweb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DetallePedidoDAO {

    // CREAR (Insert)
    public boolean insertar(DetallePedido detalle) {
        String sql = "INSERT INTO detalle_pedidos (cantidad, precio_unitario, id_pedido, id_producto) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, detalle.getCantidad());
            ps.setDouble(2, detalle.getPrecioUnitario());
            ps.setInt(3, detalle.getIdPedido());
            ps.setInt(4, detalle.getIdProducto());
            ps.executeUpdate();
            System.out.println("Detalle de pedido registrado correctamente.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar detalle de pedido: " + e.getMessage());
            return false;
        }
    }

    // LEER (Select)
    public List<DetallePedido> listar() {
        List<DetallePedido> lista = new ArrayList<>();
        String sql = "SELECT id_detalle, cantidad, precio_unitario, id_pedido, id_producto FROM detalle_pedidos";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DetallePedido det = new DetallePedido(
                    rs.getInt("id_detalle"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio_unitario"),
                    rs.getInt("id_pedido"),
                    rs.getInt("id_producto")
                );
                lista.add(det);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar detalles de pedidos: " + e.getMessage());
        }
        return lista;
    }

    // ACTUALIZAR (Update)
    public boolean actualizar(DetallePedido detalle) {
        String sql = "UPDATE detalle_pedidos SET cantidad = ?, precio_unitario = ?, id_pedido = ?, id_producto = ? WHERE id_detalle = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, detalle.getCantidad());
            ps.setDouble(2, detalle.getPrecioUnitario());
            ps.setInt(3, detalle.getIdPedido());
            ps.setInt(4, detalle.getIdProducto());
            ps.setInt(5, detalle.getIdDetalle());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Detalle de pedido actualizado correctamente.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar detalle de pedido: " + e.getMessage());
        }
        return false;
    }

    // ELIMINAR (Delete)
    public boolean eliminar(int idDetalle) {
        String sql = "DELETE FROM detalle_pedidos WHERE id_detalle = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idDetalle);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Detalle de pedido eliminado correctamente.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar detalle de pedido: " + e.getMessage());
        }
        return false;
    }
}