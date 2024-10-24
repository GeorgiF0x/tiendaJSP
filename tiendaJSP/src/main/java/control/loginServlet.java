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
import modelo.User;
import modelo.UserDAO;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        String nombre = request.getParameter("user");
	        String password = request.getParameter("pass");
	        
	        // Validación básica de longitud
	        if (nombre.length() < 4 || password.length() < 4) {
	            // Si no cumple la validación de longitud, setea el mensaje de error
	            request.setAttribute("resultadoValidacion", "Rellena con al menos 6 caracteres.");
	        } else {
	            // Si la longitud es válida, validar el usuario contra la base de datos
	        	System.out.println("validacion de longitud de cadena correcta");
	            User alumno = UserDAO.validateUser(nombre, password); // Cambiar a objeto Alumn
	            
	            if (alumno != null) { // Comprueba si el alumno no es nulo
	            	System.out.println("validacion login correcta");
	                // Si el usuario es válido, almacenar el ID y el nombre en la sesión
	                request.getSession().setAttribute("usuarioId", alumno.getId());
	                request.getSession().setAttribute("usuarioNombre", alumno.getNombre());
	                
	                //comprobar carrito
	                Carrito carroUser=CarritoDAO.getByUserId(alumno.getId());
	                if(carroUser!=null) {
	                	request.getSession().setAttribute("carritoUser", carroUser);
	                }

	                // Redirige a la página de respuesta con el mensaje de éxito
	           
	                request.setAttribute("resultado", nombre + " INICIO DE SESION CORRECTO");
	            } else {
	                // Si el usuario no es válido, setea el mensaje de error
	                request.setAttribute("resultadoValidacion", "Usuario o contraseña incorrectos.");
	            }
	        }
	        
	        
	   
	        response.sendRedirect(request.getContextPath() + "/");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        //request.getRequestDispatcher("error.jsp").forward(request, response);
	    }
	}

}
