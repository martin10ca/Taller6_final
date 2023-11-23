package excepciones;

import modelo.Producto;

public class PedidoExcedeValorException extends Exception {
	private Producto productoQueExcede;
    public PedidoExcedeValorException(String mensaje, Producto prod) {
        super(mensaje);
        this.productoQueExcede= prod;
    }
    public Producto getProductoExcedeValor() {
    	return productoQueExcede;
    }
}
