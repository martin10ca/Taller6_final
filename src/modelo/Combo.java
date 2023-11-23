package modelo;
import java.util.ArrayList;
import java.util.List;


public class Combo implements Producto{
	//descuento va a ser en decimales. Ej: 10% ->0.10
	private double descuento;
	private String nombreCombo;
	private List<Producto> ItemsCombo;
	private int calorias;
	public Combo(String nombre, double descuento)
	{
		this.descuento=descuento;
		this.nombreCombo=nombre;
		this.ItemsCombo = new ArrayList<Producto>();
		this.calorias=0;

	}
	public void agregarItemACombo(Producto itemCombo)
	{
		this.ItemsCombo.add(itemCombo);
		this.calorias+=itemCombo.getCalorias();

	}
	
	@Override
	public double getPrecio() {
		double precio=0;
		for (Producto unItem : ItemsCombo)
		{
			precio= precio+ unItem.getPrecio();
		}
		double total=precio*(1.00-this.descuento);
		return total ;
	}

	@Override
	public String getNombre() {
		return this.nombreCombo;
	}

	@Override
	public String generarTextoFactura() {		
		String retorno=this.getNombre()+" ->"+Double.toString(this.getPrecio())+" ->"+Integer.toString(this.getCalorias())+" cal";
		return retorno;
	}
	@Override
	public int getCalorias() {
		return this.calorias;
	}

}
