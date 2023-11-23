package modelo;

public class Bebida implements Producto{
	private String nombre;
	private int precio;
	private int calorias;
	public Bebida(String nombre, int precio, int calorias){
		this.nombre=nombre;
		this.precio=precio;
		this.calorias=calorias;
	}
	@Override
	public String generarTextoFactura() {
		String retorno=this.getNombre()+" ->"+Double.toString(this.getPrecio())+" ->"+Integer.toString(this.getCalorias())+" cal";
		return retorno;
	}
	@Override
	public String getNombre()
	{
		return this.nombre;
	}
	@Override
	public double getPrecio()
	{
		return this.precio;
	}
	@Override
	public int getCalorias() {
		return this.calorias;
	}
}
