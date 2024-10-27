package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Carrito;
import modelo.CarritoDAO;

@WebServlet("/carritoServlet")
public class carritoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public carritoServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idProductoCadena = request.getParameter("producto_id"); 
        Integer usuarioId = (Integer) request.getSession().getAttribute("usuarioId");
        String nombreProducto = request.getParameter("producto_nombre");
        int cantidadAddCarrito = Integer.parseInt(request.getParameter("cantidad"));
        int idProducto = Integer.parseInt(idProductoCadena);
        
        if (usuarioId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Lista de productos en el carrito, guardada en sesión
            List<Carrito> carrito = (List<Carrito>) request.getSession().getAttribute("carritoUser");
            if (carrito == null) {
                carrito = new ArrayList<>();
            }

            // Buscamos si el producto ya está en el carrito
            Carrito inCarrito = CarritoDAO.getByUserProductId(usuarioId, idProducto);
            if (inCarrito != null) {
                int nuevaCantidad = inCarrito.getCantidad() + cantidadAddCarrito;
                inCarrito.setCantidad(nuevaCantidad);
                System.out.println(inCarrito.toString());
                Carrito carritoActualizado = CarritoDAO.updateCarrito(inCarrito);
                if (carritoActualizado != null) {
                    for (Carrito item : carrito) {
                        if (item.getIdProducto() == idProducto) {
                            item.setCantidad(nuevaCantidad);
                            break;
                        }
                    }
                    // Mensaje de actualización
                    request.setAttribute("mensaje", "Producto actualizado: " + nombreProducto + " con " + nuevaCantidad + " unidades.");
                } else {
                    request.setAttribute("error", "Error al actualizar el carrito.");
                }
            } else {
                Carrito nuevoCarrito = new Carrito(usuarioId, idProducto, cantidadAddCarrito);
                Carrito insertado = CarritoDAO.insertCarrito(nuevoCarrito);
                System.out.println(insertado.toString());
                if (insertado != null) {
                    carrito.add(insertado);
                    // Mensaje de inserción
                    request.setAttribute("mensaje", nombreProducto + " insertado con " + cantidadAddCarrito + " unidades.");

                }
            }

            // Guardar lista actualizada en sesión
            request.getSession().setAttribute("carritoUser", carrito);
        } catch (SQLException e) {
            request.setAttribute("error", "Error en la base de datos.");
            e.printStackTrace();
        }

        // Redirigir a la página de inicio (puedes ajustar la URL según necesites)
        request.getRequestDispatcher("/").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

