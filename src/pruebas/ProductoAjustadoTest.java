package pruebas;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import modelo.*;

class ProductoAjustadoTest {
    private ProductoMenu productoBase;
    private ProductoAjustado productoAjustado;

    @BeforeEach
    void setUp() {
        // Crear un producto base para las pruebas
		Restaurante restaurante= new Restaurante();
		restaurante.cargarInformacionRestaurante();

        productoBase = new ProductoMenu("Hamburguesa", 100, 500);
        productoAjustado = new ProductoAjustado(productoBase);
    }

    @Test
    @DisplayName("Producto Ajustado: Test Agregar Ingredientes -> verificación al contar calorías")
    void testAgregarIngrediente() {
        Ingrediente nuevoIngrediente = new Ingrediente("Queso", 10, 50);

        productoAjustado.agregarIngrediente(nuevoIngrediente);

        assertEquals(550, productoAjustado.getCalorias());
    }

    @Test
    @DisplayName("Producto Ajustado: Test Eliminar Ingredientes -> verificación al contar calorías")
    void testEliminarIngrediente() {
        Ingrediente ingredienteEliminado = new Ingrediente("Pepinillos", 5, 20);

        productoAjustado.eliminarIngrediente(ingredienteEliminado);

        assertEquals(480, productoAjustado.getCalorias());
    }

    @RepeatedTest(5)
    @DisplayName("Producto Ajustado: Test Verificar Precio")
    void testGetPrecio() {
        Ingrediente ingrediente1 = new Ingrediente("Tomate", 8, 25);
        Ingrediente ingrediente2 = new Ingrediente("Cebolla", 6, 30);

        productoAjustado.agregarIngrediente(ingrediente1);
        productoAjustado.agregarIngrediente(ingrediente2);

        // Precio base (100.0) + Precio de los ingredientes agregados (8.0 + 6.0)
        assertEquals(114.0, productoAjustado.getPrecio());
    }

}
