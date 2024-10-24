<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tienda Online</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            margin: 0;
            background-color: #f4f7fa;
            color: #333;
        }

        /* Estilos del encabezado */
        header {
            background-color: #007BFF;
            color: white;
            padding: 20px 10%;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo {
            font-size: 24px;
            font-weight: bold;
        }

        nav {
            display: flex;
            gap: 20px;
        }

        nav a {
            color: white;
            text-decoration: none;
            font-weight: 700;
        }

        nav a:hover {
            text-decoration: underline;
        }

        /* Estilos de la sección principal */
        .main-container {
            padding: 40px 10%;
        }

        .hero {
            background-color: #e3f2fd;
            padding: 30px;
            border-radius: 8px;
            text-align: center;
            margin-bottom: 40px;
        }

        .hero h1 {
            font-size: 32px;
            margin-bottom: 10px;
        }

        .hero p {
            font-size: 18px;
        }

        /* Estilos de productos */
        .products {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
        }

        .product-card {
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 15px;
            text-align: center;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s;
        }

        .product-card:hover {
            transform: translateY(-5px);
        }

        .product-card img {
            max-width: 100%;
            border-radius: 4px;
            height: auto;
        }

        .product-card h3 {
            margin: 10px 0;
            font-size: 18px;
        }

        .product-card p {
            margin: 5px 0;
            color: #555;
        }

        .product-card .price {
            font-size: 20px;
            color: #007BFF;
            font-weight: bold;
        }

        .add-to-cart {
            background-color: #007BFF;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: 700;
            transition: background-color 0.3s;
            text-decoration: none;
            display: inline-block;
            margin-top: 10px;
        }

        .add-to-cart:hover {
            background-color: #0056b3;
        }

        /* Estilos del pie de página */
        footer {
            background-color: #333;
            color: white;
            text-align: center;
            padding: 20px;
            margin-top: 40px;
        }

        footer p {
            margin: 0;
        }
    </style>
</head>
<body>

    <header>
        <div class="logo">Mi Tienda</div>
        <nav>
            <a href="#">Inicio</a>
            <a href="#">Productos</a>
            <a href="#">Contacto</a>
            <a href="#">Mi Cuenta</a>
        </nav>
    </header>

    <div class="main-container">
        <div class="hero">
            <h1>Bienvenido a Nuestra Tienda</h1>
            <p>Encuentra los mejores productos al mejor precio.</p>
        </div>

        <h2>Productos Destacados</h2>
        <div class="products">
            <div class="product-card">
                <img src="https://via.placeholder.com/250" alt="Producto 1">
                <h3>Producto 1</h3>
                <p>Descripción breve del producto 1.</p>
                <p class="price">$29.99</p>
                <a href="#" class="add-to-cart">Agregar al Carrito</a>
            </div>
            <div class="product-card">
                <img src="https://via.placeholder.com/250" alt="Producto 2">
                <h3>Producto 2</h3>
                <p>Descripción breve del producto 2.</p>
                <p class="price">$39.99</p>
                <a href="#" class="add-to-cart">Agregar al Carrito</a>
            </div>
            <div class="product-card">
                <img src="https://via.placeholder.com/250" alt="Producto 3">
                <h3>Producto 3</h3>
                <p>Descripción breve del producto 3.</p>
                <p class="price">$19.99</p>
                <a href="#" class="add-to-cart">Agregar al Carrito</a>
            </div>
            <div class="product-card">
                <img src="https://via.placeholder.com/250" alt="Producto 4">
                <h3>Producto 4</h3>
                <p>Descripción breve del producto 4.</p>
                <p class="price">$49.99</p>
                <a href="#" class="add-to-cart">Agregar al Carrito</a>
            </div>
        </div>
    </div>

    <footer>
        <p>&copy; 2024 Mi Tienda. Todos los derechos reservados.</p>
    </footer>

</body>
</html>
