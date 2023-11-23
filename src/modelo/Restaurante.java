package modelo;

import java.util.ArrayList;
import java.util.List;

import excepciones.IngredienteRepetidoException;
import excepciones.ProductoRepetidoException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Restaurante
{
	private List<Combo> combos;
	private List<Pedido> pedidos;
	private List<ProductoMenu> menuBase;
	private List<Ingrediente> ingredientes;
	private List<Bebida> bebidas;
	private Pedido pedidoEnCurso;
	public Restaurante(){
		this.combos= new ArrayList<Combo>();
		this.pedidos= new ArrayList<Pedido>();
		this.menuBase= new ArrayList<ProductoMenu>();
		this.ingredientes= new ArrayList<Ingrediente>();
		this.bebidas= new ArrayList<Bebida>();

	}
	public void cargarInformacionRestaurante()
	{
		try {
			cargarIngredientes();
		} catch (IngredienteRepetidoException e) {
		}
		try {
			cargarMenu();
		} catch (ProductoRepetidoException e) {
		}
		try {
			cargarBebidas();
		} catch (ProductoRepetidoException e) {
		}
		try {
			cargarCombos();
		} catch (ProductoRepetidoException e) {
		}
	}
	public Restaurante getRestaurante() {
		return this;
	}
	public void IniciarPedido(String nombreCliente, String direccionCliente) {
		this.pedidoEnCurso=new Pedido(nombreCliente,direccionCliente);
	}
	public Pedido getPedidoEnCurso() {
		if (pedidoEnCurso != null) {
			return this.pedidoEnCurso;
		} else {
			return null;
		}
	}
	public String consultarPedido(List<Pedido> pedidosRestaurante,int id) {
		String info=null;
		for (Pedido pedidoAct: pedidosRestaurante) {
		if (pedidoAct.getIdPedido()==id) {
			info=pedidoAct.generarTextoFactura();
			return info;
		}
	}
		return info;
	}
	public void cerrarYGuardarPedido() {
		pedidos.add(this.pedidoEnCurso);
		pedidoEnCurso=null;
	}
	public List<Ingrediente> getIngredientes(){
		return ingredientes;
	}
	public List<ProductoMenu> getMenuBase(){
		return menuBase;
	}
	public List<Bebida> getBebidas(){
		return bebidas;
	}
	public List<Combo> getCombos(){
		return combos;
	}
    private void cargarIngredientes() throws IngredienteRepetidoException {
        try (BufferedReader br = new BufferedReader(new FileReader("./data/ingredientes.txt"))) {
        	String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 3) {
                    String nombre = partes[0];
                    int precio = Integer.parseInt(partes[1]);
                    int calorias = Integer.parseInt(partes[2]);
                    Ingrediente ingredienteActual = new Ingrediente(nombre,precio,calorias);
                    // Verificar si el ingrediente ya existe antes de agregarlo
                    boolean existeIngrediente=false;
                    for (Ingrediente i: getIngredientes()) {
                    	if (i.getNombre().equals(nombre)){
                    		existeIngrediente=true;
                    		break;
                    	}
                    }
                    if (!existeIngrediente) {
                        ingredientes.add(ingredienteActual);
                    } else {
                        // Lanzar excepción si el ingrediente ya está repetido
                        throw new IngredienteRepetidoException("Ingrediente repetido: " + ingredienteActual.getNombre(), ingredienteActual);
                    }
                } else {
                    System.out.println("Formato incorrecto en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    private void cargarMenu() throws ProductoRepetidoException	{
        try (BufferedReader br = new BufferedReader(new FileReader("./data/menu.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 3) {
                    String nombre = partes[0];
                    int precio = Integer.parseInt(partes[1]);
                    int calorias = Integer.parseInt(partes[2]);
                    ProductoMenu productoActual = new ProductoMenu(nombre,precio,calorias);
                    // Verificar si el producto ya existe antes de agregarlo
                    boolean existeProducto=false;
                    for (Producto i: getMenuBase()) {
                    	if (i.getNombre().equals(nombre)){
                    		existeProducto=true;
                    		break;
                    	}
                    }
                    try {
                        if (!existeProducto) {
                            menuBase.add(productoActual);
                        } else {
                            // Lanzar excepción si el producto ya está repetido
                            throw new ProductoRepetidoException("Producto repetido: " + productoActual.getNombre(), productoActual);
                        }
                    } 
                    catch (ProductoRepetidoException ex) {
                        System.out.println(ex.getMessage());
                    } 
                } else {
                    System.out.println("Formato incorrecto en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    private void cargarBebidas() throws ProductoRepetidoException {
        try (BufferedReader br = new BufferedReader(new FileReader("./data/bebidas.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 3) {
                    String nombre = partes[0];
                    int precio = Integer.parseInt(partes[1]);
                    int calorias = Integer.parseInt(partes[2]);
                    Bebida bebidaActual = new Bebida(nombre,precio,calorias);
                 // Verificar si el producto ya existe antes de agregarlo
                    boolean existeProducto=false;
                    for (Producto i: getBebidas()) {
                    	if (i.getNombre().equals(nombre)){
                    		existeProducto=true;
                    		break;
                    	}
                    }
                    try {
                        if (!existeProducto) {
                            bebidas.add(bebidaActual);
                        } else {
                            // Lanzar excepción si el producto ya está repetido
                            throw new ProductoRepetidoException("Bebida repetida: " + bebidaActual.getNombre(), bebidaActual);
                        }
                    } 
                    catch (ProductoRepetidoException ex) {
                        System.out.println(ex.getMessage());
                    }               
                } else {
                    System.out.println("Formato incorrecto en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
    private void cargarCombos() throws ProductoRepetidoException {
        try (BufferedReader br = new BufferedReader(new FileReader("./data/combos.txt"))) {
        	///combo corral;10%;corral;papas medianas;gaseosa
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length > 2) {
                    String nombre = partes[0];
                    String str_dto=partes[1];
                    String num_dto=str_dto.replace("%", "").trim();
                    int porcentaje_dto = Integer.parseInt(num_dto);
                    double dto= porcentaje_dto*0.01;
                    Combo comboActual = new Combo(nombre,dto);
                    String str_item1=partes[2];
                    String str_item2=partes[3];
                    String str_item3=partes[4];
                    for (ProductoMenu productoActual : menuBase) {
                    	if ((productoActual.getNombre().equals(str_item1)||(productoActual.getNombre().equals(str_item2)||(productoActual.getNombre().equals(str_item3))))){
                    		comboActual.agregarItemACombo(productoActual);
                    	}
                    }
                    for (Bebida productoActual : bebidas) {
                    	if ((productoActual.getNombre().equals(str_item1)||(productoActual.getNombre().equals(str_item2)||(productoActual.getNombre().equals(str_item3))))){
                    		comboActual.agregarItemACombo(productoActual);
                    	}
                    }
                    
                    // Verificar si el producto ya existe antes de agregarlo
                    boolean existeProducto=false;
                    for (Producto i: getCombos()) {
                    	if (i.getNombre().equals(nombre)){
                    		existeProducto=true;
                    		break;
                    	}
                    }
                    try {
                        if (!existeProducto) {
                            combos.add(comboActual);
                        } else {
                            // Lanzar excepción si el producto ya está repetido
                            throw new ProductoRepetidoException("Producto repetido: " + comboActual.getNombre(), comboActual);
                        }
                    } 
                    catch (ProductoRepetidoException ex) {
                        System.out.println(ex.getMessage());
                    }
                    
                } else {
                    System.out.println("Formato incorrecto en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}
