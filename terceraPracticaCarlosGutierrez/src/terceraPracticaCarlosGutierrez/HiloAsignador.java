package terceraPracticaCarlosGutierrez;

import java.net.Socket;
import java.util.ArrayList;

public class HiloAsignador implements Runnable 
{
	private DatosHilocreadorConCreador datos;
	private ArrayList<Socket> sockets = new ArrayList<>();
	private ArrayList<Thread> todosLosClientes = new ArrayList<>(); 
	private HiloCliente [] hilos; 
	private Tarea tarea;
	private DatosAsignadorConClientes datosAsignadorConClientes;
	
	public HiloAsignador(ArrayList<Socket> sockets, DatosHilocreadorConCreador datos) 
	{
		this.datos = datos;
		this.sockets = sockets;
		this.tarea = null;
	}

	@Override
	public void run() 
	{
		inicializarClientes();
		while(!datosAsignadorConClientes.fin())//Este bucle se ejecuta hasta que queden hilos hijos 
		{
			tarea = datos.consumidor();
			datosAsignadorConClientes.productor(tarea);
		}
		datos.setFin();//llamo al método de la clase datos que será necesario para que se apage el hilo Creador
		System.out.println("Cierro mi comunicación con el hilo");
	}

	
	//Por cada sockets defindio creo un hilo que se comunicará con el cliente , además llamo al constructor de la clase
	//datosAsigandorConClientes que sea una clase auxiliar entre este hilo asignador y sus hilos "hijos" con un método productor y otro consumidor
	private void inicializarClientes() 
	{
		datosAsignadorConClientes = new DatosAsignadorConClientes(sockets.size());
		hilos = new HiloCliente [sockets.size()];
		for(int i = 0; i < hilos.length; ++i)
			hilos[i] = new HiloCliente(sockets.get(i), datosAsignadorConClientes);
		for(int i = 0; i < hilos.length; ++i)
		{
			todosLosClientes.add(new Thread(hilos[i]));
			todosLosClientes.get(i).setName(String.valueOf(i + 1));
			todosLosClientes.get(i).start();		
		}
	}
	
	
}
