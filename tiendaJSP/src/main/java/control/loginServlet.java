package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import control.servicios.ServicioValidacionesForm;
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
	    	ArrayList<String> listaCampos = new ArrayList<>();

	        String nombre = request.getParameter("user");
	        String password = request.getParameter("pass");
	        
	        String checkRecordar= request.getParameter("recordar");
	        
	        Cookie cookieRecordar = new Cookie("recordar", nombre);
	        if (checkRecordar != null) {
	        	System.out.println("Checkbox 'recordar' está seleccionado.");
	        	 cookieRecordar.setMaxAge(60 * 60 * 24*365); //recuerde la contraseña todo el año
	        	 response.addCookie(cookieRecordar);
	        } else {
	            // El checkbox no está marcado
	        	cookieRecordar.setMaxAge(0); // si la desmarcan ya no recuerda
	            response.addCookie(cookieRecordar);
	            System.out.println("Checkbox 'recordar' no está seleccionado.");
	        }
	        
	        // Validación básica de longitud
	        if (nombre.length() < 4 || password.length() < 4) {
	       
	            request.setAttribute("resultadoValidacion", "Rellena con al menos 6 caracteres.");
	        } else {
	   
	        	System.out.println("validacion de longitud de cadena correcta");
	            User alumno = UserDAO.validateUser(nombre, password); 
	            
	            if (alumno != null) { 
	            	System.out.println("validacion login correcta");
	             
	                request.getSession().setAttribute("usuarioId", alumno.getId());
	                request.getSession().setAttribute("usuarioNombre", alumno.getNombre());
	                
	                
	                Carrito carroUser=CarritoDAO.getByUserId(alumno.getId());
	                if(carroUser!=null) {
	                	request.getSession().setAttribute("carritoUser", carroUser);
	                }

	           
	                request.setAttribute("resultado", nombre + " INICIO DE SESION CORRECTO");
	            } else {
	               
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
