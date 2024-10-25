<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login y Registro</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f0f2f5;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            padding: 20px;
        }

        .container {
            background-color: #fff;
            border-radius: 12px;
            box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
            padding: 30px;
            width: 100%;
            max-width: 400px;
            text-align: center;
        }

        .tabs {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
            border-bottom: 2px solid #ddd;
        }

        .tab {
            width: 50%;
            padding: 10px 0;
            cursor: pointer;
            font-weight: 700;
            color: #555;
            border-bottom: 3px solid transparent;
            transition: color 0.3s, border-color 0.3s;
        }

        .tab.active {
            color: #007BFF;
            border-bottom-color: #007BFF;
        }

        .form-container {
            display: none;
        }

        .form-container.active {
            display: block;
        }

        .form-container h2 {
            margin-bottom: 20px;
            color: #333;
        }

        .input-group {
            margin-bottom: 20px;
            text-align: left;
        }

        .input-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: 700;
            color: #555;
        }

        .input-group input {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            transition: border-color 0.3s;
        }

        .input-group input:focus {
            border-color: #007BFF;
            outline: none;
        }

        .login-button, .register-button {
            background-color: #007BFF;
            color: white;
            padding: 12px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            width: 100%;
            transition: background-color 0.3s;
        }

        .login-button:hover, .register-button:hover {
            background-color: #0056b3;
        }

        .forgot-password {
            margin-top: 15px;
            display: block;
            color: #007BFF;
            text-decoration: none;
            font-size: 14px;
        }

        .forgot-password:hover {
            text-decoration: underline;
        }

        p.error-message {
            color: red;
            font-size: 14px;
            margin-top: 10px;
        }
        
        .message{
        	color:green;
        	 font-size: 20px;
            margin-top: 10px;
        }
    </style>
</head>
<body>

	<%
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
	    for (Cookie cookie : cookies) {
	        if (cookie.getName().equals("recordar")) { //buscar la cookie que quiero manejar 
	            request.setAttribute("nombreGuardado", cookie.getValue());
	            break;
	        }
	    }
	}
	%>

 <div class="container">
    <div class="tabs">
        <div class="tab active" onclick="switchTab('login')">Iniciar Sesión</div>
        <div class="tab" onclick="switchTab('register')">Registrarse</div>
    </div>

    <!-- Login Form -->
    <div class="form-container active" id="login-form">
        <h2>Iniciar Sesión</h2>
        <form action="loginServlet" method="POST">
            <div class="input-group">
                <label for="username">Nombre de Usuario</label>
                	
			  <input type="text" id="username" name="user" required 
			    value="<%= request.getAttribute("nombreGuardado") != null ? request.getAttribute("nombreGuardado") : "" %>">
            </div>
            <div class="input-group">
                <label for="password">Contraseña</label>
                <input type="password" id="password" name="pass" re quired>
            </div>
            <div class="input-group">
                <label>
                    <input type="checkbox" name="recordar" id="rememberUser">
                    Recordar nombre de usuario
                </label>
            </div>
            <div class="form-actions">
                <button type="submit" class="login-button">Acceder</button>
                <a href="#" class="forgot-password">¿Olvidaste tu contraseña?</a>
            </div>
            <% 
                String mensaje;
                if (request.getAttribute("error") != null) {
                    mensaje = (String) request.getAttribute("error");
            %>
            <p class="error-message"><%= mensaje %></p>
            <% } %>
        </form>
    </div>
</div>


        <!-- Register Form -->
        <div class="form-container" id="register-form">
            <h2>Registrarse</h2>
            <form action="register" method="POST">
                <div class="input-group">
                    <label for="register-username">Nombre de Usuario</label>
                    <input type="text" id="register-username" name="reg_user" required>
                </div>
                   <div class="input-group">
                    <label for="register-username">Apellidos</label>
                    <input type="text" id="register-username" name="reg_apellidos" required>
                </div>
                <div class="input-group">
                    <label for="register-email">Numero de telefono:</label>
                    <input type="number" id="register-email" name="reg_telefono" required>
                </div>
                <div class="input-group">
                    <label for="register-password">Contraseña</label>
                    <input type="password" id="register-password" name="reg_pass" required>
                </div>
                <button type="submit" class="register-button">Crear Cuenta</button>
            </form>
        </div>
         <% String mensaje2;
             if(request.getAttribute("mensaje") != null)
             {
               mensaje2 = (String)request.getAttribute("mensaje");           
          %>
               <p class="message"><%=mensaje2 %></p>
           <%}%>
    </div>
    

    <script src="./js/javas.js"></script>

</body>
</html>
