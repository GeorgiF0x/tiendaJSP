package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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

    public loginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String nombre = request.getParameter("user");
            String password = request.getParameter("pass");
            String checkRecordar = request.getParameter("recordar");

            Cookie cookieRecordar = new Cookie("recordar", nombre);
            if (checkRecordar != null) {
                cookieRecordar.setMaxAge(60 * 60 * 24 * 365); // recordar un año
                response.addCookie(cookieRecordar);
            } else {
                cookieRecordar.setMaxAge(0); // eliminar cookie
                response.addCookie(cookieRecordar);
            }

            // Validación básica de longitud
            if (nombre.length() < 4 || password.length() < 4) {
                request.setAttribute("resultadoValidacion", "Rellena con al menos 4 caracteres.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                User alumno = UserDAO.validateUser(nombre, password);
                if (alumno != null) {
                    request.getSession().setAttribute("usuarioId", alumno.getId());
                    request.getSession().setAttribute("usuarioNombre", alumno.getNombre());

                    List<Carrito> carroUser = CarritoDAO.getByUserId(alumno.getId());
                    if (carroUser != null && !carroUser.isEmpty()) {
                        request.getSession().setAttribute("carritoUser", carroUser);
                    }

             
                    response.sendRedirect(request.getContextPath() + "/");
                } else {
                    request.setAttribute("resultadoValidacion", "Usuario o contraseña incorrectos.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de errores
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

}

