package control.filtro;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class FiltroLogIn
 */
//@WebFilter("/*")  si lo dejo no me deja pasar del login aunque lo haga con los datos correctos 
public class FiltroLogIn extends HttpFilter implements Filter {

    public FiltroLogIn() {
        super();
    }

    public void destroy() {
        // No se requiere destrucción en este caso
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        System.out.println("HA ENTRADO EN EL FILTRO");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String loginPage = "login.jsp"; // Página de inicio de sesión

        // Obtener la URL solicitada
        String requestURI = httpRequest.getRequestURI();
        System.out.println("URL solicitada: " + requestURI); // Mensaje de depuración

        // Comprobar si el usuario está autenticado
        if (httpRequest.getSession().getAttribute("usuarioId") == null) {
            // El usuario no está autenticado, permitir el acceso solo a la página de inicio de sesión
            if (requestURI.endsWith(loginPage)) {
                chain.doFilter(request, response); // Permitir acceso al login
                System.out.println("Acceso permitido a la página de login."); // Mensaje de depuración
            } else {
                // Redirigir a la página de inicio de sesión
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/" + loginPage);
                System.out.println("Redirigiendo a la página de login."); // Mensaje de depuración
            }
        } else {
            // El usuario ya está autenticado, permitir el acceso a otras páginas
            chain.doFilter(request, response);
            System.out.println("Acceso permitido a la página: " + requestURI); // Mensaje de depuración
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {
        // No se requiere inicialización en este caso
    }
}

