package terceraPracticaCarlosGutierrez;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;



public class Servidor 
{

	private static boolean llamadaHecha;
	private static ArrayList<Socket> todosLosClientes = new ArrayList<>();
	private static ServerSocket ssocket;

	public static void main(String[] args) 
	{
		
		
		
		llamadaHecha = false;
		try 
		{
			ssocket = new ServerSocket(20000);
		} catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		Socket socket;
		while(true) 
		{
			try
			{
				socket = ssocket.accept();
				todosLosClientes.add(socket);
			} catch (Exception e){}
			if(todosLosClientes.size() > 0 && !llamadaHecha) 
			{
				inicializar_timer();
			}
		}	
	}

	private static void inicializar_timer() 
	{
		Timer timer = new Timer();
		TimerTask tareaTask = new TimerTask() 
		{
			
			@Override
			public void run() 
			{
				crearYEjecutarHilosAsignadorCreador(todosLosClientes);	
				llamadaHecha = false;
			}

		};
		
		timer.schedule(tareaTask, 10000);	
		llamadaHecha = true;
	}

	private static void crearYEjecutarHilosAsignadorCreador(ArrayList<Socket> todosLosClientes) 
	{
		DatosHilocreadorConCreador datos = new DatosHilocreadorConCreador();
		Thread HiloAsignador = new Thread( new HiloAsignador(todosLosClientes, datos));
		Thread hiloCreador = new Thread(new HiloCreador(datos));
		hiloCreador.start();
		HiloAsignador.start();
	}

}
