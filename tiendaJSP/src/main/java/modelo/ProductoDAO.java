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
	        
	        // Crear objeto ResultSet para manipular los datos ejecutando el preparedStatement
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
	        
	        // Cerrar recursos
	        Conexion.desconectar();
	        rs.close();
	        pstmt.close();
	    } else {
	        System.out.println("No se pudo establecer la conexión.");
	    }
	    
	    return listaProductos;
	}
	
	public static  ProductoVO getById(int id) throws SQLException {

		ProductoVO producto= new ProductoVO();
		//instanciar clase Connection usando el metodo de la clase conexion 
		 Connection conn = Conexion.getConexion();
	        //comprobar si se ha instanicado con el metodo correctamente
	        if (conn != null) {
	            System.out.println("Conexión establecida con éxito.");
	            String sql="Select * from articulos where id = ?";
	            //crear un objeto preparedStatement al que se le pasa una cadena con el sql preparado
	            PreparedStatement pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, id);
	            //crear objeto resultSet para manipular los datos ejecutando el statment
	            ResultSet rs = pstmt.executeQuery();
	            if (!rs.isBeforeFirst()) {    
	                // Si no hay registros, mostrar mensaje
	                System.out.println("No hay registros disponibles.");
	            } else {
	                while(rs.next()) {
	                    int idProducto = rs.getInt(1);
	                    String nombre = rs.getString(2);
	                    Double precio= rs.getDouble(3);
	                    int cantidad = rs.getInt(4);
		                producto = new ProductoVO(idProducto, nombre,precio,cantidad);
	                   
	                }
	            }
	     
	        } else {
	            System.out.println("No se pudo establecer la conexión.");
	        }
	        
			return producto;
	}

}
