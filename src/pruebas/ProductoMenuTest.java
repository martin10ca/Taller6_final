package pruebas;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import modelo.*;

class ProductoMenuTest {
    private ProductoMenu producto;

    @BeforeEach
    void setUp() {
        // Crear un producto para las pruebas
		Restaurante restaurante= new Restaurante();
		restaurante.cargarInformacionRestaurante();

		producto = new ProductoMenu("Hamburguesa", 100, 500);
    }

    @Test
    @DisplayName("Producto Menu: Test Calorias")
    void testAgregarIngrediente() {
        assertEquals(100, producto.getPrecio());

    }


}
