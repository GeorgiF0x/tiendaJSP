package control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modelo.User;
import modelo.UserDAO;

/**
 * Servlet implementation class singUpSeervlet
 */
@WebServlet("/register")
public class singUpSeervlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public singUpSeervlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		    try {
		        String nombre = request.getParameter("reg_user");
		        String apellidos = request.getParameter("reg_apellido");
		        String numTelefono = request.getParameter("reg_telefono");
		        String password = request.getParameter("reg_pass");
		        
		        // Validación básica de longitud
		        if (nombre.length() < 4 || password.length() < 4 || numTelefono.length() <9) {
		            // Si no cumple la validación de longitud, setea el mensaje de error
		            request.setAttribute("resultadoValidacion", "Rellena con al menos 6 caracteres y telefono con 9.");
		        } else {
		            // Si la longitud es válida, validar el usuario contra la base de datos
		        	System.out.println("validacion de longitud de cadena correcta");
		            User usuarioNuevo = new User(999, nombre, apellidos, numTelefono, password) ; // Cambiar a objeto Alumn
		            boolean insertado =  UserDAO.insert(usuarioNuevo);
		            if (insertado) { // Comprueba si el alumno no es nulo
		            	System.out.println("registro correcto");
		                // Si el usuario es válido, almacenar el ID y el nombre en la sesión
		                request.getSession().setAttribute("usuarioRegistrado", usuarioNuevo);
		            } else {
		                // Si el usuario no es válido, setea el mensaje de error
		            	System.out.println("error al insertar");
		                request.setAttribute("mensaje", "no se ha podido registrar el usuario.");
		            }
		        }
		     
		        request.getRequestDispatcher("index").forward(request, response);
		    } catch (SQLException e) {
		        e.printStackTrace();
		        //request.getRequestDispatcher("error.jsp").forward(request, response);
		    
	}

}
}

