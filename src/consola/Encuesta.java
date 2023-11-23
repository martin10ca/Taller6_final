package consola;

public class Encuesta {
	private String nombreCliente;
	private String mailCliente;
	private int puntuacionLikert;
	private String comments;
	public Encuesta(String nombreCliente,String mailCliente, int puntuacionLikert,String comments) {
		this.nombreCliente=nombreCliente;
		this.mailCliente=mailCliente;
		this.puntuacionLikert=puntuacionLikert;
		this.comments=comments;
	}
	public int getPuntuacion() {
		return this.puntuacionLikert;
	}
}
