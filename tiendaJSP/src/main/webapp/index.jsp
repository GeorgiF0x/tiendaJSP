<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="modelo.ProductoVO, java.util.ArrayList" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Catálogo de Productos</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> <!-- Enlace a Bootstrap -->
    <link rel="stylesheet" href="../css/estilos.css"> <!-- Incluye tu archivo CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<%@ include file="./fragments/header.jsp" %>

    <!-- Main Section -->
    <section class="main container mt-5">
        <h1 class="text-center">Catálogo de Productos</h1>
        <!-- Cards -->
        <div class="row">
            <%
            if (request.getAttribute("catalogo") != null) {
                ArrayList<ProductoVO> lista = (ArrayList<ProductoVO>) request.getAttribute("catalogo");
                for (ProductoVO producto : lista) {
            %>
          <div class="col-md-4 mb-4">
    <div class="card">
        <img src="https://via.placeholder.com/300x200" class="card-img-top" alt="<%= producto.getNombre() %>">
        <div class="card-body">
            <h5 class="card-title"><%= producto.getNombre() %></h5>
            <p class="card-text">Precio: <strong>$<%= producto.getPrecio() %></strong></p>
            <p class="card-text">Cantidad en stock: <strong><%= producto.getCantidad() %></strong></p>
            
            <form action="carritoServlet" method="post" class="d-flex align-items-center">
                <input type="hidden" name="producto_id" value="<%= producto.getId() %>">
                
                <!-- Input para la cantidad -->
                <div class="input-group me-2">
                    <input type="number" name="cantidad" min="1" max="<%= producto.getCantidad() %>" 
                           class="form-control" placeholder="Cantidad" required>
                </div>
                
                <button type="submit" class="btn btn-primary" name="addCarrito">Añadir al Carrito</button>
            </form>
        </div>
    </div>
</div>


            <%
                }
            } else {
            %>
                <div class="col-12">
                    <p class="text-center">No hay productos disponibles en el catálogo.</p>
                </div>
            <%
            }
            %>
        </div>
    </section>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>

