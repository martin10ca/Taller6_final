package consola;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import excepciones.PedidoExcedeValorException;
import modelo.Pedido;
import modelo.Producto;
import modelo.Restaurante;
import modelo.Bebida;
import modelo.Combo;
import modelo.Ingrediente;
import modelo.ProductoAjustado;
import modelo.ProductoMenu;
public class ConsolaCorral
{
	/**
	 * Esta es la calculadora de estadísticas que se usará para hacer todas las
	 * operaciones de la aplicación. Esta calculadora también contiene toda la
	 * información sobre los atletas después de que se cargue desde un archivo.
	 */
	private Restaurante restaurante;
	private List<Cupon> cupones=new ArrayList<Cupon>();
	private List<Encuesta> encuestas= new ArrayList<Encuesta>();


	/**
	 * Ejecuta la aplicación: le muestra el menú al usuario y la pide que ingrese
	 * una opción, y ejecuta la opción seleccionada por el usuario. Este proceso se
	 * repite hasta que el usuario seleccione la opción de abandonar la aplicación.
	 */
	public void ejecutarAplicacion(){		
		System.out.println("Bienvenido al Corral!(MCed)\n");
		List<Combo> combos = new ArrayList<Combo>();
		List<ProductoMenu> productos=new ArrayList<ProductoMenu>();
		List<Ingrediente> ingredientes=new ArrayList<Ingrediente>();
		List<Pedido> pedidos=new ArrayList<Pedido>();
		List<Bebida> bebidas=new ArrayList<Bebida>();
		boolean info_cargada=false;
		boolean continuar = true;
		while (continuar)
		{
			try
			{
				System.out.println("\nOpciones de la aplicación\n");
				System.out.println("1. Abrir restaurante");
				System.out.println("2. Nuevo pedido");
				System.out.println("3. Consultar pedido por ID");
				System.out.println("4. Salir de la aplicación\n");
				int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
				if (opcion_seleccionada==1) {
					restaurante= new Restaurante();
					restaurante.cargarInformacionRestaurante();
					productos=restaurante.getMenuBase();
					ingredientes=restaurante.getIngredientes();
					bebidas=restaurante.getBebidas();
					combos=restaurante.getCombos();
					cargarCupones();
					System.out.println(">>> Info Restaurante disponible");
					info_cargada=true;
				}
				if (opcion_seleccionada == 2 && info_cargada==true) {
					
					String nomCliente= input("Indique su nombre");
					String direcCliente= input("Indique su dirección");
					Pedido pedido= new Pedido(nomCliente, direcCliente);
					
					int tiene_cupon=Integer.parseInt(input("Tiene un cupon de descuento vigente?\n1.Si\n2.No\n"));
					if (tiene_cupon==1) {
						String nomCupon=input("Ingrese su cupón");
						int pos_cupon=encontrarCupon(nomCupon);
						if (pos_cupon>=0) {
							Cupon cuponU= cupones.get(pos_cupon);
							if (verificarCupon(cuponU)==true) {
								cuponU.setUtilizado(true);
								eliminarCuponDeTXT(nomCupon);
								pedido.setDescuento(cuponU.getDescuento());
								System.out.println("Felicidades! Tienes un descuento del "+Double.toString(cuponU.getDescuento())+" sobre tu pedido.");
							}
							else {
								System.out.println("Tu cupón no es válido :c");

							}
							
						}
						else {
							System.out.println("Tu cupón no es válido :c");
						}
					}

					boolean comprando=true;
					while(comprando) {
						mostrarMenu();
						
						int opcion = Integer.parseInt(input("Por favor seleccione una opción"));
						if (opcion<5){
							pedido.agregarProducto(combos.get(opcion-1));
						} 
						else if (opcion<=23){
							int ajustarProd=Integer.parseInt(input("Desea personalizar el producto?\n1.Si\n2.No\n"));
							if (ajustarProd==1) {
								Boolean ajustando=true;
								ProductoAjustado Prod=new ProductoAjustado(productos.get(opcion-5));
								while (ajustando==true) {
									int numOpcion=1;
									System.out.println("\n\t INGREDIENTES:\n");
									for (Ingrediente ingAct:ingredientes) {
										String nomIng= ingAct.getNombre();
										int precioIng= ingAct.getCostoAdicional();
										System.out.println(Integer.toString(numOpcion)+": "+nomIng+" ("+Integer.toString(precioIng)+" cop)");
										numOpcion+=1;
									}
									int eleccion=Integer.parseInt(input("Señale el ingrediente que desea agregar/quitar: \n>"));
									int quitar_o_poner= Integer.parseInt(input("Desea agregar o quitar el ingrediente seleccionado?\n1. Agregar\n2. Quitar\n"));
									if (eleccion>15) {
										System.out.println("Elija una opción valida");								}
									else if (quitar_o_poner==1) {
										Prod.agregarIngrediente(ingredientes.get(eleccion-1));
									}
									else {
										Prod.eliminarIngrediente(ingredientes.get(eleccion-1));
									}
									int seguirAjustando=Integer.parseInt(input("Desea seguir personalizando el producto?\n1.Sí\n2.No\n"));
									if (seguirAjustando!=1) {
										ajustando=false;
									}
								}
								pedido.agregarProducto(Prod);
							}
							else if (ajustarProd==2){
								pedido.agregarProducto(productos.get(opcion-5));
							}
							else {
								System.out.println("Elija una opción válida");
							}
						}
						else if (opcion<=26){
							pedido.agregarProducto(bebidas.get(opcion-24));
						} 
						int seguirComprando=Integer.parseInt(input("Desea agregar otro producto al pedido?\n1.Si\n2.No\n>"));
						if (seguirComprando==2) {
							comprando=false;
							System.out.println("Antes de finalizar su pedido, por favor califique su experiencia de compra");
							int puntuacionLikert=Integer.parseInt(input("5.Muy satisfactoria\n4.Satisfactoria\n3.Suficiente\n2.Mala\n1.Muy mala\n"));
							String comentarios=input("Comentarios");
							String correo=input("Dejanos tu correo electronico para recibir cupones de descuento! ");

							Encuesta encuestaAct=new Encuesta(nomCliente,correo,puntuacionLikert,comentarios);
							encuestas.add(encuestaAct);
						}
						}
					restaurante.cerrarYGuardarPedido();
					pedido.guardarFactura();
					sortPedido(pedido);
					pedidos.add(pedido);
					checkPedidosRepetidos(pedidos);
					System.out.println("Gracias por tu compra!");
					resumenVentas();
							
						}
				else if (opcion_seleccionada == 3&& info_cargada==true)
				{
					int id=Integer.parseInt(input("Cual es la ID del pedido que desea buscar?"));
				    String info=restaurante.consultarPedido(pedidos,id);
				    if (info!=null) {
				    	System.out.println("El pedido con el ID "+Integer.toString(id)+" se muestra a continuación");
						System.out.print(info);
						}
					else{
						System.out.println("\n\tNingún pedido tiene el ID buscado");					
					}
				}
				else if (opcion_seleccionada == 4)
				{
					System.out.println("Saliendo de la aplicación ...");
					continuar = false;
				}

				else
				{
					System.out.println("Por favor seleccione una opción válida y no olvide cargar la información del restaurante.");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println("Debe seleccionar uno de los números de las opciones y cargar la información del restaurante.");
			} catch (PedidoExcedeValorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public void mostrarMenu()
	{
		System.out.println("\n\t COMBOS:\n");
		List<Combo> Combos= restaurante.getCombos();
		int numOpcion=1;
		for (Combo comboAct:Combos) {
			String nomCombo= comboAct.getNombre();
			double precioCombo=comboAct.getPrecio();
			System.out.println(Integer.toString(numOpcion)+": "+nomCombo+" ("+Double.toString(precioCombo)+" cop)");
			numOpcion+=1;
		}
		System.out.println("\n\t PRODUCTOS:\n");
		List<ProductoMenu> menuBase=restaurante.getMenuBase();
		for (ProductoMenu prodAct:menuBase) {
			String nomProd= prodAct.getNombre();
			double precioProd=prodAct.getPrecio();
			System.out.println(Integer.toString(numOpcion)+": "+nomProd+" ("+Double.toString(precioProd)+" cop)");
			numOpcion+=1;
		}
		System.out.println("\n\t BEBIDAS:\n");
		List<Bebida> bebidas=restaurante.getBebidas();
		for (Bebida bebidaAct:bebidas) {
			String nomProd= bebidaAct.getNombre();
			double precioProd=bebidaAct.getPrecio();
			System.out.println(Integer.toString(numOpcion)+": "+nomProd+" ("+Double.toString(precioProd)+" cop)");
			numOpcion+=1;
		}

	}
	public void resumenVentas()
	{
		double sumatoria=0;
		
		for (Encuesta encuestaAct: encuestas) {
			sumatoria+=encuestaAct.getPuntuacion();
		}
		double promedio=sumatoria/encuestas.size();
		String p1="----------------------RESUMEN VENTAS--------------------";
		String p2="Pedidos realizados: "+Integer.toString(encuestas.size())+". Promedio encuestas: "+Double.toString(promedio);
		String p3="--------------------------------------------------------";

		System.out.println(p1+"\n"+p2+"\n"+p3);
	}
	public void sortPedido(Pedido pedido) {
		Collections.sort(pedido.getItemsPedido(), new Comparator<Producto>() {
		    public int compare(Producto p1, Producto p2) {
		        return p1.getNombre().compareTo(p2.getNombre());
		    }
		});
	}
	public void checkPedidosRepetidos(List<Pedido> pedidos) {
		int idRepetido=-1;
		Pedido lastPedido=pedidos.get(pedidos.size()-1);
		List<Producto> itemsLastPedido=lastPedido.getItemsPedido();
		for (Pedido pedidoAct:pedidos) {
			if ((pedidoAct.getIdPedido()!=lastPedido.getIdPedido())&&(pedidoAct.getItemsPedido().size()==itemsLastPedido.size())) {
				boolean esIdentico=true;
				int pos=0;
				for (Producto items: pedidoAct.getItemsPedido()) {
					if (!items.getNombre().equals(itemsLastPedido.get(pos).getNombre())) {
						esIdentico=false;
						break;
					}
					else {
						pos+=1;
					}			
				}	
				if (esIdentico==true) {
					idRepetido=pedidoAct.getIdPedido();
					break;
				}
			}
			
		}
		if (idRepetido>=0) {
		System.out.println(">>>Este pedido es idéntido al pedido de ID: "+Integer.toString(idRepetido));
		}
		else {
			System.out.println(">>>No hay ningún pedido idéntico al último");
		}
	}
	private void eliminarCuponDeTXT(String lineaAEliminar) {
	    String archivoOriginal = "./data/Cupones.txt";
	    String archivoTemporal = "./data/CuponesTemp.txt";

	    try (BufferedReader br = new BufferedReader(new FileReader(archivoOriginal));
	         BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTemporal))) {
	        String linea;
	        while ((linea = br.readLine()) != null) {
	            if (!linea.contains(lineaAEliminar)) {
	                bw.write(linea);
	                bw.newLine();
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    File archivoOriginalFile = new File(archivoOriginal);
	    archivoOriginalFile.delete();
	    File archivoTemporalFile = new File(archivoTemporal);
	    if (archivoTemporalFile.renameTo(archivoOriginalFile)) {
	        System.out.println(">>>La línea se eliminó correctamente en el archivo original.");
	    } else {
	        System.err.println(">>>No se pudo eliminar la línea en el archivo original.");
	    }
	}


    private void cargarCupones() {
    	//ABCD1234;10%;2023-11-02;false
        try (BufferedReader br = new BufferedReader(new FileReader("./data/Cupones.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 4) {
                    String nombre = partes[0];
                    String str_dto=partes[1];
                    String num_dto=str_dto.replace("%", "").trim();
                    int porcentaje_dto = Integer.parseInt(num_dto);
                    double dto= porcentaje_dto*0.01;
                    String fecha=partes[2];
                    boolean utilizado=Boolean.parseBoolean(partes[3]);
                    Cupon cuponAct=new Cupon(fecha,nombre,dto,utilizado);
                    cupones.add(cuponAct);
                    //System.out.println("Primero: " + primero + ", Segundo: " + segundo);
                } else {
                    System.out.println("Formato incorrecto en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

    private int encontrarCupon(String Strcupon) {
        int pos = 0;
        for (Cupon cuponAct : cupones) {
            if (cuponAct.getNomCupon().equals(Strcupon)) {
                return pos;
            }
            pos+=1;
        }
        
        return -1; 
    }

    private boolean verificarCupon(Cupon cuponAct) {
    	String fechaCupon=cuponAct.getFecha();
       	LocalDate fechaActual = LocalDate.now();
   	   	String[] partes= fechaCupon.split("-");
    	LocalDate fechaEspecifica = LocalDate.of(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]),Integer.parseInt(partes[2]));
        if ((fechaEspecifica.isAfter(fechaActual)||fechaEspecifica.isEqual(fechaActual))&&(cuponAct.getUtilizado()==false)){
           	return true;
    	}
    	else {
          	return false;
   	        }
    }
	/**
	 * Le muestra el usuario el porcentaje de atletas que son medallistas
	 */

	/**
	 * Este método sirve para imprimir un mensaje en la consola pidiéndole
	 * información al usuario y luego leer lo que escriba el usuario.
	 * 
	 * @param mensaje El mensaje que se le mostrará al usuario
	 * @return La cadena de caracteres que el usuario escriba como respuesta.
	 */
	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Este es el método principal de la aplicación, con el que inicia la ejecución
	 * de la aplicación
	 * 
	 * @param args Parámetros introducidos en la línea de comandos al invocar la
	 *             aplicación
	 */
	public static void main(String[] args)
	{
		ConsolaCorral consola = new ConsolaCorral();
		consola.ejecutarAplicacion();
	}

}
