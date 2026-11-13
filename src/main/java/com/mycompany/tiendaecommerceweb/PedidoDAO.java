package com.mycompany.tiendaecommerceweb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // CREAR (Insert)
    public boolean insertar(Pedido pedido) {
        String sql = "INSERT INTO pedidos (fecha, total, estado, id_usuario) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(pedido.getFecha()));
            ps.setDouble(2, pedido.getTotal());
            ps.setString(3, pedido.getEstado());
            ps.setInt(4, pedido.getIdUsuario());
            ps.executeUpdate();
            System.out.println("Pedido registrado correctamente.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar pedido: " + e.getMessage());
            return false;
        }
    }

    // LEER (Select)
    public List<Pedido> listar() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT id_pedido, fecha, total, estado, id_usuario FROM pedidos";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pedido ped = new Pedido(
                    rs.getInt("id_pedido"),
                    rs.getTimestamp("fecha").toLocalDateTime(),
                    rs.getDouble("total"),
                    rs.getString("estado"),
                    rs.getInt("id_usuario")
                );
                lista.add(ped);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }
        return lista;
    }

    // ACTUALIZAR (Update)
    public boolean actualizar(Pedido pedido) {
        String sql = "UPDATE pedidos SET fecha = ?, total = ?, estado = ?, id_usuario = ? WHERE id_pedido = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(pedido.getFecha()));
            ps.setDouble(2, pedido.getTotal());
            ps.setString(3, pedido.getEstado());
            ps.setInt(4, pedido.getIdUsuario());
            ps.setInt(5, pedido.getIdPedido());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Pedido actualizado correctamente.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar pedido: " + e.getMessage());
        }
        return false;
    }

    // ELIMINAR (Delete)
    public boolean eliminar(int idPedido) {
        String sql = "DELETE FROM pedidos WHERE id_pedido = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Pedido eliminado correctamente.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar pedido: " + e.getMessage());
        }
        return false;
    }
}