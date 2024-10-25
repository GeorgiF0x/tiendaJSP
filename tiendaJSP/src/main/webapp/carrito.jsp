<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrito de Compras</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="styles.css"> <!-- Tu CSS -->
</head>
<body>
    <div class="container cart-container mt-5">
        <h2 class="text-center">Carrito de Compras</h2>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Producto</th>
                    <th>Precio</th>
                    <th>Cantidad</th>
                    <th>Total</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
            
                        <tr>
                            <td><%= item.getProductoNombre() %></td>
                            <td><%= item.getPrecio() %> €</td>
                            <td><%= item.getCantidad() %></td>
                            <td><%= item.getPrecio() * item.getCantidad() %> €</td>
                            <td>
                                <form action="eliminarDelCarrito" method="POST">
                                    <input type="hidden" name="productoId" value="<%= item.getProductoId() %>">
                                    <button type="submit" class="btn btn-danger">Eliminar</button>
                                </form>
                            </td>
                        </tr>
                <% 
                        }
                    } else { 
                %>
                    <tr>
                        <td colspan="5" class="text-center">Tu carrito está vacío.</td>
                    </tr>
                <% 
                    } 
                %>
            </tbody>
        </table>
        <div class="cart-summary">
            <p class="font-weight-bold">Total: <%= totalPrecio %> €</p>
            <form action="procesarPago" method="POST">
                <button type="submit" class="btn btn-primary">Proceder al Pago</button>
            </form>
        </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script> <!-- jQuery -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script> <!-- Popper.js -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script> <!-- Bootstrap JS -->
</body>
</html>

       
