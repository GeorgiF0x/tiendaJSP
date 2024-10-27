<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Carrito" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Carrito de Compras</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .table th, .table td {
            vertical-align: middle;
        }
        .form-inline {
            display: flex;
            align-items: center;
        }
        .form-inline input {
            margin-right: 5px;
        }
    </style>
</head>
<body>
<%@ include file="./fragments/header.jsp" %>

<div class="container my-5">
    <h2 class="text-center mb-4">Carrito de Compras</h2>

    <!-- Mostrar mensaje si está disponible en request -->
    <%
        String mensaje = (String) request.getAttribute("mensaje");
        if (mensaje != null) {
    %>
        <div class="alert alert-success text-center" role="alert">
            <%= mensaje %>
        </div>
    <%
        }
    %>
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <div class="alert alert-danger text-center" role="alert">
            <%= error %>
        </div>
    <%
        }
    %>

    <!-- Comprobar si la lista de carritos no está vacía -->
    <%
        List<Carrito> carritos = (List<Carrito>) request.getAttribute("carritos");
        if (carritos != null && !carritos.isEmpty()) {
    %>
       <form action="InCarritoServlet" method="post">
    <div class="table-responsive">
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th scope="col">Producto ID</th>
                <th scope="col">Cantidad</th>
            </tr>
            </thead>
            <tbody>
            <%
                for (Carrito carrito : carritos) {
            %>
                <tr>
                    <td><%= carrito.getIdProducto() %></td>
                    <td>
                        <input type="hidden" name="idProducto" value="<%= carrito.getIdProducto() %>">
                        <input type="number" name="cantidad" value="<%= carrito.getCantidad() %>" min="1" class="form-control" style="width: 80px;" required>
                    </td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
    <div class="text-center">
        <button type="submit" class="btn btn-success btn-lg">Tramitar Compra</button>
    </div>
</form>
    <%
        } else {
    %>
        <div class="alert alert-warning text-center" role="alert">
            No hay productos en el carrito.
        </div>
    <%
        }
    %>
</div>

<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>


       
