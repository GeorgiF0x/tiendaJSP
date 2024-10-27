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
import modelo.ProductoDAO;

@WebServlet("/InCarritoServlet")
public class InCarritoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public InCarritoServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("Entrando en el servlet para la vista de los productos en el carrito");

        // Obtener el idUsuario de la sesión
        Integer usuarioId = (Integer) request.getSession().getAttribute("usuarioId");

        // Verificar si el usuario está autenticado
        if (usuarioId != null) {
            try {
                // Cargar los artículos del carrito para el usuario
                List<Carrito> carritos = CarritoDAO.getByUserId(usuarioId);
                request.setAttribute("carritos", carritos); // Pasar la lista al request
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "Error al cargar los datos del carrito: " + e.getMessage());
            }
        } else {
            request.setAttribute("error", "Por favor, inicie sesión para ver su carrito.");
        }

        // Redirigir a la vista del carrito
        request.getRequestDispatcher("carrito.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Carrito> carritos = new ArrayList<>();
        String[] idProductos = request.getParameterValues("idProducto");
        String[] cantidades = request.getParameterValues("cantidad");

        // Obtener el idUsuario de la sesión
        Integer usuarioId = (Integer) request.getSession().getAttribute("usuarioId");

        // Validar el idUsuario
        if (usuarioId == null) {
            request.setAttribute("error", "Por favor, inicie sesión para tramitar la compra.");
            request.getRequestDispatcher("carrito.jsp").forward(request, response);
            return;
        }

        // Validar los parámetros
        if (idProductos != null && cantidades != null) {
            // Verificar que ambos arrays tengan la misma longitud
            if (idProductos.length != cantidades.length) {
                request.setAttribute("error", "Los productos y cantidades no coinciden.");
                request.getRequestDispatcher("carrito.jsp").forward(request, response);
                return;
            }

            for (int i = 0; i < idProductos.length; i++) {
                try {
                    int idProducto = Integer.parseInt(idProductos[i]);
                    int cantidad = Integer.parseInt(cantidades[i]);

                    // Crear el objeto Carrito
                    Carrito carrito = new Carrito(usuarioId, idProducto, cantidad);
                    carritos.add(carrito);
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Formato de número no válido en los productos o cantidades.");
                    request.getRequestDispatcher("carrito.jsp").forward(request, response);
                    return; // Salir si hay un error
                }
            }

            boolean resultado = false; 

            try {
                // Tramitar la compra
                resultado = ProductoDAO.tramitarCompra(carritos, usuarioId);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "Se produjo un error en la base de datos: " + e.getMessage());
                request.getRequestDispatcher("carrito.jsp").forward(request, response);
                return; 
            }

          
            if (resultado) {
                request.setAttribute("mensaje", "Compra tramitada con éxito.");
            } else {
                request.setAttribute("error", "Error al tramitar la compra.");
            }
        } else {
            request.setAttribute("error", "Los datos del carrito son inválidos.");
        }

       
        request.getRequestDispatcher("carrito.jsp").forward(request, response);
    }
}



