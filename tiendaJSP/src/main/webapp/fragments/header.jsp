<!-- Navbar -->
    <nav class="navbar">
        <div class="logo">TiendaOnline</div>
        <div class="nav-links">
            <a href="#">Inicio</a>
            <a href="#">Productos</a>
            <a href="#">Sobre Nosotros</a>
            <a href="#">Contacto</a>
        </div>
        <div class="search-bar">
            <input type="text" placeholder="Buscar productos...">
            <button><img src="https://img.icons8.com/ios-filled/20/ffffff/search--v1.png" alt="Buscar"></button>
        </div>
	  <div class="icons">
	    <a href="carrito.jsp"><i class="fas fa-shopping-cart"></i></a>
	    <%
	    if (request.getSession().getAttribute("usuarioId") != null) {
	        // Si el usuario está logueado, mostrar solo el botón de cerrar sesión
	    %>
		<form action="CerrarSession" method="post" style="display:inline;">
	    	<button type="submit" class="btn btn-danger">Cerrar Sesión</button>
		</form>

	    <%
	    } else {
	        // Si el usuario no está logueado, mostrar enlaces de login y registro
	    %>
	        <a href="login.jsp">Login</a>
	        <a href="registro.jsp">Registro</a>
	    <%
	    }
	    %>
	</div>
    </nav>