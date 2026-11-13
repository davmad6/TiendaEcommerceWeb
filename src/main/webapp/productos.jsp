<%@page import="java.util.List"%>
<%@page import="com.mycompany.tiendaecommerceweb.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Producto> listaProductos = (List<Producto>) request.getAttribute("listaProductos");
    Producto productoEditar = (Producto) request.getAttribute("productoEditar");
    String msg = request.getParameter("msg");
    String buscarActual = request.getParameter("buscar") != null ? request.getParameter("buscar") : "";
    boolean editando = (productoEditar != null);
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Productos - Tienda Ecommerce</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, sans-serif; background-color: #f4f6f9; margin: 0; padding: 0; color: #333; }
        header { background-color: #1e293b; color: #fff; padding: 18px 32px; display: flex; justify-content: space-between; align-items: center; }
        header h1 { margin: 0; font-size: 20px; }
        header a { color: #93c5fd; text-decoration: none; font-size: 14px; }
        .container { max-width: 1100px; margin: 28px auto; padding: 0 20px; }
        .alert { padding: 12px 16px; border-radius: 6px; margin-bottom: 20px; font-weight: 500; }
        .alert-ok { background-color: #dcfce7; color: #166534; border: 1px solid #86efac; }
        .alert-err { background-color: #fee2e2; color: #991b1b; border: 1px solid #fca5a5; }
        .grid { display: grid; grid-template-columns: 340px 1fr; gap: 24px; align-items: start; }
        .card { background: #fff; border-radius: 8px; padding: 22px; box-shadow: 0 2px 8px rgba(0,0,0,0.06); }
        .card h2 { margin-top: 0; font-size: 17px; color: #1e293b; border-bottom: 1px solid #e2e8f0; padding-bottom: 10px; }
        label { display: block; margin-top: 12px; font-size: 13px; font-weight: 600; color: #475569; }
        input[type="text"], input[type="number"], textarea {
            width: 100%; box-sizing: border-box; padding: 9px 10px; margin-top: 5px;
            border: 1px solid #cbd5e1; border-radius: 5px; font-size: 14px;
        }
        small { color: #64748b; font-size: 11px; }
        .btn { display: inline-block; padding: 9px 15px; border: none; border-radius: 5px; cursor: pointer; font-size: 13px; font-weight: 600; text-decoration: none; }
        .btn-primary { background-color: #2563eb; color: #fff; margin-top: 16px; width: 100%; }
        .btn-primary:hover { background-color: #1d4ed8; }
        .btn-secondary { background-color: #64748b; color: #fff; margin-top: 8px; width: 100%; text-align: center; box-sizing: border-box; }
        .btn-edit { background-color: #f59e0b; color: #fff; padding: 5px 10px; font-size: 12px; }
        .btn-del { background-color: #dc2626; color: #fff; padding: 5px 10px; font-size: 12px; }
        .search-bar { display: flex; gap: 8px; margin-bottom: 16px; }
        .search-bar input { margin-top: 0; }
        .search-bar button { background-color: #0f172a; color: #fff; white-space: nowrap; }
        table { width: 100%; border-collapse: collapse; font-size: 14px; }
        th, td { padding: 10px 12px; text-align: left; border-bottom: 1px solid #e2e8f0; }
        th { background-color: #f8fafc; color: #475569; font-weight: 600; }
        tr:hover { background-color: #f8fafc; }
    </style>
</head>
<body>

<header>
    <h1>Tienda Ecommerce Personal — Módulo Web (Servlets + JSP)</h1>
    <a href="index.html">Inicio</a>
</header>

<div class="container">

    <% if ("creado".equals(msg)) { %>
        <div class="alert alert-ok">Producto registrado correctamente en MySQL.</div>
    <% } else if ("actualizado".equals(msg)) { %>
        <div class="alert alert-ok">Producto actualizado correctamente.</div>
    <% } else if ("eliminado".equals(msg)) { %>
        <div class="alert alert-ok">Producto eliminado de la base de datos.</div>
    <% } else if ("error".equals(msg)) { %>
        <div class="alert alert-err">Ocurrió un error al procesar la operación en la base de datos.</div>
    <% } %>

    <div class="grid">
        <!-- FORMULARIO HTML CON MÉTODO POST -->
        <div class="card">
            <h2><%= editando ? "Editar Producto #" + productoEditar.getIdProducto() : "Registrar Nuevo Producto" %></h2>
            <form action="ProductoServlet" method="POST">
                <input type="hidden" name="idProducto" value="<%= editando ? productoEditar.getIdProducto() : 0 %>">

                <label>Nombre del producto:</label>
                <input type="text" name="nombre" required value="<%= editando ? productoEditar.getNombre() : "" %>">

                <label>Descripción:</label>
                <textarea name="descripcion" rows="2" required><%= editando ? productoEditar.getDescripcion() : "" %></textarea>

                <label>Precio ($):</label>
                <input type="number" step="0.01" name="precio" required value="<%= editando ? productoEditar.getPrecio() : "" %>">

                <label>Stock disponible:</label>
                <input type="number" name="stock" required value="<%= editando ? productoEditar.getStock() : "" %>">

                <label>ID Categoría:</label>
                <input type="number" name="idCategoria" required value="<%= editando ? productoEditar.getIdCategoria() : 1 %>">
                <small>Debe corresponder a un id_categoria existente en phpMyAdmin (ej. 1).</small>

                <button type="submit" class="btn btn-primary">
                    <%= editando ? "Guardar Cambios" : "Registrar Producto" %>
                </button>
                <% if (editando) { %>
                    <a href="ProductoServlet" class="btn btn-secondary">Cancelar Edición</a>
                <% } %>
            </form>
        </div>

        <!-- TABLA DINÁMICA Y BÚSQUEDA CON MÉTODO GET -->
        <div class="card">
            <h2>Inventario de Productos en MySQL</h2>

            <form action="ProductoServlet" method="GET" class="search-bar">
                <input type="text" name="buscar" placeholder="Buscar producto por nombre..." value="<%= buscarActual %>">
                <button type="submit" class="btn">Buscar (GET)</button>
                <% if (!buscarActual.isEmpty()) { %>
                    <a href="ProductoServlet" class="btn btn-secondary" style="width:auto; margin-top:0;">Limpiar</a>
                <% } %>
            </form>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Descripción</th>
                        <th>Precio</th>
                        <th>Stock</th>
                        <th>Cat.</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (listaProductos != null && !listaProductos.isEmpty()) {
                            for (Producto p : listaProductos) {
                    %>
                    <tr>
                        <td><%= p.getIdProducto() %></td>
                        <td><strong><%= p.getNombre() %></strong></td>
                        <td><%= p.getDescripcion() %></td>
                        <td>$<%= String.format("%.2f", p.getPrecio()) %></td>
                        <td><%= p.getStock() %></td>
                        <td><%= p.getIdCategoria() %></td>
                        <td>
                            <a href="ProductoServlet?accion=editar&id=<%= p.getIdProducto() %>" class="btn btn-edit">Editar</a>
                            <a href="ProductoServlet?accion=eliminar&id=<%= p.getIdProducto() %>" class="btn btn-del" onclick="return confirm('¿Eliminar este producto?');">Eliminar</a>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="7" style="text-align:center; color:#64748b;">No se encontraron productos registrados.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>
