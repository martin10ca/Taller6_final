package modelo;
import java.util.ArrayList;
import java.util.List;

public class ProductoAjustado implements Producto{
	private ProductoMenu base;
	private List<Ingrediente> agregados;
	private List<Ingrediente> eliminados;
	private int calorias;
	//private double precioProvisional;
	public ProductoAjustado(ProductoMenu base){
		this.base= base;
		this.agregados = new ArrayList<Ingrediente>();		
		this.eliminados = new ArrayList<Ingrediente>();
		this.calorias=base.getCalorias();
		//his.precioProvisional= base.getPrecio();
	}
	public void agregarIngrediente(Ingrediente ingrediente)
	{
		this.agregados.add(ingrediente);
		this.calorias+=ingrediente.getCalorias();
		//this.precioProvisional=this.getPrecio();
	}
	public void eliminarIngrediente(Ingrediente ingrediente)
	{
		this.eliminados.add(ingrediente);
		if((this.calorias-ingrediente.getCalorias())>0)
		{
		this.calorias-=ingrediente.getCalorias();
		}
	}
	@Override
	public double getPrecio() {
		// TODO Auto-generated method stub
		ProductoMenu productoBase= this.base;
		double precioBase= productoBase.getPrecio();
		double precioAdicional=0;
		for (Ingrediente unIngrediente:agregados)
		{
			double precioIngrediente= unIngrediente.getCostoAdicional();
			precioAdicional+=precioIngrediente;
		}
		return precioBase+precioAdicional;
	}

	@Override
	public String getNombre()
	{
		ProductoMenu productoBase= this.base;
		String nombreProductoBase= productoBase.getNombre();
		String extras="(";
		for (Ingrediente unIngrediente:agregados)
		{
			String nombreIngrediente= unIngrediente.getNombre();
			extras=extras+"EXTRA "+nombreIngrediente+ " ";
		}
		String guion =" - ";
		String extracciones="";
		
		for (Ingrediente unIngrediente:eliminados)
		{
			String nombreIngrediente= unIngrediente.getNombre();
			extracciones=extracciones+"SIN "+nombreIngrediente+ " ";
		}
		String parentesis =")";
		return nombreProductoBase+extras+guion+extracciones+parentesis;
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
