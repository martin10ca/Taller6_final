package consola;

public class Cupon {
	private String fechaVencimiento;
	private String nomCupon;
	private double descuento;
	private boolean utilizado;
	public Cupon(String fecha,String nomCupon,double descuento, boolean utilizado) {
		this.fechaVencimiento=fecha;
		this.nomCupon=nomCupon;
		this.descuento=descuento;
		this.utilizado=utilizado;
	}
	public String getNomCupon() {
		return this.nomCupon;
	}
	public String getFecha() {
		return this.fechaVencimiento;
	}
	public double getDescuento() {
		return this.descuento;
	}
	public boolean getUtilizado() {
		return this.utilizado;
	}
	public void setUtilizado(boolean value) {
		this.utilizado=value;
	}
}
