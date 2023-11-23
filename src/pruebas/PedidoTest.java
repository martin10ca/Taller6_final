package pruebas;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import excepciones.PedidoExcedeValorException;
import modelo.*;

class PedidoTest {
    private Pedido pedido;
    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        // Crear un producto para las pruebas
		restaurante= new Restaurante();
		restaurante.cargarInformacionRestaurante();
		pedido= new Pedido("Nombre Ejemplo","Direccion Ejemplo");
    }
    @Test
    @DisplayName("Pedido: Agregar productos -> chequeo de no pasarse de 150000Cop")
    void testAgregarProductos() {
    	for (int i=0; i<=20;i++) {
    	try {
    	pedido.agregarProducto(restaurante.getMenuBase().get(0));
    	assertTrue(pedido.getPrecioNetoPedido()<=150000,"Precio dentro de rango");
    	}
    	catch (PedidoExcedeValorException ex){
    		ex.getStackTrace();
    		i=20;
    	}
    	}
    }
    @Test
    @DisplayName("Pedido: Generar Factura")
    void testTextoFactura() throws PedidoExcedeValorException {
    	pedido.agregarProducto(restaurante.getMenuBase().get(0));
    	pedido.agregarProducto(restaurante.getMenuBase().get(1));
        String textoFactura = pedido.generarTextoFactura();
        assertNotNull(textoFactura);
    }
    @Test
    @DisplayName("Pedido: Guardar Factura")
    void testGuardarFactura() throws PedidoExcedeValorException {
    	pedido.agregarProducto(restaurante.getMenuBase().get(0));
    	pedido.agregarProducto(restaurante.getMenuBase().get(1));
        pedido.guardarFactura();
        //Se guarda archivo
        try {
        File file = new File("Facturas/" + pedido.getIdPedido() + ".txt");
        FileWriter writer = new FileWriter(file);
        writer.write(pedido.generarTextoFactura());
        writer.close();
        //Archivo con el que vamos a comparar
        String pathFactura = "Facturas/" + pedido.getIdPedido() + ".txt";
        File facturaFile = new File(pathFactura);
        assertTrue(facturaFile.exists());
        } catch (IOException e) {
        	fail("Error al guardar archivo");
    }
    }
    
}

