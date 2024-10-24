package modelo;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import conexion.Conexion;


public class User {

	private  int id;
	private String nombre;
	private String apellidos;
	private String numTelefono;
	private String password;
	
	public User(int id, String nombre, String apellidos, String numTelefono ,String password) {
		super();
		this.id=id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.numTelefono = numTelefono;
		this.password= password;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNumTelefono() {
		return numTelefono;
	}

	public void setNumTelefono(String numTelefono) {
		this.numTelefono = numTelefono;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	


	@Override
	public String toString() {
		return "Alumn [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", numTelefono=" + numTelefono
				+ "]";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	
}
