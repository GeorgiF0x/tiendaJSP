package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.Conexion;

public class ProductoDAO {
	
	public static List<ProductoVO> getAll() throws SQLException {
	    ArrayList<ProductoVO> listaProductos = new ArrayList<ProductoVO>();
	    
	    // Instanciar clase Connection usando el método de la clase conexión 
	    Connection conn = Conexion.getConexion();
	    
	    // Comprobar si se ha instanciado correctamente
	    if (conn != null) {
	        System.out.println("Conexión establecida con éxito.");
	        
	        String sql = "SELECT * FROM articulos";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        
	        ResultSet rs = pstmt.executeQuery(); // Cambiado a executeQuery
	        
	        // Verificar si hay registros
	        if (!rs.isBeforeFirst()) {    
	            // Si no hay registros, mostrar mensaje
	            System.out.println("No hay registros disponibles.");
	        } else {
	            while (rs.next()) {
	                int id = rs.getInt(1);
                    String nombre = rs.getString(2);
                    Double precio= rs.getDouble(3);
                    int cantidad = rs.getInt(4);
	                
	                ProductoVO producto = new ProductoVO(id, nombre,precio,cantidad);
	                listaProductos.add(producto);
	            }
	        }
	        

	        Conexion.desconectar();
	        rs.close();
	        pstmt.close();
	    } else {
	        System.out.println("No se pudo establecer la conexión.");
	    }
	    
	    return listaProductos;
	}
	
	public static ProductoVO getById(int id) throws SQLException {
	    ProductoVO producto = null;
	    Connection conn = Conexion.getConexion();

	    if (conn != null) {
	        String sql = "SELECT * FROM articulos WHERE idArticulo = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, id);
	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            int idProducto = rs.getInt("idArticulo");
	            String nombre = rs.getString("nombre");
	            double precio = rs.getDouble("precio");
	            int cantidad = rs.getInt("cantidad");
	            producto = new ProductoVO(idProducto, nombre, precio, cantidad);
	        }

	        rs.close();
	        pstmt.close();
	        Conexion.desconectar();
	    }

	    return producto;
	}

	
	public static boolean tramitarCompra(List<Carrito> carritos, int idUsuario) throws SQLException {
	    Connection conn = null; 
	    PreparedStatement pstmt = null; 
	    ResultSet rs = null;

	    try {
	   
	        conn = Conexion.getConexion();
	        conn.setAutoCommit(false); 

	        // Primero, verificamos y actualizamos el stock
	        for (Carrito carrito : carritos) {
	            // Obtener el producto
	            String queryProducto = "SELECT cantidad, nombre FROM articulos WHERE idArticulo = ?";
	            try (PreparedStatement stmt = conn.prepareStatement(queryProducto)) {
	                stmt.setInt(1, carrito.getIdProducto()); 
	                rs = stmt.executeQuery();

	                if (!rs.next()) {
	                    throw new SQLException("Producto no encontrado con ID: " + carrito.getIdProducto());
	                }

	                int cantidadActual = rs.getInt("cantidad");
	                String nombreProducto = rs.getString("nombre");

	                if (cantidadActual < carrito.getCantidad()) {
	                    throw new SQLException("No hay suficiente stock para el producto: " + nombreProducto);
	                }

	                // Actualizar el stock
	                int nuevoStock = cantidadActual - carrito.getCantidad();
	                String updateStockQuery = "UPDATE articulos SET cantidad = ? WHERE idArticulo = ?";
	                pstmt = conn.prepareStatement(updateStockQuery);
	                pstmt.setInt(1, nuevoStock);
	                pstmt.setInt(2, carrito.getIdProducto());
	                pstmt.executeUpdate();
	            }
	        }

	        // Vaciar el carrito después de procesar las compras
	        String vaciarCarritoQuery = "DELETE FROM carrito WHERE IdUsuario = ?";
	        pstmt = conn.prepareStatement(vaciarCarritoQuery);
	        pstmt.setInt(1, idUsuario);
	        pstmt.executeUpdate();

	    
	        conn.commit(); 
	        return true; 

	    } catch (SQLException e) {
	        e.printStackTrace();

	       
	        if (conn != null) {
	            try {
	                conn.rollback(); 
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	        return false; 
	    } finally {
	    
	        if (pstmt != null) {
	            try {
	                pstmt.close(); 
	            } catch (SQLException e) {
	                e.printStackTrace(); 
	            }
	        }
	        if (rs != null) {
	            try {
	                rs.close(); 
	            } catch (SQLException e) {
	                e.printStackTrace(); 
	            }
	        }
	        if (conn != null) {
	            Conexion.desconectar(); 
	        }
	    }
	}



}
