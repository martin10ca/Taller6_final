package modelo;

public class ProductoMenu implements Producto{
	private String nombre;
	private double precioBase;
	private int calorias;
	public ProductoMenu(String nombre, int precioBase,int calorias)
	{
		this.nombre=nombre;
		this.precioBase=precioBase;
		this.calorias=calorias;
	}
	public String getNombre()
	{
		return this.nombre;
	}
	public double getPrecio()
	{
		return this.precioBase;
	}
	public String generarTextoFactura()
	{
		String retorno=this.getNombre()+" ->"+Double.toString(this.getPrecio())+" ->"+Integer.toString(this.getCalorias())+" cal";
		return retorno;
	}
	@Override
	public int getCalorias() {
		return this.calorias;
	}
}