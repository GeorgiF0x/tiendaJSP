<!-- Navbar -->
    <nav class="navbar">
	        <div class="logo">TiendaOnline</div>
	<div class="nav-links">
	        <!-- pageContent para volver al inicio -->
	    <a href="${pageContext.request.contextPath}/">Inicio</a> 
	    <a href="#">Productos</a>
	    <a href="#">Sobre Nosotros</a>
	    <a href="#">Contacto</a>
	</div>


        <div class="search-bar">
            <input type="text" placeholder="Buscar productos...">
            <button><img src="https://img.icons8.com/ios-filled/20/ffffff/search--v1.png" alt="Buscar"></button>
        </div>
	  <<div class="icons">
    <a href="<%= request.getContextPath() %>/InCarritoServlet"><i class="fas fa-shopping-cart"></i></a>
    <% if (request.getSession().getAttribute("usuarioId") != null) { %>
        <!-- Botón de cerrar sesión cuando el usuario está logueado -->
        <form action="CerrarSession" method="post" style="display:inline;">
            <button type="submit" class="btn btn-danger">Cerrar Sesión</button>
        </form>
    <% } else { %>
        <!-- Enlaces de login y registro cuando el usuario no está logueado -->
        <a href="login.jsp">Login</a>
        <a href="registro.jsp">Registro</a>
    <% } %>
</div>

    </nav>