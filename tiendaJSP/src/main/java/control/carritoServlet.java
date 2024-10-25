package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.Carrito;
import modelo.CarritoDAO;

/**
 * Servlet implementation class carritoServlet
 */
@WebServlet("/carritoServlet")
public class carritoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public carritoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idProductoCadena = request.getParameter("producto_id"); 
		Integer usuarioId = (Integer) request.getSession().getAttribute("usuarioId");
		int cantidadAddCarrito = Integer.parseInt(request.getParameter("cantidad"));
		int idproducto = Integer.parseInt(idProductoCadena);
		
		if (usuarioId == null) {
	        response.sendRedirect("login.jsp"); // Redirigir al login
	        return; // Salir del método
	    }
		//Combrobar si esta en el carrito
		try {
		    Carrito inCarrito = CarritoDAO.getByUserProductId(usuarioId, idproducto);
		    
		    if (inCarrito != null) {
		        // Sumar la cantidad nueva a la cantidad existente en el carrito
		        int nuevaCantidad = inCarrito.getCantidad() + cantidadAddCarrito;
		        inCarrito.setCantidad(nuevaCantidad); // Actualiza la cantidad del carrito

		        // Llama a updateCarrito y verifica si se actualiza
		        Carrito carritoActualizado = CarritoDAO.updateCarrito(inCarrito);
		        if (carritoActualizado != null) {
		            request.getSession().setAttribute("carritoUser", carritoActualizado);
		            request.setAttribute("info", "Producto añadido al carrito");
		            System.out.println(carritoActualizado.toString());
		        } else {
		            request.setAttribute("error", "Error al actualizar el carrito.");
		        }
		    } else {
		        // Si el carrito no existe, crear uno nuevo
		        Carrito nuevoCarrito = new Carrito(usuarioId, idproducto, cantidadAddCarrito); // Asegúrate de que estás usando los parámetros correctos
		        Carrito insertado = CarritoDAO.insertCarrito(nuevoCarrito);

		        if (insertado != null) {
		            System.out.println("Inserción exitosa: " + insertado);
		            // Actualizar la sesión con el carrito
		            request.setAttribute("info", "Producto añadido al carrito");
		            request.getSession().setAttribute("carritoUser", insertado);
		            System.out.println(insertado.toString());
		        } else {
		            System.out.println("Error al insertar el carrito.");
		        }
		    }
		} catch (SQLException e) {
		    request.setAttribute("error", "Error en la base de datos.");
		    e.printStackTrace();
		}


		   response.sendRedirect(request.getContextPath() + "/");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
