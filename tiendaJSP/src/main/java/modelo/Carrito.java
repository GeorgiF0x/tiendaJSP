package modelo;

public class Carrito {
	
	private int id;
	private int idUsuario;
	private int idProducto;
	private int cantidad;
	
	public Carrito(int id, int idUsuario, int idProducto, int cantidad) {
		super();
		this.id = id;
		this.idUsuario = idUsuario;
		this.idProducto = idProducto;
		this.cantidad = cantidad;
	}
	public Carrito( int idUsuario, int idProducto, int cantidad) {
		super();
		this.idUsuario = idUsuario;
		this.idProducto = idProducto;
		this.cantidad = cantidad;
	}
	public Carrito() {

	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public String getIdCarrito() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		return "Carrito [id=" + id + ", idUsuario=" + idUsuario + ", idProducto=" + idProducto + ", cantidad="
				+ cantidad + "]";
	}
	
	
}
