package com.mycompany.tiendaecommerceweb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ProductoServlet", urlPatterns = {"/ProductoServlet"})
public class ProductoServlet extends HttpServlet {

    private final ProductoDAO productoDAO = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");

        if ("eliminar".equals(accion)) {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean eliminado = productoDAO.eliminar(id);
            response.sendRedirect("ProductoServlet?msg=" + (eliminado ? "eliminado" : "error"));
            return;
        }

        List<Producto> listaCompleta = productoDAO.listar();

        if ("editar".equals(accion)) {
            int idEditar = Integer.parseInt(request.getParameter("id"));
            for (Producto p : listaCompleta) {
                if (p.getIdProducto() == idEditar) {
                    request.setAttribute("productoEditar", p);
                    break;
                }
            }
        }

        // Filtro de búsqueda enviado mediante formulario HTML con método GET
        String buscar = request.getParameter("buscar");
        List<Producto> listaMostrar = new ArrayList<>();
        if (buscar != null && !buscar.trim().isEmpty()) {
            String filtro = buscar.trim().toLowerCase();
            for (Producto p : listaCompleta) {
                if (p.getNombre().toLowerCase().contains(filtro)) {
                    listaMostrar.add(p);
                }
            }
        } else {
            listaMostrar = listaCompleta;
        }

        request.setAttribute("listaProductos", listaMostrar);
        request.getRequestDispatcher("productos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String idTexto = request.getParameter("idProducto");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));

        if (idTexto == null || idTexto.trim().isEmpty() || "0".equals(idTexto.trim())) {
            Producto nuevo = new Producto(nombre, descripcion, precio, stock, idCategoria);
            boolean creado = productoDAO.insertar(nuevo);
            response.sendRedirect("ProductoServlet?msg=" + (creado ? "creado" : "error"));
        } else {
            int idProducto = Integer.parseInt(idTexto);
            Producto actualizado = new Producto(idProducto, nombre, descripcion, precio, stock, idCategoria);
            boolean editado = productoDAO.actualizar(actualizado);
            response.sendRedirect("ProductoServlet?msg=" + (editado ? "actualizado" : "error"));
        }
    }
}