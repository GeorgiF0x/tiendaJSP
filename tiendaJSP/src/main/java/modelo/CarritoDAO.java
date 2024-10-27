package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import conexion.Conexion;


public class CarritoDAO {
	
	public static List<Carrito> getByUserId(int id) throws SQLException {
	    List<Carrito> carritos = new ArrayList<>();
	    Connection conn = Conexion.getConexion();

	    if (conn != null) {
	        String sql = "SELECT * FROM carrito WHERE IdUsuario = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int idCarrito = rs.getInt("Id");
	            int idUsuario = rs.getInt("IdUsuario");
	            int idProducto = rs.getInt("idArticulo");
	            int cantidad = rs.getInt("Cantidad");

	            Carrito carrito = new Carrito(idCarrito, idUsuario, idProducto, cantidad);
	            carritos.add(carrito);
	        }

	        rs.close();
	        pstmt.close();
	        Conexion.desconectar();
	    }

	    return carritos;
	}


	
	public static Carrito getByUserProductId(int idUsuario, int idProducto) throws SQLException {
	    Carrito carrito = null; // Cambiar a null por defecto
	    Connection conn = Conexion.getConexion();

	    // Comprobar si se ha instanciado la conexión correctamente
	    if (conn != null) {
	        System.out.println("Conexión establecida con éxito.");
	        String sql = "SELECT * FROM carrito WHERE idUsuario = ? AND idArticulo = ?"; // Verifica los nombres de columna

	        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, idUsuario);
	            pstmt.setInt(2, idProducto);
	            
	            // Ejecutar la consulta
	            ResultSet rs = pstmt.executeQuery();
	            if (!rs.isBeforeFirst()) {    
	                // Si no hay registros, mostrar mensaje
	                System.out.println("No hay registros disponibles.");
	                return null; // Devuelve null si no hay registros
	            } else {
	                while (rs.next()) {
	                    // Suponiendo que los índices de las columnas son correctos
	                    int idCarrito = rs.getInt(1); // Asegúrate de que este índice es correcto
	                    int idUsuarioC = rs.getInt(2);
	                    int idProductoC = rs.getInt(3);
	                    int cantidad = rs.getInt(4);

	                    carrito = new Carrito(idCarrito, idUsuarioC, idProductoC, cantidad);
	                }
	            }
	        }
	    } else {
	        System.out.println("No se pudo establecer la conexión.");
	    }

	    // Desconectar al final, asegúrate de que esto esté en el bloque finally si es necesario
	    Conexion.desconectar();
	    return carrito; // Devolver el objeto Carrito o null si no se encontró
	}

	
	public static Carrito updateCarrito(Carrito carrito) throws SQLException {
	    Carrito carritoActualizado = null; 
	    Connection conn = Conexion.getConexion();

	    if (conn != null) {
	        System.out.println("Conexión establecida con éxito.");
	        String sql = "UPDATE carrito SET cantidad = ? WHERE idUsuario = ? AND idArticulo = ?";
	        
	        try {
	            // Iniciar la transacción
	            conn.setAutoCommit(false); 

	            // Actualizar carrito con la cantidad exacta
	            try (PreparedStatement pstmtCarrito = conn.prepareStatement(sql)) {
	                pstmtCarrito.setInt(1, carrito.getCantidad()); // Cantidad exacta
	                pstmtCarrito.setInt(2, carrito.getIdUsuario()); // ID de usuario
	                pstmtCarrito.setInt(3, carrito.getIdProducto()); // ID de producto

	                int filasActualizadasCarrito = pstmtCarrito.executeUpdate();
	                if (filasActualizadasCarrito > 0) {
	                    System.out.println("Carrito actualizado con éxito.");
	                    carritoActualizado = carrito; // Asignar el carrito actualizado
	                } else {
	                    System.out.println("No se encontró el carrito para actualizar.");
	                    conn.rollback(); // Revertir si no se actualiza el carrito
	                    return null;
	                }
	            }

	            // Confirmar la transacción si todo va bien
	            conn.commit();
	        } catch (SQLException e) {
	            System.out.println("Error durante la actualización: " + e.getMessage());
	            conn.rollback(); // Revertir en caso de error
	        } finally {
	            Conexion.desconectar(); 
	        }
	    } else {
	        System.out.println("No se pudo establecer la conexión.");
	    }

	    return carritoActualizado; 
	}


	
	public static Carrito insertCarrito(Carrito carrito) throws SQLException {
	    Carrito carritoInsertado = null; // Variable para almacenar el carrito insertado
	    Connection conn = Conexion.getConexion();

	    if (conn != null) {
	        System.out.println("Conexión establecida con éxito.");
	        String insertCarritoSql = "INSERT INTO carrito (idUsuario, idArticulo, cantidad) VALUES (?, ?, ?)";
	        //simular compra
	        String updateStockSql = "UPDATE articulos SET cantidad = cantidad - ? WHERE idArticulo = ? AND cantidad >= ?";

	        try {
	            // Iniciar la transacción
	            conn.setAutoCommit(false); // Desactivar el autocommit

	            // Insertar carrito
	            try (PreparedStatement pstmtCarrito = conn.prepareStatement(insertCarritoSql, Statement.RETURN_GENERATED_KEYS)) {
	                pstmtCarrito.setInt(1, carrito.getIdUsuario()); // ID de usuario
	                pstmtCarrito.setInt(2, carrito.getIdProducto()); // ID de producto
	                pstmtCarrito.setInt(3, carrito.getCantidad()); // Cantidad
	                
	                // Ejecutar la inserción
	                int filasInsertadas = pstmtCarrito.executeUpdate();
	                if (filasInsertadas > 0) {
	                    // Obtener la clave generada
	                    try (ResultSet generatedKeys = pstmtCarrito.getGeneratedKeys()) {
	                        if (generatedKeys.next()) {
	                            // Establecer el ID del carrito insertado
	                            carritoInsertado = new Carrito(generatedKeys.getInt(1), 
	                                                            carrito.getIdUsuario(), 
	                                                            carrito.getIdProducto(), 
	                                                            carrito.getCantidad());
	                            System.out.println("Carrito insertado con éxito. ID: " + carritoInsertado.getIdCarrito());
	                        }
	                    }
	                } else {
	                    System.out.println("No se pudo insertar el carrito.");
	                    conn.rollback(); // Revertir si no se inserta el carrito
	                    return null;
	                }
	            }

	            /* Actualizar stock del producto
	            try (PreparedStatement pstmtStock = conn.prepareStatement(updateStockSql)) {
	                pstmtStock.setInt(1, carrito.getCantidad()); // Cantidad a restar
	                pstmtStock.setInt(2, carrito.getIdProducto()); // ID de producto
	                pstmtStock.setInt(3, carrito.getCantidad()); // Asegurarse que haya suficiente stock

	                int filasActualizadasStock = pstmtStock.executeUpdate();
	                if (filasActualizadasStock <= 0) {
	                    System.out.println("No se pudo actualizar el stock del producto.");
	                    conn.rollback(); // Revertir si no se actualiza el stock
	                    return null;
	                }
	            }

	            // Si ambas operaciones se realizaron correctamente, confirmar la transacción
	            conn.commit();
	            System.out.println("Transacción completada con éxito.");*/
	        } catch (SQLException e) {
	            conn.rollback(); // Revertir en caso de excepción
	            System.out.println("Error durante la transacción, se ha revertido. " + e.getMessage());
	            throw e; // Volver a lanzar la excepción
	        } finally {
	            conn.setAutoCommit(true); // Restaurar el autocommit
	            Conexion.desconectar(); // Asegúrate de cerrar la conexión
	        }
	    } else {
	        System.out.println("No se pudo establecer la conexión.");
	    }

	    return carritoInsertado; 
	}






}
