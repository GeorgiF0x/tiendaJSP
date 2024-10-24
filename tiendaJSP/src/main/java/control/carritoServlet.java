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
			Carrito inCarrito=CarritoDAO.getByUserProductId(usuarioId,idproducto);
			if(inCarrito!=null) {
				//actualizar los valores del carrito
				inCarrito.setCantidad(cantidadAddCarrito);
				CarritoDAO.updateCarrito(inCarrito);
				request.getSession().setAttribute("carritoUser", inCarrito);
				request.setAttribute("info", "Producto añadido al carrito");
				System.out.println(inCarrito.toString());
			}else {
				Carrito nuevoCarrito= new Carrito(idproducto, idproducto, cantidadAddCarrito);
				Carrito insertado= CarritoDAO.insertCarrito(nuevoCarrito);
				
				if (insertado != null) {
			        System.out.println("Inserción exitosa: " + insertado);
			        //actualizar la sesion con el carrito
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
