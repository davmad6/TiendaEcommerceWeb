package com.mycompany.tiendaecommerceweb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    // CREAR (Insert)
    public boolean insertar(Categoria categoria) {
        String sql = "INSERT INTO categorias (nombre) VALUES (?)";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre());
            ps.executeUpdate();
            System.out.println("Categoria registrada correctamente.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar categoria: " + e.getMessage());
            return false;
        }
    }

    // LEER (Select)
    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT id_categoria, nombre FROM categorias";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Categoria cat = new Categoria(
                    rs.getInt("id_categoria"),
                    rs.getString("nombre")
                );
                lista.add(cat);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar categorias: " + e.getMessage());
        }
        return lista;
    }

    // ACTUALIZAR (Update)
    public boolean actualizar(Categoria categoria) {
        String sql = "UPDATE categorias SET nombre = ? WHERE id_categoria = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombre());
            ps.setInt(2, categoria.getIdCategoria());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Categoria actualizada correctamente.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar categoria: " + e.getMessage());
        }
        return false;
    }

    // ELIMINAR (Delete)
    public boolean eliminar(int idCategoria) {
        String sql = "DELETE FROM categorias WHERE id_categoria = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCategoria);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Categoria eliminada correctamente.");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar categoria: " + e.getMessage());
        }
        return false;
    }
}
