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
		while(!datosAsignadorConClientes.fin()) 
		{
			tarea = datos.consumidor();
			datosAsignadorConClientes.productor(tarea);
		}
		datos.setFin();
		System.out.println("Cierro mi comunicación con el hilo");
	}

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
