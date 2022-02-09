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
		crear_ssocket();
		Socket socket;
		while(true) 
		{
			try
			{
				socket = ssocket.accept();
				todosLosClientes.add(socket);
			} catch (Exception e){}
			if(!llamadaHecha) //Esta booleana está puesta para que solo se ejecute otra vez el timer hasta que no se haya hecho ya el anterior
				inicializar_timer();
		}	
	}

	private static void crear_ssocket() {
		try 
		{
			ssocket = new ServerSocket(20000);
		} catch (IOException e1) 
		{
			e1.printStackTrace();
		}
	}

	//Este método crea un timer y un timerTask que se ejecutará 10 segundos después de recibir la priemra llamada al servidor. 
	//Y creara el hilo asignador y el creador, con este método en principio puedes ejecutar todas las veces que quieras el programa
	//ya que cada vez que pasan 10 seg desde una llamda hace los calculos para esos 'trabajadores', borra y se vuelve a preparar.
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
		llamadaHecha = true;//se hace true para que no se ejecute más veces este método hasta que no haya ejecutado el método run()
	}
	//Este método crea los hilos asignador y creadar, espera a que mueran y limpia el arrayList del servidor para que se puedan volver a hacer llamadas y funcione correctamente
	private static void crearYEjecutarHilosAsignadorCreador(ArrayList<Socket> todosLosClientes) 
	{
		DatosHilocreadorConCreador datos = new DatosHilocreadorConCreador();
		Thread HiloAsignador = new Thread( new HiloAsignador(todosLosClientes, datos));
		Thread hiloCreador = new Thread(new HiloCreador(datos));
		hiloCreador.start();
		HiloAsignador.start();
		
		try {
			hiloCreador.join();
			HiloAsignador.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		todosLosClientes.clear();
	}

}
