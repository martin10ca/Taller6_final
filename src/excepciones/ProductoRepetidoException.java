package excepciones;

import modelo.Producto;

public class ProductoRepetidoException extends HamburguesaException {
    private Producto productoRepetido;

    public ProductoRepetidoException(String mensaje, Producto producto) {
        super(mensaje);
        this.productoRepetido = producto;
    }

    public Producto getProductoRepetido() {
        return productoRepetido;
    }
    public String getMessage() {
        return ("Producto Repetido: "+productoRepetido.getNombre());
    }
}


