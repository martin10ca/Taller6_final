package modelo;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import excepciones.PedidoExcedeValorException;

import java.io.FileWriter;
import java.io.IOException;

public class Pedido{
	private static int numeroPedidos;
	private int idPedido;
	private String nombreCliente;
	private String direccionCliente;
	private List<Producto> itemsPedido;
	private double descuento_cupon;
    private static int idCounter = 0;
	public Pedido(String nombreCliente, String direccionCliente)
	{
		idCounter+=1;
		Pedido.numeroPedidos=idCounter;
		this.idPedido=idCounter;
		this.nombreCliente=nombreCliente;
		this.direccionCliente=direccionCliente;
		this.itemsPedido = new ArrayList<Producto>();
		this.descuento_cupon=1;
	}
	public String generarTextoFactura()
	{
		String texto_p0="\n--------------------------------------------------------------\n";
		String texto_p1="\tFACTURA"+ "(ID Factura: "+this.getIdPedido()+")\n\n";
		String texto_p2="Cliente: "+this.nombreCliente+"\n"+"Dirección cliente:"+this.direccionCliente+"\n\n";
		String texto_p4="\n";
		int suma_cal=0;
		for (Producto unItem : itemsPedido)
		{
			String infoItem=unItem.generarTextoFactura();
			String[] partes = infoItem.split("->");
            String StringItemCalorias=partes[2].replace("cal","");
            int intItemCalorias=Integer.parseInt(StringItemCalorias.replace(" ", ""));
            suma_cal+=intItemCalorias;
			texto_p4=texto_p4+infoItem+"\n";
		}	
		String texto_p5="\n"+"\tCalorías totales: "+Integer.toString(suma_cal);
		String texto_p6="\n"+"\tPrecio neto: "+Double.toString(this.getPrecioNetoPedido());
		String texto_p7="\n"+"\tPrecio tras descuento ("+this.decimalAPorcentaje(this.getDescuento())+") :"+Double.toString(this.getPrecioDescontado());
		String texto_p8="\n"+"\tIVA(19%): "+Double.toString(this.getPrecioIVAPedido());
		String texto_p9="\n"+"\tPrecio total: "+Double.toString(this.getPrecioTotalPedido());
		String texto=texto_p0+texto_p1+texto_p2+texto_p4+texto_p5+texto_p6+texto_p7+texto_p8+texto_p9+texto_p0;
		return texto;
	}
	public static int getNumeroPedidos() {
		return numeroPedidos;
	}
	public int getIdPedido() 
	{
		return this.idPedido;
	}
	public void agregarProducto(Producto nuevoItem) throws PedidoExcedeValorException
	{
		double precio= this.getPrecioNetoPedido();
		try {
		if (precio+nuevoItem.getPrecio()<=150000) {
		this.itemsPedido.add(nuevoItem);
		}
		else {
            throw new PedidoExcedeValorException("El pedido excede el valor total permitido (150000 COP)",nuevoItem);

		}}catch (PedidoExcedeValorException ex) {
			ex.printStackTrace();
		}
		
	}
	//New
	public double getDescuento() {
		return this.descuento_cupon;
	}
	//new
	public void setDescuento(double value) {
		this.descuento_cupon-=value;
	}
	//new
	public List<Producto> getItemsPedido(){
		return this.itemsPedido;
	}
	public void guardarFactura()
	{
		String texto_factura=generarTextoFactura();
		String path_factura="Facturas/"+String.valueOf(this.getIdPedido())+".txt";
		try {
            File file = new File(path_factura);
            FileWriter writer = new FileWriter(file);
            writer.write(texto_factura);
            writer.close();
            System.out.println("La factura se ha guardado en: " + path_factura);
        } catch (IOException e) {
            System.err.println("Ocurrió un error: " + e.getMessage());
        }
	}
	public double getPrecioNetoPedido()
	{
		double precioNeto=0;
		for (Producto unItem : itemsPedido)
		{
			precioNeto+= unItem.getPrecio();
		}
		return precioNeto;

	}
	private double getPrecioDescontado() {
		double precioNeto= this.getPrecioNetoPedido();
		return precioNeto*this.getDescuento();
	}
	private double getPrecioTotalPedido()
	{
		double precioNeto= this.getPrecioDescontado();
		double impuestos= 1.19;
		double precioTotal= precioNeto*impuestos;
		return precioTotal;			
				
	}

	private double getPrecioIVAPedido()
	{
		double precioNeto= this.getPrecioNetoPedido();
		return 0.19*precioNeto;

	}
    private String decimalAPorcentaje(double numeroDecimal) {
        double porcentaje = (100-(numeroDecimal * 100));
        String resultado = String.format("%.1f%%", porcentaje);        
        return resultado;
    }
}
