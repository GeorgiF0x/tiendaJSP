package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.cj.xdevapi.Statement;
import conexion.Conexion;

public class UserDAO {

	public static  ArrayList<User>  getAlumnsByName(String filtro) throws SQLException {
		ArrayList <User>lista_alumnos= new ArrayList<User>();
		
		//instanciar clase Connection usando el metodo de la clase conexion 
		 Connection conn = Conexion.getConexion();
	        //comprobar si se ha instanicado con el metodo correctamente
	        if (conn != null) {
	            System.out.println("Conexión establecida con éxito.");
	            String sql="Select * from alumno where nombre like ?";
	            //crear un objeto preparedStatement al que se le pasa una cadena con el sql preparado
	            PreparedStatement pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, filtro+"%");
	            //crear objeto resultSet para manipular los datos ejecutando el statment
	            ResultSet rs = pstmt.executeQuery();
	            if (!rs.isBeforeFirst()) {    
	                // Si no hay registros, mostrar mensaje
	                System.out.println("No hay registros disponibles.");
	            } else {
	                while(rs.next()) {
	                    int id = rs.getInt(1);
	                    String nombre = rs.getString(2);
	                    String apellidos = rs.getString(3);
	                    String telefono = rs.getString(4);
	                    String password = rs.getString(4);
		                
		                User alumno = new User(id, nombre, apellidos, telefono,password);
	                    lista_alumnos.add(alumno);
	                }
	            }
	            // Finalmente, desconectamos
	            Conexion.desconectar();
	        } else {
	            System.out.println("No se pudo establecer la conexión.");
	        }
	        
			return lista_alumnos;
	}
	
	public static  User getById(int id) throws SQLException {

		User alumno= new User();
		//instanciar clase Connection usando el metodo de la clase conexion 
		 Connection conn = Conexion.getConexion();
	        //comprobar si se ha instanicado con el metodo correctamente
	        if (conn != null) {
	            System.out.println("Conexión establecida con éxito.");
	            String sql="Select * from alumno where id = ?";
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
	                    int idAlumno = rs.getInt(1);
	                    String nombre = rs.getString(2);
	                    String apellidos = rs.getString(3);
	                    String telefono = rs.getString(4);
	                    String password = rs.getString(4);
		                
		                alumno = new User(idAlumno, nombre, apellidos, telefono,password);
	                   
	                }
	            }
	     
	        } else {
	            System.out.println("No se pudo establecer la conexión.");
	        }
	        
			return alumno;
	}
	
	public static ArrayList<User> getAll() throws SQLException {
	    ArrayList<User> lista_alumnos = new ArrayList<User>();
	    
	    // Instanciar clase Connection usando el método de la clase conexión 
	    Connection conn = Conexion.getConexion();
	    
	    // Comprobar si se ha instanciado correctamente
	    if (conn != null) {
	        System.out.println("Conexión establecida con éxito.");
	        
	        String sql = "SELECT * FROM alumno";
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
                    String apellidos = rs.getString(3);
                    String telefono = rs.getString(4);
                    String password = rs.getString(4);
	                
	                User alumno = new User(id, nombre, apellidos, telefono,password);
	                lista_alumnos.add(alumno);
	            }
	        }
	        
	        // Cerrar recursos
	        rs.close();
	        pstmt.close();
	        conn.close(); // Asegúrate de cerrar la conexión también
	    } else {
	        System.out.println("No se pudo establecer la conexión.");
	    }
	    
	    return lista_alumnos;
	}
	
	public static boolean insert(User alumno) throws SQLException {
	    boolean insertado = false;
	    String sql = "INSERT INTO alumno (nombre, apellidos, telefono,password) VALUES (?, ?, ?,?)";

	    // Usar try-with-resources para cerrar automáticamente la conexión y el PreparedStatement
	    try (Connection conn = Conexion.getConexion();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        // Deshabilitar auto-commit
	        conn.setAutoCommit(false);

	        // Comprobar si la conexión fue establecida
	        if (conn != null) {
	            System.out.println("Conexión establecida con éxito.");

	            // Establecer los parámetros del PreparedStatement
	            pstmt.setString(1, alumno.getNombre());
	            pstmt.setString(2, alumno.getApellidos());
	            pstmt.setString(3, alumno.getNumTelefono());
	            pstmt.setString(4, alumno.getPassword());

	            // Ejecutar la sentencia SQL
	            int filasInsertadas = pstmt.executeUpdate();

	            if (filasInsertadas > 0) {
	                System.out.println("Alumno insertado con éxito: " + alumno.toString());
	                insertado = true;
	                // Hacer commit si la inserción fue exitosa
	                conn.commit();
	            } else {
	                System.out.println("No se pudo insertar el alumno.");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("No se pudo establecer la conexión.");
	        e.printStackTrace();  // Registrar el error 
	    }
	    
	    return insertado;
	}


	
	public static User validateUser(String nombre, String password) throws SQLException {
	    User alumno = null; // Inicializa como null

	    // Instanciar clase Connection usando el método de la clase conexión 
	    try (Connection conn = Conexion.getConexion()) { // Usar try-with-resources para cerrar automáticamente
	        if (conn != null) {
	            System.out.println("Conexión establecida con éxito.");
	            String sql = "SELECT * FROM alumno WHERE nombre = ? AND password = ?"; // Considera usar hash en lugar de texto plano
	            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	                pstmt.setString(1, nombre);
	                pstmt.setString(2, password);

	                // Ejecutar la consulta
	                try (ResultSet rs = pstmt.executeQuery()) {
	                    // Verificar si hay resultados
	                    if (rs.next()) { // Usa if en lugar de while para obtener solo un resultado
	                        int id = rs.getInt(1);
	                        String nombreAlumno = rs.getString(2);
	                        String apellidos = rs.getString(3);
	                        String telefono = rs.getString(4);
	                        String passwordAlumno = rs.getString(5); // Corrige el índice

	                        alumno = new User(id, nombreAlumno, apellidos, telefono, passwordAlumno);
	                    }
	                }
	            }
	            Conexion.desconectar();
	        } else {
	            System.out.println("No se pudo establecer la conexión.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Captura y muestra excepciones
	    }

	    return alumno; // Devolverá null si no se encontró un usuario
	}

	



	
	
	
}
