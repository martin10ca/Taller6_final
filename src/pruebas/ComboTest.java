package pruebas;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import modelo.Bebida;
import modelo.Combo;
import modelo.Producto;
import modelo.Restaurante;

class ComboTest {
    private Combo combo;
    private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        // Crear un producto para las pruebas
		restaurante= new Restaurante();
		restaurante.cargarInformacionRestaurante();
		

		combo = new Combo("ComboPrueba1",1);
    }

    @Test
    @DisplayName("Combo: Agregar productos (p1) -> verificación al contar calorías")
    void testAgregarIngrediente1() {
    	Producto producto1= restaurante.getMenuBase().get(0);
    	Producto producto2= restaurante.getBebidas().get(restaurante.getBebidas().size()-1);
    	combo.agregarItemACombo(producto1);
    	combo.agregarItemACombo(producto2);
        assertEquals(950, combo.getCalorias());

    }
    @Test
    @DisplayName("Combo: Agregar productos (p2) -> verificación al contar calorías")
    void testAgregarIngrediente2() {
    	int caloriasTotales=0;
    	for (Producto i: restaurante.getMenuBase()) {
    		combo.agregarItemACombo(i);
    		caloriasTotales+=i.getCalorias();
    	}
    	for (Bebida i: restaurante.getBebidas()) {
    		combo.agregarItemACombo(i);
    		caloriasTotales+=i.getCalorias();
    	}
        assertEquals(caloriasTotales, combo.getCalorias());

    }
}

