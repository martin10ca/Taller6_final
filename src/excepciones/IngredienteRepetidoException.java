package excepciones;

import modelo.Ingrediente;

public class IngredienteRepetidoException extends HamburguesaException {
    private Ingrediente ingredienteRepetido;

    public IngredienteRepetidoException(String mensaje, Ingrediente ingrediente) {
        super(mensaje);
        this.ingredienteRepetido = ingrediente;
    }

    public Ingrediente getIngredienteRepetido() {
        return ingredienteRepetido;
    }
    public String getMessage() {
        return ("Ingrediente Repetido: "+ingredienteRepetido.getNombre());
    }
    
}


